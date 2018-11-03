# Breadwinner/Team 7

## Iteration 2 Sprint 2 - Review & Retrospect

 * When: Nov 1st 7:30pm
 * Where: Online

## Process - Reflection

#### Decisions that turned out well

By the end of this iteration, we successfully implemented the APIs we planned on making at the start of this iteration. 
The APIs include an API to: 

- ​	the ability for the cook to update the status of the meal
- ​	order a dish
- ​	track the dish a user ordered
- ​	check a user’s order history. 

This is very important as it is the basis to modifying and querying the database for our app.

We also successfully finished the front end UI for our app. This is also an important decision to do because this is the basis of how users are going to interact with our app. 

With these, we were able to put out a MVP of our project.

#### Decisions that did not turn out as well as we hoped

One major difficulty that we had was running our backend with EC2. Since the EC2 was a linux machine, when we ran the backend APIs, specifically the add order API call, it kept appending ‘u’ to the end of the strings due to issues with Unicode. From this, we’ve learned that it’s not enough to test our Flask modules locally on Windows and mac.

Due to busy scheduling during this iteration, the front and backend was done with not as much communication as we expected, thus we were not able to get as many things done as we wanted during the middle of the sprint. However, as assignments and midterms cleared, we caught up and accomplished almost all of the tasks we wanted to do, such as finishing the APIs for the backend. 



#### Planned changes


We originally planned on making a UML diagram, but we came to a conclusion that since we were tight on time, it would be best if we fixed the bugs in the backend APIs and frontend for this deliverable instead.

We will also list out more tasks for each sprint, as during this one, some group members with multiple small tasks finished  faster than expected. This caused too much pressure for others to finish during the busy week of midterms and assignments.


## Product - Review

#### Goals and/or tasks that were met/completed:

 As mentioned above, we successfully implemented the APIs we planned on making at the start of this iteration. The APIs include an API to:

- the ability for the cook to update the status of the meal

- order a dish

- track the dish a user ordered

- check a user’s order history.  


This is very important as it is the basis to editing the database for our app.

Another task we completed was creating a functional front end UI for the app. The frontend from this iteration makes use of the search API function, as well as a new get all listings function to make listings dynamic as opposed to hard coded. These are the UIs that we have created in this iteration:

![Scree Shot 2018-11-02 at 11.24.49 PM](/Users/raymond/Desktop/Screen Shot 2018-11-02 at 11.24.49 PM.png)

![Screen Shot 2018-11-02 at 11.25.39 PM](/Users/raymond/Desktop/Screen Shot 2018-11-02 at 11.25.39 PM.png)

#### Goals and/or tasks that were planned but not met/completed:

One task that we planned to do but were not completed was creating the UML diagram to organize what types of requests the client will make to the server and how data will be transmitted between client and server. As mentioned above, this goal was not completed because we were short on time and deemed that fixing the backend and front end was more worthy for deliverable 2 than doing the UML.

Another task that was incomplete was to refactor table and column names into an .ini file. Some of the queries that our backend makes to the database are still hard coded in as we just wanted presentable functionalities for the video. Going forward, we would like to move these out into an .ini file so we have more flexibility with how we can edit out tables.


## Meeting Highlights

Going into the next iteration, our main insights are to expand and finish the rest of the features of the front end such as the map and location feature, a login feature, and views for cooks to post a meal. 

As for the backend, we encountered some problems near the end of the iteration; as of now, the database does not allow a user to make more than one of the same order. From that, the problem of tracking the number of order requests a cook has for his/her dish arises. To solve this problem, we have proposed to add a quantity attribute in the Order table in the database. We also proposed to add an attribute isActive having a value of true or false in the table listing. Because of this, this will result in changes to the APIs, specifically the queries. All this will be done in deliverable 3.

Lastly, in the next iteration, we will also do error checking in the APIs and put all the hard coded table and attribute names into an .ini config file.