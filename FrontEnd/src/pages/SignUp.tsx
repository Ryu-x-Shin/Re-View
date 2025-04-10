import AuthFormLayout from '../components/AuthFormLayout';
import EmailAuthForm from '../components/EmailAuthForm';
import styles from './SignUp.module.scss';
import NickNameInput from '../components/NickNameInput';
import IdInput from '../components/IdInput';
import PasswordInputField from '../components/PasswordInputField';
import PasswordRepeatInputField from '../components/PasswordConfirmInputField';

const SignUp = () => {
  const components = (
    <>
      <EmailAuthForm />
      <IdInput />
      <PasswordInputField />
      <PasswordRepeatInputField />
      <NickNameInput />
      <div className={`${styles.component}`}>
        <button className={`${styles.btn} ${styles['full-width']}`}>
          회원 가입
        </button>
      </div>
    </>
  );
  return <AuthFormLayout label="회원가입" components={components} />;
};

export default SignUp;
