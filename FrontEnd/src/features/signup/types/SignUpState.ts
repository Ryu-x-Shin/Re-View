import AuthState from './AuthState';
import IdCheckedState from './IdCheckedState';
import NicknameCheckedState from './NicknameCheckedState';

export default interface SignUpState {
  authState: AuthState;
  idState: IdCheckedState;
  nicknameState: NicknameCheckedState;
}
