import React from "react";
import styles from "./ListEvents.module.css";
import {
  UserOutlined,
  CalendarOutlined,
  ClockCircleOutlined,
  EnvironmentOutlined,
} from "@ant-design/icons";

import { useNavigate, useSearchParams } from "react-router-dom";

const SearchResults = ({ eventsData }) => {
  const [searchParams] = useSearchParams();

  const navigate = useNavigate();

  const handleClick = (ownerId, eventId) => {
    navigate({
      pathname: `/event/${eventId}`,
      search: `?${searchParams.toString()}`,
    });
  };

  return (
    <div className={styles.SearchResults}>
      <ul className={styles.ListStyleNone}>
        {eventsData.map((event) => (
          <li
            key={event.id}
            className={styles.ResultDataContainer}
            onClick={() => handleClick(event.owner_id, event.id)}
          >
            <img
              src={event.photo_response.photo_url}
              alt="Event"
              className={styles.EventImage}
            />
            <div className={styles.EventDescription}>
              <div className={styles.titleAndParticipants}>
                <h3>
                  {event.title.length > 16
                    ? `${event.title.slice(0, 16)}...`
                    : event.title}
                </h3>
                <div
                  className={`${styles.details} ${styles.iconTextContainer}`}
                >
                  <UserOutlined className={styles.icon} />
                  <p>
                    {event.participant_count}/{event.max_participants}
                  </p>
                </div>
              </div>
              <div className={`${styles.details} ${styles.iconTextContainer}`}>
                <CalendarOutlined className={styles.icon} />
                <p>
                  {new Date(event.start_at).toLocaleDateString()} -{" "}
                  {new Date(event.expire_at).toLocaleDateString()}
                </p>
              </div>
              <div className={`${styles.details} ${styles.iconTextContainer}`}>
                <ClockCircleOutlined className={styles.icon} />
                <p>
                  {new Date(event.start_at).toLocaleTimeString().slice(0, 5)} -{" "}
                  {new Date(event.expire_at).toLocaleTimeString().slice(0, 5)}
                </p>
              </div>
              <div className={`${styles.details} ${styles.iconTextContainer}`}>
                <EnvironmentOutlined className={styles.icon} />
                <p>{event.location}</p>
              </div>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default SearchResults;
