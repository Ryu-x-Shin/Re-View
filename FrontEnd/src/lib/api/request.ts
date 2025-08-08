import { ApiError, Result } from '../../types/ApiError';
import client from './AxiosClient';

export async function request<T>(opts: {
  url: string;
  method: 'get' | 'post' | 'put' | 'delete';
  data?: any;
  params?: any;
}): Promise<Result<T>> {
  try {
    const res = await client.request<{ data: Result<T> }>({
      method: opts.method,
      url: opts.url,
      data: opts.data,
      params: opts.params,
    });
    return res.data.data;
  } catch (error) {
    // 이미 interceptor에서 error에 대해서 return한 값이 error 인자로 들어옴
    // 인터셉터에서 reject 시킨 값
    return { ok: false, error: error as ApiError };
  }
}
