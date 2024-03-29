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
                    <div>
                        {participant.email}
                    </div>
                ))}
            </div>
        </div>
     );
}
 
export default ParticipantsList;