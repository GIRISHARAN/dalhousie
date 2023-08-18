import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";
import Form from "react-bootstrap/Form";
import InputGroup from "react-bootstrap/InputGroup";
import axios from "axios";
import { useState, useEffect } from "react";
import Cookies from "js-cookie";

function AddItemModel(props) {
  const [zoneName, setZoneName] = useState(props.zoneName);
  const [zones, setZones] = useState(props.zones || []);
  const [itemName, setItemName] = useState("");
  const [count, setCount] = useState("");
  // console.log(zones)
  const handleAddItems = async () => {
    let itemObj = {
      zoneName,
      itemName,
      count,
    };
    props.itemAdder(itemObj);
    setZoneName(props.zoneName);
    setItemName("");
    setCount("");
    props.onHide();
  };
  useEffect(() => {
    setZones(props.zones);
  }, [props.zones]);
  return (
    <Modal
      {...props}
      size="md"
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          Add Item Name
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form.Group controlId="formZoneName" className="mb-3">
          <Form.Label>Zone Name</Form.Label>
          <Form.Control
            as="select"
            value={zoneName}
            onChange={(e) => setZoneName(e.target.value)}
          >
            <option value="">Select a Zone</option>
            {zones.map((zone, index) => (
              <option key={index} value={zone}>
                {zone}
              </option>
            ))}
          </Form.Control>
        </Form.Group>

        <Form.Group controlId="formItemName" className="mb-3">
          <Form.Label>Item Name</Form.Label>
          <Form.Control
            type="text"
            placeholder="Item Name"
            value={itemName}
            onChange={(e) => setItemName(e.target.value)}
          />
        </Form.Group>

        <Form.Group controlId="formCount" className="mb-3">
          <Form.Label>Count</Form.Label>
          <Form.Control
            type="text"
            placeholder="Quantity"
            value={count}
            onChange={(e) => setCount(e.target.value)}
          />
        </Form.Group>

        <Button variant="outline-secondary" onClick={handleAddItems}>
          Add
        </Button>
      </Modal.Body>
      <Modal.Footer>
        <Button onClick={props.onHide}>Close</Button>
      </Modal.Footer>
    </Modal>
  );
}

export default AddItemModel;
