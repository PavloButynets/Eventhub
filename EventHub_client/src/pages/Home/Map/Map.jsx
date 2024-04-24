import React, { useState, useRef, useCallback, useEffect } from "react";
import { getEventsData } from "../../../api/getEventsLocation";
import { GoogleMap, Marker, InfoWindow } from "@react-google-maps/api";
import styles from "./Map.module.css";
import { useSearchParams, useParams } from "react-router-dom";
import { getEventsDataSearch } from "../../../api/getEventsData";
import { light } from "./Theme";

import { useNavigate } from "react-router-dom";
import { getFilteredEvents } from "../../../api/getFilteredEvents";
import GetLocationByCoordinates from "../../../api/getLocationByCoordinates";
import useAuth from "../../../hooks/useAuth";
import { message } from "antd";

const MAP_API_KEY = process.env.REACT_APP_GOOGLE_MAPS_API_KEY;
const containerStyle = {
  width: "100%",
  height: "100%",
};

const defaultOption = {
  panControl: true,
  zoomControl: false,
  mapTypeControl: false,
  scaleControl: false,
  streetViewControl: false,
  rotateControl: false,
  fullscreenControl: false,
  clicableIcons: false,
  scrollwheel: true,
  disableDoubleClickZoom: true,
  styles: light,
};

const Map = ({ center }) => {
  const mapRef = useRef(undefined);
  const { auth, setAuth } = useAuth();

  const navigate = useNavigate();
  const onLoad = useCallback(function callback(map) {
    mapRef.current = map;
  }, []);

  const onUnmount = useCallback(function callback(map) {
    mapRef.current = map;
  }, []);

  const [events, setEvents] = useState([]);
  const [selectedEvent, setSelectedEvent] = useState(null);
  const [searchParams, setSearchParams] = useSearchParams();
  const [selectedPlace, setSelectedPlace] = useState(null);
  const [showMarker, setShowMarker] = useState(false);

  useEffect(() => {
    const searchValue = searchParams.get("search");

    const fetchData = async () => {
      if (searchValue) {
        try {
          const data = await getEventsDataSearch(searchValue);
          setEvents(data);
        } catch (error) {
          console.error("Error getting events data:", error);
        }
      } else if (searchParams.get("filter")) {
        try {
          const data = await getFilteredEvents();
          setEvents(data);
        } catch (error) {
          console.error("Error getting events data:", error);
        }
      } else {
        getEventsData()
          .then((data) => {
            setEvents(data);
          })
          .catch((error) => {
            console.error("Error getting events data:", error);
          });
      }
    };

    fetchData();
  }, [searchParams]);
  const onMarkerClick = (event) => {
    setSelectedEvent(event);
    navigate({
      pathname: `/event/${event.owner_id}/${event.id}`,
      search: `?${searchParams.toString()}`,
    });
  };

  const onMapClick = () => {
    setSelectedEvent(null);
  };
  const handleMapClick = async (event) => {
    if (!auth.token) {
      message.info("You need to login to create an event");
      return;
    }

    const lat = event.latLng.lat();
    const lng = event.latLng.lng();
    setSearchParams({ create_event: true, latitude: lat, longitude: lng });
    const locationData = await GetLocationByCoordinates(lat, lng);
    if (locationData) {
      setSelectedPlace(locationData);
      setShowMarker(true); // Показати маркер
      setTimeout(() => setShowMarker(false), 5000); // Приховати маркер через 5 секунд
    } else {
      console.log("Error fetching location data");
    }
  };
  return (
    <div className={styles.mapcontainer}>
      <GoogleMap
        mapContainerStyle={containerStyle}
        center={center}
        zoom={10}
        onLoad={onLoad}
        onUnmount={onUnmount}
        options={defaultOption}
        onClick={handleMapClick}
      >
        <></>
        {events &&
          events.map((event) => {
            return (
              <Marker
                key={event.id}
                position={{
                  lat: Number(event.latitude),
                  lng: Number(event.longitude),
                }}
                icon={{
                  url: "/images/pin.svg",
                  scaledSize: new window.google.maps.Size(40, 40),
                }}
                onClick={() => onMarkerClick(event)}
              />
            );
          })}
        {selectedPlace && showMarker && (
          <Marker
            position={{ lat: selectedPlace.lat, lng: selectedPlace.lng }}
            icon={{
              url: "/images/pin.svg",
              scaledSize: new window.google.maps.Size(40, 40),
            }}
          />
        )}

        {events &&
          events.map((event) => (
            <Marker
              key={event.eventID}
              position={{
                lat: Number(event.latitude),
                lng: Number(event.longitude),
              }}
              icon={{
                url: "/images/pin.svg",
                scaledSize: new window.google.maps.Size(40, 40),
              }}
              onClick={() => onMarkerClick(event)}
            />
          ))}
      </GoogleMap>
    </div>
  );
};

export { Map };
