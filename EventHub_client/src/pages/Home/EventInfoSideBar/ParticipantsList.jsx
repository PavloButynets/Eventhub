import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import styles from "./ParticipantsList.module.css";

import { getUserParticipants } from "../../../api/getUserParticipants";
import { getUserById } from "../../../api/getUserById";

import GoBackButton from "../../../components/Buttons/GoBackButton/GoBackButton";
import CloseWindowButton from "../../../components/Buttons/CloseWindowButton/CloseWindowButton";
import CloseParticipantButton from "./CloseParticipantButton/CloseParticipantButton";
import OwnerPhotoOverlay from "../../../components/OwnerPhotoOverlay/OwnerPhotoOverlay";
import { deleteParticipant } from "../../../api/deleteParticipant";
import SpotsLeft from "../../../components/Spots/SpotsLeft";
import PrimaryButton from "../../../components/Buttons/PrimaryButton/PrimaryButton";
import RequestsCount from "../../../components/RequestsCount/RequestsCount";

const ParticipantsList = ({
  setIsLoading,
  handleGoBackToSideBar,
  handleCloseWindow,
  handleShowRequests,
  isOwner,
  setReloadList,
  requests,
  _event,
}) => {
  // States
  const [participants, setParticipants] = useState([]);
  const [owner, setOwner] = useState(null);

  // Params
  const { ownerId, eventId } = useParams();

  // Effects

  useEffect(() => {
    _event &&
      getUserParticipants(_event.id).then((data) => setParticipants(data));
  }, [_event]);

  useEffect(() => {
    _event && getUserById(_event.owner_id).then((data) => setOwner(data));
  }, [_event]);

  useEffect(() => {
    participants && owner && setIsLoading(false);
  }, [participants, owner]);

  return (
    _event &&
    participants &&
    owner && (
      <div className={styles["participants-list-container"]}>
        <div className={styles["header"]}>
          <GoBackButton onClick={handleGoBackToSideBar} />
          <CloseWindowButton onClick={handleCloseWindow} />
        </div>
        <main>
          {participants.length === 0 && (
            <div className={styles["no-participants-msg"]}>
              Here will be shown participants for this event...
            </div>
          )}

          <ul className={styles["participants-container"]}>
            {owner &&
              participants.find(
                (participant) => participant.user_id === ownerId
              ) && <OwnerPhotoOverlay owner={owner} />}
            {participants.map(
              (participant) =>
                participant.user_id !== ownerId && (
                  <li
                    key={participant.id}
                    className={styles["participant-container"]}
                  >
                    <img
                      className={styles["participant-photo"]}
                      src={participant.participant_photo.photo_url}
                      alt="User participant img"
                    />
                    <div className={styles["participant-info-container"]}>
                      <p className={styles["username"]}>
                        {`@${participant.username}`}
                      </p>
                      <div className={styles["full-name"]}>
                        <p>{participant.first_name}</p>
                        <p>{participant.last_name}</p>
                      </div>
                    </div>
                    {isOwner && (
                      <div className={styles["delete-participant-container"]}>
                        <CloseParticipantButton
                          onClick={() => {
                            deleteParticipant(participant.id, eventId).then(
                              () => setReloadList((prev) => !prev)
                            );
                          }}
                        />
                      </div>
                    )}
                  </li>
                )
            )}
          </ul>
        </main>

        <div className={styles["lower-container"]}>
          <SpotsLeft event={_event} />
          {isOwner && (
            <PrimaryButton
              onClick={handleShowRequests}
              className={styles["requests-btn"]}
            >
              {requests.length > 0 && (
                <div className={styles["requests-count-container"]}>
                  <RequestsCount requestsLength={requests.length} />
                </div>
              )}
              Requests
            </PrimaryButton>
          )}
        </div>
      </div>
    )
  );
};

export default ParticipantsList;
