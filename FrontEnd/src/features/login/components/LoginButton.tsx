import styles from './LoginButton.module.scss';
const SubmitButton = () => {
  return (
    <>
      <div className={`${styles.component}`}>
        <button type="submit" className={`${styles['component__btn']}`}>
          로그인
        </button>
      </div>
    </>
  );
};

export default SubmitButton;
