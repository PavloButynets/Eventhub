import styles from "./SpotsLeft.module.css";

const SpotsLeft = ({ event }) => {
  return (
    <div className={styles["spots"]}>
      {event.max_participants - event.participant_count} Spots left
    </div>
  );
};

export default SpotsLeft;
