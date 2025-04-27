import styles from './FindBranch.module.scss';
import FindIdField from './FindIdField';
import FindPasswordField from './FindPasswordField';

const FindBranch = () => {
  return (
    <>
      <p className={`${styles.label}`}>아이디/비밀번호 찾기</p>
      <div className={styles.form}>
        <FindIdField />
        <FindPasswordField />
      </div>
    </>
  );
};

export default FindBranch;
