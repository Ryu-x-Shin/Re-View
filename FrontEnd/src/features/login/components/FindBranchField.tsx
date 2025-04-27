import { Link } from 'react-router-dom';
import styles from './FindBranchField.module.scss';

const FindBranchField = () => {
  return (
    <div className={`${styles.component}`}>
      <span className={`${styles['component__label']}`}>
        아이디/비밀번호 찾기
      </span>
      <Link className={`${styles['component__link']}`} to="/findbranch">
        <button className={styles['component__btn']}>
          아이디/비밀번호 찾기
        </button>
      </Link>
    </div>
  );
};

export default FindBranchField;
