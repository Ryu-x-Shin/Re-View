import { FormProvider, SubmitHandler, useForm } from 'react-hook-form';
import styles from './FindId.module.scss';
import FormValue from '../types/FormValue';

import SubmitButton from './SubmitButton';
import EmailInputField from './EmailInputField';
import { useNavigate } from 'react-router-dom';

const initialFormValue: FormValue = {
  email: '',
};

const FindId = () => {
  const method = useForm<FormValue>({
    defaultValues: initialFormValue,
  });

  const navigate = useNavigate();

  const onSubmit: SubmitHandler<FormValue> = (data) => {
    console.log(data);
    alert('아이디를 이메일로 보내드렸습니다! 다시 로그인해주세요');
    // 로그인 페이지로 이동
    navigate('/login');
  };

  return (
    <>
      <div>
        <p className={`${styles.label}`}>아이디 찾기</p>
        <FormProvider {...method}>
          <form
            className={styles.form}
            onSubmit={method.handleSubmit(onSubmit)}
          >
            <EmailInputField />
            <SubmitButton />
          </form>
        </FormProvider>
      </div>
    </>
  );
};

export default FindId;
