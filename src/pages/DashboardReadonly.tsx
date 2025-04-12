// Dashboard.tsx (versione per ruolo 2 - solo visualizzazione di moto e auto con icona occhio)

import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from '../api/axiosInstance';
import '../App.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEye, faCartShopping } from '@fortawesome/free-solid-svg-icons';
import DashboardEnhancementsReadonly from './DashboardEnhancementsReadonly';
import { toast } from 'react-toastify';

interface MezzoBean {
  mezzoId: number;
  modello: string;
  targa: string;
  marca: string;
  colore: string;
}

interface Auto {
  autoId: number;
  mezzoBean?: MezzoBean;
}

interface Moto {
  motoId: number;
  mezzoBean?: MezzoBean;
}

interface Tir {
  tirId: number;
  mezzoBean?: MezzoBean;
}

 

const Dashboard: React.FC = () => {
  const [auto, setAuto] = useState<Auto[]>([]);
  const [moto, setMoto] = useState<Moto[]>([]);
  const [showAuto, setShowAuto] = useState(false);
  const [showMoto, setShowMoto] = useState(false);
  const [tir, setTir] = useState<Tir[]>([]);
  const [showTir, setShowTir] = useState(false);
  const navigate = useNavigate();

  const getNomeUtenteFromToken = (): string => {
    const token = localStorage.getItem('token');
    if (!token) return '';
    try {
      const payload = token.split('.')[1];
      const decoded = JSON.parse(atob(payload));
      return `${decoded.nome || ''} ${decoded.cognome || ''}`.trim();
    } catch (e) {
      return '';
    }
  };

  const nomeUtente = getNomeUtenteFromToken();

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (!token) navigate('/login');

    axios.get('/automezzi/findAllAuto')
      .then(res => setAuto(res.data.autoBeanList || []))
      .catch(() => { });

      axios.get('/automezzi/findAllTir')
      .then(res => setTir(res.data.tirBeanList || []))
      .catch(() => { });

    axios.get('/automezzi/findAllMoto')
      .then(res => setMoto(res.data.listaMoto || []))
      .catch(() => { });
  }, [navigate]);

  const handleLogout = () => {
    localStorage.removeItem('token');
    navigate('/login');
  };

  return (
    <div className="dashboard-wrapper">
      <div className="top-bar">
        <div className="user-info">Benvenuto, {nomeUtente}</div>
        <button className="logout-btn" onClick={handleLogout}>Logout</button>
      </div>

      <div className="main-layout">
        <div className="sidebar-left">
          <h3 onClick={() => setShowAuto(!showAuto)} className="menu-item">
            {showAuto ? '▼' : '▶'} Automezzi
          </h3>
          <h3 onClick={() => setShowMoto(!showMoto)} className="menu-item">
            {showMoto ? '▼' : '▶'} Moto
          </h3>
          <h3 onClick={() => setShowTir(!showTir)} className="menu-item">
            {showTir ? '▼' : '▶'} Tir
          </h3>
        </div>
        <DashboardEnhancementsReadonly
          autoCount={auto.length}
          motoCount={moto.length}
          tirCount={tir.length}
          latestAuto={auto.slice(-3).reverse().map(a => ({
            modello: a.mezzoBean?.modello || '',
            targa: a.mezzoBean?.targa || ''
          }))}
          latestMoto={moto.slice(-3).reverse().map(m => ({
            modello: m.mezzoBean?.modello || '',
            targa: m.mezzoBean?.targa || ''
          }))}
          latestTir = {tir.slice(-3).reverse().map(t => ({
            modello: t.mezzoBean?.modello || '',
            targa: t.mezzoBean?.targa || ''
          }))}
        />

        {showAuto && (
          <div className="floating-overlay">
            <div className="floating-user-box expanded">
              <div className="box-header">
                <h3>Lista Automezzi</h3>
                <button className="close-btn" onClick={() => setShowAuto(false)}>✖</button>
              </div>
              <table className="floating-table">
                <thead>
                  <tr><th>Modello</th><th>Targa</th><th>Azioni</th></tr>
                </thead>
                <tbody>
                  {auto.map(a => (
                    <tr key={a.autoId}>
                      <td>{a.mezzoBean?.modello}</td>
                      <td>{a.mezzoBean?.targa}</td>
                      <td>
                        <Link to={`/auto/${a.autoId}`}>
                          <button className="view-btn"><FontAwesomeIcon icon={faEye} /></button>
                        </Link>
                        <button className="cart-btn" onClick={() => toast.info("🚧 Acquisto non ancora disponibile")}>
                          <FontAwesomeIcon icon={faCartShopping} />
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        )}

        {showMoto && (
          <div className="floating-overlay">
            <div className="floating-user-box expanded">
              <div className="box-header">
                <h3>Lista Moto</h3>
                <button className="close-btn" onClick={() => setShowMoto(false)}>✖</button>
              </div>
              <table className="floating-table">
                <thead>
                  <tr><th>Modello</th><th>Targa</th><th>Azioni</th></tr>
                </thead>
                <tbody>
                  {moto.map(m => (
                    <tr key={m.motoId}>
                      <td>{m.mezzoBean?.modello}</td>
                      <td>{m.mezzoBean?.targa}</td>
                      <td>
                        <Link to={`/moto/${m.motoId}`}>
                          <button className="view-btn"><FontAwesomeIcon icon={faEye} /></button>
                        </Link>
                        <button className="cart-btn" onClick={() => toast.info("🚧 Acquisto non ancora disponibile")}>
                          <FontAwesomeIcon icon={faCartShopping} />
                        </button>

                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        )}

{showTir && (
          <div className="floating-overlay">
            <div className="floating-user-box expanded">
              <div className="box-header">
                <h3>Lista Tir</h3>
                <button className="close-btn" onClick={() => setShowTir(false)}>✖</button>
              </div>
              <table className="floating-table">
                <thead>
                  <tr><th>Modello</th><th>Targa</th><th>Azioni</th></tr>
                </thead>
                <tbody>
                  {tir.map(m => (
                    <tr key={m.tirId}>
                      <td>{m.mezzoBean?.modello}</td>
                      <td>{m.mezzoBean?.targa}</td>
                      <td>
                        <Link to={`/tir/${m.tirId}`}>
                          <button className="view-btn"><FontAwesomeIcon icon={faEye} /></button>
                        </Link>
                        <button className="cart-btn" onClick={() => toast.info("🚧 Acquisto non ancora disponibile")}>
                          <FontAwesomeIcon icon={faCartShopping} />
                        </button>

                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default Dashboard;
