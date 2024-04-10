import { useOutlet } from "react-router-dom";
import Header from "./Header/Header.jsx";
import SideBarMenu from "./ProfileSideBarMenu/ProfileSideBarMenu.jsx";
import EmptySpace from "./EmptySpace/EmptySpace.jsx";
import styles from "./Profile.module.css";

const Profile = () => {
  const outlet = useOutlet();
  return (
    <>
      <Header />
      <div className={styles.MainContent}>
        <SideBarMenu />
        {outlet || <EmptySpace />}
      </div>
    </>
  );
};

export default Profile;
