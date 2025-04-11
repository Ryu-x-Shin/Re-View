import EmailAuthInputField from './EmailAuthInputField';
import IdInputField from './IdInputField';
import NickNameInputField from './NickNameInputField';
import PasswordConfirmInputField from './PasswordConfirmInputField';
import PasswordInputField from './PasswordInputField';
import styles from './SignUpForm.module.scss';
import SubmitButton from './SubmitButton';

const SignUpForm = () => {
  return (
    <div>
      <p className={`${styles.label} inria-sans-regular`}>회원가입</p>
      <form className={styles.form}>
        <EmailAuthInputField />
        <IdInputField />
        <PasswordInputField />
        <PasswordConfirmInputField />
        <NickNameInputField />
        <SubmitButton />
      </form>
    </div>
  );
};

export default SignUpForm;
