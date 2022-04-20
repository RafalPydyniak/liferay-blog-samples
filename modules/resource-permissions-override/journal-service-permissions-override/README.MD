# What is it
This is a sample module which overrides journal.xml permission file.
This is an example of how permissions which are **not** in portal-impl module can be overriden.

As a sample two changes were made:
- New Permission called "TEST" was added to Journal Article model
- For Journal Article model the default VIEW and ADD_DISCUSSION permission was removed for guest

This module servers as a proof of concept and an example how such change can be done.

Please read the full article on https://pydyniak.com for more details.

# How to use it
Simply deploy the module. No extra config required. No restart of portal required