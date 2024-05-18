import { useEffect, useContext } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { message } from "antd";
import AuthContext from "../../../../context/authProvider";

const ConfirmEmail = () => {
  const navigate = useNavigate();
  const { confirmationToken } = useParams();
  const { confirmEmail } = useContext(AuthContext);

  useEffect(() => {
    const confirmUserEmail = async () => {
      try {
        await confirmEmail(confirmationToken);

        message.success("Registration successful!");
      } catch (error) {
        if (error.response) {
          message.error(error.response.data);
        } else {
          message.error("Error confirming email!");
        }
      } finally {
        navigate("/");
        window.location.reload();
      }
    };

    confirmUserEmail();
  }, []);
};

export default ConfirmEmail;
