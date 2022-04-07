# What is it
This is just a simple docker-compose environment for starting Liferay. It contains two containers:
- Liferay
- Database - MariaDB was used

The docker-compose is meant to simplify the process of using these samples

## How to run it
Simply run

``docker-compose up -d``

inside the **docker** directory. Then wait for a while until everything starts up.
To log in use the standard user/password: test@liferay.com/test

To stop the environment run

``docker-compose stop``

And to delete the environment completely (for example in order to start it from scratch with fresh data) run

``docker-compose down -v``

For more informations on commands above and other commands you might need please check the docker-compose documentation.

Or just google whatever you need :)