import IdCheckedState from '../types/IdCheckedState';

export type IdAction = { type: 'id/verified' };

const idReducer = (state: IdCheckedState, action: IdAction) => {
  switch (action.type) {
    case 'id/verified':
      return { ...state, isIdChecked: true };

    default:
      return { ...state };
  }
};

export default idReducer;
