# Custom Inventory Management System

Custom inventory management software is a comprehensive web application designed specifically for retail stores to streamline and enhance their inventory management processes. It provides a user-friendly interface and a range of features to efficiently manage inventory, track item quantities, and optimize stock control. The software allows users to create and define zones within their store, assign items to specific locations, and track real-time stock quantities across different areas.

It enables users to capture and manage item details, such as name, description, and supplier information, facilitating accurate item tracking and identification. Additionally, the software may offer reporting and analytics capabilities to generate insights on inventory performance. The software aims to improve organizational efficiency, prevent stockouts, optimize inventory levels, and enhance customer satisfaction through improved inventory availability

- _Date Created_: 27 JUL 2023
- _Last Modification Date_: 27 JUL 2023
- _Frontend/Project Deployed URL_: https://custom-inventory-management.netlify.app/
- _Backend Deployed URL_:https://custom-inventory-po3oww4fuq-wl.a.run.app/
- _Git URL_: https://git.cs.dal.ca/nagaraja/team2-inventory_management.git

## Features

##### Login and Sign Up

User authentication modules are built using bootstrap and React-bootstrap and focused on user friendly design. Both takes the parameters email and name to authenticate, once the user logs in the details will be stored in cookies for further convenience and features which will be removed after logout event occurs, to sign up to the portal it also requires email and name which will be verified and stored in the database, once the registration is done user can proceed with login.

##### Zone Functionality

The store offers a feature called "zones," which allows the organization and storage of inventory. Users can create new zones to categorize items and delete existing zones if needed.

##### Item Management

The system allows users to manage inventory items within the designated zones. Users have the ability to add new items to specific zones along with their respective quantities. They can also change the quantities of existing items within zones and remove items altogether. The user will also be notified when the quantity goes below the threshold quantity value. Additionally, the system will send notifications to the user when an item's quantity falls below a predefined threshold value.

##### Searching Items

The search functionality provides the users with the option to search the items either in the entire store or in specific zones of the store.

## Project Workflow

<ol>
    <li>User registers using email and store name.</li>
    <li>User gets redirected to login.</li>
    <li>User logs in using email and store name.</li>
    <li>Once logged in user will add different zones available in the inventory.</li>
    <li>User will add the products or items available in particular zones.</li>
    <li>User will search for a product, it can be done for specific zone or entire store.</li>
    <li>User will monitor the increase and decrease in the item quatity.</li>
    <li>User will logout to exit the portal.</li>
</ol>

## Authors

- [Badhri Nadh Arja (B00930493)]()
- [Bhargav Kanodiya (B00938588)]()
- [Giri Sharan Reddy Pusuluru (B00913674)]()
- [Kazi Aniketh Anum (B00925813)]()
- [Piyush Akoliya (B00920744)]()
- [Shreyas Balaji Nagaraja (B00928044)]()

## Deployment

### Frontend

<ol>
    <li>Create a private repository in Github.</li>
    <li>Push the code in the repository.</li>
    <li>Create a Netlify Account.</li>
    <li>Navigate to select repository for deployment.</li>
    <li>Authorize Netlify to connect to the GitHub account.</li>
    <li>Add the command to build the project (npm run build).</li>
    <li>Add the base directory(frontend) in which the code to deployed is present.</li>
    <li>Select Deploy site button.</li>
</ol>

### Backend

<ol>
    <li>Write dockerfile and cloud buid .yaml file.</li>
    <li>Create a service in cloud run.</li>
    <li>Connect github repository to cloud run service.</li>
    <li>This setup will create a ci/cd pipeline by using dockerfile and cloud build.</li>
</ol>

## Built With

- [REACT JS](https://react.dev/learn) - The JS library used
- [Netlify](https://www.netlify.com/) - Frontend Deployment
- [React-Bootstrap](https://react-bootstrap.github.io/) - Bootstrap Framework
- [Bootstrap](https://getbootstrap.com/) - CSS Framework
- [Sweet-alert](https://sweetalert2.github.io/) - Custom alert library
- [Axios](https://axios-http.com/docs/intro) - Http client for Node.js and browser
- [SpringBoot](https://spring.io/) - Spring Boot is an open source Java-based framework
- [Docker](https://www.docker.com/) - Platform to build, share, and run modern applications
