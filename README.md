
# Website Traffic Monitoring System

Contributers: [Melissa Koh](https://github.com/merriemelly) | [Kelvin Chang](https://github.com/kelvin3118) | [Yao Hai](https://github.com/yaohai1216) | [Sunil](https://github.com/dakshajaan2012)

## Overview
This project is designed to monitor the traffic patterns of our website during its beta phase, which is accessible to a limited number of users. 
Our central goal is to gain comprehensive insights into user behavior and engagement within the website. 
By systematically logging various user activities, including sign-ups, logins, session durations, and interactions, we aim to refine our understanding of user requirements. 
This valuable understanding will inform our efforts to improve the website and introduce additional features prior to its public launch.


![ERD](https://github.com/dakshajaan2012/sctp-cohort5-m3-grp3/assets/117299425/f4a13c77-36fa-45d1-aee7-cdb755674198)


# Explore REST APIs

## Users
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET    | /user    | Get all users |
| POST   | /user    | Create a new user |

## Address
| Method | Endpoint   | Description |
|--------|------------|-------------|
| GET    | /address   | Get user addresses |
| POST   | /address   | Add a new user address |
| PUT    | /address/:id | Update a user address |
| DELETE | /address/:id | Delete a user address |

## User Logs
| Method | Endpoint       | Description |
|--------|----------------|-------------|
| GET    | /user/logs     | Get all user logs |
| POST   | /user/logs     | Create a new user log |
| GET    | /user/logs/:id | Get user log by ID |
| PUT    | /user/logs/:id | Update user log by ID |
| DELETE | /user/logs/:id | Delete user log by ID |

## Sessions
| Method | Endpoint     | Description |
|--------|--------------|-------------|
| GET    | /sessions    | Get all active sessions |
| POST   | /sessions    | Create a new session |
| DELETE | /sessions/:id | Terminate session by ID |

## Features
| Method | Endpoint       | Description |
|--------|----------------|-------------|
| GET    | /features      | Get all available features |
| POST   | /features      | Add a new feature |
| GET    | /features/:id  | Get feature by ID |
| PUT    | /features/:id  | Update feature by ID |
| DELETE | /features/:id  | Delete feature by ID |

## Feature Usages
| Method | Endpoint             | Description |
|--------|----------------------|-------------|
| GET    | /feature-usages      | Get all feature usages |
| POST   | /feature-usages      | Record a feature usage |
| GET    | /feature-usages/:id  | Get feature usage by ID |
| DELETE | /feature-usages/:id  | Delete feature usage by ID || Description


