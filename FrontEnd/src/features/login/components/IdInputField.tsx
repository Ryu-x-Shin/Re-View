import React from 'react';
import styles from './IdInputField.module.scss';
import { useFormContext } from 'react-hook-form';
import FormValue from '../types/FormValue';

const IdInputField = () => {
  const {
    register,
    formState: { errors },
  } = useFormContext<FormValue>();
  return (
    <div className={styles['component']}>
      <label htmlFor="id" className={styles['component__label']}>
        아이디
      </label>
      <div>
        <input
          id="id"
          className={`${errors.id ? styles['component__input-error'] : styles['component__input']}`}
          placeholder="Id를 입력해주세요"
          {...register('id', {
            required: '아이디를 입력해주세요.',
          })}
        />
        {errors.id && <p className={styles['error']}>{errors.id.message}</p>}
      </div>
    </div>
  );
};

export default React.memo(IdInputField);
