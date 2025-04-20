import axios from '../api/axiosInstance';
import { toast } from 'react-toastify';

export const handleAcquisto = async (tipo: 'auto' | 'moto' | 'tir', id: number) => {
  const token = localStorage.getItem("token");
  if (!token) {
    toast.error("Token mancante. Effettua il login.");
    return;
  }

  const payload: any = { acquistiBean: {} };
  if (tipo === 'auto') payload.acquistiBean.extAutoId = id;
  else if (tipo === 'moto') payload.acquistiBean.extMotoId = id;
  else if (tipo === 'tir') payload.acquistiBean.extTirId = id;

  try {
    const res = await axios.post('/acquisti/insertAcquisto', payload, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });

    const data = res.data;
    if (data.errorCode === 0) {
      toast.success("✅ Acquisto effettuato con successo!");
    } else {
      toast.error(`❌ ${data.errorMessage}`);
    }
  } catch (err) {
    console.error(err);
    toast.error("Errore durante l'acquisto.");
  }
};
