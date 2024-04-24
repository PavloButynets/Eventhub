import styles from "./CancelButton.module.css";

const CancelButton = ({onclick}) => {
    return(
        <button className={styles.CancelButton} onClick={onclick}>Cancel</button>
    );
}
export default CancelButton