import styles from './SubmitButton.module.scss';
const SubmitButton = () => {
  return (
    <>
      <div className={`${styles.component}`}>
        <button className={`${styles['component__btn']}`}>회원 가입</button>
      </div>
    </>
  );
};

export default SubmitButton;
