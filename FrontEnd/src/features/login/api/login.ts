import { request } from '../../../lib/api/request';
import { Result } from '../../../types/ApiError';

function login(): Promise<Result<void>> {
  return request<void>({ url: '/users', method: 'get' });
}

export default login;
