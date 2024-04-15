import { Link } from "react-router-dom";
import { FiUser } from "react-icons/fi";
import { HiOutlineLocationMarker } from "react-icons/hi";
import { LuUsers } from "react-icons/lu";
import { FiLogOut } from "react-icons/fi";
import useAuth from "../../../hooks/useAuth";
import getIdFromToken from "../../../jwt/getIdFromToken";
import styles from "./ProfileSideBarMenu.module.css";

const ProfileSideBarMenu = () => {
  const { setAuth } = useAuth();
  let link = '';

  try{
    const userId = getIdFromToken();
    link = `/profile/${userId}/account`;
  }
  catch(e){
    link = "/login";
  }
  
  const handleLogout = () => {
    localStorage.removeItem("token");
    setAuth({});
  };

  const options = [
    {
      name: "Account",
      icon: <FiUser />,
      source: link,
    },
    { name: "Events", icon: <HiOutlineLocationMarker />, source: "/" },
  ];

  return (
    <ul className={styles.SideBarMenu}>
      {options.map((option) => {
        return (
          <li className={styles.Option}>
            <Link className={styles.Link} key={option.name} to={option.source}>
              {option.icon} {option.name}
            </Link>
          </li>
        );
      })}
      <li className={styles.Option} onClick={handleLogout}>
        <Link className={styles.Link} key="logout" to={"/login"}>
          <FiLogOut /> Logout
        </Link>
      </li>
    </ul>
  );
};

export default ProfileSideBarMenu;
