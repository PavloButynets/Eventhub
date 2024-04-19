import styles from "./MyEvents.module.css";
import React, { useState, useEffect } from "react";
import { Checkbox } from "antd";
import CloseWindowButton from "../../../components/Buttons/CloseWindowButton/CloseWindowButton";
import ListEvents from "../../../components/ListEvents/ListEvents";
import { getCheckbuttonsEvents } from "../../../api/getCheckbuttonsEvents";

const MyEventsList = ({ handleButtonClose }) => {
  const [events, setEvents] = useState([]);
  const [checkboxMy, setCheckboxMy] = useState(true);
  const [checkboxJoined, setCheckboxJoined] = useState(true);
  const [checkboxPending, setCheckboxPending] = useState(false);
  const [checkboxArchive, setCheckboxArchive] = useState(false);

  const sendCheckboxes = async () => {
    try {
      const data = await getCheckbuttonsEvents(
        checkboxMy,
        checkboxJoined,
        checkboxPending,
        checkboxArchive
      );
      setEvents(data);
    } catch (error) {
      console.error("Error getting events data:", error);
    }
  };

  useEffect(() => {
    sendCheckboxes();
  }, [checkboxMy, checkboxJoined, checkboxPending, checkboxArchive]);

  return (
    <div className={styles.BackgroungContainer}>
      <div className={styles.Heading}>
        <h2>Events</h2>
        <CloseWindowButton
          onClick={() => {
            handleButtonClose();
          }}
        />
      </div>

      <div className={styles.CheckboxContainer}>
        <label>
          <div className={styles.Checkbox}>
            <Checkbox
              onChange={() => setCheckboxMy(!checkboxMy)}
              checked={checkboxMy}
            >
              My Events
            </Checkbox>
          </div>
          <div className={styles.Checkbox}>
            <Checkbox
              onChange={() => setCheckboxJoined(!checkboxJoined)}
              checked={checkboxJoined}
            >
              Joined Events
            </Checkbox>
          </div>
          <div className={styles.Checkbox}>
            <Checkbox
              onChange={() => setCheckboxPending(!checkboxPending)}
              checked={checkboxPending}
            >
              Pending request
            </Checkbox>
          </div>
          <div className={styles.Checkbox}>
            <Checkbox
              onChange={() => setCheckboxArchive(!checkboxArchive)}
              checked={checkboxArchive}
            >
              Archive
            </Checkbox>
          </div>
        </label>
      </div>

      <div className={styles.EventResults}>
        <ListEvents eventsData={events} />
      </div>
    </div>
  );
};

export default MyEventsList;
