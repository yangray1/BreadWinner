# October 15 2018

Posting a menu that people can order dishes off of vs. posting individual listings

### MENU

### LISTINGS

* separate entities representing each listing, customers are able to query based on food type, chef, etc

### PRIORITIES

* user profiles
  * need a way to differentiate cooks and clients
* listings
  * add
  * delete
  * respond

### TIPS

* web server
* backend can be written in Python (flask based server), django
* design APIs that do all the backend tasks (look up REST APIs - "noun" based)
  * ex. facebook app on phone, how does it get all the information to show?

* FLASK, EC2
* launch an instance of db in server
* SPLIT UP TASKS based on who's doing which API
* MYSQL docker
* docker: add server.py, and run it
  * creates "machine" and starts server.py

* mysql and your server running on an instance of ec2, we can all ssh to it
* master should be running on ec2
* test by hosting server locally 