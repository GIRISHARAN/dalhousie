import Store from "./pages/Store";
import "./App.css";
import Navbar from "./components/Navbar";
import Entry from "./pages/Entry";
import Register from "./pages/Register";
import {BrowserRouter as Router,Routes,Route} from 'react-router-dom';
function App() {
  return (
    <div className="App">
      <Router>
      <Routes>
      <Route path="/" element={<Entry />} />
      <Route path="/register" element={<Register />} />
      <Route path="/store" element={<Store />} />
      </Routes>
    </Router>
    </div>
  );
}

export default App;
