
import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './pages/Login';
import Dashboard from './pages/Dashboard';
import UserDetail from './pages/UserDetail';
import AutoDetail from './pages/AutoDetail';
import './App.css';

const App: React.FC = () => {
  const token = localStorage.getItem('token');
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/" element={token ? <Dashboard /> : <Navigate to="/login" />} />
        <Route path="/user/:id" element={token ? <UserDetail /> : <Navigate to="/login" />} />
        <Route path="/auto/:id" element={token ? <AutoDetail /> : <Navigate to="/login" />} />
        <Route path="*" element={<Login />} />
      </Routes>
    </Router>
  );
};

export default App;
