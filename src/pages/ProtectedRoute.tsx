import React, { ReactElement } from 'react';
import { Navigate } from 'react-router-dom';

interface Props {
    element: ReactElement;
    requiredRole?: number;
    allowedRoles?: number[];
  }
  

const ProtectedRoute: React.FC<Props> = ({ element, requiredRole, allowedRoles }) => {
  const token = localStorage.getItem('token');
  const ruoloId = parseInt(localStorage.getItem('ruoloId') || '0');

  if (!token) return <Navigate to="/login" />;

  if (requiredRole && ruoloId !== requiredRole) {
    // Ruolo errato → redirect alla dashboard corretta
    return ruoloId === 2 ? <Navigate to="/dashboard-readonly" /> : <Navigate to="/dashboard" />;
  }
  if (allowedRoles && !allowedRoles.includes(ruoloId)) {
    return <Navigate to="/login" />;
  }
  return element;
};

export default ProtectedRoute;
