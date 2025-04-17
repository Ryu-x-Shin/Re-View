import IdInputField from './IdInputField';
import PasswordInputField from './PasswordInputField';
import styles from './LoginForm.module.scss';
import LoginButton from './LoginButton';
import SignUpField from './SignUpField';
import FindPasswordField from './FindPasswordField';

const LoginForm = () => {
  return (
    <div>
      <p className={`${styles.label}`}>Re:View</p>
      <form className={styles.form}>
        <IdInputField />
        <PasswordInputField />
        <p className={styles['form__login-init']}>
          ~이유로 로그인에 실패했습니다.
        </p>
        <LoginButton />
        <SignUpField />
        <FindPasswordField />
      </form>
    </div>
  );
};

export default LoginForm;
