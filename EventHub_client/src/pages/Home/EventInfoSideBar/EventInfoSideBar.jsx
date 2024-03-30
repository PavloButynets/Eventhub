import { useState, useEffect, useRef } from 'react';
import styles from './EventInfoSideBar.module.css'
import { IoIosMore } from "react-icons/io";
import {getParticipants} from '../../../api/getParticipants';
import {getUserById} from '../../../api/getUserById';
import { RiVipCrownLine } from "react-icons/ri";
import { IoClose } from "react-icons/io5";
import { CiCalendar } from "react-icons/ci";
import ImageSlider from '../../../components/ImageSlider/ImageSlider';


const EventInfoSideBar = ({event, handleCloseWindow, handleShowAllParticipants}) => {

    
    const [isShowMore, setIsShowMore] = useState(false);
    const [isOverflowAboutText, setIsOverflowAboutText] = useState(false);
    const [isShowMoreParticipants, setIsShowMoreParticipants] = useState(false);
    const [participantsToShow, setParticipantsToShow] = useState([]);
    
    const [owner, setOwner] = useState(null);

    const showMoreBtn = useRef(null);
    const aboutText = useRef(null);

    useEffect(() => {
        getParticipants(event.id)
        .then(data => {
            console.log('Data: ',data);
            if (data.length > 1) {
                setIsShowMoreParticipants(true);
                setParticipantsToShow(data.slice(0,4));
            }
            else {
                setParticipantsToShow(data);
            }

            if (aboutText.current.scrollHeight > aboutText.current.clientHeight) {
                setIsOverflowAboutText(true);
            }
        });

        return () => setIsShowMoreParticipants(false);
        
        
    }, [event]);

    useEffect(() => {
        getUserById(event.owner_id)
        .then(data => {
            setOwner(data);
            console.log(`Owner photo response: ${data.photo_responses[0].photo_url}`)
        })
    }, [event])

    useEffect(() => {
        console.log('participants to show: ',participantsToShow);
    }, [participantsToShow]);


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

    

    const handleShowMore = () => {
        setIsShowMore(prev => !prev);
        showMoreBtn.current.innerHTML = isShowMore ? 'Show more' : 'Show less';
    }

    

    return ( 
        <div className={styles['side-bar-container']}>
            <div className={styles['header']}>
                <h2 className={styles['event-title']}>{event.title}</h2>
                <button className={styles['close-btn']} onClick={handleCloseWindow}><IoClose className={styles['close-btn-icon']} size="2.5em" /></button>
            </div>
            

            {/* Photo */}
            <div className={styles['photo-container']}>
                <ImageSlider images={event.photo_responses}/>
            </div>
            

            {/* Category */}
            <div className={styles['category-container']}>
                {event.category_responses.map((category) => (
                    <div key={category.id} className={styles['category']}>{category.name}</div>
                ))}
            </div>

            {/* Date */}
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

                    <hr />

                    <div className={styles['expire-at']}>
                        <div className={styles['day']}>
                            {month.get(event.expire_at.slice(5,7)) + ' ' + event.expire_at.slice(8,10)}
                        </div>
                        <div className={styles['time']}>
                            {event.expire_at.slice(11,16)}
                        </div>
                    </div>
                </div>
                <div className={styles['date-icon']}>
                    <button className={styles['date-btn']}><CiCalendar className={styles['date-btn-icon']} /></button>
                </div>
                
            </div>

            {/* Participants */}
            <h3 className={styles['heading']}>Participants</h3>
            <div className={styles['participant-container']}>
                <div className={styles['owner-photo']}>
                    {owner && <img src={owner.photo_responses[0].photo_url} alt="" />}
                    <div className={styles['crown-container']}>
                        <RiVipCrownLine className={styles['crown-icon']} />
                    </div>
                    
                </div>
                <div className={styles['participants-photos']}>
                    {participantsToShow.map(participant => (
                        <div className={styles['item']} key={participant.id}>
                            <img src={participant.participant_photo.photo_url} alt="Participant Img" />
                        </div>
                    ))}    
                    { isShowMoreParticipants && <div className={styles['show-more-participants']}><button onClick={handleShowAllParticipants} className={styles['show-more-participants-btn']} ><IoIosMore className={styles['show-more-participants-btn-icon']} /></button></div>}
                </div>
                
            </div>

            {/* About section */}
            <h3 className={styles['heading']}>About this event</h3>
            <div className={styles['about-container']}>
                <div className={styles[isShowMore ? null : 'about-text']} ref={aboutText}>
                Lorem, ipsum dolor sit amet consectetur adipisicing elit.sdfssdsfsdfssddsdsdfsfsdfsdf
                Lorem, ipsum dolor sit amet consectetur adipisicing elit.sdfssdsfsdfssddsdsdfsfsdfsdf
                Lorem, ipsum dolor sit amet consectetur adipisicing elit.sdfssdsfsdfssddsdsdfsfsdfsdf
                Lorem, ipsum dolor sit amet consectetur adipisicing elit.sdfssdsfsdfssddsdsdfsfsdfsdf
                Lorem, ipsum dolor sit amet consectetur adipisicing elit.sdfssdsfsdfssddsdsdfsfsdfsdf
                Lorem, ipsum dolor sit amet consectetur adipisicing elit.sdfssdsfsdfssddsdsdfsfsdfsdf
                Lorem, ipsum dolor sit amet consectetur adipisicing elit.sdfssdsfsdfssddsdsdfsfsdfsdf
                Lorem, ipsum dolor sit amet consectetur adipisicing elit.sdfssdsfsdfssddsdsdfsfsdfsdf
                Lorem, ipsum dolor sit amet consectetur adipisicing elit.sdfssdsfsdfssddsdsdfsfsdfsdf
                Lorem, ipsum dolor sit amet consectetur adiing elit.sdfssdsfsdfssddsdsdfsfsdfsdf
                Lorem, ipsum dolor sit amet consectetur adipisicing elit.sdfssdsfsdfssddsdsdfsfsdfsdf
                Lorem, ipsum dolor sit amet consectetur adig elit.sdfssdsfsdfssddsdsdfsfsdfsdf
                Lorem, ipsum dolor sit amet consectetur adipisicing elit.sdfssdsfsdfssddsdsdfsfsdfsdf
                Lorem, ipsum dolor sit amet consectetur adipisicing elit.sdfssdsfsdfssddsdsdfsfsdfsdf
                Lorem, ipsum dolor sit amet consectetur adipisicing elit.sdfssdsfsdfssddsdsdfsfsdfsdf
                
                </div>
                {isOverflowAboutText &&  <button onClick={handleShowMore} className={styles['show-more-btn']} ref={showMoreBtn}>Show more</button>}
            </div>

            {/* Lower section */}
            <div className={styles['lower-container']}>
                <div className={styles['spots']}>
                        {event.max_participants - event.participant_count} Spots left
                </div>

                <button className={styles['action-btn']}>Action</button>
            </div>

            
            
        </div>
     );
}
 
export default EventInfoSideBar;