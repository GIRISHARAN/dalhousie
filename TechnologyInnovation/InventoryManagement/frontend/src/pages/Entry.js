import React from "react";
import { Card, Form, Button, Col } from "react-bootstrap";
import p2 from "../resource/img2.jpg";
import "bootstrap/dist/css/bootstrap.css";
import "../styles/Entrystyle.css";
import { Link, useNavigate } from "react-router-dom";
import Cookies from "js-cookie";
import axios from "axios";
import Swal from "sweetalert";

export default function Entry() {
  const navigate = useNavigate();
  const handleLogin = (event) => {
    event.preventDefault();
    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;
    const payload = {
      name: name,
      email: email,
    };
    axios
      .post(
        "https://custom-inventory-po3oww4fuq-wl.a.run.app/store/open",
        payload
      )
      .then((resp) => {
        if (resp.data.message === "OK") {
          Cookies.set("email", email, { expires: 7 });
          Cookies.set("name", name, { expires: 7 });
          Swal("Login Successful");
          navigate("/store");
        }
      })
      .catch((error) => {
        Swal("Account doesn't exist");
      });
  };
  const handleRegister = () => {
    navigate("/register");
  };
  return (
    <div className="container-fluid">
      <div className="row">
        <div className="col-lg-6">
          <br />
          <br />
          <img src={p2} className="w-100" alt="Image" />
        </div>
        <div className="col-lg-6" style={{ backgroundColor: "grey" }}>
          <h2 className="responsive-h2">Inventory Management</h2>
          <h6 className="responsive-h6">Manage your warehouse with ease</h6>
          <Card className="custom-card">
            <Card.Body>
              <Form>
                <Form.Group className="mb-3" controlId="formBasicEmail">
                  <Form.Label style={{ color: "white" }}>
                    Email address
                  </Form.Label>
                  <Form.Control
                    type="email"
                    placeholder="Enter email"
                    id="email"
                  />
                </Form.Group>

                <Form.Group className="mb-3" controlId="formBasicPassword">
                  <Form.Label style={{ color: "white" }}>Name</Form.Label>
                  <Form.Control type="text" placeholder="Name" id="name" />
                </Form.Group>

                <div className="d-flex justify-content-center">
                  <Button variant="primary" type="submit" onClick={handleLogin}>
                    Open
                  </Button>
                </div>
              </Form>
            </Card.Body>
          </Card>
          <br />
          <br />
          <br />
          <h5
            style={{ color: "white" }}
            className="d-flex justify-content-center"
          >
            Do not have an account ?
          </h5>
          <div className="d-flex justify-content-center">
            <Button variant="primary" type="submit" onClick={handleRegister}>
              Register
            </Button>
          </div>
          <br />
          <br />
          <br />
          <br />
        </div>
      </div>
    </div>
  );
}
