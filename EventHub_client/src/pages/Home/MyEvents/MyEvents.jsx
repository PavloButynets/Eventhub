import React, { useState } from "react";
import { CalendarOutlined } from "@ant-design/icons";
import { RoundButton } from "../../../components/Buttons/RoundButton/roundButton";
import styles from "./MyEvents.module.css";
import EventList from "./MyEventsList";
import { useSearchParams } from "react-router-dom";

const MyEvents = () => {
  const [searchParams, setSearchParams] = useSearchParams("");

  const handleButtonClose = async () => {
    if (searchParams.get("my_events")) {
      setSearchParams("");
    } else {
      setSearchParams({ my_events: "true" });
    }
  };

  return (
    <div className={styles.filterContainer}>
      <div className={styles.filterButtonContainer}>
        <RoundButton onClick={handleButtonClose} icon={<CalendarOutlined />} />
      </div>
      {searchParams.get("my_events") && (
        <EventList
          handleButtonClose={handleButtonClose}
          searchParams={searchParams}
        />
      )}
    </div>
  );
};

export default MyEvents;
