
import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './pages/Login';
import Dashboard from './pages/Dashboard';
import UserDetail from './pages/UserDetail';
import AutoDetail from './pages/AutoDetail';
import UserCreate from './pages/UserCreate';
import UserEdit from './pages/UserEdit';
import AutoCreate from './pages/AutoCreate';
import AutoEdit from './pages/AutoEdit';
import './App.css';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const App: React.FC = () => {
  const token = localStorage.getItem('token');
  return (
    <Router>
      {/* Mostra toast in tutta l'app */}
      <ToastContainer />
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/" element={token ? <Dashboard /> : <Navigate to="/login" />} />
        <Route path="/user/:userId" element={token ? <UserDetail /> : <Navigate to="/login" />} />
        <Route path="/auto/:autoId" element={token ? <AutoDetail /> : <Navigate to="/login" />} />
        <Route path="/user/new" element={<UserCreate />} />
        <Route path="/user/edit/:userId" element={token ? <UserEdit /> : <Navigate to="/login" />} />
        <Route path="/auto/new" element={<AutoCreate />} />
        <Route path="/auto/edit/:autoId" element={<AutoEdit />} />
        <Route path="*" element={<Login />} />
      </Routes>
    </Router>
  );
};

export default App;
