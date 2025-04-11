import React from 'react';
import styles from '../../../components/LoginForm.module.scss';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../../store/store';
import { setPassword } from '../../../store/slices/signUpSlice';

const PasswordInputField = () => {
  const dispatch = useDispatch();
  const password = useSelector((state: RootState) => state.signUp.password);

  console.log('여기서 패스워드 입력 Input이 리렌더링됨');
  return (
    <div className={styles.component}>
      <label className={styles['label']} htmlFor="password">
        Password
      </label>
      <div>
        <input
          id="password"
          className={styles['input']}
          value={password}
          onChange={(e) => dispatch(setPassword(e.target.value))}
        />
      </div>
    </div>
  );
};

export default React.memo(PasswordInputField);
