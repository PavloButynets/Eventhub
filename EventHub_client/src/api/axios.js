import axios from "axios";

const accessToken = localStorage.getItem("token");

const axiosInstance = axios.create({
  baseURL: "http://localhost:9090",
  // headers: {
  //   "Content-Type": "application/json",
  //   "Access-Control-Allow-Origin": "*",
  //   "Access-Control-Allow-Headers": "content-type",
  //   "Access-Control-Allow-Credentials": "true",
  //   ...(accessToken && { Authorization: `Bearer ${accessToken}` }),
  // },
});

export default axiosInstance;
