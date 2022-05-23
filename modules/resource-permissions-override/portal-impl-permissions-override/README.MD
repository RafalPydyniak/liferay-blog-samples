# What is it
This is a sample module which overrides documentlibrary.xml permission file.
This is an example of how permissions inside portal-impl module can be overriden.

As a sample two changes were made:
- New Permission called "XYZ" was added to DLFileEntry model (translation was not provided)
- For DLFileEntry model the default permissions for guest & site member were changed. See the documentlibrary-ext.xml file for the details

This module servers as a proof of concept and an example how such change can be done.

See a [Overriding Liferay Core Permissions article](http://localhost:8000/overriding-liferay-core-permissions/) 
for more details.

# How to use it
The module has to be deployed to LIFERAY_HOME/tomcat/lib directory. 
You also need to update the portal properties using:
- LIFERAY_RESOURCE_PERIOD_ACTIONS_PERIOD_CONFIGS - if you're using Docker environment variables
- resource.actions.configs - if you're using portal-ext.properties file

See the blog post for the details