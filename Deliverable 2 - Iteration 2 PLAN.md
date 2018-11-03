# BREADWINNER TEAM 7

## Iteration 2

 * Start date: October 27
 * End date: November 1

## Process

#### Roles & responsibilities

Front End development with Android Studio/Java: Tasbir, Sean

- **Tasbir** will create functions to abstract calls to our backend API into 1 file.
- **Sean** will set up the EC2 server on AWS and start the backend server at an endpoint on AWS, and integrate the existing search API call into the frontend of the app

BackEnd Python Flask API Methods: **Crystal**, **Michael**, **Raymond**, **Jerry**

- Michael:  Put method for the cook so they can update the status of a user’s order
- Crystal: Post Method to let users create orders
- Jerry: Get method to obtain all of the user’s active orders and their current status
- Raymond: Get method to obtain a user’s order history

#### Events
We are having this meeting right now to discuss roles and tasks to do before Deliverable 2 is due. 

At the end of this sprint (November 1), we will come together to review and accept pull requests and collaboratively put everything together into an MVP. This will also allow us to create the video.

#### Artifacts

We are outlining tasks with a Trello scrum board, as well as in this planning document.

#### Git / GitHub workflow

We will each create a branch of the master repo and commit changes to our respective branches. We will then make a pull request when a feature is done. This will allow each person to view and test their peers’ work before merging new features to the master. Once every member agrees that the feature works, it can then be merged into the master branch.

## Product

#### Goals and tasks

Our goals involve adding additional methods to the API so we can produce our MVP as well as have the app be able to call our API.

Back end:

- customer ordering api - **Crystal**
  - A method for customers to be able to place an order for a certain Listing.
- Change order status to “in progress” - **michael**
  - Once a chef has accepted the order have a method that sets it to in progress.
- Get Order Status - **Jerry**
  - Return the status of a given order.
- Get Order History - **Raymond**
  - Return all orders made by a user, their dates, and statuses.

Front end:

- Start integrating existing API functions to the front end to form an MVP -**Sean**
  - Add functionality to the search bar so that a user can enter a query and a list of listings will appear.
- Functions for REST API calls - **Tasbir**
  - Function to abstract API calls for the app itself as opposed to using Postman. Should be in its own file.


#### Artifacts


Our artifacts for this sprint will be comprised of code as we want to finish our minimum viable product for the video. We will have the code for our python flask server with the functionalities listed above.

In addition we will finish the front end views for our app so that we can have a presentable product for the video that is due at the end of this deliverable.