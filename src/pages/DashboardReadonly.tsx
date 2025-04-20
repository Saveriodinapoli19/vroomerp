// Dashboard.tsx (versione per ruolo 2 - solo visualizzazione di moto e auto con icona occhio)

import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from '../api/axiosInstance';
import '../App.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEye, faCartShopping } from '@fortawesome/free-solid-svg-icons';
import DashboardEnhancementsReadonly from './DashboardEnhancementsReadonly';
import { toast } from 'react-toastify';
import { handleAcquisto } from '../api/acquistoUtils';

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

interface Acquisti {
  acquistiId: number;
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

  const handleCart = async () => {
    try {
      const token = localStorage.getItem("token");
      const res = await axios.get('/acquisti/findAcquistiByUserId', {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
  
      const data = res.data;
  
      if (data.errorCode === 0 && Array.isArray(data.acquistiBeanList)) {
        const acquisti = data.acquistiBeanList;
        const totale = data.spesaTotale;
  
        if (acquisti.length === 0) {
          toast.info("🛒 Nessun acquisto effettuato.");
        } else {
          toast.info(
            <div style={{ lineHeight: '1.6' }}>
              <strong>🧾 Riepilogo Acquisti:</strong>
              <ul>
                {acquisti.slice(0, 3).map((a: any, i: number) => (
                  <li key={i}>
                    {a.autoBean?.mezzoBean?.modello ||
                     a.motoBean?.mezzoBean?.modello ||
                     a.tirBean?.mezzoBean?.modello || 'Mezzo'} - 
                    € {a.autoBean?.mezzoBean?.prezzo || 
                        a.motoBean?.mezzoBean?.prezzo || 
                        a.tirBean?.mezzoBean?.prezzo}
                  </li>
                ))}
              </ul>
              <div><strong>Totale:</strong> € {totale}</div>
            </div>,
            { autoClose: false, position: "top-center" }
          );
        }
      } else {
        toast.error(`⚠️ ${data.errorMessage || 'Errore durante il recupero acquisti'}`);
      }
    } catch (err) {
      console.error(err);
      toast.error("❌ Errore di rete durante il recupero degli acquisti.");
    }
  };
  

  return (
    <div className="dashboard-wrapper">
      <div className="top-bar">
        <div className="user-info">Benvenuto, {nomeUtente}</div>
        <button className="logout-btn" onClick={handleLogout}>Logout</button>
      
      </div>
     

      <div className="main-layout">
        <div className="sidebar-left2">
          <h3 onClick={() => setShowAuto(!showAuto)} className="menu-item">
            {showAuto ? '▼' : '▶'} Automezzi
          </h3>
          <h3 onClick={() => setShowMoto(!showMoto)} className="menu-item">
            {showMoto ? '▼' : '▶'} Moto
          </h3>
          <h3 onClick={() => setShowTir(!showTir)} className="menu-item">
            {showTir ? '▼' : '▶'} Tir
          </h3>
          <hr style={{ margin: '15px 0' }} />
          <button
    onClick={() => navigate('/acquisti')}
    style={{
      backgroundColor: '#dc35c6',
      color: 'white',
      border: 'none',
      padding: '10px 15px',
      borderRadius: '6px',
      fontSize: '15px',
      cursor: 'pointer',
      width: '100%',
      marginTop: 'auto'
    }}
  >
    📋 Visualizza Acquisti
  </button>

        </div>
        <DashboardEnhancementsReadonly
          autoCount={auto.length}
          motoCount={moto.length}
          tirCount={tir.length}
          latestAuto={auto.slice(-3).reverse().map(a => ({
            id: a.autoId,
            modello: a.mezzoBean?.modello || '',
            targa: a.mezzoBean?.targa || ''
          }))}
          latestMoto={moto.slice(-3).reverse().map(m => ({
            id: m.motoId,
            modello: m.mezzoBean?.modello || '',
            targa: m.mezzoBean?.targa || ''
          }))}
          latestTir={tir.slice(-3).reverse().map(t => ({
            id: t.tirId,
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
                        <button className="cart-btn" onClick={() => handleAcquisto('auto', a.autoId)}>
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
                        <button className="cart-btn" onClick={() => handleAcquisto('moto', m.motoId)}>
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
                        <button className="cart-btn" onClick={() => handleAcquisto('tir', m.tirId)}>
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
