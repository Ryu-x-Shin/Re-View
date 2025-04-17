import styles from './DuplicateCheckInputField.module.scss';

type DuplicateCheckInputFieldProps = {
  label: string;
  value: string;
  onChange: React.ChangeEventHandler<HTMLInputElement>;
  placeholder: string;
  buttonText: string;
  onButtonClick: () => void;
};

const DuplicateCheckInputField: React.FC<DuplicateCheckInputFieldProps> = ({
  label,
  value,
  onChange,
  placeholder,
  buttonText,
  onButtonClick,
}) => {
  return (
    <>
      <div className={styles.component}>
        <label className={styles.label} htmlFor={label}>
          {label}
        </label>
        <div style={{ display: 'flex' }}>
          <input
            id={label}
            className={`${styles.input} ${styles['flex-grow']}`}
            value={value}
            onChange={(e) => onChange(e)}
            placeholder={placeholder}
          />
          <button
            className={`${styles.btn} ${styles['btn-input']}`}
            onClick={onButtonClick}
          >
            {buttonText}
          </button>
        </div>
      </div>
    </>
  );
};

export default DuplicateCheckInputField;
