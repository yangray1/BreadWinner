# BREADWINNER - TEAM 7


## Iteration 3 Sprint 1 - Review & Retrospect

 * When: Nov. 18
 * Where: Online

## Process - Reflection

Deliverable 3 was the heaviest in terms of workload. We had a handful of functionalities we wanted to implement but we were still missing some views and api that were needed to support these features. 

#### Decisions that turned out well

Splitting the work into two sprints allowed us to first craft all of the remaining XML views and api in the first sprint, and then have the same person integrate them together in the second sprint. Additionally, we already had some available api that could be immediately routed to existing views - this was completed in the first sprint.

In the previous deliverable, we spent too much time on each REST api. In this deliverable, we decided to condense the api tasks into one sprint. This decision allowed us to allocate more time to complete other remaining work.

The tasks were clear and concise. Everyone knew exactly what they had to do, which resulted in no conflicts. This is how we are planning to split the work moving on:

Sprint 1:

- Two features that already had views and api that could be integrated

- - Tasbir and Crystal worked on this

- Four remaining views or api that still had to be implemented:

- - Sean, Michael, Raymond, Jerry completed these remaining requirements

Sprint 2:

- Six features that needed the front-end and back-end to be integrated

- - Each member was assigned one feature
  - We’re planning on having the same person who wrote an XML sheet in sprint 1 integrate backend functionality to that same XML sheet, so they’ll have a better understanding of what’s going on

This division of work has now allowed us to wrap up our project within our timeline. This includes: cleaning up XML views, code cleanup, sanity testing, and video recording.

We are also glad that we figured out how to send POST requests from our app way back last month, which made posting orders possible for testing through our app and not just Postman. This will also make our lives easier looking into the next sprint, where we will rely a lot on POST requests to add data to our database (ex. add listing).

#### Decisions that did not turn out as well as we hoped

We decided on adding a status column in the orders table to track the status of a client’s order (ex. Pending, in progress, delivered, completed, canceled). However, this decision did not consider the scenario where a user reorders a listing that he/she has previously canceled. Since the row is not deleted when an order is canceled, a new row cannot be inserted when a user tries to reorder due to the primary key constraint in the order table.

We decided to address image storage in this review meeting as we’re about to wrap up the project, but we felt that having users input an imgur url for the image of the listing they’re trying to post (as it currently stands in the database) seems very impractical. We are thinking of just using a generic food image (as we are right now with a picture of rice) as a sample image for every listing as a result when the next sprint happens.


#### Planned changes

For this project, we used an ec2 VM server to host our REST api services. Members were constantly redeploying the backend in the ec2 server for testing. This sometimes led to broken back-end code being pushed to the master branch as code was being checked-in for testing.  If there were future deliverables, we would plan on testing the api on localhost first, and then pull requesting to merge the api changes into master.


## Product - Review

#### Goals and/or tasks that were met/completed:

We were able to complete all the tasks we had planned for this sprint. 

## Meeting Highlights

For our next sprint, we will integrate the newly added api and front-end views. Members who worked on front to back-end integration created a class to make API calls and DAO interfaces to populate data on the client side. Moving forward into our next sprint, we will be able to use these classes and interfaces for our remaining features.