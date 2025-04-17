import { Link } from 'react-router-dom';
import styles from './SignUpField.module.scss';

const SignUpField = () => {
  return (
    <div className={`${styles.component}`}>
      <label className={`${styles['component__label']}`}>
        혹시 처음이신가요?
      </label>
      <Link className={`${styles['component__link']}`} to="/signup">
        <button className={styles['component__btn']}>회원가입</button>
      </Link>
    </div>
  );
};

export default SignUpField;
