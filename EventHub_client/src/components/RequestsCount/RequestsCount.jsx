import styles from "./RequestsCount.module.css";
const RequestsCount = ({ requestsLength }) => {
  return (
    <div className={styles["requests-count"]}>
      {requestsLength < 10 ? requestsLength.toString() : "9+"}
    </div>
  );
};

export default RequestsCount;
