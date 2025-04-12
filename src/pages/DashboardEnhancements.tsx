import React from 'react';
import './DashboardEnhancements.css';

interface EnhancementProps {
  userCount: number;
  autoCount: number;
  motoCount: number;
  tirCount: number;
  latestUsers: { nome: string; cognome: string }[];
  latestAuto: { modello: string; targa: string }[];
  latestMoto: { modello: string; targa: string }[];
  latestTir: { modello: string; targa: string }[];
}

const DashboardEnhancements: React.FC<EnhancementProps> = ({
  userCount,
  autoCount,
  motoCount,
  tirCount,
  latestUsers,
  latestAuto,
  latestMoto,
  latestTir
}) => {
  return (
    <div className="dashboard-enhancements">

<div className="stats-grid">
  <div className="stat-block">
    <div className="stat-box">
      <h3>{userCount}</h3>
      <p>Utenti registrati</p>
    </div>
    <div className="stat-list">
    <div className="list-header">
      <h4>Ultimi utenti inseriti</h4>
      <button onClick={() => window.location.href = '/user/new'}>➕ Crea Utente</button>
      </div>
      <ul>
        {latestUsers.map((u, idx) => (
          <li key={idx}>{u.nome} {u.cognome}</li>
        ))}
      </ul>
      
    </div>
  </div>

  <div className="stat-block">
    <div className="stat-box">
      <h3>{autoCount}</h3>
      <p>Automezzi registrati</p>
    </div>
    <div className="stat-list">
    <div className="list-header">
      <h4>Ultimi automezzi inseriti</h4>
      <button onClick={() => window.location.href = '/auto/new'}>➕ Crea Automezzo</button>
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
      <button onClick={() => window.location.href = '/moto/new'}>➕ Crea Moto</button>
      </div>
      <ul>
        {latestMoto.map((m, idx) => (
          <li key={idx}>{m.modello} - {m.targa}</li>
        ))}
      </ul>
     
    </div>
  </div>

  <div className="stat-block">
    <div className="stat-box">
      <h3>{tirCount}</h3>
      <p>Tir registrati</p>
    </div>
    <div className="stat-list">
    <div className="list-header">
      <h4>Ultimi tir inseriti</h4>
      <button onClick={() => window.location.href = '/tir/new'}>➕ Crea Tir</button>
      </div>
      <ul>
        {latestTir.map((m, idx) => (
          <li key={idx}>{m.modello} - {m.targa}</li>
        ))}
      </ul>
     
    </div>
  </div>
</div>


      <div className="quick-actions">
      
      
       
      </div>
    </div>
  );
};

export default DashboardEnhancements;
