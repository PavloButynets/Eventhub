// PrimaryButton.js
import React from "react";
import { Link } from "react-router-dom";
import styles from "./PrimaryButton.module.css";

const PrimaryButton = ({ children, to, onClick, className }) => {
  const buttonClass = `${styles.PrimaryButton} ${className || ""}`;

  if (to) {
    return (
      <Link to={to} className={buttonClass}>
        {children}
      </Link>
    );
  } else {
    return (
      <button className={buttonClass} onClick={onClick}>
        {children}
      </button>
    );
  }
};

export default PrimaryButton;
