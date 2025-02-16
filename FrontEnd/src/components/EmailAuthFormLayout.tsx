import React, { useReducer } from 'react';
import styles from './EmailAuthFormLayout.module.scss';

type State = {
  email: string;
  code: string;
};

type Action = { type: 'email' | 'code'; value: string };

const reducer = (state: State, action: Action): State => ({
  ...state,
  [action.type]: action.value,
});

const EmailAuthForm = () => {
  const [state, dispatch] = useReducer(reducer, { email: '', code: '' });
  return (
    <div>
      <span>Email</span>
      <div style={{ display: 'flex' }}>
        <input
          className={styles.input}
          style={{ display: 'inline-block' }}
          value={state.email}
          onChange={(e) => dispatch({ type: 'email', value: e.target.value })}
        />
        <button>인증 코드 전송</button>
      </div>
      <div style={{ display: 'flex' }}>
        <input
          className={styles.input}
          style={{ display: 'inline-block' }}
          value={state.code}
          onChange={(e) => dispatch({ type: 'code', value: e.target.value })}
        />
        <button>인증</button>
        <span>05:30</span>
      </div>
    </div>
  );
};

export default React.memo(EmailAuthForm);
