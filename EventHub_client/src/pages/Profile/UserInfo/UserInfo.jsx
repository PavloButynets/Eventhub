import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { getUserById } from "../../../api/getUserById";
import getIdFromToken from "../../../jwt/getIdFromToken";
import UserImages from "../../../components/UserImages/UserImages";
import ChangePasswordButton from "../../../components/Buttons/EditPasswordButton/ChangePasswordButton";
import EditProfileButton from "../../../components/Buttons/EditProfileButton/EditProfileButton";
import styles from "./UserInfo.module.css";

const UserInfo = () => {
  const [user, setUser] = useState(null);
  const { userId } = useParams();
  const [loading, setLoading] = useState(true); // Add loading state
  const [tokenId, setTokenId] = useState(null);

  useEffect(() => {
    async function fetchUser() {
      try {
        const response = await getUserById(userId);
        setUser(response);
      } catch (error) {
        console.error("Error fetching user:", error);
      } finally {
        setLoading(false);
      }
    }
    fetchUser();

    try {
      setTokenId(getIdFromToken());
    } catch (e) {
      setTokenId(null);
    }
  }, [userId]);

  if (loading) {
    return (
      <div className={styles.Loading}>
        <p>Loading...</p>
      </div>
    );
  }
  return (
    <div className={styles.UserInfo}>
      <div className={styles.MainInfo}>
        <UserImages images={user.photo_responses} />
        <div className={styles.AboutUser}>
          <div className={styles.Header}>
            <p className={styles.MainCaption}>
              {user.first_name + " " + user.last_name}
            </p>
            <p className={styles.SecondaryCaption}>{"@" + user.username}</p>
          </div>
          <div className={styles.Information}>
            <p className={styles.InformationText}>Basic information</p>
            <div className={styles.InfoBox}>
              <p className={styles.Caption}>Birthday</p>
              <p className={styles.Info}>{user.birth_date}</p>
            </div>
            <div className={styles.InfoBox}>
              <p className={styles.Caption}>Gender</p>
              <p className={styles.Info}>{user.gender}</p>
            </div>
          </div>
          <div className={styles.Information}>
            <p className={styles.InformationText}>Contact information</p>
            <div className={styles.InfoBox}>
              <p className={styles.Caption}>E-mail</p>
              <p className={styles.Info}>{user.email}</p>
            </div>
            <div className={styles.InfoBox}>
              <p className={styles.Caption}>Address</p>
              <p className={styles.Info}>{user.city}</p>
            </div>
          </div>
        </div>
      </div>
      <div className={styles.DescriptionBox}>
        <p className={styles.DescriptionCaption}>About</p>
        <div className={styles.Description}>{user.description}</div>
      </div>
      {tokenId === user.id && (
        <div className={styles.Buttons}>
          <ChangePasswordButton />
          <EditProfileButton />
        </div>
      )}
    </div>
  );
};

export default UserInfo;
