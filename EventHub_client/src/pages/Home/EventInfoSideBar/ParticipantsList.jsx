import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import styles from "./ParticipantsList.module.css";

import GoBackButton from "../../../components/Buttons/GoBackButton/GoBackButton";
import CloseWindowButton from "../../../components/Buttons/CloseWindowButton/CloseWindowButton";
import CloseParticipantButton from "./CloseParticipantButton/CloseParticipantButton";
import OwnerPhotoOverlay from "../../../components/OwnerPhotoOverlay/OwnerPhotoOverlay";
import { deleteParticipant } from "../../../api/deleteParticipant";
import SpotsLeft from "../../../components/Spots/SpotsLeft";
import PrimaryButton from "../../../components/Buttons/PrimaryButton/PrimaryButton";
import RequestsCount from "../../../components/RequestsCount/RequestsCount";
import { message } from "antd";

const ParticipantsList = ({
  handleGoBackToSideBar,
  handleCloseWindow,
  handleShowRequests,
  isOwner,
  setReloadList,
  requests,
  _event,
  participants,
  owner,
}) => {
  // Params
  const { eventId } = useParams();

  const navigate = useNavigate();

  return (
    _event &&
    participants && (
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
                (participant) => participant.user_id === owner.id
              ) && <OwnerPhotoOverlay owner={owner} />}
            {participants.map(
              (participant) =>
                participant.user_id !== owner.id && (
                  <li
                    key={participant.id}
                    className={styles["participant-container"]}
                  >
                    <img
                      onClick={() =>
                        navigate(`/profile/${participant.username}`)
                      }
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
                            deleteParticipant(participant.id, eventId)
                              .then(() => setReloadList((prev) => !prev))
                              .catch((error) =>
                                message.error("An error occured")
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
