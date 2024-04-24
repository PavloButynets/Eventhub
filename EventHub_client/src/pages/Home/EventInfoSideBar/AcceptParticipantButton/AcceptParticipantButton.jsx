import styles from "./AcceptParticipantButton.module.css";
import { BsCheck } from "react-icons/bs";

const AcceptParticipantButton = ({ onClick }) => {
  return (
    <button className={styles["btn"]} onClick={onClick}>
      <BsCheck className={styles["btn-icon"]} size="1.5em" />
    </button>
  );
};

export default AcceptParticipantButton;
