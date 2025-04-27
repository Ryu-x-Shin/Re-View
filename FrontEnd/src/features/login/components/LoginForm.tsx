import IdInputField from './IdInputField';
import PasswordInputField from './PasswordInputField';
import styles from './LoginForm.module.scss';
import LoginButton from './LoginButton';
import SignUpField from './SignUpField';
import FindBranchField from './FindBranchField';
import FormValue from '../types/FormValue';
import { FormProvider, SubmitHandler, useForm } from 'react-hook-form';

const initialFormValue: FormValue = {
  id: '',
  password: '',
};

const onSubmit: SubmitHandler<FormValue> = (data) => console.log(data);
console.log('로그인 context도 변경됨');

const LoginForm = () => {
  const method = useForm<FormValue>({
    defaultValues: initialFormValue,
  });

  return (
    <>
      <p className={`${styles.label}`}>Re:View</p>
      <FormProvider {...method}>
        <form className={styles.form} onSubmit={method.handleSubmit(onSubmit)}>
          <IdInputField />
          <PasswordInputField />
          <p className={styles['form__login-init']}>
            ~이유로 로그인에 실패했습니다.
          </p>
          <LoginButton />
          <SignUpField />
          <FindBranchField />
        </form>
      </FormProvider>
    </>
  );
};

export default LoginForm;
