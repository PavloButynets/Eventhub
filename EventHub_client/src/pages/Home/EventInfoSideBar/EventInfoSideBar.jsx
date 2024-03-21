import styles from './EventInfoSideBar.module.css'

const EventInfoSideBar = ({event}) => {
    return ( 
        <div className={styles['side-bar-container']}>
            <h2 className={styles['event-title']}>side-bar-container</h2>
            
            {event.photo_responses.length !== 0} ? 
            <div className={styles['photo-container']}>

            </div> :
            <img className={styles['event-photo']} src='https://eventhub12.blob.core.windows.net/images/default.jpg?sp=r&st=2024-03-18T06:52:24Z&se=2024-03-24T14:52:24Z&spr=https&sv=2022-11-02&sr=b&sig=nWb0Dzb9%2FWPfAZ6X5MRrwoi%2FxHU8OLe0I6nPtwpBkbQ%3D' alt='Event img' />
        </div>
     );
}
 
export default EventInfoSideBar;