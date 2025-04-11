import styles from './Signup.module.scss';
const SubmitButton = () => {
  return (
    <>
      <div className={`${styles.component}`}>
        <button className={`${styles.btn} ${styles['full-width']}`}>
          회원 가입
        </button>
      </div>
    </>
  );
};

export default SubmitButton;
