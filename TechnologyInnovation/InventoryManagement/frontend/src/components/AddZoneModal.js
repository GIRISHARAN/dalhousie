import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";
import Form from "react-bootstrap/Form";
import InputGroup from "react-bootstrap/InputGroup";
import { useState } from "react";
import axios from "axios";

function AddZoneModal(props) {
  const [zoneName, setZoneName] = useState("");

  const handleAddZone = async () => {
    props.zoneAdder(zoneName);

    setZoneName("");
    props.onHide();
  };

  return (
    <Modal
      {...props}
      size="md"
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          Add Zone Name
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        {/* <h5>Enter Zone Name:</h5> */}
        <InputGroup className="mb-3">
          <Form.Control onChange={(e) => setZoneName(e.target.value)} />
          <Button
            variant="outline-secondary"
            id="button-addon2"
            onClick={handleAddZone}
          >
            Add
          </Button>
        </InputGroup>
      </Modal.Body>
      <Modal.Footer>
        <Button onClick={props.onHide}>Close</Button>
      </Modal.Footer>
    </Modal>
  );
}

export default AddZoneModal;
