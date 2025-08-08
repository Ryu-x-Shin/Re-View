import IdInputField from './IdInputField';
import PasswordInputField from './PasswordInputField';
import styles from './LoginForm.module.scss';
import LoginButton from './LoginButton';
import SignUpField from './SignUpField';
import FindBranchField from './FindBranchField';
import FormValue from '../types/FormValue';
import { FormProvider, SubmitHandler, useForm } from 'react-hook-form';
import login from '../api/login';

const initialFormValue: FormValue = {
  id: '',
  password: '',
};

console.log('로그인 context도 변경됨');

const LoginForm = () => {
  const method = useForm<FormValue>({
    defaultValues: initialFormValue,
  });

  const onSubmit: SubmitHandler<FormValue> = (data) => {
    console.log(data);
    (async () => {
      let result = await login();
      if (result.ok) {
        // Todo 성공하면 Main 화면으로 Navigation 시키는 코드
      } else {
        alert(
          `로그인에 실패하셨습니다\n${result.error.status} : ${result.error.message}`,
        );

        // 현재 값을 유지하면서 비밀번호만 초기화
        const currentValues = method.getValues();
        method.reset({
          ...currentValues,
          password: '',
        });
      }
    })();
  };

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
