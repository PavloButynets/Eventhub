import {
    LoadingOutlined,
  } from "@ant-design/icons";
import styles from "./ProcessingEffect.module.css";

const ProcessingEffect = () => {
  return (
    <div className={styles.Effect}>
      <LoadingOutlined
        style={{ fontSize: "72px", color: "white", fontWeight: "1000" }}
      />
    </div>
  );
};

export default ProcessingEffect;
