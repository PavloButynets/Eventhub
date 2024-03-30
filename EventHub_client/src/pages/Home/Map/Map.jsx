import React, { useState, useRef, useCallback, useEffect } from 'react';
import { getEventsData } from '../../../api/getEventsLocation';
import { GoogleMap, Marker,InfoWindow } from '@react-google-maps/api';
import styles from './Map.module.css'
import { EnvironmentOutlined } from '@ant-design/icons';
import { light } from "./Theme"
import EventInfoSideBar from '../EventInfoSideBar/EventInfoSideBar';
import ParticipantsList from '../EventInfoSideBar/ParticipantsList';



const MAP_API_KEY = process.env.REACT_APP_GOOGLE_MAPS_API_KEY
const containerStyle = {
  width: '100%',
  height: '100%',

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

}

const Map = ({ center }) => {

  const mapRef = useRef(undefined)

  const onLoad = useCallback(function callback(map) {
    mapRef.current = map;
  }, [])

  const onUnmount = useCallback(function callback(map) {
    mapRef.current = map;
  }, [])

  const [events, setEvents] = useState([]);
  const [selectedEvent, setSelectedEvent] = useState(null);
  

  useEffect(() => {
    getEventsData()
      .then(data => {
        console.log("Event Data", data);
        setEvents(data)
      })
  }, [])

  const onMarkerClick = (event) => {
    setSelectedEvent(event);
    // setShowAllParticipants(false);
  };

  const onMapClick = () => {
    setSelectedEvent(null);
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
        onClick={onMapClick}
      >

        <></>
        
        {events && events.map(event => (
          <Marker
            key={event.eventID}
            position={{ lat: Number(event.latitude), lng: Number(event.longitude) }}

            icon={{ url: '/images/pin.svg', 
            scaledSize: new window.google.maps.Size(40, 40) }}
            onClick={() => onMarkerClick(event)
            }
          />
        ))}
        {selectedEvent && (
          <InfoWindow
            position={{ lat: Number(selectedEvent.latitude), lng: Number(selectedEvent.longitude) }}
            onCloseClick={() => setSelectedEvent(null)} 
          >
            <div>
              <h3>{selectedEvent.title}</h3>
              <p>{selectedEvent.location}</p>
            </div>
          </InfoWindow>
        )}

        
      </GoogleMap>
      
    </div>
  );
}

export { Map };