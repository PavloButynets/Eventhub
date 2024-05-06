import { useNavigate } from "react-router-dom";
import styles from "./OwnerPhotoOverlay.module.css";

const OwnerPhotoOverlay = ({ owner, onMouseEnter, onMouseLeave }) => {
  const navigate = useNavigate();

  return (
    <div
      className={styles["owner-container"]}
      onMouseEnter={onMouseEnter}
      onMouseLeave={onMouseLeave}
    >
      <img
        onClick={() => navigate(`/profile/${owner.username}`)}
        className={styles["owner-img"]}
        src={owner.photo_responses[0].photo_url}
        alt=""
      />
      <div className={styles["info-container"]}>
        <div className={styles["username-container"]}>
          <p className={styles["username"]}>{`@${owner.username}`}</p>
          <p className={styles["creator-text"]}>- creator</p>
        </div>
        <div className={styles["full-name-container"]}>
          <p className={styles["full-name"]}>{owner.first_name}</p>
          <p className={styles["full-name"]}>{owner.last_name}</p>
        </div>
      </div>
    </div>
  );
};

export default OwnerPhotoOverlay;
