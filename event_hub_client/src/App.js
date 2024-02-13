import SignIn from './pages/SignIn/SignIn';
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Link,
} from "react-router-dom";

import './App.css';

function App() {
  return (
      <Router>
        <div>
          <Routes>
            <Route path="/" element={<SignIn/>}/>
          </Routes>
        </div>
      </Router>
      
    );
}

export default App;
