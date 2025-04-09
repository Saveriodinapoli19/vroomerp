import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from '../api/axiosInstance'; // usa la nuova istanza
import '../App.css';


const AutoDetail: React.FC = () => {
  const { id } = useParams();
  const [auto, setAuto] = useState<any>(null);
  const navigate = useNavigate();

  useEffect(() => {
    axios.get(`/automezzi/finAutoById/${id}`)
      .then(res => setAuto(res.data))
      .catch(err => {
        console.error('Errore nel recupero automezzo:', err);
        navigate('/login');
      });
  }, [id, navigate]);

  if (!auto) return <p>Caricamento...</p>;

  return (
    <div style={{ padding: '2rem' }}>
      <h2>Dettaglio Automezzo</h2>
      <p><strong>Modello:</strong> {auto.modello}</p>
      <p><strong>Targa:</strong> {auto.targa}</p>
    </div>
  );
};

export default AutoDetail;
