import SignUp from "./pages/SignUp/SignUp";
import LogIn from "./pages/LogIn/LogIn";
import { Home } from "./pages/Home/Home";
import styles from "./App.css";
import "./App.css";

import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";

import EventInfoSideBar from "./pages/Home/EventInfoSideBar/EventInfoSideBar";
import AnimatedRoutes from "./components/Routes/AnimatedRoutes";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />}>
          <Route
            path="event/:ownerId/:eventId"
            element={<EventInfoSideBar />}
          />
        </Route>
        <Route path="/register" element={<SignUp />} />
        <Route path="/login" element={<LogIn />} />
      </Routes>
    </Router>
  );
}

export default App;
