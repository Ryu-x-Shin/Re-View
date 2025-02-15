import React from 'react';
import styles from './LoginForm.module.scss';
type LoginFormProps = {
  className: string;
  label: string;
  value: string;
  onChange: (newValue: string) => void;
};

const LoginForm = (props: LoginFormProps) => {
  console.log('여기서 ' + props.label + '이 리렌더링됨');
  return (
    <div className={props.className}>
      <span className={styles['label']}>{props.label}</span>
      <div>
        <input
          className={styles['input']}
          value={props.value}
          onChange={(e) => props.onChange(e.target.value)}
        />
      </div>
    </div>
  );
};

export default React.memo(LoginForm);
