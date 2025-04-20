import React, { useState } from 'react';
import axios from '../api/axiosInstance';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import BackToDashboard from './BackToDashboard';

const AcquistoCreate: React.FC = () => {
  const [tipo, setTipo] = useState('');
  const [id, setId] = useState('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!tipo || !id) {
      toast.error("Compila tutti i campi.");
      return;
    }

    const payload: any = { acquistiBean: {} };
    if (tipo === 'auto') payload.acquistiBean.extAutoId = parseInt(id);
    else if (tipo === 'moto') payload.acquistiBean.extMotoId = parseInt(id);
    else if (tipo === 'tir') payload.acquistiBean.extTirId = parseInt(id);

    try {
      const token = localStorage.getItem("token");
      const res = await axios.post('/acquisti/insertAcquisto', payload, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });

      const data = res.data;
      if (data.errorCode === 0) {
        toast.success("✅ Acquisto effettuato con successo!");
        setTipo('');
        setId('');
      } else {
        toast.error(`❌ ${data.errorMessage}`);
      }
    } catch (err) {
      console.error(err);
      toast.error("Errore durante l'acquisto.");
    }
  };

  return (
    <div className="form-container">
      <BackToDashboard />
      <h2 className="form-title">Effettua un Acquisto</h2>
      <form onSubmit={handleSubmit} className="form-container">
        <div className="form-group">
          <label>Tipo Veicolo:</label>
          <select value={tipo} onChange={(e) => setTipo(e.target.value)} required>
            <option value="">Seleziona</option>
            <option value="auto">Auto</option>
            <option value="moto">Moto</option>
            <option value="tir">Tir</option>
          </select>
        </div>
        <div className="form-group">
          <label>ID Veicolo:</label>
          <input
            type="number"
            value={id}
            onChange={(e) => setId(e.target.value)}
            placeholder="Inserisci l'ID del veicolo"
            required
          />
        </div>
        <button type="submit" className="submit-button">Acquista</button>
      </form>
      <ToastContainer />
    </div>
  );
};

export default AcquistoCreate;
