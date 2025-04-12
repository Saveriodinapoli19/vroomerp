import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from '../api/axiosInstance';
import '../App.css';
import BackToDashboard from '../pages/BackToDashboard';
interface MezzoBean {
  mezzoId: number;
  modello: string;
  targa: string;
  marca: string;
  colore: string;
  annoImmatricolazione: number;
  prezzo: number;
}

interface Moto {
  motoId: number;
  consumoMedio: number;
  cilindrata: string;
  motoreEuro?: string;
  alimentazione?: string;
  tipoMoto?: string;
  raffreddamento?: string;
  tipoMotore: string;
  potenzaKw: string;
  peso: string;
  mezzoBean?: MezzoBean;
}  
            

const MotoDetail: React.FC = () => {
  const { motoId } = useParams<{ motoId: string }>();
  const [moto, setMoto] = useState<Moto | null>(null);
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (!token) {
      navigate('/login');
      return;
    }

    axios.get(`/automezzi/findMotoById/${motoId}`, {
      headers: { Authorization: `Bearer ${token}` }
    })
    .then(res => {
      if (res.data.errorCode === 0) {
        setMoto(res.data.motoBean);
      } else {
        console.warn('Errore:', res.data.errorMessage);
      }
    })
    .catch(err => {
      console.error('Errore nel caricamento auto:', err);
    });
  }, [motoId, navigate]);

  if (!moto) return <p>Caricamento...</p>;

  return (
    <div className="detail-container">
      <BackToDashboard />
      <h2>Dettaglio Moto</h2>
      <p><strong>Modello:</strong> {moto.mezzoBean?.modello}</p>
      <p><strong>Targa:</strong> {moto.mezzoBean?.targa}</p>
      <p><strong>Marca:</strong> {moto.mezzoBean?.marca}</p>
      <p><strong>Colore:</strong> {moto.mezzoBean?.colore}</p>
      <p><strong>Anno Immatricolazione:</strong> {moto.mezzoBean?.annoImmatricolazione}</p>
      <p><strong>Prezzo:</strong> {moto.mezzoBean?.prezzo} €</p>
      <hr />
      <p><strong>Alimentazione:</strong> {moto.alimentazione}</p>
      <p><strong>Motore Euro:</strong> {moto.motoreEuro}</p>
      <p><strong>Cilindrata:</strong> {moto.cilindrata} cc</p>
      <p><strong>Peso:</strong> {moto.peso}</p>
      <p><strong>Potenza (kW):</strong> {moto.potenzaKw}</p>
      <p><strong>Consumo Medio:</strong> {moto.consumoMedio} L/100km</p>
      <p><strong>Raffreddamento:</strong> {moto.raffreddamento}</p>
      <p><strong>Tipo Moto:</strong> {moto.tipoMoto}</p>
      <p><strong>Tipo Motore:</strong> {moto.tipoMotore}</p>
    </div>
  );
};

export default MotoDetail;

