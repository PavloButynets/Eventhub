import React from "react";
import styles from "./ListEvents.module.css";
import {
  UserOutlined,
  CalendarOutlined,
  ClockCircleOutlined,
  EnvironmentOutlined,
} from "@ant-design/icons";

const SearchResults = ({ eventsData }) => {
  const results = [
    {
      id: 1,
      title: "Football tournament",
      date_start: "12.03",
      date_end: "31.03",
      start_time: "10:00",
      end_time: "21:00",
      location: "Lviv, Shevchenka, 51",
      curr_number_of_participants: "7",
      max_number_of_participants: "12",
      image: "/images/test_event_image.png",
    },
    {
      id: 2,
      title: "Event 2",
      date_start: "12.03",
      date_end: "31.03",
      start_time: "10:00",
      end_time: "21:00",
      location: "Lviv, Shevchenka, 51",
      curr_number_of_participants: "7",
      max_number_of_participants: "12",
      image: "/images/test_event_image.png",
    },
    {
      id: 3,
      title: "Event 2",
      date_start: "12.03",
      date_end: "31.03",
      start_time: "10:00",
      end_time: "21:00",
      location: "Lviv, Shevchenka, 51",
      curr_number_of_participants: "7",
      max_number_of_participants: "12",
      image: "/images/test_event_image.png",
    },
    {
      id: 4,
      title: "Event 2",
      date_start: "12.03",
      date_end: "31.03",
      start_time: "10:00",
      end_time: "21:00",
      location: "Lviv, Shevchenka, 51",
      curr_number_of_participants: "7",
      max_number_of_participants: "12",
      image: "/images/test_event_image.png",
    },
    {
      id: 5,
      title: "Event 2",
      date_start: "12.03",
      date_end: "31.03",
      start_time: "10:00",
      end_time: "21:00",
      location: "Lviv, Shevchenka, 51",
      curr_number_of_participants: "7",
      max_number_of_participants: "12",
      image: "/images/test_event_image.png",
    },
    {
      id: 6,
      title: "Event 2",
      date_start: "12.03",
      date_end: "31.03",
      start_time: "10:00",
      end_time: "21:00",
      location: "Lviv, Shevchenka, 51",
      curr_number_of_participants: "7",
      max_number_of_participants: "12",
      image: "/images/test_event_image.png",
    },
    {
      id: 7,
      title: "Event 2",
      date_start: "12.03",
      date_end: "31.03",
      start_time: "10:00",
      end_time: "21:00",
      location: "Lviv, Shevchenka, 51",
      curr_number_of_participants: "7",
      max_number_of_participants: "12",
      image: "/images/test_event_image.png",
    },
    {
      id: 8,
      title: "Event 2",
      date_start: "12.03",
      date_end: "31.03",
      start_time: "10:00",
      end_time: "21:00",
      location: "Lviv, Shevchenka, 51",
      curr_number_of_participants: "7",
      max_number_of_participants: "12",
      image: "/images/test_event_image.png",
    },
    {
      id: 9,
      title: "Event 2",
      date_start: "12.03",
      date_end: "31.03",
      start_time: "10:00",
      end_time: "21:00",
      location: "Lviv, Shevchenka, 51",
      curr_number_of_participants: "7",
      max_number_of_participants: "12",
      image: "/images/test_event_image.png",
    },
    {
      id: 10,
      title: "Event 2",
      date_start: "12.03",
      date_end: "31.03",
      start_time: "10:00",
      end_time: "21:00",
      location: "Lviv, Shevchenka, 51",
      curr_number_of_participants: "7",
      max_number_of_participants: "12",
      image: "/images/test_event_image.png",
    },
    {
      id: 11,
      title: "Event 2",
      date_start: "12.03",
      date_end: "31.03",
      start_time: "10:00",
      end_time: "21:00",
      location: "Lviv, Shevchenka, 51",
      curr_number_of_participants: "7",
      max_number_of_participants: "12",
      image: "/images/test_event_image.png",
    },
    {
      id: 12,
      title: "Event 2",
      date_start: "12.03",
      date_end: "31.03",
      start_time: "10:00",
      end_time: "21:00",
      location: "Lviv, Shevchenka, 51",
      curr_number_of_participants: "7",
      max_number_of_participants: "12",
      image: "/images/test_event_image.png",
    },
  ];
  const default_image =
    "https://eventhub12.blob.core.windows.net/images/default.jpg?sp=r&st=2024-03-18T06:52:24Z&se=2024-03-24T14:52:24Z&spr=https&sv=2022-11-02&sr=b&sig=nWb0Dzb9%2FWPfAZ6X5MRrwoi%2FxHU8OLe0I6nPtwpBkbQ%3D";

  return (
    <div className={styles.SearchResults}>
      <ul className={styles.ListStyleNone}>
        {eventsData.map((event) => (
          <li key={event.id} className={styles.ResultDataContainer}>
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
