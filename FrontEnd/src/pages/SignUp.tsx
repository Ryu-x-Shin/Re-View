import { useCallback, useReducer } from 'react';
import AuthFormLayout from '../components/AuthFormLayout';
import EmailAuthFormLayout from '../components/EmailAuthFormLayout';
import LoginForm from '../components/LoginForm';
import styles from './SignUp.module.scss';

type State = {
  password: string;
  passwordRepeat: string;
};

type Action = { type: 'password' | 'passwordRepeat'; value: string };

const reducer = (state: State, action: Action): State => ({
  ...state,
  [action.type]: action.value,
});

const SignUp = () => {
  const [state, dispatch] = useReducer(reducer, {
    password: '',
    passwordRepeat: '',
  });
  const handlePasswordChange = useCallback(
    (value: string) => dispatch({ type: 'password', value }),
    [],
  );
  const handlePasswordRepeatChange = useCallback(
    (value: string) => dispatch({ type: 'passwordRepeat', value }),
    [],
  );

  const components = (
    <>
      <EmailAuthFormLayout />
      <LoginForm
        className={styles.component}
        label="Password"
        value={state.password}
        onChange={handlePasswordChange}
      />
      <LoginForm
        className={styles.component}
        label="Password 재입력"
        value={state.passwordRepeat}
        onChange={handlePasswordRepeatChange}
      />

      <div >
        <span >NickName</span>
        <div style={{ display: 'flex' }}>
          <input
            className={styles.input}
            style={{ display: 'inline-block' }}
            // className={styles['input']}
            // value={props.value}
            // onChange={(e) => props.onChange(e.target.value)}
          />
          <button className={styles.btn}>중복 확인</button>
        </div>
      </div>

      <button className={styles.btn}>회원 가입</button>
    </>
  );
  return <AuthFormLayout label="Register" components={components} />;
};

export default SignUp;
