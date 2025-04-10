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

const Dashboard: React.FC = () => {
  const [users, setUsers] = useState<User[]>([]);
  const [auto, setAuto] = useState<Auto[]>([]);
  const [showUsers, setShowUsers] = useState(false);
  const [showAuto, setShowAuto] = useState(false);
  const navigate = useNavigate();

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
  }, [navigate]);

  const handleLogout = () => {
    localStorage.removeItem('token');
    navigate('/login');
  };

  const handleAddUser = () => {
    navigate('/user/new');
  };

  const handleDelete = async (userId: number) => {
    if (!window.confirm(`Sei sicuro di voler eliminare l'utente?`)) return;

    try {
      const res = await axios.delete(`/user/deleteUser/${userId}`);
      if (res.data.errorCode === 0) {
        setUsers(prev => prev.filter(u => u.userId !== userId));
        toast('✅ Utente eliminato con successo');
      } else {
        toast.warning(`⚠️ Errore: ${res.data.errorMessage}`);
      }
    } catch (err) {
      console.error('Errore durante eliminazione utente:', err);
      toast.error('❌ Errore durante la richiesta al server.');
    }
  };

  const handleDeleteAuto = async (autoId: number) => {
    if (!window.confirm("Sei sicuro di voler eliminare questo automezzo?")) return;

    try {
      const res = await axios.delete(`/automezzi/deleteAuto/${autoId}`);
      if (res.data.errorCode === 0) {
        setAuto(prev => prev.filter(a => a.autoId !== autoId));
        toast("✅ Automezzo eliminato con successo");
      } else {
        toast.warning(`⚠️ Errore: ${res.data.errorMessage}`);
      }
    } catch (err) {
      console.error("Errore durante eliminazione automezzo:", err);
      toast.error("❌ Errore durante la richiesta al server.");
    }
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
        </div>

        <div className="main-content">
          <DashboardEnhancements
            userCount={users.length}
            autoCount={auto.length}
            latestUsers={users.slice(-3).reverse()}
            latestAuto={auto.slice(-3).reverse().map(a => ({
              modello: a.mezzoBean?.modello || '',
              targa: a.mezzoBean?.targa || ''
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
    </div>
  );
};

export default Dashboard;