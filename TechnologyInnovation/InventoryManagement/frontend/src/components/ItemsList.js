import React, { useState, useEffect } from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import InputGroup from "react-bootstrap/InputGroup";
import "./../styles/ItemsList.css";
import Table from "react-bootstrap/Table";
import Swal from "sweetalert2";
import { BsFillArrowDownCircleFill } from "react-icons/bs";
import Cookies from "js-cookie";
import axios from "axios";
import AddItemModel from "./AddItemModel";

function ItemsList(props) {
  const [searchItem, setSearchItem] = useState("");
  const [items, setItems] = useState(props.items);
  const [zones, setZones] = useState(props.zones);
  const [modelShow, setModelShow] = React.useState(false);
  const [storeName, setStoreName] = useState(Cookies.get("name"));
  const [email, setEmail] = useState(Cookies.get("email"));

  function handleSearch() {
    if (!searchItem.trim()) {
      setItems(props.items);
    } else {
      const searchTerm = searchItem.toLowerCase();
      const newItemsList = props.items.filter((el) =>
        el.itemName.toLowerCase().includes(searchTerm)
      );

      setItems(newItemsList);
    }
  }

  useEffect(() => {
    console.log(props.items);
    setItems(props.items);
    setZones(props.zones);
  }, [props.items, props.zones]);

  const fetchUpdatedItems = () => {
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
      .then((response) => {
        // console.log(response.data);
        setItems(response.data.data.items);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  function deleteItem(zone, item, count) {
    Swal.fire({
      title: `Do you want to delete the item: ${item}?`,
      showDenyButton: true,
      showCancelButton: false,
      confirmButtonText: "Yes",
      denyButtonText: `No`,
    }).then((result) => {
      if (result.isConfirmed) {
        Swal.fire(`Deleted ${item} from ${zone}`, "", "success");
        const newItemsList = items.filter(
          (el) => !(el.name === item && el.zone === zone)
        );
        console.log(`Deleting ${item} in zone ${zone}`);
        setItems(newItemsList);
        const name = Cookies.get("name");
        const email = Cookies.get("email");
        const payload = {
          name: name,
          email: email,
          zoneName: zone,
          itemName: item,
          count: count,
        };
        axios
          .post(
            "https://custom-inventory-po3oww4fuq-wl.a.run.app/item/delete",
            payload
          )
          .then((resp) => {
            if (resp.data.message === "OK") {
              console.log(resp.data);
              fetchUpdatedItems();
            }
          })
          .catch((error) => {
            console.log(error);
            Swal("Account doesn't exist");
          });
      }
    });
  }
  function handleDecreaseQuantity(zone, item, count) {
    console.log("reached here -");
    const updatedItems = items.map((el) => {
      if (el.itemName === item && el.zoneName === zone) {
        const newQuantity = parseInt(el.count, 10) - 1;
        const name = Cookies.get("name");
        const email = Cookies.get("email");
        const payload = {
          name: name,
          email: email,
          zoneName: zone,
          itemName: item,
          count: newQuantity,
        };

        axios
          .post(
            "https://custom-inventory-po3oww4fuq-wl.a.run.app/item/update",
            payload
          )
          .then((response) => {
            const updatedItemsList = items.map((item) =>
              item.itemName === el.itemName && item.zoneName === el.zoneName
                ? { ...item, count: newQuantity }
                : item
            );
            setItems(updatedItemsList);
          })
          .catch((error) => {
            console.log(error);
          });
        return { ...el, quantity: newQuantity };
      }
      return el;
    });
    setItems(updatedItems);
  }
  async function addItems(value) {
    items.push(value);
    setItems(items);
    const payload = {
      name: Cookies.get("name"),
      email: Cookies.get("email"),
      zoneName: value.zoneName,
      itemName: value.itemName,
      count: value.count,
    };
    axios
      .post(
        "https://custom-inventory-po3oww4fuq-wl.a.run.app/item/add",
        payload
      )
      .then((res) => {
        if (res.data.message == "OK") {
          Swal.fire(
            `Item: ${value.itemName} added Successfully!`,
            "",
            "success"
          );
        }
      })
      .catch((error) => {
        Swal.fire(`Item not added! Something went wrong`, "", "error");
      });
  }
  function handleIncreaseQuantity(zone, item, count) {
    const updatedItems = items.map((el) => {
      if (el.itemName === item && el.zoneName === zone) {
        const newQuantity = parseInt(el.count, 10) + 1;
        const name = Cookies.get("name");
        const email = Cookies.get("email");
        const payload = {
          name: name,
          email: email,
          zoneName: zone,
          itemName: item,
          count: newQuantity,
        };

        console.log(payload);
        axios
          .post(
            "https://custom-inventory-po3oww4fuq-wl.a.run.app/item/update",
            payload
          )
          .then((response) => {
            console.log("Idhaaaarrr");
            const updatedItemsList = items.map((item) =>
              item.itemName === el.itemName && item.zoneName === el.zoneName
                ? { ...item, count: newQuantity }
                : item
            );
            setItems(updatedItemsList);
          })
          .catch((error) => {
            console.log(error);
          });
        return { ...el, quantity: newQuantity };
      }
      return el;
    });
    setItems(updatedItems);
  }
  return (
    <div className="itemsList">
      <div className="header">
        <h1 className="storeHeading">
          Store: {storeName} - {props.selectedZone}
        </h1>
        <Button
          variant="primary"
          class="btn btn-primary float-right"
          onClick={() => setModelShow(true)}
        >
          Add Items
        </Button>
      </div>
      <Button id="hide-on-mobile" onClick={() => props.handleShow()}>
        Zones
      </Button>
      <InputGroup className="mb-3">
        <Form.Control
          placeholder="Search Item"
          onChange={(e) => setSearchItem(e.target.value)}
        />
        <Button
          variant="outline-secondary"
          id="button-addon2"
          onClick={() => {
            handleSearch();
          }}
        >
          Search
        </Button>
      </InputGroup>
      <AddItemModel
        show={modelShow}
        itemAdder={addItems}
        zones={zones}
        onHide={() => setModelShow(false)}
      />

      {items.length === 0 ? (
        <h2 className="no-availability"> No items available for this zone.</h2>
      ) : (
        <Table striped bordered hover>
          <thead>
            <tr>
              <th>Zone Name</th>
              <th>Item Name</th>
              <th>Quantity</th>
              <th>Remove</th>
            </tr>
          </thead>
          <tbody>
            {items.map((el) => {
              if (props.selectedZone === "All Zones") {
                return (
                  <tr>
                    <td>{el.zoneName}</td>
                    <td>{el.itemName}</td>
                    <td>
                      <div
                        style={{
                          display: "flex",
                          justifyContent: "space-evenly",
                        }}
                      >
                        {" "}
                        <Button
                          style={{
                            borderRadius: "50%",
                            display: "flex",
                            alignItems: "center",
                            justifyContent: "center",
                            width: "2em",
                            height: "2em",
                            textAlign: "center",
                          }}
                          variant="secondary"
                          onClick={() =>
                            handleDecreaseQuantity(
                              el.zoneName,
                              el.itemName,
                              el.count
                            )
                          }
                        >
                          -
                        </Button>
                        <div>{el.count}</div>
                        <div>
                          {el.count < 3 ? (
                            <BsFillArrowDownCircleFill title="Running low" />
                          ) : (
                            <></>
                          )}
                        </div>
                        <Button
                          variant="secondary"
                          style={{
                            borderRadius: "50%",
                            display: "flex",
                            alignItems: "center",
                            justifyContent: "center",
                            width: "2em",
                            height: "2em",
                            textAlign: "center",
                          }}
                          onClick={() =>
                            handleIncreaseQuantity(
                              el.zoneName,
                              el.itemName,
                              el.count
                            )
                          }
                        >
                          +
                        </Button>
                      </div>
                    </td>
                    <td>
                      <Button
                        variant="danger"
                        onClick={() =>
                          deleteItem(el.zoneName, el.itemName, el.count)
                        }
                      >
                        Delete
                      </Button>
                    </td>
                  </tr>
                );
              }
              if (el.zoneName === props.selectedZone) {
                return (
                  <tr>
                    <td>{el.zoneName}</td>
                    <td>{el.itemName}</td>
                    <td>
                      <div
                        style={{
                          display: "flex",
                          justifyContent: "space-evenly",
                        }}
                      >
                        <Button
                          style={{
                            borderRadius: "50%",
                            display: "flex",
                            alignItems: "center",
                            justifyContent: "center",
                            width: "2em",
                            height: "2em",
                            textAlign: "center",
                          }}
                          variant="secondary"
                          onClick={() =>
                            handleDecreaseQuantity(
                              el.zoneName,
                              el.itemName,
                              el.count
                            )
                          }
                        >
                          -
                        </Button>
                        <div>{el.count}</div>
                        <div>
                          {el.count < 3 ? (
                            <BsFillArrowDownCircleFill title="Running low" />
                          ) : (
                            <></>
                          )}
                        </div>
                        <Button
                          style={{
                            borderRadius: "50%",
                            display: "flex",
                            alignItems: "center",
                            justifyContent: "center",
                            width: "2em",
                            height: "2em",
                            textAlign: "center",
                          }}
                          variant="secondary"
                          onClick={() =>
                            handleIncreaseQuantity(
                              el.zoneName,
                              el.itemName,
                              el.count
                            )
                          }
                        >
                          +
                        </Button>
                      </div>
                    </td>
                    <td>
                      <Button
                        variant="danger"
                        onClick={() =>
                          deleteItem(el.zoneName, el.itemName, el.count)
                        }
                      >
                        Delete
                      </Button>
                    </td>
                  </tr>
                );
              }
            })}
          </tbody>
        </Table>
      )}
    </div>
  );
}

export default ItemsList;
