import styles from "./ParticipantInfoPopUp.module.css";

import { MdOutlineEmail } from "react-icons/md";
import { SlLocationPin } from "react-icons/sl";
import { CiCalendar } from "react-icons/ci";
import PrimaryButton from "../Buttons/PrimaryButton/PrimaryButton";

import { motion } from "framer-motion";

const ParticipantInfoPopUp = ({ participant }) => {
  return (
    <motion.div
      className={styles["pop-up-container"]}
      initial={{
        opacity: 0,
      }}
      animate={{
        opacity: 1,
      }}
      transition={{
        duration: 0.5,
      }}
    >
      <div className={styles["header-container"]}>
        <img
          className={styles["participant-img"]}
          src={participant.photo_responses[0].photo_url}
          alt="Img"
        />

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
        <p className={styles["heading"]}>Information:</p>
        <div className={styles["information"]}>
          <div className={styles["location"]}>
            <SlLocationPin size="1.4rem" />
            {participant.city}
          </div>
          <div className={styles["birth-date"]}>
            <CiCalendar size="1.4rem" />
            {participant.birth_date}
          </div>
        </div>
        <hr />
      </div>
      <div className={styles["description"]}>{participant.description} </div>
      <PrimaryButton className={styles["show-more-btn"]}>
        Show more
      </PrimaryButton>
    </motion.div>
  );
};

export default ParticipantInfoPopUp;
