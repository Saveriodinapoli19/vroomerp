// Nuova versione della dashboard migliorata lato UI con 3 bottoni per ogni voce e aggiunta utente
import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from '../api/axiosInstance';
import '../App.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEye, faPen, faTrash, faPlus } from '@fortawesome/free-solid-svg-icons';
import { toast } from 'react-toastify';
import DashboardEnhancements from './DashboardEnhancements';

interface User {
  userId: number;
  nome: string;
  cognome: string;
}

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
  const [users, setUsers] = useState<User[]>([]);
  const [auto, setAuto] = useState<Auto[]>([]);
  const [showUsers, setShowUsers] = useState(false);
  const [showAuto, setShowAuto] = useState(false);
  const navigate = useNavigate();
  const [moto, setMoto] = useState<Moto[]>([]);
  const [showMoto, setShowMoto] = useState(false);
  const [tir, setTir] = useState<Tir[]>([]);
  const [showTir, setShowTir] = useState(false);
  
  const mostraToastConferma = (messaggio: string, onConferma: () => void) => {
    toast.info(
      ({ closeToast }) => (
        <div className="toast-conferma">
          <p>{messaggio}</p>
          <div className="toast-conferma-buttons">
            <button
              onClick={() => {
                onConferma();
                closeToast();
              }}
              style={{ background: "red", color: "white", border: "none", padding: "5px 10px", borderRadius: "4px" }}
            >
              Sì
            </button>
            <button
              onClick={closeToast}
              style={{ background: "gray", color: "white", border: "none", padding: "5px 10px", borderRadius: "4px" }}
            >
              No
            </button>
          </div>
        </div>
      ),
      {
        autoClose: false,
        closeOnClick: false,
        closeButton: false,
        draggable: false,
        position: "top-center"
      }
    );
  };
  
  
  
  const getNomeUtenteFromToken = (): string => {
    const token = localStorage.getItem('token');
    if (!token) return '';
    try {
      const payload = token.split('.')[1];
      const decoded = JSON.parse(atob(payload));
      return `${decoded.nome || ''} ${decoded.cognome || ''}`.trim();
    } catch (e) {
      console.error('Errore nel parsing del token:', e);
      return '';
    }
  };

  const nomeUtente = getNomeUtenteFromToken();

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (!token) {
      navigate('/login');
      return;
    }

    axios.get('/user/findAllUsers')
      .then(res => {
        const list = res.data.userBeanList;
        if (Array.isArray(list)) setUsers(list);
      })
      .catch(err => console.error('Errore nel caricamento utenti:', err));

    axios.get('/automezzi/findAllAuto')
      .then(res => {
        const list = res.data.autoBeanList;
        if (Array.isArray(list)) setAuto(list);
      })
      .catch(err => console.error('Errore nel caricamento auto:', err));

      axios.get('/automezzi/findAllMoto')
      .then(res => {
        const list = res.data.listaMoto;
        if (Array.isArray(list)) setMoto(list);
      })
      .catch(err => console.error('Errore nel caricamento delle moto:', err));

      axios.get('/automezzi/findAllTir')
      .then(res => {
        const list = res.data.tirBeanList;
        if (Array.isArray(list)) setTir(list);
      })
      .catch(err => console.error('Errore nel caricamento dei tir:', err));

  }, [navigate]);

  const handleLogout = () => {
    localStorage.removeItem('token');
    navigate('/login');
  };

  const handleAddUser = () => {
    navigate('/user/new');
  };

