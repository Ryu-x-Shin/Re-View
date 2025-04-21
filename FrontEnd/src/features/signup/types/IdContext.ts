import { IdAction } from '../hooks/IdReducer';
import IdCheckedState from './IdCheckedState';

export default interface IdContext extends IdCheckedState {
  dispatch: React.ActionDispatch<[action: IdAction]>;
}
