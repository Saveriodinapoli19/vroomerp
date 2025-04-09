
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from '../api/axiosInstance';
import '../App.css';



const Login: React.FC = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');

    try {
      const response = await axios.post('/login/login', {
        email,
        password
      });

      const token = response.data.token;
      localStorage.setItem('token', token);
     window.location.href = '/';
    } catch (err: any) {
      setError('Login fallito. Verifica le credenziali.');
    }
  };

  return (
    <div className="login-container">
  <img src="/logo.png" alt="VroomERP Logo" className="login-logo" />
  <h2 className="login-title">Benvenuto su <span>VroomERP</span></h2>
  <form onSubmit={handleSubmit} className="login-form">
    <input type="email" placeholder="Email" value={email} onChange={e => setEmail(e.target.value)} required />
    <input type="password" placeholder="Password" value={password} onChange={e => setPassword(e.target.value)} required />
    <button type="submit">Accedi</button>
    {error && <p style={{ color: 'red' }}>{error}</p>}
  </form>
</div>

  );
};

export default Login;
