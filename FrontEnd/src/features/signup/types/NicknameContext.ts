import { NicknameAction } from '../hooks/NicknameReducer';
import NicknameCheckedState from './NicknameCheckedState';

export default interface NicknameContext extends NicknameCheckedState {
  dispatch: React.ActionDispatch<[action: NicknameAction]>;
}
