import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from '../api/axiosInstance';
import BackToDashboard from './BackToDashboard';
import '../App.css';

interface MezzoBean {
    mezzoId: number;
    modello: string;
    targa: string;
    marca: string;
    colore: string;
    annoImmatricolazione: number;
    prezzo: number;
}

interface Tir {
    tirId: number;
    altezzaCassone: string;
    extMotoreEuroId: number;
    motoreEuro?: string;
    extTipoAlimentazioneId: number;
    alimentazione?: string;
    extTipoRimorchioId: number;
    rimorchio?: string;
    lunghezza: number;
    numeroAssi: number;
    portataMax: number;
    cilindrata: string;
    mezzoBean?: MezzoBean;
}

const TirDetail: React.FC = () => {
    const { tirId } = useParams<{ tirId: string }>();
    const [tir, setTir] = useState<Tir | null>(null);
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem('token');
        if (!token) {
            navigate('/login');
            return;
        }

        axios.get(`/automezzi/findTirById/${tirId}`, {
            headers: { Authorization: `Bearer ${token}` }
        })
            .then(res => {
                if (res.data.errorCode === 0) {
                    setTir(res.data.tirBean);
                } else {
                    console.warn('Errore:', res.data.errorMessage);
                }
            })
            .catch(err => {
                console.error('Errore nel caricamento tir:', err);
            });
    }, [tirId, navigate]);

    if (!tir) return <p>Caricamento...</p>;

    return (
        <div className="detail-container">
            <BackToDashboard />
            <h2>Dettaglio Tir</h2>

            <p><strong>Modello:</strong> {tir.mezzoBean?.modello}</p>
            <p><strong>Targa:</strong> {tir.mezzoBean?.targa}</p>
            <p><strong>Marca:</strong> {tir.mezzoBean?.marca}</p>
            <p><strong>Colore:</strong> {tir.mezzoBean?.colore}</p>
            <p><strong>Anno Immatricolazione:</strong> {tir.mezzoBean?.annoImmatricolazione}</p>
            <p><strong>Prezzo:</strong> {tir.mezzoBean?.prezzo} €</p>

            <hr />

            <p><strong>Alimentazione:</strong> {tir.alimentazione}</p>
            <p><strong>Motore Euro:</strong> {tir.motoreEuro}</p>
            <p><strong>Tipo Rimorchio:</strong> {tir.rimorchio}</p>
            <p><strong>Cilindrata:</strong> {tir.cilindrata} cc</p>
            <p><strong>Lunghezza:</strong> {tir.lunghezza} m</p>
            <p><strong>Altezza Cassone:</strong> {tir.altezzaCassone} m</p>
            <p><strong>Numero Assi:</strong> {tir.numeroAssi}</p>
            <p><strong>Portata Massima:</strong> {tir.portataMax} kg</p>
        </div>
    );
};

export default TirDetail;
