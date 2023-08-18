import Container from "react-bootstrap/Container";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import NavDropdown from "react-bootstrap/NavDropdown";
import { Link, useNavigate } from "react-router-dom";
import Cookies from "js-cookie";
function ContainerOutsideExample() {
  const navigate = useNavigate();
  const handleLogout = () => {
    Cookies.remove("email");
    Cookies.remove("name");
    navigate("/");
  };
  return (
    <Navbar bg="primary" data-bs-theme="dark">
      <Container>
        <Navbar.Brand href="#home">Inventory Management</Navbar.Brand>
      </Container>
      <NavDropdown title={Cookies.get("email")} id="collasible-nav-dropdown">
        <NavDropdown.Item href="#action/3.1" onClick={handleLogout}>
          Logout
        </NavDropdown.Item>
      </NavDropdown>
    </Navbar>
  );
}

export default ContainerOutsideExample;
