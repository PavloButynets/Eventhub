import ParticipantInfoPopUp from "../PopUp/ParticipantInfoPopUp";
import styles from "./OwnerPhotoOverlay.module.css";

import { RiVipCrownLine } from "react-icons/ri";

const OwnerPhotoOverlay = ({
  owner,
  onMouseEnter,
  onMouseLeave,
  showPopUp,
}) => {
  return (
    <div
      className={styles["owner-photo"]}
      onMouseEnter={onMouseEnter}
      onMouseLeave={onMouseLeave}
    >
      {owner && (
        <img
          className={styles["owner-img"]}
          src={owner.photo_responses[0].photo_url}
          alt=""
        />
      )}
      <div className={styles["crown-container"]}>
        <RiVipCrownLine className={styles["crown-icon"]} />
      </div>
      {showPopUp && <ParticipantInfoPopUp participant={owner} />}
    </div>
  );
};

export default OwnerPhotoOverlay;
