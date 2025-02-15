import { Outlet } from "react-router-dom";
import './AuthCommonLayout.scss'
const AuthLayout = () => {
  return (
    <div className="container">
      <Outlet/>
    </div>
  );
}

export default AuthLayout;