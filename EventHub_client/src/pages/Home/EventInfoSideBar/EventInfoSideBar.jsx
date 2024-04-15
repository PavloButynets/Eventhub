import { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import styles from "./EventInfoSideBar.module.css";

import { IoIosMore } from "react-icons/io";

import { getJoinedParticipants } from "../../../api/getJoinedParticipants";
import { getUserById } from "../../../api/getUserById";
import { getFullEventById } from "../../../api/getFullEventById";

import ImageSlider from "../../../components/ImageSlider/ImageSlider";
import CloseWindowButton from "../../../components/Buttons/CloseWindowButton/CloseWindowButton";
import ParticipantsList from "./ParticipantsList";

import month from "../../../utils/month";

import { AnimatePresence, motion } from "framer-motion";
import ParticipantInfoPopUp from "../../../components/PopUp/ParticipantInfoPopUp";
import PrimaryButton from "../../../components/Buttons/PrimaryButton/PrimaryButton";
import OwnerPhotoOverlay from "../../../components/OwnerPhotoOverlay/OwnerPhotoOverlay";
import useAuth from "../../../hooks/useAuth";
import getIdFromToken from "../../../jwt/getIdFromToken";
import { getParticipantState } from "../../../api/getParticipantState";
import { getParticipantByUserId } from "../../../api/getParticipantByUserId";
import { deleteParticipant } from "../../../api/deleteParticipant";
import { createParticipant } from "../../../api/createParticipant";
import { addParticipant } from "../../../api/addParticipant";

const EventInfoSideBar = ({ ownerId, eventId }) => {
  // States
  const [isShowMore, setIsShowMore] = useState(false);
  const [isOverflowAboutText, setIsOverflowAboutText] = useState(false);
  const [isShowMoreParticipants, setIsShowMoreParticipants] = useState(false);
  const [participantsToShow, setParticipantsToShow] = useState([]);
  const [event, setEvent] = useState(null);

  const [owner, setOwner] = useState(null);

  const [hoveredParticipant, setHoveredParticipant] = useState(null);

  const [showAllParticipants, setShowAllParticipants] = useState(false);

  const [userId, setUserId] = useState(null);

  const [participantState, setParticipantState] = useState(null);

  // Auth
  const { auth } = useAuth();

  // Navigation
  const navigate = useNavigate();

  // Refs
  const sideBar = useRef(null);
  const showMoreBtn = useRef(null);
  const aboutText = useRef(null);

  // Effects
  useEffect(() => {
    try {
      setUserId(getIdFromToken());
    } catch (e) {
      setUserId(null);
      setParticipantState(null);
      console.log("User is not logged in.");
    }
  }, [auth]);

  useEffect(() => {
    const fetchData = async () => {
      userId &&
        getParticipantState(userId, eventId).then((data) =>
          setParticipantState(data)
        );
    };

    fetchData();
  }, [eventId, userId]);

  useEffect(() => {
    getFullEventById(ownerId, eventId).then((data) => {
      setEvent(data);
    });
  }, [ownerId, eventId, participantState]);

  useEffect(() => {
    event &&
      participantState &&
      getJoinedParticipants(event.id).then((data) => {
        console.log("Data: ", data);
        if (data.length > 2) {
          setIsShowMoreParticipants(true);
          setParticipantsToShow(data.slice(0, 5));
        } else {
          setParticipantsToShow(data);
        }
        setShowAllParticipants(false);
        if (
          !showAllParticipants &&
          aboutText.current.scrollHeight > aboutText.current.clientHeight
        ) {
          setIsOverflowAboutText(true);
        }
      });

    return () => setIsShowMoreParticipants(false);
  }, [event]);

  useEffect(() => {
    event &&
      getUserById(event.owner_id).then((data) => {
        setOwner(data);
        console.log(
          `Owner photo response: ${data.photo_responses[0].photo_url}`
        );
      });
  }, [event]);

  //TODO Fix opacity when allParticipants is toggled

  // useEffect(() => {
  //   console.log("Show All: ", showAllParticipants);
  //   event &&
  //     (showAllParticipants
  //       ? (sideBar.current.style.opacity = 0)
  //       : (sideBar.current.style.opacity = 1));
  // }, [showAllParticipants, event]);

  // Funcs
  const handleShowAllParticipants = () => {
    setShowAllParticipants(!showAllParticipants);
  };

  const handleShowMore = () => {
    setIsShowMore(!isShowMore);
    showMoreBtn.current.innerHTML = isShowMore ? "Show more" : "Show less";
  };

  const handleCloseWindow = () => {
    navigate("../");
  };

  return (
    <AnimatePresence>
      (
      {event && (
        <div>
          {!showAllParticipants && (
            <motion.div
              className={styles["side-bar-container"]}
              ref={sideBar}
              initial={{
                opacity: 0,
              }}
              animate={{
                opacity: 1,
              }}
              exit={{
                opacity: 0,
              }}
              transition={{
                duration: 0.7,
              }}
            >
              <div className={styles["header"]}>
                <h2 className={styles["event-title"]}>{event.title}</h2>
                <CloseWindowButton onClick={handleCloseWindow} />
              </div>

              {/* Photo */}
              <div className={styles["photo-container"]}>
                <ImageSlider images={event.photo_responses} />
              </div>

              {/* Category */}
              <div className={styles["category-container"]}>
                {event.category_responses.map((category) => (
                  <div key={category.id} className={styles["category"]}>
                    {category.name}
                  </div>
                ))}
              </div>

              {/* Owner */}
              {owner && <OwnerPhotoOverlay owner={owner} />}

              {/* Date */}
              <h3 className={styles["heading"]}>Date and time</h3>
              <div className={styles["date-container"]}>
                <div className={styles["date-range-container"]}>
                  <div className={styles["start-at"]}>
                    <div className={styles["day"]}>
                      {month.get(event.start_at.slice(5, 7)) +
                        " " +
                        event.start_at.slice(8, 10)}
                    </div>
                    <div className={styles["time"]}>
                      {event.start_at.slice(11, 16)}
                    </div>
                  </div>

                  <div className={styles["expire-at"]}>
                    <div className={styles["day"]}>
                      {month.get(event.expire_at.slice(5, 7)) +
                        " " +
                        event.expire_at.slice(8, 10)}
                    </div>
                    <div className={styles["time"]}>
                      {event.expire_at.slice(11, 16)}
                    </div>
                  </div>
                </div>

                <div className={styles["vl"]}></div>
                <div className={styles["location"]}>{event.location}</div>
              </div>

              {/* Participants */}
              <h3 className={styles["heading"]}>Participants</h3>
              <div className={styles["participant-container"]}>
                <div
                  className={styles["participants-photos"]}
                  onMouseLeave={() => setHoveredParticipant(null)}
                >
                  {participantsToShow.map((participant) => (
                    <div
                      className={styles["item"]}
                      key={participant.id}
                      onMouseEnter={() => {
                        getUserById(participant.user_id).then((data) => {
                          setHoveredParticipant(data);
                        });
                      }}
                    >
                      <img
                        className={styles["participant-img"]}
                        src={participant.participant_photo.photo_url}
                        alt="Participant Img"
                      />
                    </div>
                  ))}
                  {isShowMoreParticipants && (
                    <div className={styles["show-more-participants"]}>
                      <button
                        onClick={handleShowAllParticipants}
                        className={styles["show-more-participants-btn"]}
                      >
                        <IoIosMore
                          className={styles["show-more-participants-btn-icon"]}
                        />
                      </button>
                    </div>
                  )}

                  {hoveredParticipant && (
                    <ParticipantInfoPopUp participant={hoveredParticipant} />
                  )}
                </div>
              </div>

              {/* About section */}
              <h3 className={styles["heading"]}>About this event</h3>
              <div className={styles["about-container"]}>
                <div
                  className={styles[isShowMore ? null : "about-text"]}
                  ref={aboutText}
                >
                  {event.description}
                </div>
                {isOverflowAboutText && (
                  <button
                    onClick={handleShowMore}
                    className={styles["show-more-btn"]}
                    ref={showMoreBtn}
                  >
                    Show more
                  </button>
                )}
              </div>

              {/* Lower section */}
              <div className={styles["lower-container"]}>
                <div className={styles["spots"]}>
                  {event.max_participants - event.participant_count} Spots left
                </div>

                {participantState === null && (
                  <PrimaryButton
                    className={styles["action-btn"]}
                    onClick={() => navigate("login")}
                  >
                    Join
                  </PrimaryButton>
                )}

                {participantState === "NONE" &&
                  userId !== event.owner_id &&
                  userId && (
                    <PrimaryButton
                      className={styles["action-btn"]}
                      onClick={() => {
                        createParticipant(userId, eventId).then(() =>
                          setParticipantState("REQUESTED")
                        );
                      }}
                    >
                      Join
                    </PrimaryButton>
                  )}

                {participantState === "REQUESTED" &&
                  userId !== event.owner_id &&
                  userId && (
                    <PrimaryButton
                      className={`${styles["action-btn"]} ${styles["action-2-btn"]}`}
                      onClick={() =>
                        getParticipantByUserId(userId, eventId).then((data) => {
                          deleteParticipant(data.id, eventId).then(() =>
                            setParticipantState("NONE")
                          );
                        })
                      }
                    >
                      Cancel
                    </PrimaryButton>
                  )}

                {participantState === "JOINED" &&
                  userId !== event.owner_id &&
                  userId && (
                    <PrimaryButton
                      className={`${styles["action-btn"]} ${styles["action-2-btn"]}`}
                      onClick={() =>
                        getParticipantByUserId(userId, eventId).then((data) => {
                          deleteParticipant(data.id, eventId).then(() =>
                            setParticipantState("NONE")
                          );
                        })
                      }
                    >
                      Leave
                    </PrimaryButton>
                  )}

                {userId === event.owner_id && (
                  <div className={styles["btns-container"]}>
                    {participantState === "NONE" && (
                      <PrimaryButton
                        className={styles["isOwner-action-btn"]}
                        onClick={() => {
                          createParticipant(userId, eventId).then(() =>
                            getParticipantByUserId(userId, eventId).then(
                              (participant) => {
                                addParticipant(
                                  userId,
                                  eventId,
                                  participant.id
                                ).then(() => {
                                  setParticipantState("JOINED");
                                });
                              }
                            )
                          );
                        }}
                      >
                        Join
                      </PrimaryButton>
                    )}

                    {participantState === "JOINED" && (
                      <PrimaryButton
                        className={`${styles["isOwner-action-btn"]} ${styles["isOwner-action-2-btn"]}`}
                        onClick={() =>
                          getParticipantByUserId(userId, eventId).then(
                            (data) => {
                              deleteParticipant(data.id, eventId).then(() =>
                                setParticipantState("NONE")
                              );
                            }
                          )
                        }
                      >
                        Leave
                      </PrimaryButton>
                    )}

                    <PrimaryButton className={styles["isOwner-edit-btn"]}>
                      Edit
                    </PrimaryButton>
                  </div>
                )}
              </div>
            </motion.div>
          )}

          {showAllParticipants && (
            <ParticipantsList
              event={event}
              handleGoBackToSideBar={handleShowAllParticipants}
              handleCloseWindow={handleCloseWindow}
            />
          )}
        </div>
      )}
      )
    </AnimatePresence>
  );
};

export default EventInfoSideBar;
