import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from '../api/axiosInstance';
import '../App.css';
import { toast } from 'react-toastify';
import BackToDashboard from '../pages/BackToDashboard';

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

interface Tir {
  tirId: number;
  altezzaCassone: string;
  extMotoreEuroId: number;
  extTipoAlimentazioneId: number;
  extTipoRimorchioId: number;
  motoreEuro?: string;
  alimentazione?: string;
  rimorchio?: string;
  lunghezza: number;
  numeroAssi: number;
  portataMax: number;
  cilindrata: string;
  mezzoBean?: MezzoBean;
}

const TirEdit: React.FC = () => {
  const { tirId } = useParams<{ tirId: string }>();
  const navigate = useNavigate();

  const [tir, setTir] = useState<Tir | null>(null);
  const [alimentazioni, setAlimentazioni] = useState<any[]>([]);
  const [motori, setMotori] = useState<any[]>([]);
  const [rimorchi, setRimorchi] = useState<any[]>([]);
  const [error, setError] = useState('');

  useEffect(() => {
    if (!tirId) return;

    axios.get(`/automezzi/findTirById/${tirId}`)
      .then(res => setTir(res.data.tirBean))
      .catch(() => setError('Errore nel caricamento tir'));

    axios.get('/automezzi/findAllTipoAlimentazione')
      .then(res => setAlimentazioni(res.data.tipoAlimentazioneBeanList));

    axios.get('/automezzi/findAllMotoreEuro')
      .then(res => setMotori(res.data.motoreEuroBeanList));

    axios.get('/automezzi/findAllTipoRimorchio')
      .then(res => setRimorchi(res.data.tipoRimorchioBeanList));
  }, [tirId]);

  const handleChange = (field: string, value: any) => {
    setTir(prev => prev ? { ...prev, [field]: value } : prev);
  };

  const handleMezzoChange = (field: string, value: any) => {
    setTir(prev => prev && prev.mezzoBean ? {
      ...prev,
      mezzoBean: { ...prev.mezzoBean, [field]: value }
    } : prev);
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    setError('');

    axios.post('/automezzi/updateTir', {
      tirBean: tir,
      mezzoBean: tir?.mezzoBean
    })
      .then(res => {
        if (res.data.errorCode === 0) {
          toast('✅ Tir modificato con successo');
          navigate('/');
        } else {
          setError(res.data.errorMessage);
        }
      })
      .catch(() => setError('Errore durante la richiesta'));
  };

  if (!tir) return <p>Caricamento...</p>;

  return (
    <div className="form-container">
      <BackToDashboard />
      <h2>Modifica Tir</h2>
      <form onSubmit={handleSubmit} className="form">
        <input value={tir.mezzoBean?.marca || ''} onChange={e => handleMezzoChange('marca', e.target.value)} placeholder="Marca" required />
        <input value={tir.mezzoBean?.modello || ''} onChange={e => handleMezzoChange('modello', e.target.value)} placeholder="Modello" required />
        <input value={tir.mezzoBean?.targa || ''} onChange={e => handleMezzoChange('targa', e.target.value)} placeholder="Targa" required />
        <input value={tir.mezzoBean?.colore || ''} onChange={e => handleMezzoChange('colore', e.target.value)} placeholder="Colore" required />
        <input type="number" value={tir.mezzoBean?.annoImmatricolazione || 0} onChange={e => handleMezzoChange('annoImmatricolazione', parseInt(e.target.value))} placeholder="Anno Immatricolazione" required />
        <input type="number" value={tir.mezzoBean?.prezzo || 0} onChange={e => handleMezzoChange('prezzo', parseFloat(e.target.value))} placeholder="Prezzo" required />

        <input type="number" value={tir.numeroAssi} onChange={e => handleChange('numeroAssi', parseInt(e.target.value))} placeholder="Numero Assi" required />
        <input type="number" value={tir.portataMax} onChange={e => handleChange('portataMax', parseFloat(e.target.value))} placeholder="Portata Max" required />
        <input value={tir.cilindrata} onChange={e => handleChange('cilindrata', e.target.value)} placeholder="Cilindrata" required />
        <input type="number" value={tir.lunghezza} onChange={e => handleChange('lunghezza', parseFloat(e.target.value))} placeholder="Lunghezza" required />
        <input value={tir.altezzaCassone} onChange={e => handleChange('altezzaCassone', e.target.value)} placeholder="Altezza Cassone" required />

        <select value={tir.extTipoAlimentazioneId} onChange={e => handleChange('extTipoAlimentazioneId', parseInt(e.target.value))} required>
          <option value="">Seleziona Alimentazione</option>
          {alimentazioni.map(a => (
            <option key={a.idTipoAlimentazione} value={a.idTipoAlimentazione}>{a.descrizione}</option>
          ))}
        </select>

        <select value={tir.extMotoreEuroId} onChange={e => handleChange('extMotoreEuroId', parseInt(e.target.value))} required>
          <option value="">Seleziona Motore Euro</option>
          {motori.map(m => (
            <option key={m.motoreEuroId} value={m.motoreEuroId}>{m.descrizione}</option>
          ))}
        </select>

        <select value={tir.extTipoRimorchioId} onChange={e => handleChange('extTipoRimorchioId', parseInt(e.target.value))} required>
          <option value="">Seleziona Tipo Rimorchio</option>
          {rimorchi.map(r => (
            <option key={r.tipoRimorchioId} value={r.tipoRimorchioId}>{r.descrizione}</option>
          ))}
        </select>

        <button type="submit">💾 Salva</button>
        {error && <p className="error-text">{error}</p>}
      </form>
    </div>
  );
};

export default TirEdit;
