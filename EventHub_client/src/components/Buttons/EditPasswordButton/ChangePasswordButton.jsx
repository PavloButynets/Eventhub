import React from 'react';
import styles from './ChangePasswordButton.module.css';

const ChangePasswordButton = ({onClick}) => {
  return(
    <button className={styles.ChangePasswordButton}>Change password</button>
  );
};

export default ChangePasswordButton;
