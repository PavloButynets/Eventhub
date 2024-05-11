import { useState } from "react";
import Registration from "./Registration/Registration";
import EmailVerification from "./EmailVerification/EmailVerification";

const SignUp = () => {
  const [isRegistered, setIsRegistered] = useState(false);
  const [userEmail, setUserEmail] = useState();

  return isRegistered ? (
    <EmailVerification email={userEmail} />
  ) : (
    <Registration
      setUserEmail={setUserEmail}
      setIsRegistered={setIsRegistered}
    />
  );
};

export default SignUp;
