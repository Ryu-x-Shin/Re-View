import React from 'react';
import styles from './SubmitButton.module.scss';
const SubmitButton = () => {
  console.log('SubmitButton 리렌더링');
  return (
    <>
      <div className={`${styles.component}`}>
        <button type="submit" className={`${styles['component__btn']}`}>
          확인
        </button>
      </div>
    </>
  );
};

export default SubmitButton;
