import React, { useState, useEffect } from 'react';
import axios from '../api/axiosInstance';
import { useNavigate } from 'react-router-dom';
import '../App.css';
import { toast } from 'react-toastify';

interface Ruolo {
  ruoloUtenteId: number;
  descrizione: string;
}

const UserCreate: React.FC = () => {
  const [nome, setNome] = useState('');
  const [cognome, setCognome] = useState('');
  const [email, setEmail] = useState('');
  const [telefono, setTelefono] = useState('');
  const [password, setPassword] = useState('');
  const [ruolo, setRuolo] = useState('');
  const [ruoli, setRuoli] = useState<Ruolo[]>([]);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    axios.get('/user/findAllRuoloUtente')
      .then(res => {
        if (res.data.errorCode === 0 && Array.isArray(res.data.listaRuoloUtenteBean)) {
            setRuoli(res.data.listaRuoloUtenteBean);
          } else {
          console.warn('⚠️ Nessun ruolo ricevuto');
        }
      })
      .catch(err => {
        console.error('Errore nel recupero ruoli:', err);
      });
  }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');

    try {
      const res = await axios.post('/user/insertUser', {
        userBean: {
          nome,
          cognome,
          email,
          telefono,
          password,
          extRuoloUtenteId: ruolo
        }
      });

      if (res.data.errorCode === 0) {
        toast.success('Utente creato correttamente!');
        navigate('/');
      }else {
        setError(res.data.errorMessage);
      }
    } catch (err) {
      console.error('Errore creazione utente:', err);
      setError('Errore durante la creazione.');
    }
  };

  return (
    <div className="form-container">
      <h2>Crea Nuovo Utente</h2>
      <form onSubmit={handleSubmit} className="form" autoComplete="off">
        <input placeholder="Nome" value={nome} onChange={(e) => setNome(e.target.value)} required autoComplete="off" />
        <input placeholder="Cognome" value={cognome} onChange={(e) => setCognome(e.target.value)} required autoComplete="off" />
        <input type="email" placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} required autoComplete="off" />
        <input placeholder="Telefono" value={telefono} onChange={(e) => setTelefono(e.target.value)} required autoComplete="off" />
        <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} required autoComplete="new-password" />
        
        <select value={ruolo} onChange={(e) => setRuolo(e.target.value)} required>
          <option value="">Seleziona ruolo</option>
          {ruoli.map((r) => (
            <option key={r.ruoloUtenteId} value={r.ruoloUtenteId}>{r.descrizione}</option>
          ))}
        </select>

        <button type="submit">Salva</button>
        {error && <p className="error-text">{error}</p>}
      </form>
    </div>
  );
};

export default UserCreate;
