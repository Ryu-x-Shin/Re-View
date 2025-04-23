import React from 'react';
import styles from './SubmitButton.module.scss';
const SubmitButton = () => {
  console.log('SubmitButton 리렌더링');
  return (
    <>
      <div className={`${styles.component}`}>
        <button type="submit" className={`${styles['component__btn']}`}>
          비밀번호 초기화
        </button>
      </div>
    </>
  );
};

export default SubmitButton;
