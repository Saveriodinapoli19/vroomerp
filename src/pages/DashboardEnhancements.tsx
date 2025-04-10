import React from 'react';
import './DashboardEnhancements.css';

interface EnhancementProps {
  userCount: number;
  autoCount: number;
  latestUsers: { nome: string; cognome: string }[];
  latestAuto: { modello: string; targa: string }[];
}

const DashboardEnhancements: React.FC<EnhancementProps> = ({
  userCount,
  autoCount,
  latestUsers,
  latestAuto
}) => {
  return (
    <div className="dashboard-enhancements">

      <div className="stats-boxes">
        <div className="stat-box">
          <h3>{userCount}</h3>
          <p>Utenti registrati</p>
        </div>
        <div className="stat-box">
          <h3>{autoCount}</h3>
          <p>Automezzi registrati</p>
        </div>
      </div>

      <div className="latest-inserts">
        <div className="latest-users">
          <h4>Ultimi utenti inseriti</h4>
          <ul>
            {latestUsers.map((u, idx) => (
              <li key={idx}>{u.nome} {u.cognome}</li>
            ))}
          </ul>
        </div>
        <div className="latest-auto">
          <h4>Ultimi automezzi inseriti</h4>
          <ul>
            {latestAuto.map((a, idx) => (
              <li key={idx}>{a.modello} - {a.targa}</li>
            ))}
          </ul>
        </div>
      </div>

      <div className="quick-actions">
        <button onClick={() => window.location.href = '/user/new'}>➕ Crea Utente</button>
        <button onClick={() => window.location.href = '/auto/new'}>➕ Crea Automezzo</button>
      </div>
    </div>
  );
};

export default DashboardEnhancements;
