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

interface Auto {
  autoId: number;
  consumoMedio: number;
  cilindrata: string;
  motoreEuro?: string;
  alimentazione?: string;
  descTipoAuto?: string;
  numeroPorte: number;
  numeroPosti: number;
  potenzaKw: string;
  mezzoBean?: MezzoBean;
}

const AutoDetail: React.FC = () => {
  const { autoId } = useParams<{ autoId: string }>();
  const [auto, setAuto] = useState<Auto | null>(null);
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (!token) {
      navigate('/login');
      return;
    }

    axios.get(`/automezzi/findAutoById/${autoId}`, {
      headers: { Authorization: `Bearer ${token}` }
    })
    .then(res => {
      if (res.data.errorCode === 0) {
        setAuto(res.data.autoBean);
      } else {
        console.warn('Errore:', res.data.errorMessage);
      }
    })
    .catch(err => {
      console.error('Errore nel caricamento auto:', err);
    });
  }, [autoId, navigate]);

  if (!auto) return <p>Caricamento...</p>;

  return (
    <div className="detail-container">
      <BackToDashboard />
      <h2>Dettaglio Automezzo</h2>
      <p><strong>Modello:</strong> {auto.mezzoBean?.modello}</p>
      <p><strong>Targa:</strong> {auto.mezzoBean?.targa}</p>
      <p><strong>Marca:</strong> {auto.mezzoBean?.marca}</p>
      <p><strong>Colore:</strong> {auto.mezzoBean?.colore}</p>
      <p><strong>Anno Immatricolazione:</strong> {auto.mezzoBean?.annoImmatricolazione}</p>
      <p><strong>Prezzo:</strong> {auto.mezzoBean?.prezzo} €</p>
      <hr />
      <p><strong>Alimentazione:</strong> {auto.alimentazione}</p>
      <p><strong>Motore Euro:</strong> {auto.motoreEuro}</p>
      <p><strong>Cilindrata:</strong> {auto.cilindrata} cc</p>
      <p><strong>Potenza (kW):</strong> {auto.potenzaKw}</p>
      <p><strong>Consumo Medio:</strong> {auto.consumoMedio} L/100km</p>
      <p><strong>Numero Porte:</strong> {auto.numeroPorte}</p>
      <p><strong>Numero Posti:</strong> {auto.numeroPosti}</p>
      <p><strong>Tipo Auto:</strong> {auto.descTipoAuto}</p>
    </div>
  );
};

export default AutoDetail;

