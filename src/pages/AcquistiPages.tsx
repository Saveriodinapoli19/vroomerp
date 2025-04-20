import React, { useEffect, useState } from 'react';
import axios from '../api/axiosInstance';
import { useNavigate } from 'react-router-dom';
import './AcquistiPage.css';
import { toast } from 'react-toastify';
import BackToDashboard from './BackToDashboard';

interface MezzoBean {
  marca: string;
  modello: string;
  targa: string;
  colore: string;
  prezzo: number;
  annoImmatricolazione: number;
}

interface AutoBean {
  mezzoBean: MezzoBean;
}

interface MotoBean {
  mezzoBean: MezzoBean;
}

interface TirBean {
  mezzoBean: MezzoBean;
}

interface Acquisto {
  autoBean?: AutoBean;
  motoBean?: MotoBean;
  tirBean?: TirBean;
}

const AcquistiPage: React.FC = () => {
  const [acquisti, setAcquisti] = useState<Acquisto[]>([]);
  const [spesaTotale, setSpesaTotale] = useState<number>(0);
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (!token) {
      navigate('/login');
      return;
    }

    axios.get('/acquisti/findAcquistiByUserId', {
      headers: { Authorization: `Bearer ${token}` }
    })
      .then(res => {
        if (res.data.errorCode === 0) {
          setAcquisti(res.data.acquistiBeanList || []);
          setSpesaTotale(res.data.spesaTotale || 0);
        } else {
          toast.error("Errore: " + res.data.errorMessage);
        }
      })
      .catch(() => toast.error("Errore di rete durante il recupero degli acquisti"));
  }, [navigate]);

  const renderMezzo = (mezzo: MezzoBean | undefined) => {
    if (!mezzo) return null;
    return (
      <div className="mezzo-info">
        <p><strong>Marca:</strong> {mezzo.marca}</p>
        <p><strong>Modello:</strong> {mezzo.modello}</p>
        <p><strong>Targa:</strong> {mezzo.targa}</p>
        <p><strong>Colore:</strong> {mezzo.colore}</p>
        <p><strong>Prezzo:</strong> €{mezzo.prezzo}</p>
        <p><strong>Anno Immatricolazione:</strong> {mezzo.annoImmatricolazione}</p>
      </div>
    );
  };

  return (
    <div className="acquisti-container">
      <BackToDashboard />
      <h2 className="acquisti-title">I tuoi Acquisti</h2>

      {acquisti.length === 0 ? (
        <div className="no-acquisti">Nessun acquisto effettuato.</div>
      ) : (
        acquisti.map((acq, idx) => {
          let tipo = '';
          let mezzo: MezzoBean | undefined;

          if (acq.autoBean) {
            tipo = 'Auto';
            mezzo = acq.autoBean.mezzoBean;
          } else if (acq.motoBean) {
            tipo = 'Moto';
            mezzo = acq.motoBean.mezzoBean;
          } else if (acq.tirBean) {
            tipo = 'Tir';
            mezzo = acq.tirBean.mezzoBean;
          }

          return (
            <div className="acquisto-card" key={idx}>
              <div className="acquisto-header">Acquisto {tipo}</div>
              {renderMezzo(mezzo)}
            </div>
          );
        })
      )}

      {acquisti.length > 0 && (
        <div className="spesa-totale">
          Totale speso: €{spesaTotale.toFixed(2)}
        </div>
      )}
    </div>
  );
};

export default AcquistiPage;
