import { useState, useEffect, useRef } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import styles from "./EventInfoSideBar.module.css";

import { IoIosMore } from "react-icons/io";

import { LoadingOutlined } from "@ant-design/icons";

import { getJoinedParticipants } from "../../../api/getJoinedParticipants";
import { getUserById } from "../../../api/getUserById";
import { getFullEventById } from "../../../api/getFullEventById";

import ImageSlider from "../../../components/ImageSlider/ImageSlider";
import CloseWindowButton from "../../../components/Buttons/CloseWindowButton/CloseWindowButton";
import ParticipantsList from "./ParticipantsList";

import ParticipantState from "../../../utils/ParticipantState";

import ParticipantInfoPopUp from "../../../components/PopUp/ParticipantInfoPopUp";
import PrimaryButton from "../../../components/Buttons/PrimaryButton/PrimaryButton";
import OwnerPhotoOverlay from "../../../components/OwnerPhotoOverlay/OwnerPhotoOverlay";
import useAuth from "../../../hooks/useAuth";
import { getParticipantState } from "../../../api/getParticipantState";
import { getParticipantByUser } from "../../../api/getParticipantByUser";
import { deleteParticipant } from "../../../api/deleteParticipant";
import { createParticipant } from "../../../api/createParticipant";
import { addParticipant } from "../../../api/addParticipant";
import SpotsLeft from "../../../components/Spots/SpotsLeft";
import RequestsList from "./RequestsList";
import { getRequestsByEventId } from "../../../api/getRequestsByEventId";
import RequestsCount from "../../../components/RequestsCount/RequestsCount";
import { message } from "antd";

