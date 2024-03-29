import { useEffect, useState } from 'react';
import styles from './ParticipantsList.module.css'

import {getUserParticipants} from '../../../api/getUserParticipants';

import { IoArrowBackOutline } from "react-icons/io5";
import { IoClose } from "react-icons/io5";




const ParticipantsList = ({event, handleGoBackToSideBar, handleCloseWindow}) => {

    const [participants, setParticipants] = useState([]);

    useEffect(() => {
        getUserParticipants(event.id)
        .then(data => setParticipants(data));
    });

    return ( 
        <div className={styles['participants-list-container']}>
            <div className={styles['header']}>
                <button onClick={handleGoBackToSideBar}><IoArrowBackOutline /></button>
                <button onClick={handleCloseWindow}><IoClose /></button>
            </div>

            <div className={styles['participants-container']}>
                {participants.map(participant => (
                    <div className={styles['participant-container']}>
                        <img src={participant.participant_photo.photo_url} alt="User participant img" />
                        <div className={styles['participant-info-container']}>
                            <div className={styles['full-name']}>
                                <p>{participant.first_name}</p>
                                <p>{participant.last_name}</p>
                            </div>
                            <p>{participant.email}</p>
                        </div>
                    </div>
                ))}
            </div>
        </div>
     );
}
 
export default ParticipantsList;