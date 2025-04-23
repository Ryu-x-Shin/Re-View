import { useFormContext } from 'react-hook-form';
import FormValue from '../types/FormValue';
import styles from './IdInputField.module.scss';
import React from 'react';

const IdInputField = () => {
  const {
    register,
    formState: { errors },
  } = useFormContext<FormValue>();

  console.log('IdInputField 리렌더링');

  return (
    <div className={styles.container}>
      <label className={styles.label} htmlFor="ID">
        ID
      </label>
      <div className={styles['component']}>
        <input
          id="ID"
          className={`${errors.id ? styles['component__input-error'] : styles['component__input']}`}
          placeholder="회원가입 시 등록한 아이디를 입력해주세요."
          {...register('id', {
            required: '회원가입 시 등록한 아이디를 입력해주세요.',
          })}
        />
        {errors.id && <p className={styles['error']}>{errors.id.message}</p>}
      </div>
    </div>
  );
};

export default React.memo(IdInputField);
