import { FormProvider, SubmitHandler, useForm } from 'react-hook-form';
import styles from './FindPw.module.scss';
import FormValue from '../types/FormValue';
import IdInputField from './IdInputField';
import SubmitButton from './SubmitButton';
import EmailInputField from './EmailInputField';
import { useNavigate } from 'react-router-dom';

const initialFormValue: FormValue = {
  email: '',
  id: '',
};

const FindPw = () => {
  const method = useForm<FormValue>({
    defaultValues: initialFormValue,
  });

  const navigate = useNavigate();

  const onSubmit: SubmitHandler<FormValue> = (data) => {
    console.log(data);
    alert('비밀번호가 초기화 되었습니다! 다시 로그인해주세요');
    // 로그인 페이지로 이동
    navigate('/login');
  };

  return (
    <>
      <div>
        <p className={`${styles.label}`}>비밀번호 찾기</p>
        <FormProvider {...method}>
          <form
            className={styles.form}
            onSubmit={method.handleSubmit(onSubmit)}
          >
            <EmailInputField />
            <IdInputField />
            <SubmitButton />
          </form>
        </FormProvider>
      </div>
    </>
  );
};

export default FindPw;
