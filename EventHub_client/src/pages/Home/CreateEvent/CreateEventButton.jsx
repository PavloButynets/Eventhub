import React from 'react';
import { useNavigate } from 'react-router-dom';
import  useAuth  from "../../../hooks/useAuth";
import styles from './CreateEvent.module.css';
import { Button } from 'antd';

const CreateEventButton = () => {
  const navigate = useNavigate();
  const { auth, setAuth } = useAuth();


  const handleClick = () => {
    if (!auth.token) {
        navigate('/login');
    }
  };

  return (
    <Button
      className={styles.CreateEventButton}
      onClick={handleClick}
    >
      Create Event
    </Button>
  );
};

export default CreateEventButton;
