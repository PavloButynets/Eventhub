import styles from "./CloseWindowButton.module.css";
import { IoClose } from "react-icons/io5";

const CloseWindowButton = ({ onClick }) => {
  return (
    <button className={styles["close-btn"]} onClick={onClick}>
      <IoClose className={styles["close-btn-icon"]} size="2.5em" />
    </button>
  );
};

export default CloseWindowButton;
