import { useCallback, useReducer } from "react";
import LoginForm from "../components/LoginForm";
import { Link } from "react-router-dom";
import styles from "./AuthFormLayout.module.scss";

type Props = {
  label: string;
  components: React.ReactNode
}

const AuthFormLayout = ({label, components}: Props) => {
  return (
    <div className={styles['border-box']}>
      <p className={`${styles.label} inria-sans-regular`}>{label}</p>
      <div className={styles['container']}>
        {components}
      </div>
    </div>
  )
}

export default AuthFormLayout;