import React from 'react';
import styles from '../../../components/LoginForm.module.scss';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../../store/store';
import { setPasswordConfirm } from '../../../store/slices/signUpSlice';

const PasswordConfirmInputField = () => {
  const dispatch = useDispatch();
  const passwordConfirm = useSelector(
    (state: RootState) => state.signUp.passwordConfirm,
  );

  console.log('여기서 패스워드 Confirm 입력 Input이 리렌더링됨');
  return (
    <div className={styles.component}>
      <label className={styles['label']} htmlFor="password confirm">
        Password 재입력
      </label>
      <div>
        <input
          id="password confirm"
          className={styles['input']}
          value={passwordConfirm}
          onChange={(e) => dispatch(setPasswordConfirm(e.target.value))}
        />
      </div>
    </div>
  );
};

export default React.memo(PasswordConfirmInputField);
