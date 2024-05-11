import SignUp from "./pages/Home/SignUp/SignUp.jsx"
import LogIn from "./pages/Home/LogIn/LogIn.jsx";
import { Home } from "./pages/Home/Home";
import ChangePassword from "./pages/Home/ChangePassword/ChangePassword";
import EditEvent from "./pages/Home/EditEvent/EditEvent.jsx";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import UserProfile from "./pages/Home/UserProfile/UserProfile";
import EditUserProfile from "./pages/Home/EditUserProfile/EditUserProfile";
import EventInfoSideBar from "./pages/Home/EventInfoSideBar/EventInfoSideBar.jsx";
import ConfirmEmail from "./pages/Home/SignUp/EmailVerification/ConfirmEmail.jsx";

function App() {
  return (
    <Router>
      <div>
        <Routes>
          <Route path="/" element={<Home />}>
            <Route path="event/:eventId" element={<EventInfoSideBar />} />
            <Route path="/edit" element={<EditEvent />} />
            <Route
              path="/profile/change-password"
              element={<ChangePassword />}
            />
            <Route path="/profile/:username" element={<UserProfile />} />
            <Route path="/profile/edit" element={<EditUserProfile />} />
            <Route path="/register" element={<SignUp />} />
            <Route path="/login" element={<LogIn />} />
            <Route
              path="/confirm/:confirmationToken"
              element={<ConfirmEmail />}
            />
          </Route>
        </Routes>
      </div>
    </Router>
  );
}

export default App;
