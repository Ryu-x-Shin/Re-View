import { useContext } from 'react';
import { useFormContext } from 'react-hook-form';
import NicknameCheckedContext from '../contexts/NicknameCheckedContext';
import styles from './NickNameInputField.module.scss';
import FormValue from '../types/FormValue';

const NickNameInputField = () => {
  const {
    register,
    formState: { errors },
    trigger,
  } = useFormContext<FormValue>();

  const nicknameCheckedContext = useContext(NicknameCheckedContext)!;

  console.log('NicknameInputField 리렌더링');

  return (
    <div className={styles.container}>
      <label className={styles.label} htmlFor="NickName">
        NickName
      </label>
      <div className={styles['component']}>
        <div>
          <input
            id="NickName"
            className={`${errors.nickname ? styles['component__input-error'] : styles['component__input']}`}
            placeholder="닉네임을 입력해주세요"
            disabled={nicknameCheckedContext.isNicknameChecked}
            {...register('nickname', {
              required: '닉네임을 입력해주세요.',
            })}
          />
          {errors.nickname && (
            <p className={styles['error']}>{errors.nickname.message}</p>
          )}
        </div>
        <button
          className={styles['component__btn']}
          type="button"
          disabled={nicknameCheckedContext.isNicknameChecked}
          onClick={async () => {
            // const isNicknameValid = await trigger('nickname');
            await trigger('nickname');
          }}
        >
          중복체크
        </button>
      </div>
    </div>
  );
};

export default NickNameInputField;
