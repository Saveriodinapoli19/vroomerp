import React from 'react';
import { useNavigate } from 'react-router-dom';

const BackToDashboard: React.FC = () => {
  const navigate = useNavigate();
  const ruoloId = parseInt(localStorage.getItem('ruoloId') || '0');

  const handleBack = () => {
    if (ruoloId === 2) {
      navigate('/dashboard-readonly');
    } else {
      navigate('/dashboard');
    }
  };

  return (
    <button onClick={handleBack} className="back-btn">
      ← Torna alla Dashboard
    </button>
  );
};

export default BackToDashboard;
