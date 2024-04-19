import { jwtDecode } from "jwt-decode";

const getIdFromToken = () => {
  const authToken = localStorage.getItem("token");
  return jwtDecode(authToken).id;
};

export default getIdFromToken;
