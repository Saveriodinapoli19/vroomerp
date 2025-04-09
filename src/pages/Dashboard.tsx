import React, { useEffect, useState } from 'react';
import axios from '../api/axiosInstance';
import { Link, useNavigate } from 'react-router-dom';
import '../App.css';

interface User {
    userId: number;
    nome: string;
    cognome: string;
}

interface MezzoBean {
    mezzoId: number;
    modello: string;
    targa: string;
    marca: string;
    colore: string;
}

interface Auto {
    autoId: number;
    mezzoBean?: MezzoBean;
}

const Dashboard: React.FC = () => {
    const [users, setUsers] = useState<User[]>([]);
    const [auto, setAuto] = useState<Auto[]>([]);
    const [showUsers, setShowUsers] = useState(false);
    const [showAuto, setShowAuto] = useState(false);
    const navigate = useNavigate();

    const getNomeUtenteFromToken = (): string => {
        const token = localStorage.getItem('token');
        if (!token) return '';
        try {
            const payload = token.split('.')[1];
            const decoded = JSON.parse(atob(payload));
            return `${decoded.nome || ''} ${decoded.cognome || ''}`.trim();
        } catch (e) {
            console.error('Errore nel parsing del token:', e);
            return '';
        }
    };

    const nomeUtente = getNomeUtenteFromToken();

    useEffect(() => {
        const token = localStorage.getItem('token');
        if (!token) {
            navigate('/login');
            return;
        }

        axios.get('/user/findAllUsers')
            .then(res => {
                //console.log('📦 Risposta utenti:', res.data);
                const list = res.data.userBeanList;
                if (Array.isArray(list)) {
                    list.forEach(user => {
                       // console.log('🔍 User:', user);
                    });
                    setUsers(list);
                } else {
                    console.error('userBeanList non è un array:', list);
                }
            })
            .catch(err => {
                console.error('Errore nel caricamento utenti:', err);
            });

        axios.get('/automezzi/findAllAuto')
            .then(res => {
               // console.log('📦 Risposta automezzi:', res.data);
                const list = res.data.autoBeanList;
                if (Array.isArray(list)) {
                    setAuto(list);
                } else {
                    console.error('autoBeanList non è un array:', list);
                }
            })
            .catch(err => {
                console.error('Errore nel caricamento auto:', err);
            });
    }, [navigate]);

    const handleLogout = () => {
        localStorage.removeItem('token');
        navigate('/login');
    };

    return (
        <div className="dashboard-container">
            <div className="dashboard-header">
                <div className="dashboard-username">
                    Benvenuto{nomeUtente ? `, ${nomeUtente}` : ''}
                </div>
                <button className="logout" onClick={handleLogout}>Logout</button>
            </div>

            <div className="toggle-section">
                <h3 onClick={() => setShowUsers(!showUsers)}>
                    {showUsers ? '▼' : '▶'} Utenti
                </h3>
                {showUsers && (
                    <ul>
                        {users.map(user => (
                            <li key={user.userId}>
                                <Link to={`/user/${user.userId}`}>{user.nome} {user.cognome}</Link>
                            </li>
                        ))}
                    </ul>
                )}
            </div>

            <div className="toggle-section">
                <h3 onClick={() => setShowAuto(!showAuto)}>
                    {showAuto ? '▼' : '▶'} Automezzi
                </h3>
                {showAuto && (
                    <ul>
                        {auto.map(a => {
                           // console.log('🚗 AUTO ID:', a.autoId);
                            return (
                                <li key={a.autoId}>
                                    <Link to={`/auto/${a.autoId}`}>
                                        {a.mezzoBean?.modello} - {a.mezzoBean?.targa}
                                    </Link>
                                </li>
                            );
                        })}
                    </ul>
                )}
            </div>
        </div>
    );
};

export default Dashboard;