const EventInfoSideBar = ({ ownerId, eventId }) => {
  // States
  const [isShowMore, setIsShowMore] = useState(false);
  const [isOverflowAboutText, setIsOverflowAboutText] = useState(false);
  const [participantsToShow, setParticipantsToShow] = useState([]);
  const [event, setEvent] = useState(null);

  const [owner, setOwner] = useState(null);

  const [hoveredParticipant, setHoveredParticipant] = useState(null);

  const [showAllParticipants, setShowAllParticipants] = useState(false);

  const [showRequests, setShowRequests] = useState(false);

  const [requests, setRequests] = useState(null);

  const [userState, setUserState] = useState(null);

  const [isOwner, setIsOwner] = useState(false);

  const [reloadList, setReloadList] = useState(false);

  const [isLoading, setIsLoading] = useState(true);

  const [isFull, setIsFull] = useState(true);

  const [errorMsg, setErrorMsg] = useState(null);

  // Params
  const [searchParams] = useSearchParams();

  // Auth
  const { auth } = useAuth();

  // Navigation
  const navigate = useNavigate();

  // Refs
  const sideBar = useRef(null);
  const showMoreBtn = useRef(null);
  const aboutText = useRef(null);

  useEffect(() => {
    if (auth.token) {
      getParticipantState(eventId)
        .then((data) => {
          setUserState(data.state);

          setIsOwner(data.owner);
        })
        .catch((error) => {
          setErrorMsg("An error occurerd");
        });
    } else {
      setUserState(null);
      setIsOwner(false);
    }
  }, [eventId, auth]);

  useEffect(() => {
    getFullEventById(ownerId, eventId)
      .then((data) => {
        setEvent(data);
        setIsLoading(false);
        setIsFull(data.max_participants === data.participant_count);
      })
      .catch((error) => {
        setErrorMsg("An error occurerd");
      });
  }, [ownerId, eventId, userState, reloadList]);

  useEffect(() => {
    event &&
      getJoinedParticipants(event.id)
        .then((data) => {
          setParticipantsToShow(data.slice(0, 5));
        })
        .catch((error) => {
          setErrorMsg("An error occurerd");
        });

    return () => setIsShowMore(false);
  }, [event]);

  useEffect(() => {
    if (
      event &&
      !showAllParticipants &&
      !showRequests &&
      aboutText.current.scrollHeight > aboutText.current.clientHeight
    ) {
      setIsOverflowAboutText(true);
    } else {
      setIsOverflowAboutText(false);
    }
  }, [event]);

  useEffect(() => {
    event &&
      getUserById(event.owner_id)
        .then((data) => {
          setOwner(data);
        })
        .catch((error) => {
          setErrorMsg("An error occurerd");
        });
  }, [event]);

  useEffect(() => {
    const resetSideBar = () => {
      setShowAllParticipants(false);
      setShowRequests(false);
    };
    resetSideBar();
  }, [eventId]);

  useEffect(() => {
    try {
      isOwner &&
        getRequestsByEventId(eventId)
          .then((data) => setRequests(data))
          .catch((error) => {
            setErrorMsg("An error occurerd");
          });
    } catch (error) {
      setRequests(null);
    }
  }, [eventId, isOwner, reloadList]);

  useEffect(() => {
    showAllParticipants && setIsLoading(true);
  }, [showAllParticipants]);

  useEffect(() => {
    // Display error message
    if (errorMsg) {
      message.error({
        content: errorMsg,
        onClose: handleCloseMessage,
      });
    }
  }, [errorMsg]);

  // Funcs
  const handleCloseMessage = () => {
    // Clear error message
    setErrorMsg(null);
  };

  const handleShowAllParticipants = () => {
    setShowAllParticipants(!showAllParticipants);
    setShowRequests(false);
  };

  const handleShowRequests = () => {
    setShowRequests(!showRequests);
    setShowAllParticipants(false);
  };

  const handleShowMore = () => {
    setIsShowMore(!isShowMore);
    showMoreBtn.current.innerHTML = isShowMore ? "Show more" : "Show less";
  };

  const handleCloseWindow = () => {
    navigate({ pathname: "../", search: `?${searchParams.toString()}` });
  };

  const handleJoinEvent = async () => {
    try {
      if (userState === ParticipantState.NONE) {
        await createParticipant(eventId);
        setUserState(ParticipantState.REQUESTED);
      }
    } catch (error) {
      if (error.response) {
        const responseData = error.response.data;
        if (
          typeof responseData === "string" &&
          responseData.includes("is full")
        ) {
          message.info("Event is full");
        } else {
          setErrorMsg("An error occurred");
        }
      } else {
        setErrorMsg("An error occurred");
      }
    }
  };

  const handleCancelRequest = async () => {
    try {
      if (userState === ParticipantState.REQUESTED) {
        const participant = await getParticipantByUser(eventId);
        await deleteParticipant(participant.id, eventId);
        setUserState(ParticipantState.NONE);
      }
    } catch (error) {
      setErrorMsg("An error occured");
    }
  };

  const handleLeaveEvent = async () => {
    try {
      if (userState === ParticipantState.JOINED) {
        const participant = await getParticipantByUser(eventId);
        await deleteParticipant(participant.id, eventId);
        setUserState(ParticipantState.NONE);
      }
    } catch (error) {
      setErrorMsg("An error occured");
    }
  };

  const handleJoinOwnerEvent = async () => {
    try {
      if (!isOwner) return;

      if (userState === ParticipantState.NONE) {
        await createParticipant(eventId);
        const participant = await getParticipantByUser(eventId);
        await addParticipant(eventId, participant.id);
        setUserState(ParticipantState.JOINED);
      }
    } catch (error) {
      if (error.response) {
        const responseData = error.response.data;
        if (
          typeof responseData === "string" &&
          responseData.includes("is full")
        ) {
          message.info("Event is full");
        } else {
          setErrorMsg("An error occurred");
        }
      } else {
        setErrorMsg("An error occurred");
      }
    }
  };

  const getFormattedDate = (dateTimeString) => {
    const date = new Date(dateTimeString);
    const month = date.toLocaleString("default", { month: "short" });
    const day = date.getDate();
    return `${month} ${day}`;
  };

  const getFormattedTime = (dateTimeString) => {
    const date = new Date(dateTimeString);
    const hours = date.getHours().toString().padStart(2, "0");
    const minutes = date.getMinutes().toString().padStart(2, "0");
    return `${hours}:${minutes}`;
  };

  return (
    <div className={styles["wrapper-container"]}>
      <div className={styles["loading-circle"]}>
        {isLoading && (
          <LoadingOutlined
            style={{ fontSize: "72px", color: "#aaaaaa", fontWeigh: "1000" }}
          />
        )}
      </div>
      {!showAllParticipants && !showRequests && event && (
        <div className={styles["side-bar-container"]} ref={sideBar}>
          <div className={styles["header"]}>
            <h2 className={styles["event-title"]}>{event.title}</h2>
            <CloseWindowButton onClick={handleCloseWindow} />
          </div>
          <main>
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
                    {getFormattedDate(event.start_at)}
                  </div>
                  <div className={styles["time"]}>
                    {getFormattedTime(event.start_at)}
                  </div>
                </div>

                <div className={styles["expire-at"]}>
                  <div className={styles["day"]}>
                    {getFormattedDate(event.expire_at)}
                  </div>
                  <div className={styles["time"]}>
                    {getFormattedTime(event.expire_at)}
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
                      getUserById(participant.user_id)
                        .then((data) => {
                          setHoveredParticipant(data);
                        })
                        .catch((error) => {
                          setErrorMsg("An error occurerd");
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
                <div className={styles["show-more-participants"]}>
                  <button
                    onClick={handleShowAllParticipants}
                    className={styles["show-more-participants-btn"]}
                  >
                    {requests && isOwner && requests.length > 0 && (
                      <div className={styles["requests-count-container"]}>
                        <RequestsCount requestsLength={requests.length} />
                      </div>
                    )}
                    <IoIosMore
                      className={styles["show-more-participants-btn-icon"]}
                    />
                  </button>
                </div>

                {hoveredParticipant && (
                  <ParticipantInfoPopUp participant={hoveredParticipant} />
                )}
              </div>
            </div>

            {/* About section */}
            <h3 className={styles["heading"]}>About this event</h3>
            <div className={styles["about-container"]}>
              <div
                className={
                  styles[isShowMore ? "about-text-full" : "about-text-hidden"]
                }
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
          </main>

          {/* Lower section */}
          <div className={styles["lower-container"]}>
            <SpotsLeft event={event} />

            {userState === null && (
              <PrimaryButton
                className={styles["action-btn"]}
                onClick={() => navigate("login")}
              >
                Join
              </PrimaryButton>
            )}

            {userState === ParticipantState.NONE && !isOwner && (
              <PrimaryButton
                className={styles["action-btn"]}
                onClick={handleJoinEvent}
              >
                Join
              </PrimaryButton>
            )}

            {userState === ParticipantState.REQUESTED && !isOwner && (
              <PrimaryButton
                className={`${styles["action-btn"]} ${styles["action-2-btn"]}`}
                onClick={handleCancelRequest}
              >
                Cancel
              </PrimaryButton>
            )}

            {userState === ParticipantState.JOINED && !isOwner && (
              <PrimaryButton
                className={`${styles["action-btn"]} ${styles["action-2-btn"]}`}
                onClick={handleLeaveEvent}
              >
                Leave
              </PrimaryButton>
            )}

            {isOwner && (
              <div className={styles["btns-container"]}>
                {userState === ParticipantState.NONE && (
                  <PrimaryButton
                    className={styles["isOwner-action-btn"]}
                    onClick={handleJoinOwnerEvent}
                  >
                    Join
                  </PrimaryButton>
                )}

                {userState === ParticipantState.JOINED && (
                  <PrimaryButton
                    className={`${styles["isOwner-action-btn"]} ${styles["isOwner-action-2-btn"]}`}
                    onClick={handleLeaveEvent}
                  >
                    Leave
                  </PrimaryButton>
                )}

                <PrimaryButton
                  to={`/edit?eventId=${eventId}`}
                  className={styles["isOwner-edit-btn"]}
                >
                  Edit
                </PrimaryButton>
              </div>
            )}
          </div>
        </div>
      )}

      {showAllParticipants && !showRequests && (
        <ParticipantsList
          setIsLoading={setIsLoading}
          handleGoBackToSideBar={handleShowAllParticipants}
          handleCloseWindow={handleCloseWindow}
          handleShowRequests={handleShowRequests}
          isOwner={isOwner}
          setReloadList={setReloadList}
          requests={requests}
          _event={event}
        />
      )}

      {showRequests && isOwner && (
        <RequestsList
          _event={event}
          requests={requests}
          handleGoBackToParticipantsList={handleShowAllParticipants}
          handleCloseWindow={handleCloseWindow}
          setReloadList={setReloadList}
        />
      )}
    </div>
  );
};

export default EventInfoSideBar;
