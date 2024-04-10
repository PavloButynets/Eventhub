import { Link } from "react-router-dom";
import { FiUser } from "react-icons/fi";
import { HiOutlineLocationMarker } from "react-icons/hi";
import { LuUsers } from "react-icons/lu";
import { FiLogOut } from "react-icons/fi";
import getIdFromToken from "../../../jwt/getIdFromToken";
import styles from "./ProfileSideBarMenu.module.css";

const ProfileSideBarMenu = () => {
  const userId = getIdFromToken();

  const options = [
    { name: "Account", icon: <FiUser />, source: `/profile/${userId}/account` },
    { name: "Events", icon: <HiOutlineLocationMarker />, source: "/" },
  ];

  return (
    <ul className={styles.SideBarMenu}>
      {options.map((option) => {
        return (
          <li className={styles.Option}>
            <Link className={styles.Link} key={option} to={option.source}>
              {option.icon} {option.name}
            </Link>
          </li>
        );
      })}
      <li className={styles.Option}>
        <Link className={styles.Link} key="logout" to={"/login"}>
          <FiLogOut /> Logout
        </Link>
      </li>
    </ul>
  );
};

export default ProfileSideBarMenu;
