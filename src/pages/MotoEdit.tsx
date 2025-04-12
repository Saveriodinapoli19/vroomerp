import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from '../api/axiosInstance';
import '../App.css';
import BackToDashboard from '../pages/BackToDashboard';
import { toast } from 'react-toastify';

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
  potenzaKw: string;
  peso: string;
  cilindrata: string;
  raffreddamento: string;
  tipoMotore: string;
  extTipoMotoreId?: number;
  extMotoreEuroId?: number;
  extTipoAlimentazione?: number;
  extTipoMotoId?: number;
  alimentazione?: string;
  motoreEuro?: string;
  tipoMoto?: string;
  mezzoBean?: MezzoBean;
}

const MotoEdit: React.FC = () => {
  const { motoId } = useParams<{ motoId: string }>();
  const navigate = useNavigate();

  const [moto, setMoto] = useState<Moto | null>(null);
  const [alimentazioni, setAlimentazioni] = useState<any[]>([]);
  const [motori, setMotori] = useState<any[]>([]);
  const [tipoMoto, setTipoMoto] = useState<any[]>([]);
  const [tipoMotore, setTipoMotore] = useState<any[]>([]);
  const [error, setError] = useState('');

  useEffect(() => {
    // Carica dati selezione
    axios.get('/automezzi/findAllTipoAlimentazione')
      .then(res => setAlimentazioni(res.data.tipoAlimentazioneBeanList || []));
    axios.get('/automezzi/findAllMotoreEuro')
      .then(res => setMotori(res.data.motoreEuroBeanList || []));
    axios.get('/automezzi/findAllTipoMoto')
      .then(res => setTipoMoto(res.data.tipoMotoBeanList || []));
    axios.get('/automezzi/findAllTipoMotore')
      .then(res => setTipoMotore(res.data.tipoMotoreBeanList || []));

    // Carica la moto da modificare
    axios.get(`/automezzi/findMotoById/${motoId}`)
      .then(res => {
        if (res.data.errorCode === 0) {
          setMoto(res.data.motoBean);
        } else {
          setError(res.data.errorMessage);
        }
      })
      .catch(() => setError('Errore nel caricamento della moto'));
  }, [motoId]);

  const handleChange = (field: string, value: any) => {
    setMoto(prev => prev ? { ...prev, [field]: value } : prev);
  };

  const handleMezzoChange = (field: string, value: any) => {
    setMoto(prev => prev && prev.mezzoBean ? {
      ...prev,
      mezzoBean: { ...prev.mezzoBean, [field]: value }
    } : prev);
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    setError('');

    axios.post('/automezzi/updateMoto', {
      motoBean: moto,
      mezzoBean: moto?.mezzoBean
    })
      .then(res => {
        if (res.data.errorCode === 0) {
          toast.success('✅ Moto modificata con successo');
          navigate('/');
        } else {
          setError(res.data.errorMessage);
        }
      })
      .catch(() => setError('Errore durante la richiesta'));
  };

  if (!moto) return <p>Caricamento...</p>;

  return (
    <div className="detail-container">
      <BackToDashboard />
      <h2>Modifica Moto</h2>

      <form onSubmit={handleSubmit} className="form">
        {/* Mezzo */}
        <input value={moto.mezzoBean?.marca || ''} onChange={e => handleMezzoChange('marca', e.target.value)} placeholder="Marca" required />
        <input value={moto.mezzoBean?.modello || ''} onChange={e => handleMezzoChange('modello', e.target.value)} placeholder="Modello" required />
        <input value={moto.mezzoBean?.targa || ''} onChange={e => handleMezzoChange('targa', e.target.value)} placeholder="Targa" required />
        <input value={moto.mezzoBean?.colore || ''} onChange={e => handleMezzoChange('colore', e.target.value)} placeholder="Colore" required />
        <input type="number" value={moto.mezzoBean?.annoImmatricolazione || 0} onChange={e => handleMezzoChange('annoImmatricolazione', parseInt(e.target.value))} placeholder="Anno Immatricolazione" required />
        <input type="number" value={moto.mezzoBean?.prezzo || 0} onChange={e => handleMezzoChange('prezzo', parseFloat(e.target.value))} placeholder="Prezzo" required />

        {/* Moto */}
        <input value={moto.potenzaKw || ''} onChange={e => handleChange('potenzaKw', e.target.value)} placeholder="Potenza kW" required />
        <input value={moto.peso || ''} onChange={e => handleChange('peso', e.target.value)} placeholder="Peso (kg)" required />
        <input value={moto.cilindrata || ''} onChange={e => handleChange('cilindrata', e.target.value)} placeholder="Cilindrata" required />
        <input value={moto.raffreddamento || ''} onChange={e => handleChange('raffreddamento', e.target.value)} placeholder="Raffreddamento" required />

        <select value={moto.extTipoMotoreId || ''} onChange={e => handleChange('extTipoMotoreId', parseInt(e.target.value))} required>
          <option value="">Seleziona Tipo Motore</option>
          {tipoMotore.map(t => (
            <option key={t.tipoMotoreId} value={t.tipoMotoreId}>{t.descrizione}</option>
          ))}
        </select>

        <select value={moto.extMotoreEuroId || ''} onChange={e => handleChange('extMotoreEuroId', parseInt(e.target.value))} required>
          <option value="">Seleziona Motore Euro</option>
          {motori.map(m => (
            <option key={m.motoreEuroId} value={m.motoreEuroId}>{m.descrizione}</option>
          ))}
        </select>

        <select value={moto.extTipoAlimentazione || ''} onChange={e => handleChange('extTipoAlimentazione', parseInt(e.target.value))} required>
          <option value="">Seleziona Alimentazione</option>
          {alimentazioni.map(a => (
            <option key={a.idTipoAlimentazione} value={a.idTipoAlimentazione}>{a.descrizione}</option>
          ))}
        </select>

        <select value={moto.extTipoMotoId || ''} onChange={e => handleChange('extTipoMotoId', parseInt(e.target.value))} required>
          <option value="">Seleziona Tipo Moto</option>
          {tipoMoto.map(tm => (
            <option key={tm.tipoMotoId} value={tm.tipoMotoId}>{tm.descrizione}</option>
          ))}
        </select>

        <button type="submit">💾 Salva</button>
        {error && <p className="error-text">{error}</p>}
      </form>
    </div>
  );
};

export default MotoEdit;
