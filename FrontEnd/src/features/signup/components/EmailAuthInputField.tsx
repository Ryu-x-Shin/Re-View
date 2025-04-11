import React from 'react';
import styles from './EmailAuthInputField.module.scss';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../../store/store';
import { setEmail, setEmailCode } from '../../../store/slices/signUpSlice';

const EmailAuthInputField = () => {
  const dispatch = useDispatch();
  const email = useSelector((state: RootState) => state.signUp.email);
  const emailCode = useSelector((state: RootState) => state.signUp.emailCode);
  const timerSeconds = useSelector((state: RootState) => {
    const minuate = Math.floor(state.signUp.timerSeconds / 60);
    const second = state.signUp.timerSeconds % 60;
    return `${minuate}:${second}`;
  });

  return (
    <div className={styles['field']}>
      <label htmlFor="email">Email</label>
      <div className={styles['email']}>
        <input
          id="email"
          className={`${styles['email__input']}`}
          style={{ display: 'inline-block' }}
          value={email}
          onChange={(e) => dispatch(setEmail(e.target.value))}
        />
        <button className={`${styles['email__btn']}`}>인증 코드 전송</button>
      </div>
      <div className={styles['auth-code']}>
        <input
          className={styles['auth-code__input']}
          value={emailCode}
          onChange={(e) => dispatch(setEmailCode(e.target.value))}
        />
        <button className={`${styles['auth-code__btn']}`}>인증</button>
        <span className={`${styles['auth-code__timer']}`}>{timerSeconds}</span>
      </div>
    </div>
  );
};

export default React.memo(EmailAuthInputField);
