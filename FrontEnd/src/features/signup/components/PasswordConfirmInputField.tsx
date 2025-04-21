import React from 'react';
import styles from './PasswordConfirmInputField.module.scss';
import { useFormContext } from 'react-hook-form';
import FormValue from '../types/FormValue';

const PasswordConfirmInputField = () => {
  const {
    register,
    formState: { errors },
  } = useFormContext<FormValue>();

  console.log('PasswordConfirmInputField 리렌더링');
  return (
    <div className={styles.component}>
      <label className={styles['component__label']} htmlFor="password confirm">
        Password 재입력
      </label>
      <div>
        <input
          id="password confirm"
          className={`${errors.passwordConfirm ? styles['component__input-error'] : styles['component__input']}`}
          {...register('passwordConfirm', {
            required: '패스워드를 다시 입력해주세요.',
          })}
        />
        {errors.passwordConfirm && (
          <p className={styles['error']}>{errors.passwordConfirm.message}</p>
        )}
      </div>
    </div>
  );
};

export default React.memo(PasswordConfirmInputField);
