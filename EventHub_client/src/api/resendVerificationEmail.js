import axios
 from "./axios";
export const resedVerificationEmail = async (email) => {
  const authAxios = axios.create({
    headers: {
      "Access-Control-Allow-Origin": "*",
      "Access-Control-Allow-Headers": "content-type",
      "Access-Control-Allow-Credentials": "true",
    },
  });
  const response = await authAxios.get(`/authentication/resend?email=${email}`);
  return response.data;
};