const handleDelete = (userId: number) => {
  mostraToastConferma("Sei sicuro di voler eliminare l'utente?", async () => {
    try {
      const res = await axios.delete(`/user/deleteUser/${userId}`);
      if (res.data.errorCode === 0) {
        setUsers(prev => prev.filter(u => u.userId !== userId));
        toast.success("✅ Utente eliminato con successo");
      } else {
        toast.warning(`⚠️ Errore: ${res.data.errorMessage}`);
      }
    } catch (err) {
      console.error('Errore durante eliminazione utente:', err);
      toast.error('❌ Errore durante la richiesta al server.');
    }
  });
};


  const handleDeleteAuto = (autoId: number) => {
    mostraToastConferma("Sei sicuro di voler eliminare l'automezzo?", async () => {
      try {
        const res = await axios.delete(`/automezzi/deleteAuto/${autoId}`);
        if (res.data.errorCode === 0) {
          setAuto(prev => prev.filter(a => a.autoId !== autoId));
          toast.success("✅ Automezzo eliminato con successo");
        } else {
          toast.warning(`⚠️ Errore: ${res.data.errorMessage}`);
        }
      } catch (err) {
        console.error("Errore durante eliminazione automezzo:", err);
        toast.error("❌ Errore durante la richiesta al server.");
      }
    });
  };
  

  const handleDeleteMoto = (motoId: number) => {
    mostraToastConferma("Sei sicuro di voler eliminare la moto?", async () => {
      try {
        const res = await axios.delete(`/automezzi/deleteMoto/${motoId}`);
        if (res.data.errorCode === 0) {
          setMoto(prev => prev.filter(m => m.motoId !== motoId));
          toast.success("✅ Moto eliminata con successo");
        } else {
          toast.warning(`⚠️ Errore: ${res.data.errorMessage}`);
        }
      } catch (err) {
        console.error("Errore durante eliminazione moto:", err);
        toast.error("❌ Errore durante la richiesta al server.");
      }
    });
  };

  const handleDeletTir = (tirId: number) => {
    mostraToastConferma("Sei sicuro di voler eliminare il Tir?", async () => {
      try {
        const res = await axios.delete(`/automezzi/deleteTir/${tirId}`);
        if (res.data.errorCode === 0) {
          setTir(prev => prev.filter(m => m.tirId !== tirId));
          toast.success("✅ Tir eliminato con successo");
        } else {
          toast.warning(`⚠️ Errore: ${res.data.errorMessage}`);
        }
      } catch (err) {
        console.error("Errore durante eliminazione tir:", err);
        toast.error("❌ Errore durante la richiesta al server.");
      }
    });
  };
  

  return (
    <div className="dashboard-wrapper">
      <div className="top-bar">
        <div className="user-info">Benvenuto, {nomeUtente}</div>
        <button className="logout-btn" onClick={handleLogout}>Logout</button>
      </div>

      <div className="main-layout">
        <div className="sidebar-left">
          <h3 onClick={() => setShowUsers(!showUsers)} className="menu-item">
            {showUsers ? '▼' : '▶'} Utenti
          </h3>
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

        <div className="main-content">
          <DashboardEnhancements
            userCount={users.length}
            autoCount={auto.length}
            motoCount={moto.length}
            tirCount={tir.length}
            latestUsers={users.slice(-3).reverse()}
            latestAuto={auto.slice(-3).reverse().map(a => ({
              modello: a.mezzoBean?.modello || '',
              targa: a.mezzoBean?.targa || ''
            }))}
            latestTir={tir.slice(-3).reverse().map(a => ({
              modello: a.mezzoBean?.modello || '',
              targa: a.mezzoBean?.targa || ''
            }))}
            latestMoto={moto.slice(-3).reverse().map(m => ({
              modello: m.mezzoBean?.modello || '',
              targa: m.mezzoBean?.targa || ''
            }))}
          />
        </div>
      </div>

      {showUsers && (
        <div className="floating-overlay">
          <div className="floating-user-box expanded">
            <div className="box-header">
              <h3>Lista Utenti</h3>
              <button className="close-btn" onClick={() => setShowUsers(false)}>✖</button>
            </div>
            <table className="floating-table">
              <thead>
                <tr>
                  <th>Nome</th>
                  <th>Azioni</th>
                </tr>
              </thead>
              <tbody>
                {users.map(user => (
                  <tr key={user.userId}>
                    <td>{user.nome} {user.cognome}</td>
                    <td>
                      <Link to={`/user/${user.userId}`}>
                        <button className="view-btn"><FontAwesomeIcon icon={faEye} /></button>
                      </Link>
                      <Link to={`/user/edit/${user.userId}`}>
                        <button className="edit-btn"><FontAwesomeIcon icon={faPen} /></button>
                      </Link>
                      <button className="delete-btn" onClick={() => handleDelete(user.userId)}>
                        <FontAwesomeIcon icon={faTrash} />
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      )}

      {showAuto && (
        <div className="floating-overlay">
          <div className="floating-user-box expanded">
            <div className="box-header">
              <h3>Lista Automezzi</h3>
              <button className="close-btn" onClick={() => setShowAuto(false)}>✖</button>
            </div>
            <table className="floating-table">
              <thead>
                <tr>
                  <th>Modello</th>
                  <th>Targa</th>
                  <th>Azioni</th>
                </tr>
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
                      <Link to={`/auto/edit/${a.autoId}`}>
                        <button className="edit-btn">
                          <FontAwesomeIcon icon={faPen} />
                        </button>
                      </Link>
                      <button className="delete-btn" onClick={() => handleDeleteAuto(a.autoId)}>
                        <FontAwesomeIcon icon={faTrash} />
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
                <tr>
                  <th>Modello</th>
                  <th>Targa</th>
                  <th>Azioni</th>
                </tr>
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
                      <Link to={`/moto/edit/${m.motoId}`}>
                        <button className="edit-btn">
                          <FontAwesomeIcon icon={faPen} />
                        </button>
                      </Link>
                      <button className="delete-btn" onClick={() => handleDeleteMoto(m.motoId)}>
                        <FontAwesomeIcon icon={faTrash} />
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
          <tr>
            <th>Modello</th>
            <th>Targa</th>
            <th>Azioni</th>
          </tr>
        </thead>
        <tbody>
          {tir.map(t => (
            <tr key={t.tirId}>
              <td>{t.mezzoBean?.modello}</td>
              <td>{t.mezzoBean?.targa}</td>
              <td>
                <Link to={`/tir/${t.tirId}`}>
                  <button className="view-btn"><FontAwesomeIcon icon={faEye} /></button>
                </Link>
                <Link to={`/tir/edit/${t.tirId}`}>
                  <button className="edit-btn">
                    <FontAwesomeIcon icon={faPen} />
                  </button>
                </Link>
                <button className="delete-btn" onClick={() => handleDeletTir(t.tirId)}>
                  <FontAwesomeIcon icon={faTrash} />
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
  );
};

export default Dashboard;