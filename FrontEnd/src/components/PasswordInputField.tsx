import React from 'react';
import styles from './LoginForm.module.scss';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../store/store';
import { setPassword } from '../store/slices/signUpSlice';

const PasswordInputField = () => {
  const dispatch = useDispatch();
  const password = useSelector((state: RootState) => state.signUp.password);

  console.log('여기서 패스워드 입력 Input이 리렌더링됨');
  return (
    <div className={styles.component}>
      <span className={styles['label']}>Password</span>
      <div>
        <input
          className={styles['input']}
          value={password}
          onChange={(e) => dispatch(setPassword(e.target.value))}
        />
      </div>
    </div>
  );
};

export default React.memo(PasswordInputField);
