import React from 'react';
import AuthLayout from '../components/AuthCommonLayout';
import MainLayout from '../components/MainCommonLayout';
import { Routes, Route } from 'react-router-dom';
// import Login from '../pages/Login';
import SignUp from './routes/SignUp';
import Home from './routes/Home';
import FindPassword from './routes/FindPassword';
import '../styles/global.scss';
import Login from './routes/Login';
import FindBranch from '../features/findbranch/components/FindBranch';
import FindId from '../features/findid/components/FindId';

function App() {
  return (
    // 각 경로들을 정의하기 위한 Routes
    <Routes>
      <Route element={<AuthLayout />}>
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/findbranch" element={<FindBranch />} />
        <Route path="/findpassword" element={<FindPassword />} />
        <Route path="/findid" element={<FindId />} />
      </Route>

      <Route element={<MainLayout />}>
        <Route index element={<Home />} />
      </Route>
    </Routes>
  );
}

export default App;
