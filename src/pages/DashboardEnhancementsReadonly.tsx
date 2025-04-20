import React from 'react';
import './DashboardEnhancementsReadonly.css';
import { toast } from 'react-toastify';
import axios from '../api/axiosInstance'; // Assicurati che punti alla tua API backend

interface EnhancementProps {
  autoCount: number;
  motoCount: number;
  tirCount: number;
  latestAuto: { id: number; modello: string; targa: string }[];
  latestMoto: { id: number; modello: string; targa: string }[];
  latestTir: { id: number; modello: string; targa: string }[];
}

const acquistaVeicolo = async (
  tipo: 'auto' | 'moto' | 'tir',
  id: number
) => {
  const token = localStorage.getItem('token');

  if (!token) {
    toast.error("Token mancante. Effettua il login.");
    return;
  }

  const payload: any = {
    acquistiBean: {}
  };

  if (tipo === 'auto') payload.acquistiBean.extAutoId = id;
  if (tipo === 'moto') payload.acquistiBean.extMotoId = id;
  if (tipo === 'tir') payload.acquistiBean.extTirId = id;

  try {
    const res = await axios.post('/acquisti/insertAcquisto', payload, {
      headers: {
        Authorization: `Bearer ${token}`,
      }
    });

    const data = res.data;

    if (data.errorCode === 0) {
      toast.success("✅ Acquisto completato!");
    } else {
      toast.error(`❌ ${data.errorMessage || "Errore durante l'acquisto"}`);
    }
  } catch (err) {
    toast.error("❌ Errore di rete durante l'acquisto");
    console.error(err);
  }
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
        {/* Auto */}
        <div className="stat-block">
          <div className="stat-box">
            <h3>{autoCount}</h3>
            <p>Automezzi registrati</p>
          </div>
          <div className="stat-list">
            <h4>Ultimi automezzi inseriti</h4>
            <ul>
              {latestAuto.map((a, idx) => (
                <li key={idx} className="mezzo-item">
                  <span>{a.modello} - {a.targa}</span>
                  <button onClick={() => acquistaVeicolo('auto', a.id)}>🛒 Acquista</button>
                </li>
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
            <h4>Ultime moto inserite</h4>
            <ul>
              {latestMoto.map((m, idx) => (
                <li key={idx} className="mezzo-item">
                  <span>{m.modello} - {m.targa}</span>
                  <button onClick={() => acquistaVeicolo('moto', m.id)}>🛒 Acquista</button>
                </li>
              ))}
            </ul>
          </div>
        </div>

        {/* Tir */}
        <div className="stat-block">
          <div className="stat-box">
            <h3>{tirCount}</h3>
            <p>Tir registrati</p>
          </div>
          <div className="stat-list">
            <h4>Ultimi tir inseriti</h4>
            <ul>
              {latestTir.map((t, idx) => (
                <li key={idx} className="mezzo-item">
                  <span>{t.modello} - {t.targa}</span>
                  <button onClick={() => acquistaVeicolo('tir', t.id)}>🛒 Acquista</button>
                </li>
              ))}
            </ul>
          </div>
        </div>
      </div>
    </div>
  );
};

export default DashboardEnhancementsReadonly;
