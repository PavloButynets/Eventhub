import styles from './FilteredEvents.module.css';
import { useState, useEffect, useRef} from 'react';
import { useSearchParams} from 'react-router-dom';
import { getFilteredEvents } from '../../../api/getFilteredEvents';
import ListEvents from '../../../components/ListEvents/ListEvents'; 
import EmptyFilteredEvents from './EmptyFilteredEvents.jsx';
import CloseWindowButton from '../../../components/Buttons/CloseWindowButton/CloseWindowButton';

const FilteredEvents = ({handleClose}) => {
    const [eventsData, setEventsData] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const events = await getFilteredEvents();
                setEventsData(events);
            } catch (error) {
                console.log('Error fetching filtered events', error);
            }
        };
        fetchData(); 
    }, []);

    return (
        <div className={styles.FilterResultContainer}>
            <div className={styles.Heading}>
                <h2>Events</h2>
                <CloseWindowButton onClick={()=>{handleClose()}}/>
            </div>
            <hr />
            {(eventsData.length > 0)?
                <ListEvents eventsData={eventsData}/>
                :<EmptyFilteredEvents/>}
        </div>
    );
  };
  
  export default FilteredEvents;