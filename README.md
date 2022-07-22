# What is this repository about
This repository contains sample Liferay modules which people might find interesting. 
Mostly these modules will be created for a blog posts on [my personal blog](https://pydyniak.com)
so if you're interested in any of the modules you will most likely find a whole blog post describing it there.

## How is this project structured
This project is a standard [Liferay Workspace](https://learn.liferay.com/dxp/latest/en/building-applications/tooling/liferay-workspace/what-is-liferay-workspace.html).

There are few things you should know thought. 
### Docker directory
There is a `docker` directory which contains a simple Docker-Compose environment which can be started for testing the examples.
By default if you run the "deploy" task the JARs will be put under docker/liferay/osgi/modules
and wars will be put under docker/liferay/osgi/wars. 

This makes usage of this docker environment really simple - no need to change any paths, move
files manually etc. Please go to the `docker` directory for more informations (there is another README)

### Modules
The `Modules` directory is a standard directory in Liferay workspace. 
What is important is what strategy is used in the modules creation.
Basically each functionality/topic is created under separate directory. Meaning:
- modules/not-resolved-bundles - contains couple of modules connected to this particular blog topic
- modules/resource-permissions-override - contains couple of modules connected to this particular blog post

etc.

What is really important is that each of that "parent" directories under modules
**will always contain additional README.md file which describes what can you find in the 
modules under this "parent" directory and will also contain link to particular blogpost (if applicable)**.

So this README.md only contains basic informations about the project structure and you can find
more READMEs in different directories.


## Warning
Please note these modules are just examples. In some cases they might not follow best coding practises.

Please also note that some modules might not start at all. If that happens this is most likely intentional
to show some concept. Please read the module-specific README for more details (also please check the 
"How is this project structured" section).


## Questions
If you have any questions feel free to email me at rafal@pydyniak.pl