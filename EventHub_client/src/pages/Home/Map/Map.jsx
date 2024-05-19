import React, { useState, useRef, useCallback, useEffect } from "react";
import queryString from "query-string";
import { getEventsData } from "../../../api/getEventsLocation";
import { GoogleMap, Marker, MarkerClusterer } from "@react-google-maps/api";

import styles from "./Map.module.css";
import { useSearchParams } from "react-router-dom";
import { getEventsDataSearch } from "../../../api/getEventsData";
import { getCheckbuttonsEvents } from "../../../api/getCheckbuttonsEvents";
import { RoundButton } from "../../../components/Buttons/RoundButton/roundButton";
import { light } from "./Theme";

import { useNavigate } from "react-router-dom";
import { getFilteredEvents } from "../../../api/getFilteredEvents";
import GetLocationByCoordinates from "../../../api/getLocationByCoordinates";
import useAuth from "../../../hooks/useAuth";
import useStore from '../../../hooks/useStore';
import { message } from "antd";
import { AimOutlined } from "@ant-design/icons";

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
  minZoom: 5,
  maxZoom: 20,
  restriction: {
    latLngBounds: {
      north: 85,
      south: -80,
      west: -180,
      east: 180,
    },
    strictBounds: false,
  },

};
const defaultCenter = {
  lat: 49.83826,
  lng: 24.02324,
};
const Map = () => {

  const [events, setEvents] = useState([]);
  const [searchParams, setSearchParams] = useSearchParams();
  const [selectedPlace, setSelectedPlace] = useState(null);
  const [showMarker, setShowMarker] = useState(false);
  const [userLocation, setUserLocation] = useState(null);
  const mapRef = useRef(undefined);
  const { auth } = useAuth();
  const navigate = useNavigate();
  const location = useStore((state) => state.location);
  const onLoad = useCallback(function callback(map) {
    mapRef.current = map;
  }, []);

  const onUnmount = useCallback(function callback(map) {
    mapRef.current = map;
  }, []);

  useEffect(() => {
    if (location.lat && location.lng) {
      mapRef.current.panTo({ lat: location.lat, lng: location.lng });
      mapRef.current.setZoom(17);
    }
  }, [location]);

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
      } else if (searchParams.get("my_events")) {
        const parsed = queryString.parse(window.location.search);

        try {
          const data = await getCheckbuttonsEvents(
            parsed.checkboxMy === "true",
            parsed.checkboxJoined === "true",
            parsed.checkboxPending === "true",
            parsed.checkboxArchive === "true"
          );
          setEvents(data);
        } catch (error) {
          console.error("Error getting events data:", error);
        }
      } else {
        await getEventsData()
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
    navigate({
      pathname: `/event/${event.id}`,
      search: `?${searchParams.toString()}`,
    });
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

  useEffect(() => {
    handleCenterMap()
  }, [])
  const handleCenterMap = () => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          const { latitude, longitude } = position.coords;
          mapRef.current.panTo({ lat: latitude, lng: longitude }); // Плавно центрує мапу
          mapRef.current.setZoom(17); // Змінює зум мапи
          setUserLocation({ lat: latitude, lng: longitude })
        },
        (error) => {
          console.error("Error getting user location:", error);
        },
        {
          enableHighAccuracy: true, // Включає високу точність
          timeout: 5000, // Максимальний час очікування запиту
          maximumAge: 0, // Використовуємо завжди найсвіжіші дані
        }
      );
    } else {
      console.error("Geolocation is not supported by this browser.");
    }
  };

  return (
    <div className={styles.mapcontainer}>
      <GoogleMap
        mapContainerStyle={containerStyle}
        center={defaultCenter}
        zoom={12}
        onLoad={onLoad}
        onUnmount={onUnmount}
        options={defaultOption}
        onClick={handleMapClick}
      >
        {/* <MarkerClusterer>
  {(clusterer) =>
    events.map((event) => {
      console.log(clusterer); // Розмістіть console.log тут
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
          clusterer={clusterer}
        />
      );
    })
  }
</MarkerClusterer> */}

        {userLocation && (
          <Marker
            position={userLocation}
            icon={{
              url: "/images/myLocation.svg",
              scaledSize: new window.google.maps.Size(40, 40),
            }}
          />
        )}
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
      <div className={styles.centerButton}>
        <RoundButton icon={<AimOutlined />} onClick={handleCenterMap} />
      </div>
    </div>
  );
};


export { Map };
