import React, { useEffect, useState } from "react";
import ZoneList from "./../components/ZoneList";
import ItemsList from "./../components/ItemsList";
import Offcanvas from "react-bootstrap/Offcanvas";
import "./../styles/Store.css";
import ZoneListMobile from "../components/ZoneListMobile";
import Navbar from "../components/Navbar";
import Cookies from "js-cookie";
import axios from "axios";
import Swal from "sweetalert";
function Store() {
  const [zone, setZone] = useState("All Zones");
  const [show, setShow] = useState(false);
  const [zones, setZones] = useState([]);
  const [items, setItems] = useState([]);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  useEffect(() => {
    const name = Cookies.get("name");
    const email = Cookies.get("email");
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
          // console.log(resp.data.data.zones);
          setZones(resp.data.data.zones);
          setItems(resp.data.data.items);
        }
      })
      .catch((error) => {
        Swal("Account doesn't exist");
      });
  }, []);

  const zoneSetter = (z) => {
    // console.log(`Zone set to ${z}`);
    setZone(z);
  };
  return (
    <div>
      <Navbar />
      <div className="store">
        <ZoneList zones={zones} zoneSetter={zoneSetter} />
        <div className="mobileView">
          <Offcanvas show={show} onHide={handleClose}>
            <Offcanvas.Header closeButton>
              <Offcanvas.Title>Zones List</Offcanvas.Title>
            </Offcanvas.Header>
            <Offcanvas.Body>
              <ZoneListMobile zones={zones} zoneSetter={zoneSetter} />
            </Offcanvas.Body>
          </Offcanvas>
        </div>
        <ItemsList
          zones={zones}
          items={items}
          handleShow={handleShow}
          selectedZone={zone}
        />
      </div>
    </div>
  );
}

export default Store;
