import React, { useEffect, useState } from "react";
import Button from "react-bootstrap/Button";
import ListGroup from "react-bootstrap/ListGroup";
import "./../styles/ZoneList.css";
import AddZoneModal from "./AddZoneModal";
import Swal from "sweetalert2";
import Cookies from "js-cookie";
import axios from "axios";

function ZoneList(props) {
  const [modalShow, setModalShow] = React.useState(false);
  const [activeItem, setActiveItem] = useState(5000);
  const handleClick = (el, index) => {
    props.zoneSetter(el);
    setActiveItem(index);
  };

  const deleteZone = async (el) => {
    const name = Cookies.get("name");
    const email = Cookies.get("email");
    const payload = {
      name: name,
      email: email,
      zoneName: props.zones[activeItem],
    };
    console.log(payload);
    Swal.fire({
      title: "Do you want to delete the zone?",
      showDenyButton: true,
      showCancelButton: false,
      confirmButtonText: "Delete",
      denyButtonText: `No`,
    }).then((result) => {
      if (result.isConfirmed) {
        if (activeItem !== null && activeItem !== 5000) {
          props.zones.splice(activeItem, 1); // Remove the zone from the array
          //delete api
          axios
            .post(
              "https://custom-inventory-po3oww4fuq-wl.a.run.app/zone/delete",
              payload
            )
            .then((resp) => {
              if (resp.data.message === "OK") {
                // console.log(resp.data);
                // setZones(resp.data.data.zones);
              }
            })
            .catch((error) => {
              console.log(error);
              Swal("Account doesn't exist");
            });
          setActiveItem(5000); // Reset activeItem to "All Zones"
        }
        Swal.fire("Deleted!", "", "success");
        window.location.reload();
      }
    });
  };

  async function addZone(value) {
    props.zones.push(value);
    const name = Cookies.get("name");
    const email = Cookies.get("email");
    const payload = {
      name: name,
      email: email,
      zoneName: value,
    };
    await axios
      .post(
        "https://custom-inventory-po3oww4fuq-wl.a.run.app/zone/create",
        payload
      )
      .then((res) => {
        console.log("Zone adder is working fine....");
        console.log(res);
      })
      .catch((err) => {
        console.log(err);
        alert("Error");
      });
  }
  return (
    <div className="zoneSection">
      <Button variant="primary" onClick={() => setModalShow(true)}>
        Add Zones
      </Button>
      <ListGroup defaultActiveKey="#link1" className="zonesList">
        <ListGroup.Item
          key=""
          action
          onClick={() => handleClick("All Zones", 5000)}
          className={activeItem === 5000 ? "active" : ""}
        >
          All Zones
        </ListGroup.Item>
        {props.zones.map((el, index) => {
          return (
            <ListGroup.Item
              key={index}
              action
              onClick={() => handleClick(el, index)}
              className={activeItem === index ? "active" : ""}
            >
              {el}
            </ListGroup.Item>
          );
        })}
      </ListGroup>
      {activeItem !== 5000 && (
        <Button variant="danger" onClick={deleteZone}>
          Delete Zone
        </Button>
      )}
      <AddZoneModal
        show={modalShow}
        zoneAdder={addZone}
        onHide={() => setModalShow(false)}
      />
    </div>
  );
}

export default ZoneList;
