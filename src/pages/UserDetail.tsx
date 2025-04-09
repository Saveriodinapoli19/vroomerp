import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from '../api/axiosInstance'; // usa la nuova istanza
import '../App.css';


const UserDetail: React.FC = () => {
  const { id } = useParams();
  const [user, setUser] = useState<any>(null);
  const navigate = useNavigate();

  useEffect(() => {
    axios.get(`/user/findById/${id}`)
      .then(res => setUser(res.data))
      .catch(err => {
        console.error('Errore nel recupero utente:', err);
        navigate('/login');
      });
  }, [id, navigate]);

  if (!user) return <p>Caricamento...</p>;

  return (
    <div style={{ padding: '2rem' }}>
      <h2>Dettaglio Utente</h2>
      <p><strong>Nome:</strong> {user.nome}</p>
      <p><strong>Cognome:</strong> {user.cognome}</p>
    </div>
  );
};

export default UserDetail;
