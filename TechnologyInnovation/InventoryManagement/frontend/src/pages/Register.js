import React from "react";
import { Card, Form, Button, Col } from "react-bootstrap";
import p1 from "../resource/img1.jpg";
import "bootstrap/dist/css/bootstrap.css";
import "../styles/Entrystyle.css";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import swal from "sweetalert";
export default function Register() {
  const navigate = useNavigate();
  const handleLogin = () => {
    navigate("/");
  };
  const handleRegister = (event) => {
    event.preventDefault();
    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;
    const payload = {
      name: name,
      email: email,
    };
    console.log(payload);
    axios
      .post(
        "https://custom-inventory-po3oww4fuq-wl.a.run.app/store/create",
        payload
      )
      .then((resp) => {
        console.log(resp.data);
        if (resp.data.message === "CREATED") {
          swal("Registration Successful, Log In to access your inventory");
          navigate("/");
        }
      })
      .catch((error) => {
        swal("Registration us=nsuccessful");
      });
  };
  return (
    <div className="container-fluid">
      <div className="row">
        <div className="col-lg-6">
          <br />
          <br />
          <img src={p1} className="w-100" alt="Image" />
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
                  <Button
                    variant="primary"
                    type="submit"
                    onClick={handleRegister}
                  >
                    Register
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
            Already have an account ?
          </h5>
          <div className="d-flex justify-content-center">
            <Button variant="primary" type="submit" onClick={handleLogin}>
              Login
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
