# Breadwinner - Team 7

## Iteration 1 (Deliverable 2)

 \* Start date: October 18, 2018

 \* End date: October 26, 2018

## Process

#### Roles & responsibilities

For this time:

Front end: Michael, Crystal

Back end: Sean, Raymond, Tasbir, Jerry

For next sprint:
Front end: Tasbir, Sean

Back end: The rest

#### Events


We are meeting online on October 18, 2018 for this iteration. The meeting will discuss the responsibilities and tasks of each member for this iteration.

#### Artifacts

We will be organizing tasks with a Trello scrum board.

#### Git / GitHub workflow

We will each create a branch of the master repo and commit changes to our respective branches. Branches will allow each person to view and test their peers’ work before merging new features to the master. We can use pull-requests that have been reviewed by 5 other teammates to integrate changes into master.

## Product

#### Goals and tasks

Our goal for this iteration is to have an API endpoint that can retrieve and remove food listings from the database and make progress on several other api’s that will support login, meal orders, order tracking, etc. These API functions will support the basic functionality of the app, and we’re planning on adding the “extra” functions in the next sprint.

Here is a detailed breakdown of our APIs we plan to write (in Python flask).

Add listing* TASBIR

​	-’POST’ request that adds a listing to the database.

User upon searching … *Sean

- Need api to pull data from database based on filters the user used  ‘GET’
- Sends a list of data back - each entry in the list contains the {[foodname, cookname, price, location, image]}

User wants to cancel an order … *Jerry

- Need api to get the status of the order ‘DELETE’
- If status of order is already in status of “preparation” api returns {[“cannot cancel”]} to front-end
- Otherwise, api will modify the field of “isActive” ‘PUT’ api then returns {[“cancel complete”]} to front-end

Marking an order as completed *Raymond

- write a delete function and call that changes the status of the order to complete

Before working on API’s, we will need to do some work regarding the database

- modify the existing ER diagram by removing information we won’t be needing
- create a database schema based on the modified ER diagram
- figure out how to launch the database on RDS
- add test data to the running database

We also plan on creating a frontend prototype that will eventually make use of our backend APIs.

This frontend API will consist of multiple views, with the most important being:

- A “search” view, where the user will be able to type in a search query and get a list of all relevant listings
- A “display listings” view, that displays either all listings, or listings that have been searched from the search bar

#### Artifacts

- UML diagrams to organize what types of requests the client will make to the server and how data will be transmitted between client and server (i.e. designing the different api endpoints) Examples: 

- - POST https://server.url/Login

{

“Username”:”Admin”

“Password”:”password123”

}

- - GET https://server.url/Search/Nearby

{

“Tags”:[“Chinese”, “Vegetarian”]

}

- Python flask module containing our API functions, that we will be able to deploy on EC2 and connect to in order to make HTTP requests

- A frontend template in Android Studio representing what our basic frontend will look like

- Database schema created as a result of augmenting the current ER diagram, which will be the design for the one we launch on RDS
