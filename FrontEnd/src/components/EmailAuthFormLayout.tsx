import React, { useReducer } from 'react';
import styles from './EmailAuthFormLayout.module.scss';

type LoginProps = {
  className: string;
};

type State = {
  email: string;
  code: string;
};

type Action = { type: 'email' | 'code'; value: string };

const reducer = (state: State, action: Action): State => ({
  ...state,
  [action.type]: action.value,
});

const EmailAuthForm = (props: LoginProps) => {
  const [state, dispatch] = useReducer(reducer, { email: '', code: '' });
  return (
    <div className={props.className}>
      <span>Email</span>
      <div style={{ margin: '8px 0', display: 'flex' }}>
        <input
          className={styles.input}
          style={{ display: 'inline-block' }}
          value={state.email}
          onChange={(e) => dispatch({ type: 'email', value: e.target.value })}
        />
        <button className={`${styles.btn} ${styles['btn-input']}`}>
          인증 코드 전송
        </button>
      </div>
      <div style={{ display: 'flex', marginRight: '140px' }}>
        <input
          className={styles.input}
          style={{ display: 'inline-block' }}
          value={state.code}
          onChange={(e) => dispatch({ type: 'code', value: e.target.value })}
        />
        <button
          className={`${styles.btn} ${styles['btn-input']}`}
          style={{ minWidth: '60px' }}
        >
          인증
        </button>
        <span style={{ display: 'flex', alignItems: 'center' }}>05:30</span>
      </div>
    </div>
  );
};

export default React.memo(EmailAuthForm);
