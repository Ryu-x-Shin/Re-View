import { Link } from 'react-router-dom';
import styles from './FindIdField.module.scss';

const FindIdField = () => {
  return (
    <div className={`${styles.component}`}>
      <p className={`${styles['component__label']}`}>아이디 찾기</p>
      <Link className={`${styles['component__link']}`} to="/findid">
        <button className={styles['component__btn']}>아이디 찾기</button>
      </Link>
    </div>
  );
};

export default FindIdField;
