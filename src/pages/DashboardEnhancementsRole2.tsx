// DashboardEnhancements.tsx (versione ruolo 2: solo lettura, senza pulsanti di creazione)
import React from 'react';
import './DashboardEnhancements.css';

interface EnhancementProps {
  userCount: number;
  autoCount: number;
  motoCount: number;
  latestUsers: { nome: string; cognome: string }[];
  latestAuto: { modello: string; targa: string }[];
  latestMoto: { modello: string; targa: string }[];
}

const DashboardEnhancements: React.FC<EnhancementProps> = ({
  autoCount,
  motoCount,
  latestAuto,
  latestMoto
}) => {
  return (
    <div className="dashboard-enhancements">
      <div className="stats-grid">
        <div className="stat-block">
          <div className="stat-box">
            <h3>{autoCount}</h3>
            <p>Automezzi registrati</p>
          </div>
          <div className="stat-list">
            <div className="list-header">
              <h4>Ultimi automezzi inseriti</h4>
            </div>
            <ul>
              {latestAuto.map((a, idx) => (
                <li key={idx}>{a.modello} - {a.targa}</li>
              ))}
            </ul>
          </div>
        </div>

        <div className="stat-block">
          <div className="stat-box">
            <h3>{motoCount}</h3>
            <p>Moto registrate</p>
          </div>
          <div className="stat-list">
            <div className="list-header">
              <h4>Ultime moto inserite</h4>
            </div>
            <ul>
              {latestMoto.map((m, idx) => (
                <li key={idx}>{m.modello} - {m.targa}</li>
              ))}
            </ul>
          </div>
        </div>
      </div>
    </div>
  );
};

export default DashboardEnhancements;
