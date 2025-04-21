import { useFormContext } from 'react-hook-form';
import styles from './IdInputField.module.scss';
import React, { useContext } from 'react';
import IdCheckedContext from '../contexts/IdCheckedContext';
import FormValue from '../types/FormValue';

const IdInputField = () => {
  const {
    register,
    formState: { errors },
    trigger,
  } = useFormContext<FormValue>();

  const idCheckedContext = useContext(IdCheckedContext)!;

  console.log('IdInputField 리렌더링');

  return (
    <div className={styles.container}>
      <label className={styles.label} htmlFor="ID">
        ID
      </label>
      <div className={styles['component']}>
        <div>
          <input
            id="ID"
            className={`${errors.id ? styles['component__input-error'] : styles['component__input']}`}
            placeholder="아이디를 입력해주세요"
            disabled={idCheckedContext.isIdChecked}
            {...register('id', {
              required: '아이디를 입력해주세요.',
            })}
          />
          {errors.id && <p className={styles['error']}>{errors.id.message}</p>}
        </div>
        <button
          className={styles['component__btn']}
          type="button"
          disabled={idCheckedContext.isIdChecked}
          onClick={async () => {
            // const isIdValid = await trigger('id');
            await trigger('id');
          }}
        >
          중복체크
        </button>
      </div>
    </div>
  );
};

export default React.memo(IdInputField);
