import React from 'react';
import styles from './EmailAuthForm.module.scss';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../store/store';
import { setEmail, setEmailCode } from '../store/slices/signUpSlice';

const EmailAuthForm = () => {
  const dispatch = useDispatch();
  const email = useSelector((state: RootState) => state.signUp.email);
  const emailCode = useSelector((state: RootState) => state.signUp.emailCode);
  const timerSeconds = useSelector((state: RootState) => {
    const minuate = Math.floor(state.signUp.timerSeconds / 60);
    const second = state.signUp.timerSeconds % 60;
    return `${minuate}:${second}`;
  });

  return (
    <div className={styles.component}>
      <span>Email</span>
      <div style={{ margin: '8px 0', display: 'flex' }}>
        <input
          className={styles.input}
          style={{ display: 'inline-block' }}
          value={email}
          onChange={(e) => dispatch(setEmail(e.target.value))}
        />
        <button className={`${styles.btn} ${styles['btn-input']}`}>
          인증 코드 전송
        </button>
      </div>
      <div style={{ display: 'flex', marginRight: '140px' }}>
        <input
          className={styles.input}
          style={{ display: 'inline-block' }}
          value={emailCode}
          onChange={(e) => dispatch(setEmailCode(e.target.value))}
        />
        <button
          className={`${styles.btn} ${styles['btn-input']}`}
          style={{ minWidth: '60px' }}
        >
          인증
        </button>
        <span style={{ display: 'flex', alignItems: 'center' }}>
          {timerSeconds}
        </span>
      </div>
    </div>
  );
};

export default React.memo(EmailAuthForm);
