import { z, ZodSchema } from 'zod';

// 성공했을 때 전달받을 값
export interface ApiResponse<T> {
  data: T;
  message?: string;
}

export const ApiErrorSchema: ZodSchema<ApiError> = z.object({
  status: z.number(),
  message: z.string(),
  details: z.any().optional(),
});

// return 했을 때 에러가 나면 서버로부터 전달받을 값
export interface ApiError {
  status: number;
  message: string;
  details?: any;
}

//
export type Result<T> = { ok: true; data: T } | { ok: false; error: ApiError };
