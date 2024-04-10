import ProfileInfo from "../../../components/ProfileInfo/ProfileInfo";
import Logo from "../../../images/EventHubLogo.png";
import styles from "./Header.module.css";

const Header = () => {
  return (
    <div className={styles.Header}>
      <div className={styles.Logo}>
        <img src={Logo} alt="Event Hub Logo" />
      </div>
      <ProfileInfo
        nickname={"Nickname"}
        email={"Email@gmail.com"}
        onProfileClick={null}
      />
    </div>
  );
};

export default Header;
