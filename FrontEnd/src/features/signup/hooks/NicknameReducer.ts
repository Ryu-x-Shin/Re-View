import NicknameCheckedState from '../types/NicknameCheckedState';

export type NicknameAction = { type: 'nickname/verified' };

const nicknameReducer = (
  state: NicknameCheckedState,
  action: NicknameAction,
) => {
  switch (action.type) {
    case 'nickname/verified':
      return {
        ...state,
        authState: {
          ...state,
          isNicknameChecked: true,
        },
      };
    default:
      return { ...state };
  }
};

export default nicknameReducer;
