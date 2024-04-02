import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import styles from "./ParticipantsList.module.css";

import { getUserParticipants } from "../../../api/getUserParticipants";
import { getFullEventById } from "../../../api/getFullEventById";
import { getUserById } from "../../../api/getUserById";

import { RiVipCrownLine } from "react-icons/ri";
import { IoClose } from "react-icons/io5";
import GoBackButton from "../../../components/Buttons/GoBackButton/GoBackButton";
import CloseWindowButton from "../../../components/Buttons/CloseWindowButton/CloseWindowButton";

const ParticipantsList = ({ handleGoBackToSideBar, handleCloseWindow }) => {
  // States
  const [participants, setParticipants] = useState([]);
  const [event, setEvent] = useState(null);
  const [owner, setOwner] = useState(null);

  // Params
  const { ownerId, eventId } = useParams();

  // Effects
  useEffect(() => {
    getFullEventById(ownerId, eventId).then((data) => setEvent(data));
  }, [ownerId, eventId]);

  useEffect(() => {
    event &&
      getUserParticipants(event.id).then((data) => setParticipants(data));
  }, [event]);

  useEffect(() => {
    event && getUserById(event.owner_id).then((data) => setOwner(data));
  }, [event]);

  return (
    event && (
      <div className={styles["participants-list-container"]}>
        <div className={styles["header"]}>
          <GoBackButton onClick={handleGoBackToSideBar} />
          <CloseWindowButton onClick={handleCloseWindow} />
        </div>

        <div className={styles["participants-container"]}>
          {owner && (
            <div className={styles["participant-container"]}>
              <div className={styles["participant-photo-owner-container"]}>
                <img
                  className={styles["owner-photo-img"]}
                  src={owner.photo_responses[0].photo_url}
                  alt="Owner participant img"
                />
                <div className={styles["crown-container"]}>
                  <RiVipCrownLine className={styles["crown-icon"]} />
                </div>
              </div>

              <div className={styles["participant-info-container"]}>
                <div className={styles["full-name"]}>
                  <p>{owner.first_name}</p>
                  <p>{owner.last_name}</p>
                  <p className={styles["creator-text"]}> - creator</p>
                </div>
                <p className={styles["email"]}>{owner.email}</p>
              </div>
            </div>
          )}
          {participants.map((participant) => (
            <div
              key={participant.id}
              className={styles["participant-container"]}
            >
              <img
                className={styles["participant-photo"]}
                src={participant.participant_photo.photo_url}
                alt="User participant img"
              />
              <div className={styles["participant-info-container"]}>
                <div className={styles["full-name"]}>
                  <p>{participant.first_name}</p>
                  <p>{participant.last_name}</p>
                </div>
                <p className={styles["email"]}>{participant.email}</p>
              </div>
            </div>
          ))}
        </div>
      </div>
    )
  );
};

export default ParticipantsList;
