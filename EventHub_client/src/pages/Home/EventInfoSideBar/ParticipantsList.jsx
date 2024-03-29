import styles from './ParticipantsList.module.css'

import { IoArrowBackOutline } from "react-icons/io5";
import { IoClose } from "react-icons/io5";

const ParticipantsList = ({event}) => {
    return ( 
        <div className={styles['participants-list-container']}>
            <div className={styles['header']}>
                <button><IoArrowBackOutline /></button>
                <button><IoClose /></button>
            </div>

            <div className={styles['participants-container']}>

            </div>
        </div>
     );
}
 
export default ParticipantsList;