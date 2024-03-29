import styles from './ParticipantsList.module.css'

import { IoArrowBackOutline } from "react-icons/io5";
import { IoClose } from "react-icons/io5";

const ParticipantsList = ({event, handleGoBackToSideBar, handleCloseWindow}) => {
    return ( 
        <div className={styles['participants-list-container']}>
            <div className={styles['header']}>
                <button onClick={handleGoBackToSideBar}><IoArrowBackOutline /></button>
                <button onClick={handleCloseWindow}><IoClose /></button>
            </div>

            <div className={styles['participants-container']}>

            </div>
        </div>
     );
}
 
export default ParticipantsList;