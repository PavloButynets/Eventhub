import styles from "./CloseParticipantButton.module.css";
import { IoClose } from "react-icons/io5";

const CloseWindowButton = ({ onClick }) => {
  return (
    <button className={styles["btn"]} onClick={onClick}>
      <IoClose className={styles["btn-icon"]} size="2em" />
    </button>
  );
};

export default CloseWindowButton;
