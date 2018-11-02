# Breadwinner - Team 7


## Iteration 1 - Review & Retrospect

 * When: October 26
 * Where: In person

## Process - Reflection

#### Decisions that turned out well

Being very specific with the API functions that we were to write in the backend.

* Tasks were clear and concise. Everyone knew exactly what they had to do, which resulted in no conflicts.

Splitting the team into frontend and backend.

* The frontend will already have API functions that can be used and routed.

#### Decisions that did not turn out as well as we hoped

Due to busy schedules during this iteration, the backend was approached without doing much detailed, high level planning and writing documentation.

* When it came time to review pull requests, we all had inconsistent backend implementations.
  * For example, some of us chose to macro the relation names, while others hardcoded them

Frontend - lack of communication: One person was refactoring views for relative layout. However, another person began simultaneously adding new details to these views. As a result, the refactoring had to be done a second time to the views with the new details added.


#### Planned changes

Take things slower: we will eventually have to write documentation, so might as well write it before coding.


## Product - Review

#### Goals and/or tasks that were met/completed:

* Database design was completed, and we deployed the database on RDS

 * Every API function we planned on finishing was finished.
   * Searching for listings
   * Adding listings, which requires the use of Postman to use and test
   * Cancelling an order
   * Marking an order as completed
 * The frontend members made a working frontend that supported the views we planned, and added navigation so that we could switch between these views easily:
   * A "search" view
   * A display listings view, which has a hardcoded listing as of now.

#### Goals and/or tasks that were planned but not met/completed:

N/A

## Meeting Highlights

For the next iteration, we will allocate more time for the frontend as our group is unfamiliar with Android Studio.

At the same time, we need to think about details such as using macros instead of hardcoding relation and column names in the backend. It turns out our naming convention for Postgres is incorrect, and by using macros, we can  change relation and column names later and only have to change one line in the backend file as a result.