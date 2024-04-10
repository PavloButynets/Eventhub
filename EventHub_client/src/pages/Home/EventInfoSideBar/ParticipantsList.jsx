import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import styles from "./ParticipantsList.module.css";

import { getUserParticipants } from "../../../api/getUserParticipants";
import { getFullEventById } from "../../../api/getFullEventById";
import { getUserById } from "../../../api/getUserById";

import GoBackButton from "../../../components/Buttons/GoBackButton/GoBackButton";
import CloseWindowButton from "../../../components/Buttons/CloseWindowButton/CloseWindowButton";
import OwnerPhotoOverlay from "../../../components/OwnerPhotoOverlay/OwnerPhotoOverlay";

const ParticipantsList = ({
  handleGoBackToSideBar,
  handleCloseWindow,
  event,
}) => {
  // States
  const [participants, setParticipants] = useState([]);
  const [owner, setOwner] = useState(null);

  // Navigation, redirection
  const navigate = useNavigate();

  // Effects

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
          {owner && <OwnerPhotoOverlay owner={owner} />}
          {participants.map((participant) => (
            <div
              key={participant.id}
              className={styles["participant-container"]}
              onClick={() =>
                navigate(`/profile/${participant.user_id}/account`)
              }
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
