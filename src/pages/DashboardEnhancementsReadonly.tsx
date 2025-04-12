import React from 'react';
import './DashboardEnhancementsReadonly.css';
import { toast } from 'react-toastify';
interface EnhancementProps {
  autoCount: number;
  motoCount: number;
  tirCount : number,
  latestAuto: { modello: string; targa: string }[];
  latestMoto: { modello: string; targa: string }[];
  latestTir: { modello: string; targa: string }[];
}
const showComingSoonToast = () => {
    toast.info("🚧 Funzionalità di acquisto in arrivo", {
        position: "top-center",
        className: "custom-toast", // ✅ questa è corretta
        autoClose: 3000,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: false,
        hideProgressBar: false
      });
      
      
      
      
  };
  
const DashboardEnhancementsReadonly: React.FC<EnhancementProps> = ({
  autoCount,
  motoCount,
  tirCount,
  latestAuto,
  latestMoto,
  latestTir
}) => {
  return (
    <div className="dashboard-enhancements">
      <div className="stats-grid">

        {/* Automezzi */}
        <div className="stat-block">
          <div className="stat-box">
            <h3>{autoCount}</h3>
            <p>Automezzi registrati</p>
          </div>
          <div className="stat-list">
            <div className="list-header">
              <h4>Ultimi automezzi inseriti</h4>
              <button onClick={showComingSoonToast}>
                🛒 Acquista Automezzo
              </button>
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
            <h3>{tirCount}</h3>
            <p>Tir registrati</p>
          </div>
          <div className="stat-list">
            <div className="list-header">
              <h4>Ultimi Tir inseriti</h4>
              <button onClick={showComingSoonToast}>
                🛒 Acquista Tir
              </button>
            </div>
            <ul>
              {latestTir.map((a, idx) => (
                <li key={idx}>{a.modello} - {a.targa}</li>
              ))}
            </ul>
          </div>
        </div>

        {/* Moto */}
        <div className="stat-block">
          <div className="stat-box">
            <h3>{motoCount}</h3>
            <p>Moto registrate</p>
          </div>
          <div className="stat-list">
            <div className="list-header">
              <h4>Ultime moto inserite</h4>
              <button onClick={showComingSoonToast}>
                🛒 Acquista Moto
              </button>
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

export default DashboardEnhancementsReadonly;
