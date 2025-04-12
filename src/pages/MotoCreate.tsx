import React, { useEffect, useState } from 'react';
import axios from '../api/axiosInstance';
import { useNavigate } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import '../App.css';
import BackToDashboard from './BackToDashboard';

const MotoCreate: React.FC = () => {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    peso: '',
    potenzaKw: '',
    cilindrata: '',
    raffreddamento: '',
    extTipoAlimentazione: '',
    extTipoMotoreId: '',
    extMotoreEuroId: '',
    extTipoMotoId: '',
    annoImmatricolazione: '',
    colore: '',
    marca: '',
    modello: '',
    targa: '',
    prezzo: '',
    dataAcquisto: ''
  });

  const [alimentazioni, setAlimentazioni] = useState<any[]>([]);
  const [motori, setMotori] = useState<any[]>([]);
  const [tipoMoto, setTipoMoto] = useState<any[]>([]);
  const [tipoMotore, setTipoMotore] = useState<any[]>([]);

  useEffect(() => {
    axios.get('/automezzi/findAllTipoAlimentazione')
      .then(res => setAlimentazioni(res.data.tipoAlimentazioneBeanList || []));

    axios.get('/automezzi/findAllMotoreEuro')
      .then(res => setMotori(res.data.motoreEuroBeanList || []));

    axios.get('/automezzi/findAllTipoMoto')
      .then(res => setTipoMoto(res.data.tipoMotoBeanList || []));

    axios.get('/automezzi/findAllTipoMotore')
      .then(res => setTipoMotore(res.data.tipoMotoreBeanList || []));
  }, []);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    const motoBean = {
      peso: form.peso,
      potenzaKw: form.potenzaKw,
      cilindrata: form.cilindrata,
      raffreddamento: form.raffreddamento,
      extTipoAlimentazione: form.extTipoAlimentazione,
      extTipoMotoreId: form.extTipoMotoreId,
      extMotoreEuroId: form.extMotoreEuroId,
      extTipoMotoId: form.extTipoMotoId
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
      const res = await axios.post('/automezzi/insertMoto', {
        motoBean,
        mezzoBean
      });

      if (res.data.errorCode === 0) {
        toast.success('Moto creata con successo!');
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
      <h2 className="form-title">Crea Nuova Moto</h2>
      <form onSubmit={handleSubmit} className="form-container">
        <div className="form-group">
          <label>Marca:</label>
          <input type="text" name="marca" value={form.marca} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label>Modello:</label>
          <input type="text" name="modello" value={form.modello} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label>Targa:</label>
          <input type="text" name="targa" value={form.targa} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label>Colore:</label>
          <input type="text" name="colore" value={form.colore} onChange={handleChange} />
        </div>
        <div className="form-group">
          <label>Prezzo:</label>
          <input type="number" name="prezzo" value={form.prezzo} onChange={handleChange} />
        </div>
        <div className="form-group">
          <label>Anno Immatricolazione:</label>
          <input type="number" name="annoImmatricolazione" value={form.annoImmatricolazione} onChange={handleChange} />
        </div>
        <div className="form-group">
          <label>Data Acquisto:</label>
          <input type="date" name="dataAcquisto" value={form.dataAcquisto} onChange={handleChange} />
        </div>
        <div className="form-group">
          <label>Peso:</label>
          <input type="number" name="peso" value={form.peso} onChange={handleChange} />
        </div>
        <div className="form-group">
          <label>Potenza (kW):</label>
          <input type="number" name="potenzaKw" value={form.potenzaKw} onChange={handleChange} />
        </div>
        <div className="form-group">
          <label>Cilindrata:</label>
          <input type="number" name="cilindrata" value={form.cilindrata} onChange={handleChange} />
        </div>
        <div className="form-group">
          <label>Raffreddamento:</label>
          <input type="text" name="raffreddamento" value={form.raffreddamento} onChange={handleChange} />
        </div>
        <div className="form-group">
          <label>Tipo Alimentazione:</label>
          <select name="extTipoAlimentazione" value={form.extTipoAlimentazione} onChange={handleChange}>
            <option value="">Seleziona</option>
            {alimentazioni.map(al => (
              <option key={al.idTipoAlimentazione} value={al.idTipoAlimentazione}>{al.descrizione}</option>
            ))}
          </select>
        </div>
        <div className="form-group">
          <label>Tipo Motore:</label>
          <select name="extTipoMotoreId" value={form.extTipoMotoreId} onChange={handleChange}>
            <option value="">Seleziona</option>
            {tipoMotore.map(tm => (
              <option key={tm.tipoMotoreId} value={tm.tipoMotoreId}>{tm.descrizione}</option>
            ))}
          </select>
        </div>
        <div className="form-group">
          <label>Motore Euro:</label>
          <select name="extMotoreEuroId" value={form.extMotoreEuroId} onChange={handleChange}>
            <option value="">Seleziona</option>
            {motori.map(me => (
              <option key={me.motoreEuroId} value={me.motoreEuroId}>{me.descrizione}</option>
            ))}
          </select>
        </div>
        <div className="form-group">
          <label>Tipo Moto:</label>
          <select name="extTipoMotoId" value={form.extTipoMotoId} onChange={handleChange}>
            <option value="">Seleziona</option>
            {tipoMoto.map(tm => (
              <option key={tm.tipoMotoId} value={tm.tipoMotoId}>{tm.descrizione}</option>
            ))}
          </select>
        </div>

        <button type="submit" className="submit-button">Crea Moto</button>
      </form>
      <ToastContainer />
    </div>
  );


};

export default MotoCreate;
