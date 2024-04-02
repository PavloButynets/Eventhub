import { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import styles from "./EventInfoSideBar.module.css";

import { IoIosMore } from "react-icons/io";

import { getParticipants } from "../../../api/getParticipants";
import { getUserById } from "../../../api/getUserById";
import { getFullEventById } from "../../../api/getFullEventById";

import ImageSlider from "../../../components/ImageSlider/ImageSlider";
import CloseWindowButton from "../../../components/Buttons/CloseWindowButton/CloseWindowButton";
import ParticipantsList from "./ParticipantsList";

import { AnimatePresence, motion } from "framer-motion";
import ParticipantInfoPopUp from "../../../components/PopUp/ParticipantInfoPopUp";
import PrimaryButton from "../../../components/Buttons/PrimaryButton/PrimaryButton";
import OwnerPhotoOverlay from "../../../components/OwnerPhotoOverlay/OwnerPhotoOverlay";

const EventInfoSideBar = ({ ownerId, eventId }) => {
  // States
  const [isShowMore, setIsShowMore] = useState(false);
  const [isOverflowAboutText, setIsOverflowAboutText] = useState(false);
  const [isShowMoreParticipants, setIsShowMoreParticipants] = useState(false);
  const [participantsToShow, setParticipantsToShow] = useState([]);
  const [event, setEvent] = useState(null);

  const [owner, setOwner] = useState(null);

  const [hoveredParticipant, setHoveredParticipant] = useState(null);
  const [showOwnerPopUp, setShowOwnerPopUp] = useState(false);

  const [showAllParticipants, setShowAllParticipants] = useState(false);

  const navigate = useNavigate();

  const handleCloseWindow = () => {
    // setEvent(null);
    navigate("../");
  };

  // Refs
  const sideBar = useRef(null);
  const showMoreBtn = useRef(null);
  const aboutText = useRef(null);

  // Params
  // const { ownerId, eventId } = useParams();

  // Effects
  useEffect(() => {
    getFullEventById(ownerId, eventId).then((data) => {
      setEvent(data);
    });
  }, [ownerId, eventId]);

  useEffect(() => {
    event &&
      getParticipants(event.id).then((data) => {
        console.log("Data: ", data);
        if (data.length > 4) {
          setIsShowMoreParticipants(true);
          setParticipantsToShow(data.slice(0, 4));
        } else {
          setParticipantsToShow(data);
        }

        if (aboutText.current.scrollHeight > aboutText.current.clientHeight) {
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

  const month = new Map();
  month.set("01", "Jan");
  month.set("02", "Feb");
  month.set("03", "Mar");
  month.set("04", "Apr");
  month.set("05", "May");
  month.set("06", "Jun");
  month.set("07", "Jul");
  month.set("08", "Aug");
  month.set("09", "Sep");
  month.set("10", "Nov");
  month.set("11", "Oct");
  month.set("12", "Dec");

  // Funcs
  const handleShowAllParticipants = () => {
    setShowAllParticipants(!showAllParticipants);
  };

  const handleShowMore = () => {
    setIsShowMore(!isShowMore);
    showMoreBtn.current.innerHTML = isShowMore ? "Show more" : "Show less";
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
                <div className={styles["participants-photos"]}>
                  <OwnerPhotoOverlay
                    owner={owner}
                    onMouseEnter={() => setShowOwnerPopUp(true)}
                    onMouseLeave={() => setShowOwnerPopUp(false)}
                    showPopUp={showOwnerPopUp}
                  />
                  {participantsToShow.map((participant) => (
                    <div
                      className={styles["item"]}
                      key={participant.id}
                      onMouseEnter={() => {
                        getUserById(participant.user_id).then((data) => {
                          setHoveredParticipant(data);
                        });
                      }}
                      onMouseLeave={() => setHoveredParticipant(null)}
                    >
                      <img
                        className={styles["participant-img"]}
                        src={participant.participant_photo.photo_url}
                        alt="Participant Img"
                      />
                      {hoveredParticipant &&
                        hoveredParticipant.id === participant.user_id && (
                          <ParticipantInfoPopUp
                            participant={hoveredParticipant}
                          />
                        )}
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

                <PrimaryButton className={styles["action-btn"]}>
                  Action
                </PrimaryButton>
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
