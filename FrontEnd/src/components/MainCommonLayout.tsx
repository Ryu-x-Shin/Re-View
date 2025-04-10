import { Outlet } from 'react-router-dom';

const MainLayout = () => {
  return (
    <>
      {/* Outlet은 React-router-dom에서 중첩된 경로에 있는 화면들을 표시해주기 위한 것 ex: login/user, login/example이 있을 때 login에 으로 시작하는 화면 등 */}
      <Outlet />
    </>
  );
};

export default MainLayout;
