import React from 'react';
import AuthLayout from './components/AuthCommonLayout';
import MainLayout from './components/MainCommonLayout';
import { Routes, Route } from 'react-router-dom';
import Login from './pages/Login';
import SignUp from './pages/SignUp';
import Home from './pages/Home';
import FindPassword from './pages/FindPassword';
import { Provider } from 'react-redux';
import { store } from './store/store';

function App() {
  return (
    <Provider store={store}>
      <Routes>
        <Route element={<AuthLayout />}>
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path="/findpassword" element={<FindPassword />} />
        </Route>

        <Route element={<MainLayout />}>
          <Route index element={<Home />} />
        </Route>
      </Routes>
    </Provider>
  );
}

export default App;
