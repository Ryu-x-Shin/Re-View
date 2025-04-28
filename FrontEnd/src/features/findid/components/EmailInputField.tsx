import { useFormContext } from 'react-hook-form';
import FormValue from '../types/FormValue';
import styles from './EmailInputField.module.scss';

const EmailInputField = () => {
  const {
    register,
    formState: { errors },
  } = useFormContext<FormValue>();

  console.log('EmailInputField 리렌더링');
  return (
    <div className={styles['field']}>
      <label htmlFor="email">Email</label>
      <div className={styles['email']}>
        <input
          id="email"
          className={`${errors.email ? styles['email__input-error'] : styles['email__input']}`}
          placeholder="회원가입시 등록한 이메일을 입력해주세요"
          {...register('email', {
            required: '회원가입시 등록한 이메일을 입력해주세요.',
            pattern: {
              value: /^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/,
              message: '유효한 이메일 형식이 아닙니다.',
            },
          })}
        />
        {errors.email && (
          <p className={styles['error']}>{errors.email.message}</p>
        )}
      </div>
    </div>
  );
};

export default EmailInputField;
