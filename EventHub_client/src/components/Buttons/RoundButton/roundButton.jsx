import React from 'react';
import styles from './roundButton.module.css'
export const RoundButton = ({ icon, onClick }) => {
  return (
    <button className={styles.roundButton} onClick={onClick}>
      {icon}
    </button>
  );
};

