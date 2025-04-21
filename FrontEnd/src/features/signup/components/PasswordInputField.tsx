import React from 'react';
import styles from './PasswordInputField.module.scss';
import { useFormContext } from 'react-hook-form';
import FormValue from '../types/FormValue';

const PasswordInputField = () => {
  const {
    register,
    formState: { errors },
  } = useFormContext<FormValue>();

  console.log('PasswordInputField 리렌더링');
  return (
    <div className={styles.component}>
      <label className={styles['component__label']} htmlFor="password">
        Password
      </label>
      <div>
        <input
          id="password"
          className={`${errors.password ? styles['component__input-error'] : styles['component__input']}`}
          {...register('password', {
            required: '패스워드를 입력해주세요.',
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
