import { useContext, useEffect, useState } from "react";
import { useOutlet } from "react-router-dom";
import AuthContext from "../../context/authProvider";
import { Link, Outlet, useParams, useLocation } from "react-router-dom";
import { Map } from "./Map/Map";
import useAuth from "../../hooks/useAuth";
import { Button } from "antd";
import styles from "./Home.module.css";
import { useJsApiLoader } from "@react-google-maps/api";
import MenuButton from "./ProfileORlogin/ProfileButton";
import LoginRegisterButton from "./ProfileORlogin/LoginRegisterButton";

import SearchEvents from "./Search/Search";
import CreateEvent from "./CreateEvent/CreateEvent";
import EventFilter from "./Filter/Filter";
import ProcessingEffect from "../../components/ProcessingEffect/ProcessingEffect";
import MyEvents from "./MyEvents/MyEvents";
import useLogin from "../../hooks/useLogin";


const MAP_API_KEY = process.env.REACT_APP_GOOGLE_MAPS_API_KEY;

const defaultCenter = {
  lat: 49.83826,
  lng: 24.02324,
};
const libraries = ["places"];
const Home = () => {
  const authenticated = useLogin();
  const location = useLocation();

  const outlet = useOutlet();
  

  const { isLoaded } = useJsApiLoader({
    id: "google-map-script",
    googleMapsApiKey: MAP_API_KEY,
    libraries,
  });

  return (
    <div className={styles.Home}>
      {isLoaded ? (
        <>
          <Map center={defaultCenter} />

          {authenticated ? <MenuButton /> : <LoginRegisterButton />}

          <SearchEvents />
          <CreateEvent />
          <EventFilter />
          <MyEvents />

          {outlet}
        </>
      ) : (
        <ProcessingEffect/>
      )}
    </div>
  );
};

export { Home };
