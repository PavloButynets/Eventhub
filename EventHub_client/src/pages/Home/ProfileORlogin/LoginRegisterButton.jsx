import { Link } from 'react-router-dom';
import { Button } from 'antd';
import styles from "./Buttons.module.css";


import React from 'react';
import PrimaryButton from '../../../components/Buttons/PrimaryButton/PrimaryButton';

const LoginRegisterButton = () => {
  return (
  
    <div className = {styles.LoginRegisterButtonContainer}>
      <PrimaryButton to="/login">Login</PrimaryButton>
      <PrimaryButton to="/register">Register</PrimaryButton>
    </div>
  );
}

export default LoginRegisterButton;
