# Amplience DM Hybris Integration

## Local Development

### Setting up development environment

You need Oracle Java 1.8 JDK. You need java in your path and you need your `JAVA_HOME` set correctly.
Make sure that you have the JCE cryptography extensions installed into this version of java.
If you upgrade Java make sure that you install the JCE cryptography extensions into the new version of Java.

Add the standard hybris Accelerator Apparel Store hostnames to your `/etc/hosts` file:

```
127.0.0.1	apparel-uk.local. apparel-de.local.
```

Checkout the project from bitbucket (if you are reading this then presumably you have already done this).

Unzip the hybris folder from the `hybris-commerce-suite-6.2.x.x.zip` into your working copy folder.

Run the install script from the root of the working copy.

This script will:
* install the `ampliencedmaddon` into the `yacceleratorstorefront` extension.
* Build the project.
* Initialise the hybris DB.

```bash
./install.sh
```

Once the system has initialised you can open the Intellij IDEA project which is in the root of the working copy.


### Building the code

The code is built using `ant` run from the `hybris/bin/platform` folder.

```bash
cd hybris/bin/platform
. ./setantenv.sh
ant clean all
```

### Running the jUnit tests

Execute the following ant command to run the junit unittests for the amplience extensions.

```bash
ant unittests -Dtestclasses.extensions="ampliencedm,ampliencedmaddon,ampliencedmbackoffice,ampliencedmcockpits"
```


### Running hybris

Hybris includes a distribution of tomcat and runs everything from a single start script.
The start script is run from the `hybris/bin/platform` folder.


```bash
cd hybris/bin/platform
./hybrisserver.sh
```

The `hybrisserver.sh` script supports the following parameters:
* `run` - Starts the hybris server attached to the current console. This is the default option.
* `start` - Start in the background as a daemon process.
* `stop` - Stop if running as a daemon process.
* `debug` - Run the hybris server on attached to the console in a mode where it listens for a Java debugger to attach on port `8000`.

When running attached to the current console a `Ctrl-C` is used to trigger shutdown.


### Hybris Websites and Cockpits

The hybris application server will by default open 2 ports, `9001` for http traffic and `9002` for https traffic.

* `hac` - <http://localhost:9001/> The Hybris Administration Console is used to monitor or manage the hybris node/cluster.
* `hmc` - <http://localhost:9001/hmc> The Hybris Management Console is used to view and edit the data stored in the hybris database.
* `productcockip` - <http://localhost:9001/productockpit> The Product Cockpit is used to create and manage products and categories.
* `cmscockpit` - <http://localhost:9001/cmscockpit> The CMS Cockpit is used to manage content displayed on the storefronts.
* UK Apparel Demo Store - <https://apparel-uk.local:9002/yacceleratorstorefront/> The UK version of the Apparel demo store.
* German Apparel Demo Store - <https://apparel-de.local:9002/yacceleratorstorefront/> The German version of the Apparel demo store.

Users

* User: `admin` Password: `nimda` - Used to login to the `hac` or `hmc`.
* User: `productmanager` Password: `1234` - Used to login to the `productcockpit`.
* User: `cmsmanager` Password: `1234` - Used to login to the `cmscockpit`.



## Distribution

### Building a distribution for 3rd parties

To build a distribution of the Amplience DM Hybris extensions run:

```bash
./dist.sh
```

The distribution zip will be output to `hybris/temp/hybris/dists`.

This script runs the hybris `ant dist` command using the `dist.properties` file in the root of the workspace.
This file configures how the distribution is built and what it contains.


## Amplience Test Server

Amplience have a test server running hybris. The server is running on AWS. Access to the server is limited.
The server is running on hostname `hybris.dev.adis.ws`.

Setting up a new environment, selecting an appropriate DB, etc.. is beyond the scope of this document and is covered in the hybris setup documentation.


### Building the Artifacts

The artifacts to deploy are built in either a dev or CI environment.
To build the deployment artifacts run the following `ant` command from the `hybris/bin/platform` folder.

```bash
ant clean all production
```

The artifact will be output to `hybris/temp/hybris/hybrisServer`. The following files will be created:

