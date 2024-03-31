import {
  BrowserRouter as Router,
  Routes,
  Route,
  Link,
  useLocation,
} from "react-router-dom";
import SignUp from "../../pages/SignUp/SignUp";
import LogIn from "../../pages/LogIn/LogIn";
import { Home } from "../../pages/Home/Home";
import EventInfoSideBar from "../../pages/Home/EventInfoSideBar/EventInfoSideBar";
import { AnimatePresence } from "framer-motion";

const AnimatedRoutes = () => {
  const location = useLocation();
  return (
    <AnimatePresence>
      <Routes location={location} key={location.pathname}>
        <Route path="/" element={<Home />}>
          <Route
            path="event/:ownerId/:eventId"
            element={<EventInfoSideBar />}
          />
        </Route>
        <Route path="/register" element={<SignUp />} />
        <Route path="/login" element={<LogIn />} />
      </Routes>
    </AnimatePresence>
  );
};

export default AnimatedRoutes;
