import React from 'react';
import AuthLayout from '../components/AuthCommonLayout';
import MainLayout from '../components/MainCommonLayout';
import { Routes, Route } from 'react-router-dom';
// import Login from '../pages/Login';
import SignUp from './routes/SignUp';
import Home from './routes/Home';
import FindPassword from './routes/FindPassword';

function App() {
  return (
    // 각 경로들을 정의하기 위한 Routes
    <Routes>
      <Route element={<AuthLayout />}>
        {/* <Route path="/login" element={<Login />} /> */}
        <Route path="/signup" element={<SignUp />} />
        <Route path="/findpassword" element={<FindPassword />} />
      </Route>

      <Route element={<MainLayout />}>
        <Route index element={<Home />} />
      </Route>
    </Routes>
  );
}

export default App;
