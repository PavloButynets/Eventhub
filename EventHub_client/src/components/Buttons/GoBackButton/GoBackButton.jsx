import styles from "./GoBackButton.module.css";
import { IoArrowBackOutline } from "react-icons/io5";

const GoBackButton = ({ onClick }) => {
  return (
    <button className={styles["btn"]} onClick={onClick}>
      <IoArrowBackOutline className={styles["btn-icon"]} />
    </button>
  );
};

export default GoBackButton;
