import React from 'react';
import styles from './IdInputField.module.scss';

const IdInputField = () => {
  return (
    <div className={styles['component']}>
      <label htmlFor="id" className={styles['component__label']}>
        아이디
      </label>
      <div>
        <input
          id="id"
          className={styles['component__input']}
          value=""
          onChange={() => {}}
          placeholder="Id를 입력해주세요"
        />
      </div>
    </div>
  );
};

export default React.memo(IdInputField);
