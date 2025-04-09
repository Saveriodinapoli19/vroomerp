import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from '../api/axiosInstance';
import '../App.css';

interface User {
    userId: number;
  nome: string;
  cognome: string;
  email: string;
  ruolo?: string;
}

const UserDetail: React.FC = () => {
  const { userId } = useParams<{ userId: string }>();
  const [user, setUser] = useState<User | null>(null);
  const navigate = useNavigate();
 // console.log('📌 id utente da URL:', userId);
  useEffect(() => {
    const token = localStorage.getItem('token');
    if (!token) {
      navigate('/login');
      return;
    }

    axios.get(`/user/findById/${userId}`, {
      headers: { Authorization: `Bearer ${token}` }
     
    })
    .then(res => {
      if (res.data.errorCode === 0) {
        setUser(res.data.userBean);
      } else {
        console.warn('⚠️ Errore utente:', res.data.errorMessage);
      }
    })
    .catch(err => {
      console.error('❌ Errore nel caricamento utente:', err);
    });
  }, [userId, navigate]);

  

  if (!user) return <p>Caricamento in corso...</p>;

  return (
    <div className="detail-container">
      <h2>Dettaglio Utente</h2>
      <p><strong>Nome:</strong> {user.nome}</p>
      <p><strong>Cognome:</strong> {user.cognome}</p>
      <p><strong>Email:</strong> {user.email}</p>
      {user.ruolo && <p><strong>Ruolo:</strong> {user.ruolo}</p>}
    </div>
  );
};

export default UserDetail;
