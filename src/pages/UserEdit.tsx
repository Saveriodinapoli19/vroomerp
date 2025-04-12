import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from '../api/axiosInstance';
import BackToDashboard from '../pages/BackToDashboard';
import { toast } from 'react-toastify';

const UserEdit: React.FC = () => {
  const { userId } = useParams<{ userId: string }>();
  const [nome, setNome] = useState('');
  const [cognome, setCognome] = useState('');
  const [telefono, setTelefono] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    axios.get(`/user/findById/${userId}`)
      .then(res => {
        const user = res.data.userBean;
        setNome(user.nome);
        setCognome(user.cognome);
        setTelefono(user.telefono);
      })
      .catch(err => {
        toast.error('Errore nel recupero dati utente');
      });
  }, [userId]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const response = await axios.post('/user/updateUser', {
        userBean: {
          userId,
          nome,
          cognome,
          telefono,
          password: password || undefined
        }
      });

      if (response.data.errorCode === 0) {
        toast.success(' Utente aggiornato con successo');
        navigate('/');
      } else {
        toast.error(response.data.errorMessage);
      }
    } catch (err) {
      toast.error('❌ Errore durante l\'aggiornamento');
    }
  };

  return (
    <div className="form-container">
       <BackToDashboard />
      <h2>Modifica Utente</h2>
      <form onSubmit={handleSubmit} className="form">
        <input placeholder="Nome" value={nome} onChange={e => setNome(e.target.value)} required />
        <input placeholder="Cognome" value={cognome} onChange={e => setCognome(e.target.value)} required />
        <input placeholder="Telefono" value={telefono} onChange={e => setTelefono(e.target.value)} required />
        <input type="password" placeholder="Nuova Password (opzionale)" value={password} onChange={e => setPassword(e.target.value)} />
        <button type="submit">💾 Salva</button>
      </form>
    </div>
  );
};

export default UserEdit;
