import React from 'react';
import styles from './PasswordInputField.module.scss';

const PasswordInputField = () => {
  return (
    <div className={styles.component}>
      <label htmlFor="password" className={styles['component__label']}>
        패스워드
      </label>
      <div>
        <input
          id="password"
          className={styles['component__input']}
          value=""
          onChange={() => {}}
          placeholder="Password를 입력해주세요"
        />
      </div>
    </div>
  );
};

export default React.memo(PasswordInputField);
