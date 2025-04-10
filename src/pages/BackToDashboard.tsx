// src/components/BackToDashboard.tsx
import React from 'react';
import { useNavigate } from 'react-router-dom';
import './BackToDashboard.css';

const BackToDashboard: React.FC = () => {
  const navigate = useNavigate();

  return (
    <div className="back-button-container">
      <button className="back-button" onClick={() => navigate('/')}>
        ⬅ Indietro
      </button>
    </div>
  );
};

export default BackToDashboard;
