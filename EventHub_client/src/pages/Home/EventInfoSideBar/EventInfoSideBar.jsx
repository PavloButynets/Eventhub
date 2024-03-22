import { useState } from 'react';
import styles from './EventInfoSideBar.module.css'
import { SlArrowLeft } from "react-icons/sl";
import { SlArrowRight } from "react-icons/sl";
import { MdOutlineDateRange } from "react-icons/md";
import {getParticipantsWithPhotos} from '../../../api/getParticipantsWithPhotos';

const EventInfoSideBar = ({event}) => {

    const [photoIndex, setPhotoIndex] = useState(0);

    const month = new Map();
    month.set('01', 'Jan');
    month.set('02', 'Feb');
    month.set('03', 'Mar');
    month.set('04', 'Apr');
    month.set('05', 'May');
    month.set('06', 'Jun');
    month.set('07', 'Jul');
    month.set('08', 'Aug');
    month.set('09', 'Sep');
    month.set('10', 'Nov');
    month.set('11', 'Oct');
    month.set('12', 'Dec');

    const handleRightPhotoClick = () => {
        if (photoIndex < event.photo_responses.length-1) {
            setPhotoIndex(prev => prev + 1);
        }
    }

    const handleLeftPhotoClick = () => {
        if (photoIndex > 0) {
            setPhotoIndex(prev => prev - 1);
        }
    }

    return ( 
        <div className={styles['side-bar-container']}>
            <h2 className={styles['event-title']}>side-bar-container</h2>
            {
                (event.photo_responses.length !== 0) ? 
                (<div className={styles['photo-container']}>
                    <img className={styles['event-photo']} src={event.photo_responses[photoIndex].photo_url} alt="Event img" />
                    <div className={styles['arrow-container']}>
                        <button onClick={handleLeftPhotoClick}> <SlArrowLeft /> </button>
                        <button onClick={handleRightPhotoClick}> <SlArrowRight /> </button>
                    </div>
                </div>) :
                (<img className={styles['event-photo']} src='https://eventhub12.blob.core.windows.net/images/default.jpg?sp=r&st=2024-03-18T06:52:24Z&se=2024-03-24T14:52:24Z&spr=https&sv=2022-11-02&sr=b&sig=nWb0Dzb9%2FWPfAZ6X5MRrwoi%2FxHU8OLe0I6nPtwpBkbQ%3D' alt='Event img' />)
            }

            <div className={styles['category-container']}>
                {event.category_responses.map((category) => (
                    <div className={styles['category']}>{category.name}</div>
                ))}
            </div>

            <div className={styles['date-container']}>
                <div className={styles['date-range-container']}>
                    <div className={styles['start-at']}>
                        <div className={styles['day']}>
                            {month.get(event.start_at.slice(5,7)) + ' ' + event.start_at.slice(8,10)}
                        </div>
                        <div className={styles['time']}>
                            {event.start_at.slice(11,16)}
                        </div>
                    </div>

                    <div className={styles['expire-at']}>
                        <div className={styles['day']}>
                            {month.get(event.expire_at.slice(5,7)) + ' ' + event.expire_at.slice(8,10)}
                        </div>
                        <div className={styles['time']}>
                            {event.expire_at.slice(11,16)}
                        </div>
                    </div>
                </div>
                <MdOutlineDateRange />
            </div>
            <h3 className={styles['participants-text']}>Participants</h3>
            <div className={styles['participant-container']}>
                
            </div>
        </div>
     );
}
 
export default EventInfoSideBar;