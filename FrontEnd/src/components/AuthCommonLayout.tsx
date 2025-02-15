import { Outlet } from "react-router-dom";
import styles from './AuthCommonLayout.module.scss'
const AuthLayout = () => {
  return (
    <div className={styles.container}>
      <Outlet/>
    </div>
  );
}

export default AuthLayout;