import axios
 from "./axios";
const logIn = async (email, password) =>{
    const REGISTER_URL = "/authentication/login";
    const res = await axios.post(
        REGISTER_URL,
        {
          email,
          password,
        },
        {
          headers: { "Content-Type": "application/json" },
        }
      );
      console.log(res?.data);
      const accessToken = res?.data?.token;
      localStorage.setItem("token", accessToken);
      console.log(res.data);

      axios.defaults.headers.common[
        "Authorization"
      ] = `Bearer ${res.data["token"]}`;
}

export default logIn