import { Link } from 'react-router-dom';
import styles from './FindPasswordField.module.scss';

const FindPasswordField = () => {
  return (
    <div className={`${styles.component}`}>
      <span className={`${styles['component__label']}`}>비밀번호 찾기</span>
      <Link className={`${styles['component__link']}`} to="/findpassword">
        <button className={styles['component__btn']}>비밀번호 찾기</button>
      </Link>
    </div>
  );
};

export default FindPasswordField;
