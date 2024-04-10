import React from 'react';
import { FiEdit3 } from "react-icons/fi";
import styles from './EditProfileButton.module.css';

const EditProfileButton = ({ onClick }) => {
  return (
    <button className={styles.EditProfileButton}>
      Edit <FiEdit3 />
    </button>
  );
};

export default EditProfileButton;
