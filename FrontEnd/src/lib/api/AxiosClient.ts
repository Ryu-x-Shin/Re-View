import axios, { AxiosError, AxiosInstance, AxiosRequestConfig } from 'axios';
import { ApiError, ApiErrorSchema } from '../../types/ApiError';
import {
  getAccessToken,
  getRefreshToken,
  removeTokens,
  setAccessToken,
} from './Token';

// 🔁 토큰 갱신 중 여부를 추적하는 플래그
let isRefreshing = false;

// 🔁 갱신 중일 때 실패한 요청들을 잠시 보관하는 큐
let failedQueue: {
  resolve: (token: string) => void;
  reject: (error: AxiosError) => void;
}[] = [];

/**
 * 실패한 요청들을 처리하는 함수
 * @param error - 갱신 중 에러가 발생한 경우 전달
 * @param token - 새로 발급받은 accessToken
 */
const processQueue = (
  error: AxiosError | null,
  token: string | null = null,
) => {
  failedQueue.forEach((prom) => {
    if (error || !token) {
      // 갱신 실패: 대기 중이던 요청들도 모두 실패 처리
      prom.reject(error!);
    } else {
      // 갱신 성공: Authorization 헤더를 갱신하고 요청 재실행
      prom.resolve(token);
    }
  });
  failedQueue = []; // 큐 초기화
};

const client: AxiosInstance = axios.create({
  baseURL: '',
  timeout: 5_000,
  headers: { 'Content-Type': 'application/json' },
});

client.interceptors.request.use((config) => {
  const token = getAccessToken();
  if (token && config.headers) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

client.interceptors.response.use(
  // 성공했을 때는 데이터 그대로 반환
  (res) => res,

  //  통신에 실패했을 때 or 서버에서 400 같은 에러를 던졌을 때 들어오는 곳
  async (err: AxiosError) => {
    const originalRequest = err.config as AxiosRequestConfig & {
      _retry?: boolean;
    };

    // 1. 401 Unauthorized 에러이면서, 아직 재시도하지 않은 요청만 처리
    if (err.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true; // 무한 루프 방지를 위해 플래그 설정

      // 2. 이미 다른 요청이 토큰 갱신 중이라면, 현재 요청은 큐에 넣고 대기
      if (isRefreshing) {
        return new Promise((resolve, reject) => {
          failedQueue.push({
            resolve: (token: string) => {
              // 새 토큰으로 Authorization 헤더 갱신
              originalRequest.headers = {
                ...originalRequest.headers,
                Authorization: `Bearer ${token}`,
              };
              resolve(client(originalRequest)); // 요청 재시도
            },
            reject: (err) => {
              reject(err);
            },
          });
        });
      }

      // 3. 토큰 갱신 시작
      isRefreshing = true;

      try {
        const refreshToken = getRefreshToken();
        if (!refreshToken) {
          throw new Error('No refresh token available');
        }

        // refreshToken을 이용해 새 accessToken 요청
        const refreshResponse = await axios.post(
          'TODO refreshToken 갱신 주소로 변경',
          {
            refreshToken,
          },
        );

        const newAccessToken = refreshResponse.data.accessToken;
        setAccessToken(newAccessToken); // 새 accessToken 저장
        processQueue(null, newAccessToken); // 대기 중이던 요청들 재시도

        // 원래 요청에 새 토큰을 삽입 후 재시도
        originalRequest.headers = {
          ...originalRequest.headers,
          Authorization: `Bearer ${newAccessToken}`,
        };
        return client(originalRequest); // 원래 요청 재실행
      } catch (refreshError: any) {
        processQueue(refreshError, null); // 대기 중 요청들도 모두 실패 처리
        removeTokens(); // 토큰 모두 삭제
        window.location.href = '/login'; // 로그인 페이지로 리디렉션
        return Promise.reject(refreshError);
      } finally {
        isRefreshing = false; // 리프레시 완료
      }
    }

    // Zod의 safeParse를 통해서 해당 데이터가 ApiErrorSchema의 형식과 일치하는지 확인해서 parsed에 반환
    const parsed = ApiErrorSchema.safeParse(err.response?.data);
    // 만약 prased 결과가 success면 해당 데이터를 사용, false면 ApiErrorScheme의 형식에 맞지 않는다는 것이므로, 형식에 맞춰서 객제 생성 후 반환
    const apiError: ApiError = parsed.success
      ? parsed.data
      : {
          status: err.response?.status ?? 500,
          message: err.message,
          details: err.response?.data ?? null,
        };

    console.error('[API_ERROR]', apiError);

    return Promise.reject(apiError);
  },
);

export default client;
