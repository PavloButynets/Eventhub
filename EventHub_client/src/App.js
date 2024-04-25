import SignUp from "./pages/Home/SignUp/SignUp.jsx";
import LogIn from "./pages/Home/LogIn/LogIn.jsx";
import { Home } from "./pages/Home/Home";
import ChangePassword from "./pages/Home/ChangePassword/ChangePassword";
import "./App.css";

import EditEvent from "./pages/Home/EditEvent/EditEvent.jsx";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import UserProfile from "./pages/Home/UserProfile/UserProfile";
import EditUserProfile from "./pages/Home/EditUserProfile/EditUserProfile";

function App() {
  return (
    <Router>
      <div>
        <Routes>
          <Route path="/" element={<Home />}>
            <Route path="event/:ownerId/:eventId" />
            <Route path="/edit" element={<EditEvent />} />
            <Route
              path="/profile/change-password"
              element={<ChangePassword />}
            />
            <Route path="/profile/:username" element={<UserProfile />} />
            <Route path="/profile/edit" element={<EditUserProfile />} />
            <Route path="/register" element={<SignUp />} />
            <Route path="/login" element={<LogIn />} />
          </Route>
        </Routes>
      </div>
    </Router>
  );
}

export default App;
