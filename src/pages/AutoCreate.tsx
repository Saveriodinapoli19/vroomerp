import React, { useEffect, useState } from 'react';
import axios from '../api/axiosInstance';
import { useNavigate } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import '../App.css';

const AutoCreate: React.FC = () => {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    numeroPosti: '',
    numeroPorte: '',
    potenzaKw: '',
    cilindrata: '',
    consumoMedio: '',
    extTipoAlimentazioneId: '',
    extMotoreEuro: '',
    extTipoAuto: '',
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
  const [tipiAuto, setTipiAuto] = useState<any[]>([]);

  useEffect(() => {
    axios.get('/automezzi/findAllTipoAlimentazione')
      .then(res => setAlimentazioni(res.data.tipoAlimentazioneBeanList || []));

    axios.get('/automezzi/findAllMotoreEuro')
      .then(res => setMotori(res.data.motoreEuroBeanList || []));

    axios.get('/automezzi/findAllTipoAuto')
      .then(res => setTipiAuto(res.data.tipoAutoBeanList || []));
  }, []);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    const autoBean = {
      numeroPosti: parseInt(form.numeroPosti),
      numeroPorte: parseInt(form.numeroPorte),
      potenzaKw: form.potenzaKw,
      cilindrata: form.cilindrata,
      consumoMedio: parseFloat(form.consumoMedio),
      extTipoAlimentazioneId: parseInt(form.extTipoAlimentazioneId),
      extMotoreEuro: parseInt(form.extMotoreEuro),
      extTipoAuto: parseInt(form.extTipoAuto)
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
      const res = await axios.post('/automezzi/insertAuto', {
        autoBean,
        mezzoBean
      });

      if (res.data.errorCode === 0) {
        toast.success('Automezzo creato con successo!');
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
      <ToastContainer />
      <h2>Inserisci Nuovo Automezzo</h2>
      <form onSubmit={handleSubmit} className="form">
        <input type="text" name="marca" placeholder="Marca" value={form.marca} onChange={handleChange} required />
        <input type="text" name="modello" placeholder="Modello" value={form.modello} onChange={handleChange} required />
        <input type="text" name="targa" placeholder="Targa" value={form.targa} onChange={handleChange} required />
        <input type="text" name="colore" placeholder="Colore" value={form.colore} onChange={handleChange} required />
        <input type="number" name="prezzo" placeholder="Prezzo (€)" value={form.prezzo} onChange={handleChange} required />
        <input type="number" name="annoImmatricolazione" placeholder="Anno Immatricolazione" value={form.annoImmatricolazione} onChange={handleChange} required />
        <input type="date" name="dataAcquisto" value={form.dataAcquisto} onChange={handleChange} />
        
        <input type="number" name="numeroPosti" placeholder="Numero Posti" value={form.numeroPosti} onChange={handleChange} required />
        <input type="number" name="numeroPorte" placeholder="Numero Porte" value={form.numeroPorte} onChange={handleChange} required />
        <input type="text" name="potenzaKw" placeholder="Potenza kW" value={form.potenzaKw} onChange={handleChange} required />
        <input type="text" name="cilindrata" placeholder="Cilindrata (es. 1600)" value={form.cilindrata} onChange={handleChange} required />
        <input type="number" name="consumoMedio" step="0.1" placeholder="Consumo Medio (L/100km)" value={form.consumoMedio} onChange={handleChange} required />

        <select name="extTipoAlimentazioneId" value={form.extTipoAlimentazioneId} onChange={handleChange} required>
          <option value="">Tipo Alimentazione</option>
          {alimentazioni.map(al => (
            <option key={al.idTipoAlimentazione} value={al.idTipoAlimentazione}>
              {al.descrizione}
            </option>
          ))}
        </select>

        <select name="extMotoreEuro" value={form.extMotoreEuro} onChange={handleChange} required>
          <option value="">Motore Euro</option>
          {motori.map(m => (
            <option key={m.motoreEuroId} value={m.motoreEuroId}>
              {m.descrizione}
            </option>
          ))}
        </select>

        <select name="extTipoAuto" value={form.extTipoAuto} onChange={handleChange} required>
          <option value="">Tipo Auto</option>
          {tipiAuto.map(t => (
            <option key={t.tipoAutoId} value={t.tipoAutoId}>
              {t.descrizione}
            </option>
          ))}
        </select>

        <button type="submit">Salva</button>
      </form>
    </div>
  );
};

export default AutoCreate;
