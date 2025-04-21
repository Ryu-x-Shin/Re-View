import AuthState from '../types/AuthState';

export type AuthAction =
  | { type: 'auth/codeSent' }
  | { type: 'auth/isVerified' }
  | { type: 'auth/timerActive' }
  | { type: 'auth/tick' }
  | { type: 'auth/setIntervalId'; payload: NodeJS.Timeout }
  | { type: 'auth/clearIntervalId' }
  | { type: 'auth/timeOver' };

const signUpReducer = (state: AuthState, action: AuthAction) => {
  switch (action.type) {
    case 'auth/codeSent':
      return {
        ...state,
        isEmailCodeSent: true,
        timerSeconds: 10,
      };
    case 'auth/isVerified':
      return { ...state };
    case 'auth/timerActive':
      return { ...state };
    case 'auth/tick':
      return {
        ...state,
        // timerSeconds: state.timerSeconds - 1,
      };
    case 'auth/setIntervalId':
      return {
        ...state,
        intervalId: action.payload,
      };
    case 'auth/clearIntervalId':
      return { ...state, intervalId: null };

    case 'auth/timeOver':
      // if (state.intervalId) {
      //   clearInterval(state.intervalId);
      // }
      return {
        ...state,
        isEmailCodeSent: false,
        intervalId: null,
      };
    default:
      return { ...state };
  }
};

export default signUpReducer;
