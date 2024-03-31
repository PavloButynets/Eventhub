import { useEffect, useState } from 'react';
import styles from './ParticipantsList.module.css'

import {getUserParticipants} from '../../../api/getUserParticipants';

import { IoArrowBackOutline } from "react-icons/io5";
import { IoClose } from "react-icons/io5";
import { useParams } from 'react-router-dom';
import { getEventById } from '../../../api/getEventById';




const ParticipantsList = ({handleGoBackToSideBar, handleCloseWindow}) => {

    const [participants, setParticipants] = useState([]);
    const [event, setEvent] = useState(null);

    const {ownerId, eventId} = useParams();

    useEffect(() => {
        getEventById(ownerId, eventId)
        .then(data => setEvent(data));
    }, [ownerId, eventId]);

    useEffect(() => {
        event && getUserParticipants(event.id)
        .then(data => setParticipants(data));
    }, [event]);

    return ( 
        event && <div className={styles['participants-list-container']}>
            <div className={styles['header']}>
                <button className={styles['btn']} onClick={handleGoBackToSideBar}><IoArrowBackOutline className={styles['btn-icon']} /></button>
                <button className={styles['btn']} onClick={handleCloseWindow}><IoClose className={styles['btn-icon']} /></button>
            </div>

            <div className={styles['participants-container']}>
                {participants.map(participant => (
                    <div key={participant.id} className={styles['participant-container']}>
                        <img className={styles['participant-photo']} src={participant.participant_photo.photo_url} alt="User participant img" />
                        <div className={styles['participant-info-container']}>
                            <div className={styles['full-name']}>
                                <p>{participant.first_name}</p>
                                <p>{participant.last_name}</p>
                            </div>
                            <p className={styles['email']}>{participant.email}</p>
                        </div>
                    </div>
                ))}
            </div>
        </div>
     );
}
 
export default ParticipantsList;