* `hybrisServer-AllExtensions.zip`
* `hybrisServer-Config.zip`
* `hybrisServer-Licence.zip`
* `hybrisServer-Platform.zip`


### Deployment

The artifacts above need to be copied to the test server. `rsync` or `scp` are good ways of copying these files up to the test server.
The files should be copied into the `/home/hybris/artifacts` folder on the test server.

On the test server the hybris instance is running as the `hybris` user and the application is deployed into the `/home/hybris/hybris/` folder.

Stop the current instance of hybris and remove the old binaries:

```bash
cd /home/hybris/hybris/bin/platform
./hybrisserver.sh stop

cd /home/hybris
rm -rf /home/hybris/hybris/bin /home/hybris/hybris/temp
```

Unpack the deployment artifacts and copy the DB driver:

```bash
cd /home/hybris/artifacts
unzip -o -d /home/hybris hybrisServer-Platform.zip
unzip -o -d /home/hybris hybrisServer-AllExtensions.zip
unzip -o -d /home/hybris hybrisServer-Config.zip
unzip -o -d /home/hybris hybrisServer-Licence.zip

cp mysql-connector-java-5.1.38-bin.jar /home/hybris/hybris/bin/platform/lib/dbdriver/
```

Append environment specific configuration to the hybris config file:

```bash
cat /home/hybris/hybris/config/hybris.properties >> /home/hybris/hybris/config/local.properties
```

Run the `ant` build script (yes as part of deployment):

```bash
cd /home/hybris/hybris/bin/platform
. ./setantenv.sh
ant clean all
```

Fix the user used to run the hybris application server:

```bash
sed -i "s/#RUN_AS_USER=/RUN_AS_USER=hybris/" /home/hybris/hybris/bin/platform/tomcat/bin/wrapper.sh
```

Start the hybris application server:

```bash
cd /home/hybris/hybris/bin/platform
./hybrisserver.sh start
```

Wait for the application server to start:

```bash
curl --output /dev/null --silent --connect-timeout 30 --max-time 240 http://localhost:9001/
```


### Test Server Websites

* `hac` - <http://hybris.dev.adis.ws/>
* `hmc` - <http://hybris.dev.adis.ws/hmc>
* `productcockip` - <http://hybris.dev.adis.ws/productockpit>
* `cmscockpit` - <http://hybris.dev.adis.ws/cmscockpit>
* UK Apparel Demo Store - <https://apparel-uk.hybris.dev.adis.ws/yacceleratorstorefront/>
* German Apparel Demo Store - <https://apparel-uk.hybris.dev.adis.ws/yacceleratorstorefront/>

## Documentation

 * [ Installation and Setup Guide ](/documentation/AmplienceForSAPHybrisCommerceInstallationGuide.pdf)
 * [ Business User Guide](documentation/AmplienceForSAPHybrisCommerceBusinessUserGuide.pdf)
 * [ System Integration Guide ](AmplienceForSAPHybrisCommerceSystemIntegrationGuide)
 * See also the [ Amplience Customer Hub ](http://hub.amplience.com/display/DEV/SAP+Hybris+Commerce)

## Changelog ##

* For version history see the [Changelog](CHANGELOG.md)

## Contribution guidelines ##
There are two ways you can contribute to this project:

1. File an `Issue` using the `Issues` facility in the Navigation Menu.  There are no guarantees that issues that are filed will be addressed or fixed within a certain time frame, but logging issues is an important step in improving the quality of these integrations.

2. If you have a suggested code fix, please fork this repository and issue a 'pull request' against it.  Amplience will evaluate the pull request, test it, and possibly integrate it back into the main code branch.  Even if the Amplience partner does not choose to adopt your pull request, the pull request is now logged with this repository where other customers, clients, and integrators can see it and any of them can choose to adopt your suggested changes.

Thank you for helping improve the quality of this integration!

## Who do I talk to? ##

* [Oliver Secluna](mailto:osecluna@amplience.com) - Product Owner, Integrations @ Amplience

## License and Copyright

This software is licensed under the Apache License, Version 2.0. See the [ License file ](LICENSE).

Copyright 2017 Amplience