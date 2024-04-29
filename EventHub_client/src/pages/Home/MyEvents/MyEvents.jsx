import React, { useState } from "react";
import { CalendarOutlined } from "@ant-design/icons";
import { RoundButton } from "../../../components/Buttons/RoundButton/roundButton";
import styles from "./MyEvents.module.css";
import EventList from "./MyEventsList";
import { useSearchParams, useNavigate } from "react-router-dom";
import useAuth from "../../../hooks/useAuth";

const MyEvents = () => {
  const [searchParams, setSearchParams] = useSearchParams("");
  const navigate = useNavigate();
  const { auth, setAuth } = useAuth();

  const handleButtonClose = async () => {
    if(!auth.token){
      navigate("/login")
    }
    else if (searchParams.get("my_events")) {
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
        />
      )}
    </div>
  );
};

export default MyEvents;
