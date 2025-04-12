import React, { useEffect, useState } from 'react';
import axios from '../api/axiosInstance';
import { useNavigate } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import '../App.css';
import BackToDashboard from '../pages/BackToDashboard';

const TirCreate: React.FC = () => {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    portataMax: '',
    numeroAssi: '',
    cilindrata: '',
    lunghezza: '',
    altezzaCassone: '',
    extTipoAlimentazioneId: '',
    extMotoreEuroId: '',
    extTipoRimorchioId: '',
    marca: '',
    modello: '',
    targa: '',
    colore: '',
    prezzo: '',
    annoImmatricolazione: '',
    dataAcquisto: ''
  });

  const [alimentazioni, setAlimentazioni] = useState<any[]>([]);
  const [motori, setMotori] = useState<any[]>([]);
  const [rimorchi, setRimorchi] = useState<any[]>([]);

  useEffect(() => {
    axios.get('/automezzi/findAllTipoAlimentazione')
      .then(res => setAlimentazioni(res.data.tipoAlimentazioneBeanList || []));

    axios.get('/automezzi/findAllMotoreEuro')
      .then(res => setMotori(res.data.motoreEuroBeanList || []));

    axios.get('/automezzi/findAllTipoRimorchio')
      .then(res => setRimorchi(res.data.tipoRimorchioBeanList || []));
  }, []);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    const tirBean = {
      portataMax: parseFloat(form.portataMax),
      numeroAssi: parseInt(form.numeroAssi),
      cilindrata: form.cilindrata,
      lunghezza: parseFloat(form.lunghezza),
      altezzaCassone: form.altezzaCassone,
      extTipoAlimentazioneId: parseInt(form.extTipoAlimentazioneId),
      extMotoreEuroId: parseInt(form.extMotoreEuroId),
      extTipoRimorchioId: parseInt(form.extTipoRimorchioId)
    };

    const mezzoBean = {
      marca: form.marca,
      modello: form.modello,
      targa: form.targa,
      colore: form.colore,
      prezzo: parseFloat(form.prezzo),
      annoImmatricolazione: parseInt(form.annoImmatricolazione),
      dataAcquisto: form.dataAcquisto ? new Date(form.dataAcquisto) : null
    };

    try {
      const res = await axios.post('/automezzi/insertTir', {
        tirBean,
        mezzoBean
      });

      if (res.data.errorCode === 0) {
        toast.success('Tir creato con successo!');
        setTimeout(() => navigate('/'), 1500);
      } else {
        toast.error(res.data.errorMessage);
      }
    } catch (err) {
      console.error(err);
      toast.error('Errore nella creazione.');
    }
  };

  return (
    <div className="form-container">
      <BackToDashboard />
      <ToastContainer />
      <h2>Inserisci Nuovo Tir</h2>
      <form onSubmit={handleSubmit} className="form">
        <input type="text" name="marca" placeholder="Marca" value={form.marca} onChange={handleChange} required />
        <input type="text" name="modello" placeholder="Modello" value={form.modello} onChange={handleChange} required />
        <input type="text" name="targa" placeholder="Targa" value={form.targa} onChange={handleChange} required />
        <input type="text" name="colore" placeholder="Colore" value={form.colore} onChange={handleChange} required />
        <input type="number" name="prezzo" placeholder="Prezzo (€)" value={form.prezzo} onChange={handleChange} required />
        <input type="number" name="annoImmatricolazione" placeholder="Anno Immatricolazione" value={form.annoImmatricolazione} onChange={handleChange} required />
        <input type="date" name="dataAcquisto" value={form.dataAcquisto} onChange={handleChange} />

        <input type="number" name="portataMax" placeholder="Portata Max (kg)" value={form.portataMax} onChange={handleChange} required />
        <input type="number" name="numeroAssi" placeholder="Numero Assi" value={form.numeroAssi} onChange={handleChange} required />
        <input type="text" name="cilindrata" placeholder="Cilindrata (es. 4800)" value={form.cilindrata} onChange={handleChange} required />
        <input type="number" name="lunghezza" step="0.1" placeholder="Lunghezza (m)" value={form.lunghezza} onChange={handleChange} required />
        <input type="text" name="altezzaCassone" placeholder="Altezza Cassone (m)" value={form.altezzaCassone} onChange={handleChange} required />

        <select name="extTipoAlimentazioneId" value={form.extTipoAlimentazioneId} onChange={handleChange} required>
          <option value="">Tipo Alimentazione</option>
          {alimentazioni.map(al => (
            <option key={al.idTipoAlimentazione} value={al.idTipoAlimentazione}>{al.descrizione}</option>
          ))}
        </select>

        <select name="extMotoreEuroId" value={form.extMotoreEuroId} onChange={handleChange} required>
          <option value="">Motore Euro</option>
          {motori.map(m => (
            <option key={m.motoreEuroId} value={m.motoreEuroId}>{m.descrizione}</option>
          ))}
        </select>

        <select name="extTipoRimorchioId" value={form.extTipoRimorchioId} onChange={handleChange} required>
          <option value="">Tipo Rimorchio</option>
          {rimorchi.map(r => (
            <option key={r.tipoRimorchioId} value={r.tipoRimorchioId}>{r.descrizione}</option>
          ))}
        </select>

        <button type="submit">Salva</button>
      </form>
    </div>
  );
};

export default TirCreate;