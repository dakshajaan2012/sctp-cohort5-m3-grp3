version: 2.1

orbs:
  maven: circleci/maven@1.3
  docker: circleci/docker@2.1.4
  snyk: snyk/snyk@1.5.0
  heroku: circleci/heroku@2.0.0

jobs:
  deploy:
    docker:
      - image: cimg/node:16.10
    steps:
      - setup_remote_docker
      - heroku/install
      - checkout
      - run:
          name: Heroku Container Push
          command: |
            heroku container:login
            heroku container:push web -a dakshajaan-su-devops
            heroku container:release web -a dakshajaan-su-devops

  security_scan:
    docker:
      - image: cimg/openjdk:17.0
    steps:
      - checkout
      - run:
          name: Run OWASP Dependency-Check
          command: |
            # Make the script executable
            chmod +x run.bat

  publish:
    executor: docker/docker
    steps:
      - checkout
      - setup_remote_docker
      - docker/check
      - docker/build:
          image: dakshajaan/educational-space:project-g3-m4
          tag: v1.0.0
      - docker/push:
          image: dakshajaan/educational-space:project-g3-m4
          tag: v1.0.0

executors:
  openjdk17:
    docker:
      - image: cimg/openjdk:17.0
      - image: cimg/postgres:15.6
        environment:
          POSTGRES_USER: postgres
          POSTGRES_PASSWORD: 2024
          POSTGRES_DB: demodb

workflows:
  version: 2
  build-and-test:
    jobs:
      - maven/test:
          executor: openjdk17
          pre-steps:
            - run:
                name: Install dockerize
                command: wget https://github.com/jwilder/dockerize/releases/download/$DOCKERIZE_VERSION/dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz && sudo tar -C /usr/local/bin -xzvf dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz && rm dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz
                environment:
                  DOCKERIZE_VERSION: v0.6.1
            - run:
                name: Wait for db
                command: dockerize -wait tcp://localhost:5432 -timeout 1m
      - security_scan:
          #requires:
          #- maven/test
      - publish:
          #requires:
          #- maven/test
          filters:
            branches:
              only: develop, release
      - deploy:
          requires:
            - publish
          filters:
            branches:
              only: release
