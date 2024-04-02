import styles from "./ParticipantInfoPopUp.module.css";

import { MdOutlineEmail } from "react-icons/md";
import { SlLocationPin } from "react-icons/sl";
import { CiCalendar } from "react-icons/ci";

const ParticipantInfoPopUp = ({ participant }) => {
  return (
    <div className={styles["pop-up-container"]}>
      <div className={styles["header-container"]}>
        <img src={participant.photo_responses[0].photo_url} alt="Img" />
        <div className={styles["name-container"]}>
          <div className={styles["full-name"]}>
            {`${participant.first_name} ${participant.last_name}`}
          </div>
          <div className={styles["email"]}>
            <MdOutlineEmail />
            {participant.email}
          </div>
        </div>
      </div>
      <div className={styles["info-container"]}>
        <p className={styles["heading"]}>Information</p>
        <div className={styles["information"]}>
          <div className={styles["location"]}>
            <SlLocationPin />
            {participant.city}
          </div>
          <div className={styles["birth-date"]}>
            <CiCalendar />
            {participant.birth_date}
          </div>
        </div>
        <hr />
      </div>
      <div className={styles["description"]}>{participant.description}</div>
      <button>Show more</button>
    </div>
  );
};

export default ParticipantInfoPopUp;
