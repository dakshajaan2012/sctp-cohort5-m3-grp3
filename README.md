# Website Traffic Monitoring System

Contributers: [Melissa Koh](https://github.com/merriemelly) | [Kelvin Chang](https://github.com/kelvin3118) | [Yao Hai](https://github.com/yaohai1216) | [Sunil](https://github.com/dakshajaan2012)

## Overview

This project is designed to monitor the traffic patterns of our [website](https://relaxed-crepe-fe83a4.netlify.app/) during its beta phase, which is accessible to a limited number of users.
Our central goal is to gain comprehensive insights into user behavior and engagement within the website.
By systematically logging various user activities, including sign-ups, logins, session durations, and interactions, we aim to refine our understanding of user requirements.
This valuable understanding will inform our efforts to improve the website and introduce additional features prior to its public launch.

![ERD](https://github.com/dakshajaan2012/sctp-cohort5-m3-grp3/assets/117299425/9ecbbd23-7f02-40a8-92e3-63435a7f9cb4)

# Explore REST APIs

## Users - Melissa Koh

| METHOD | ENDPOINT                 | DESCRIPTION                       |
| ------ | ------------------------ | --------------------------------- |
| GET    | /user                    | Retrieve all users                |
| GET    | /user/{userId}           | Retrieve one user based on userId |
| DELETE | /user/{userId}           | Delete one user                   |
| POST   | /user/create             | Create a user                     |
| PATCH  | /user/{userId}/update    | Update user details               |
| PATCH  | /user/bulk-update        | Bulk update user details          |
| POST   | /user/search             | Multifiltering search for users   |
| POST   | /user/{userId}/user-logs | Create user logs for user         |

## Address - Yao Hai

| METHOD | ENDPOINT                     | DESCRIPTION                                       |
| ------ | ---------------------------- | ------------------------------------------------- |
| POST   | /addresses                   | Create a new address                              |
| GET    | /addresses                   | Retrieve all addresses                            |
| GET    | /addresses/{id}              | Retrieve an address by ID                         |
| PUT    | /addresses/{id}              | Update an existing address by ID                  |
| DELETE | /addresses/{id}              | Delete an address by ID                           |
| GET    | /addresses/{userId}/address  | Retrieve all addresses of a user by user ID       |
| GET    | /addresses/{userId}/address/ | Retrieve addresses of a user by user ID and alias |

## User Logs - Kelvin Chang

| METHOD | ENDPOINT               | DESCRIPTION             |
| ------ | ---------------------- | ----------------------- |
| POST   | /user-logs             | Create a new user log   |
| GET    | /user-logs             | Retrieve all user logs  |
| DELETE | /user-logs/{userLogId} | Delete a user log by ID |

## Sessions - Sunil

| METHOD | ENDPOINT       | DESCRIPTION              |
| ------ | -------------- | ------------------------ |
| GET    | /sessions      | Retrieve all sessions    |
| POST   | /sessions      | Create a new session     |
| GET    | /sessions/{id} | Retrieve a session by ID |
| PUT    | /sessions/{id} | Update a session by ID   |
| DELETE | /sessions/{id} | Delete a session by ID   |

## Parking Slot - Sunil

| METHOD | ENDPOINT    | DESCRIPTION               |
| ------ | ----------- | ------------------------- |
| POST   | /slots      | Create a new parking slot |
| GET    | /slots      | Get all parking slots     |
| DELETE | /slots/{id} | Delete parking slot by ID |

## Bookings - Sunil

| METHOD | ENDPOINT              | DESCRIPTION          |
| ------ | --------------------- | -------------------- |
| GET    | /bookings             | Get all bookings     |
| POST   | /bookings             | Create a new booking |
| GET    | /bookings/{bookingId} | Get booking by ID    |
| PUT    | /bookings/{bookingId} | Update booking       |

## Features - Melissa Koh

| METHOD | ENDPOINT               | DESCRIPTION                              |
| ------ | ---------------------- | ---------------------------------------- |
| POST   | /feature/insert-sample | Insert sample features into the database |
| GET    | /feature               | Retrieve all features                    |
| GET    | /feature/{featureId}   | Retrieve a feature by ID                 |
| DELETE | /feature/{featureId}   | Delete a feature by ID                   |

## Feature Usages - Melissa Koh

| METHOD | ENDPOINT                 | DESCRIPTION                                       |
| ------ | ------------------------ | ------------------------------------------------- |
| GET    | /feature-usage           | Get all feature usages                            |
| GET    | /feature-usage/aggregate | Group feature usage data by feature name          |
| GET    | /feature-usage/median    | Calculate features per session and overall median |
| POST   | /feature-usage/create    | Create one feature usage                          |
