
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
import MotoCreate from './pages/MotoCreate';
import MotoEdit from './pages/MotoEdit';
import DashboardReadonly from './pages/DashboardReadonly';
import ProtectedRoute from './pages/ProtectedRoute';
import TirDetail from './pages/TirDetail';
import TirCreate from './pages/TirCreate';
import TirEdit from './pages/TirEdit';
import AcquistiPages from './pages/AcquistiPages';
import './App.css';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import MotoDetail from './pages/MotoDetail';

const App: React.FC = () => {
  const token = localStorage.getItem('token');
  return (
    <Router>
      {/* Mostra toast in tutta l'app */}
      <ToastContainer />
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route
          path="/"
          element={
            <ProtectedRoute
              element={
                parseInt(localStorage.getItem('ruoloId') || '0') === 2
                  ? <Navigate to="/dashboard-readonly" />
                  : <Navigate to="/dashboard" />
              }
            />
          }
        />

        <Route
          path="/dashboard"
          element={<ProtectedRoute element={<Dashboard />} requiredRole={1} />}
        />

        <Route
          path="/dashboard-readonly"
          element={<ProtectedRoute element={<DashboardReadonly />} requiredRole={2} />}
        />
        <Route
          path="/auto/:autoId"
          element={
            <ProtectedRoute element={<AutoDetail />} allowedRoles={[1, 2]} />
          }
        />
        <Route
          path="/moto/:motoId"
          element={
            <ProtectedRoute element={<MotoDetail />} allowedRoles={[1, 2]} />
          }
        />

        <Route
          path="/tir/:tirId"
          element={
            <ProtectedRoute element={<TirDetail />} allowedRoles={[1, 2]} />
          }
        />


        <Route path="/user/:userId" element={token ? <UserDetail /> : <Navigate to="/login" />} />
        {/* <Route path="/auto/:autoId" element={token ? <AutoDetail /> : <Navigate to="/login" />} /> */}
        <Route path="/user/new" element={<UserCreate />} />
        <Route path="/user/edit/:userId" element={token ? <UserEdit /> : <Navigate to="/login" />} />
        <Route path="/auto/new" element={<AutoCreate />} />
        <Route path="/auto/edit/:autoId" element={<AutoEdit />} />
        {/* <Route path="/moto/:motoId" element={token ? <MotoDetail /> : <Navigate to="/login" />} /> */}
        <Route path="/moto/new" element={<MotoCreate />} />
        <Route path="/moto/edit/:motoId" element={<MotoEdit />} />
        <Route path="/tir/new" element={<TirCreate />} />
        <Route path="/tir/edit/:tirId" element={<TirEdit />} />
        <Route path="/acquisti" element={<AcquistiPages/>} />
        <Route path="*" element={<Login />} />
      </Routes>
    </Router>
  );
};

export default App;
