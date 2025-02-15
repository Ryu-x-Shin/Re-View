import React from 'react';
import './App.css';
import AuthLayout from './components/AuthCommonLayout';
import MainLayout from './components/MainCommonLayout';
import { Routes, Route } from 'react-router-dom';
import Login from './pages/Login';
import SignUp from './pages/SignUp';
import Home from './pages/Home';
import FindPassword from './pages/FindPassword';

function App() {
  return (
    <Routes>
      <Route element={<AuthLayout/>}>
        <Route path="/login" element={<Login/>} />
        <Route path="/signup" element={<SignUp/>} />
        <Route path="/findpassword" element={<FindPassword/>} />
      </Route>

      <Route element={<MainLayout/>}>
        <Route index element={<Home/>} />
      </Route>
    </Routes>
  );
}

export default App;
