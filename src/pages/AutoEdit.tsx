import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from '../api/axiosInstance';
import '../App.css';
import { toast } from 'react-toastify';
interface MezzoBean {
  mezzoId: number;
  modello: string;
  targa: string;
  marca: string;
  colore: string;
  annoImmatricolazione: number;
  prezzo: number;
  dataAcquisto?: string;
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
  extTipoAuto: number;
  extTipoAlimentazioneId: number;
  extMotoreEuro: number;
  mezzoBean?: MezzoBean;
}

const AutoEdit: React.FC = () => {
  const { autoId } = useParams<{ autoId: string }>();
  const navigate = useNavigate();

  const [auto, setAuto] = useState<Auto | null>(null);
  const [alimentazioni, setAlimentazioni] = useState<any[]>([]);
  const [motori, setMotori] = useState<any[]>([]);
  const [tipiAuto, setTipiAuto] = useState<any[]>([]);
  const [error, setError] = useState('');

  useEffect(() => {
    if (!autoId) return;

    axios.get(`/automezzi/findAutoById/${autoId}`)
      .then(res => setAuto(res.data.autoBean))
      .catch(() => setError('Errore nel caricamento auto'));

    axios.get('/automezzi/findAllTipoAlimentazione')
      .then(res => setAlimentazioni(res.data.tipoAlimentazioneBeanList));

    axios.get('/automezzi/findAllMotoreEuro')
      .then(res => setMotori(res.data.motoreEuroBeanList));

    axios.get('/automezzi/findAllTipoAuto')
      .then(res => setTipiAuto(res.data.tipoAutoBeanList));
  }, [autoId]);

  const handleChange = (field: string, value: any) => {
    setAuto(prev => prev ? { ...prev, [field]: value } : prev);
  };

  const handleMezzoChange = (field: string, value: any) => {
    setAuto(prev => prev && prev.mezzoBean ? {
      ...prev,
      mezzoBean: { ...prev.mezzoBean, [field]: value }
    } : prev);
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    setError('');

    axios.post('/automezzi/updateAuto', {
      autoBean: auto,
      mezzoBean: auto?.mezzoBean
    })
      .then(res => {
        if (res.data.errorCode === 0) {
            toast('✅ Modificato con successo');
          navigate('/');
        } else {
          setError(res.data.errorMessage);
        }
      })
      .catch(() => setError('Errore durante la richiesta'));
  };

  if (!auto) return <p>Caricamento...</p>;

  return (
    <div className="form-container">
      <h2>Modifica Automezzo</h2>
      <form onSubmit={handleSubmit} className="form">
        <input value={auto.mezzoBean?.modello} onChange={e => handleMezzoChange('modello', e.target.value)} placeholder="Modello" required />
        <input value={auto.mezzoBean?.targa} onChange={e => handleMezzoChange('targa', e.target.value)} placeholder="Targa" required />
        <input value={auto.mezzoBean?.marca} onChange={e => handleMezzoChange('marca', e.target.value)} placeholder="Marca" required />
        <input value={auto.mezzoBean?.colore} onChange={e => handleMezzoChange('colore', e.target.value)} placeholder="Colore" required />
        <input type="number" value={auto.mezzoBean?.annoImmatricolazione} onChange={e => handleMezzoChange('annoImmatricolazione', parseInt(e.target.value))} placeholder="Anno Immatricolazione" required />
        <input type="number" value={auto.mezzoBean?.prezzo} onChange={e => handleMezzoChange('prezzo', parseFloat(e.target.value))} placeholder="Prezzo" required />

        <input type="number" value={auto.numeroPosti} onChange={e => handleChange('numeroPosti', parseInt(e.target.value))} placeholder="Numero Posti" required />
        <input type="number" value={auto.numeroPorte} onChange={e => handleChange('numeroPorte', parseInt(e.target.value))} placeholder="Numero Porte" required />
        <input value={auto.potenzaKw} onChange={e => handleChange('potenzaKw', e.target.value)} placeholder="Potenza Kw" required />
        <input value={auto.cilindrata} onChange={e => handleChange('cilindrata', e.target.value)} placeholder="Cilindrata" required />
        <input type="number" step="0.1" value={auto.consumoMedio} onChange={e => handleChange('consumoMedio', parseFloat(e.target.value))} placeholder="Consumo Medio" required />

        <select value={auto.extTipoAlimentazioneId} onChange={e => handleChange('extTipoAlimentazioneId', parseInt(e.target.value))} required>
          <option value="">Seleziona Alimentazione</option>
          {alimentazioni.map(al => <option key={al.idTipoAlimentazione} value={al.idTipoAlimentazione}>{al.descrizione}</option>)}
        </select>

        <select value={auto.extMotoreEuro} onChange={e => handleChange('extMotoreEuro', parseInt(e.target.value))} required>
          <option value="">Seleziona Motore Euro</option>
          {motori.map(m => <option key={m.motoreEuroId} value={m.motoreEuroId}>{m.descrizione}</option>)}
        </select>

        <select value={auto.extTipoAuto} onChange={e => handleChange('extTipoAuto', parseInt(e.target.value))} required>
          <option value="">Seleziona Tipo Auto</option>
          {tipiAuto.map(t => <option key={t.tipoAutoId} value={t.tipoAutoId}>{t.descrizione}</option>)}
        </select>

        <button type="submit">Salva Modifiche</button>
        {error && <p className="error-text">{error}</p>}
      </form>
    </div>
  );
};

export default AutoEdit;
