# Pusuluru Giri Sharan Reddy - Master of Applied Computer Science, Dalhousie University, Canada
This includes all the course assignments and important materials to study

All materials are subjected to either dalhousie university or the original owner

For Docker:
    (Note: Ensure docker desktop is present in your system)
    1) Download docker-compose and file.dat files to an empty folder
    2) open any command terminal
    3) type command "docker-compose up" command and press enter
    4) once the command is run successfully, open postman application and test by sending a HTTPS POST request to a server on a custom port        6000 and endpoint /calculate with some body in JSON format such as
        {
           "file":"file.dat",
           "product":"<product-name>"
        }
