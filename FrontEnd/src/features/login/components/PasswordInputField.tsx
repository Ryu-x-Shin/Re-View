import React from 'react';
import styles from './PasswordInputField.module.scss';
import FormValue from '../types/FormValue';
import { useFormContext } from 'react-hook-form';

const PasswordInputField = () => {
  const {
    register,
    formState: { errors },
  } = useFormContext<FormValue>();
  return (
    <div className={styles.component}>
      <label htmlFor="password" className={styles['component__label']}>
        패스워드
      </label>
      <div>
        <input
          id="password"
          className={`${errors.id ? styles['component__input-error'] : styles['component__input']}`}
          placeholder="Password를 입력해주세요"
          {...register('password', {
            required: '비밀번호를 입력해주세요.',
          })}
        />
        {errors.password && (
          <p className={styles['error']}>{errors.password.message}</p>
        )}
      </div>
    </div>
  );
};

export default React.memo(PasswordInputField);
