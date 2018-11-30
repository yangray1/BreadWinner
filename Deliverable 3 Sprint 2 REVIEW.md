# BREADWINNER TEAM 7

## Deliverable 3 Sprint 2 - Review & Retrospect

 * When:  November 26 2018
 * Where: BA3200

## Process - Reflection

#### Decisions that turned out well

Redeciding images for this sprint turned out very well, and we were able to figure out a way for users to upload images with each listing, and how to tie each image to each listing. If this weren’t the case, we would have had to use a static generic food image for each listing.

In terms of workload distribution, we were very happy with how we planned things. We actually discussed the estimated workload of each task, and allocated tasks based on personal schedules, etc, and we feel like we should have estimated workloads earlier in this project to avoid situations where a task takes longer than anticipated.

#### Decisions that did not turn out as well as we hoped

Since we were constrained on time, we had to scrap some features in favor of more important ones. Because of this, we chose to implement adding user reviews, at the sacrifice of not implementing user registration. In hindsight, we should have planned to implement user registration instead, as it defines a much more useful part in our app, while user reviews are sort of a “luxury” feature.

Reflecting on our process, we ended up hooking up a lot of backend APIs to the frontend on the last sprint of the last deliverable. While we ran into some minor issues, I feel like we should have had most of the APIs linked to the frontend before this sprint, and spent this sprint just testing everything and ensuring that everything works as expected for the demo and video. Because of this, we felt very rushed trying to put together all the pieces of the app in one deliverable.

#### Planned changes

Assign heavier tasks in earlier sprints, and have a light last sprint to test features and implement small luxury features. We felt very constrained and under pressure as our last sprint consisted of hooking up features and doing a lot of work before the deadline.

## Product - Review

#### Goals and/or tasks that were met/completed:

We finished everything that we had planned to do, but the tasks that we did plan to do could have been planned better.

![img](https://lh3.googleusercontent.com/bTHOggDdp8qQeVb6CqzyHftIeNocbIUsnf629-TVxVozxwTV1ajhXuN-xPjLs02-YHtUjB0yqsUtkuU27oMzfK4mKgySOr8zvKJp4pKkBroVM-f1JYJ1t8k8ATDoI5mUOPJyZhBe)![img](https://lh5.googleusercontent.com/pI7ZKxQFWG9Jt5J8pqOSJ9jU_fCc_OkPU4DJ16ASL0Ofb6iGNZTTsUaiX1_PX5HAFSmi4zvx4B-7S1zQoKV46gkMVroq6nDvqMI7JTrOw0VGVTSOT2evPmjnG22uFHiDObYeEvKM)

![img](https://lh3.googleusercontent.com/qLi7g9grCl4TwBAs17H-HwPz5XCnYdZhgghNK1rgjMrlomV_jhrTtmIKFUQW4AjC-bSITypj03LbcPjH8a-2C532CT4DC5579_jmGmk0Qj4DDPnyv-H7YPnRi0OhbbqvvP2UQgUp)

![img](https://lh6.googleusercontent.com/iFsjqv5M6GS92_fKVTTPj_dkeVUS4Aeo80itIWRuznLeypkd4-pMz266nR1Hem_zFL0yB6FWuyp_P1D6zDq9KaWQqkpTSaEWy0TYAFdEfzvB0lHMDUkmu1xaDEHGWCLwUmWaewb9)![img](https://lh3.googleusercontent.com/3FsV9qT-whE6zUTd2kONmb1RvQMqmdbkcSV7x6ei9l3KwUn6rs8xSIjSan4I-XsFUgBg2nA1Y52YwLju5Gn80K9zWpDIooZ19k62VECzbroq8xVwFNGM6WiSRzwjjM9yJdLmmvWW)

![img](https://lh4.googleusercontent.com/flVSbHVN3uc_gtAa0I8Q269kcJndr0aH0WaHypZPaLAgj7Ys_qvnXv6O9K7kF8O67JUfr3T9_-kiiok_JWYx2Ls0qf_cActPQF4fXUA4DsmoKVTsuVPEXonZdw9jGkxAYRWdeQW_)

#### Goals and/or tasks that were planned but not met/completed:

We completed all our assigned tasks, but we still have some features missing from our app and areas that can be improved. Most importantly, we have:

1. Users aren’t able to register new accounts on our app. We have to login with an existing userid/password combination defined in the database.

2. We should have constrained writing reviews to users who have ordered from another user. As of right now, anyone can leave a review for any user, regardless of whether or not they’ve ordered from this user.

## Meeting Highlights

For next iterations, we'd like to finish off "luxury"/non-core parts of our app, such as choosing meetup points from the app, and proper filtering of listings by location, etc.

There is also some parts of the database schema that could be improved, for example, the current primary key constrain on Orders means that a user can't order from a listing if they've cancelled an order for that listing previously. There are also a few columns and tables that aren't being used (such as Client ratings, and the images column in Listing).