import {Link, useNavigate} from "react-router-dom";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import {useState} from "react";
import VisibilityIcon from "@mui/icons-material/Visibility";
import VisibilityOffIcon from "@mui/icons-material/VisibilityOff";
import {BarLoader} from "react-spinners";
import "./login.scss";
import { useLocation } from "react-router-dom";
import useAuth from '../../hooks/useAuth';
import instance from '../../service/LoginService';
import { Box, Button, Container, CssBaseline, TextField, ThemeProvider, Typography, createTheme } from '@mui/material';

const LoginPage = () => {
  const navigate = useNavigate();
  const { isLoading, setIsLoading, persistAuthState } = useAuth();
  const [showPassword, setShowPassword] = useState(false);
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const {state} = useLocation();


  const handleSubmit = async (e) => {
    e.preventDefault();
    if (username === '' || password === '') {
      return;
    }
    setIsLoading(true);
    await login(username, password);

    setPassword('');
    setUsername('');
  };

  const login = async (username, password) => {
    const res = await instance.post('/api/auth/token', { username, password });
    if (res.status === 200) {
      persistAuthState(res.data);
      setIsLoading(false);
      navigate(state?.from ? state.from : '/home');
    }
  };

  const toggleShowPassword = () => {
    setShowPassword(!showPassword);
  };

  return (
    <ThemeProvider theme={createTheme()}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <div className="wrapper">
          <div className="banner">
            <div className="circle"></div>
            <div className="overlay"></div>
          </div>
          <div className="auth-body">
            <form className="auth-form" onSubmit={handleSubmit}>
              <Typography component="h1" variant="h5" className="auth-title">
                Welcome back
              </Typography>
              <div className="input-group">
                <label htmlFor="username" className="label">
                  Username
                </label>
                <div className="input-container" data-error="Enter valid username">
                  <TextField
                    type="text"
                    name="username"
                    required
                    placeholder="Enter username"
                    autoComplete="username"
                    autoFocus
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    id="username"
                    fullWidth
                  />
                </div>
              </div>
              <div className="input-group">
                <label htmlFor="password" className="label">
                  Password
                </label>
                <div className="input-container">
                  <TextField
                    type={showPassword ? 'text' : 'password'}
                    name="password"
                    required
                    placeholder="Enter your password"
                    min="6"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    id="current-password"
                    fullWidth
                  />
                  {!showPassword ? (
                    <VisibilityOffIcon className="pwd-icon" onClick={toggleShowPassword} />
                  ) : (
                    <VisibilityIcon className="pwd-icon" onClick={toggleShowPassword} />
                  )}
                </div>
              </div>
              <Button type="submit" fullWidth variant="contained" sx={{ mt: 3, mb: 2 }}>
                {isLoading ? (
                  <div className="loader">
                    <BarLoader color="#fff" loading={isLoading} size={10} height={2} />
                  </div>
                ) : (
                  "Sign In"
                )}
              </Button>
            </form>
          </div>
        </div>
      </Container>
    </ThemeProvider>
  );
};

export default LoginPage;
