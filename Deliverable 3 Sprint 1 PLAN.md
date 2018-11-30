# BREADWINNER TEAM 7

## Iteration 3 - Sprint 1

* Start date: Nov. 12 
* End date: Nov. 18

## Process

In this deliverable, we aim to implement more functionalities. This includes the following: dynamic updating of user order tracking, dynamic updating of cook listing tracking, login, and cook review and rating.

#### Changes from the previous iteration

We figured out that writing a single backend API is very little work, so instead of assigning 1 API function per person, we’ll be assigning the remaining APIs to one person, while having the rest of the group work on frontend integration/sheets in order to balance out the workload.

#### Roles & responsibilities

Frontend:

Java coding/adding functionality to views:

- Crystal: add functionality to order tracking page (i.e. have tracking page display real-time tracking status), and allow users to cancel orders if the order has not been accepted yet
- Tasbir: add front-end functionality to customer order placing 

XML Sheets:

- Sean: add a view for cooks to add a listing
- Raymond: view for the cook to check his/her listing details (i.e. how many people ordered, how much time until delivery, etc. etc.)
- Jerry: view for order history

Backend:

- Michael: API for login, API for leaving reviews and rating cooks

#### Events

We will meet once in person, and once online. 

In the first meeting (this one!), we will delegate the remaining features we want to implement. We will outline all our tasks and responsibilities.

In the second meeting (Nov 18), we will review each other’s work and merge any conflicts to ensure all added features are fully supported in our application.

#### Artifacts

List/describe the artifacts you will produce in order to organize your team.


Trello board
![img](https://lh5.googleusercontent.com/pITbcfAiBWbxMGnM5NN9ZnsW6GYsAZpG_H8TLNJ63PTcG-dBa1_oe0ZMT_OAlXCDtsgTaIfqaS-KmcbzLhpgPUTiIFt4TvWIRZvPY9DbdiMM1UEmIkCZfccB7hdkFMD3TyT6sikR)

Group chat

![img](https://lh3.googleusercontent.com/gXZ-a52tCaTEMSKDi9KtUfcoMReFtLuO6ojx1eopzQIbQWwm5EfgVl22CtbfCCYaYbIPA5QzM82Yo9V0nqQ53mxTrofcgRU7rekG973oIcNuiev-ENCSekz6mq4uE6EoFi06ubFs)

#### Git / GitHub workflow

We will each create a branch of the master repo and commit changes to our respective branches. Branches will allow each person to view and test their peers’ work before merging new features to the master branch. The merge should be collaboratively done by any members who have code affected by the merge conflict. 


## Product

#### Goals and tasks

For this deliverable, we will complete functionalities to support these following scenarios: 

- Customers would like to be able to see the real-time status of their orders. They would also like to be able to cancel if the cook has not yet accepted their order. A customer may order more than one item; they would like to be able to track all of their orders. 
- Cooks would like to have a place to view how many people have ordered their dish and how much time they have remaining until delivery time. They would also like to be notified every time a customer places an order on their listing.
- Customers should be able to rate the cook based on how satisfied they are with the cook’s food and service.
- Login: we want to implement logins so that orders and reviews can be made with a specific userID, and not a hardcoded one.

Some backend API and static views for the requirements above have not been implemented. Since we have some missing API and views, we will add them in this sprint. This will allow us to immediately integrate everything together in the second sprint. We will also add functionality to the views that already exist in this sprint.

#### Artifacts

List/describe the artifacts you will produce in order to present your project idea.

-Java coding: functional orders in the frontend - users should be able to place orders and track orders

-XML sheets: create static pages for adding a listing, viewing “own” listing details, and a page to view order history

-Backend: functions that let users log in and leave reviews