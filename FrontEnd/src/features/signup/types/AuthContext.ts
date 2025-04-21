import { AuthAction } from '../hooks/AuthReducer';
import AuthState from './AuthState';

export default interface AuthContext extends AuthState {
  dispatch: React.ActionDispatch<[action: AuthAction]>;
}
