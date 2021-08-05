# Amplience Extension for SAP Commerce Cloud


## Local Development

### Setting up development environment

You need Java 11 JDK. You need java in your path and you need your `JAVA_HOME` set correctly.

Add the standard SAP Commerce Accelerator Apparel Store hostnames to your `/etc/hosts` file:

```
127.0.0.1	apparel-uk.local. apparel-de.local.
```

Checkout the project from github (if you are reading this then presumably you have already done this).

Unzip the SAP Commerce folder from the `CXCOM200500P_2-70004955.zip` into the `core-customize` sub-folder of your
repository.

Run the `install.sh` script from the root of the repository.

This script will:
* install the `ampliencedmaddon` into the `yacceleratorstorefront` extension.
* Build the project.
* Initialise the SAP Commerce DB.

```bash
./install.sh
```

Once the system has initialised you can open the Intellij IDEA project which is in the root of the repository.

### Building the code

The code is built using `ant` run from the `core-customize/hybris/bin/platform` folder.

```bash
cd core-customize/hybris/bin/platform
. ./setantenv.sh
ant clean all
```

### Running the jUnit tests

To prepare the environment to run the jUnit tests the test tenant must be initialised, this only needs to be done
once before the tests are executed.

```
ant yunitinit
```

Execute the following ant command to run the junit unittests for the Amplience extensions.

```bash
ant unittests -Dtestclasses.extensions="ampliencedm,ampliencedmaddon,ampliencedmbackoffice,ampliencedmcockpits"
```

### Running SAP Commerce

SAP Commerce includes a distribution of tomcat and runs everything from a single start script.
The start script is run from the `core-customize/hybris/bin/platform` folder.

```bash
cd core-customize/hybris/bin/platform
./hybrisserver.sh
```

The `hybrisserver.sh` script supports the following parameters:
* `run` - Starts the SAP Commerce server attached to the current console. This is the default option.
* `start` - Start in the background as a daemon process.
* `stop` - Stop if running as a daemon process.
* `debug` - Run the SAP Commerce server on attached to the console in a mode where it listens for a Java debugger to
  attach on port `8000`.

When running attached to the current console a `Ctrl-C` is used to trigger shutdown.

### SAP Commerce Websites and Cockpits

The SAP Commerce application server will by default open 2 ports, `9001` for http traffic and `9002` for https traffic.

* `hac` - <http://localhost:9001/> The SAP Commerce Administration Console is used to monitor or manage the SAP Commerce node/cluster.
* `Backoffice` - <http://localhost:9001/backoffice> The Backoffice used to view and edit the data stored in the SAP Commerce system.
* UK Apparel Demo Store - <https://apparel-uk.local:9002/yacceleratorstorefront/> The UK version of the Apparel demo store.
* German Apparel Demo Store - <https://apparel-de.local:9002/yacceleratorstorefront/> The German version of the Apparel demo store.

Default Users

* User: `admin` Password: `nimda` - Used to login to the `hac` or `Backoffice`.

> Note that the `admin` user password is set during initialisation from the value of the `initialpassword.admin` property.


## Distribution

### Building a distribution for 3rd parties

To build a distribution of the Amplience Extension for SAP Commerce, run:

```bash
./dist.sh
```

The distribution zip will be output to the root of the repository.

This script runs the SAP Commerce `ant dist` command using the `dist.properties` file in the root of the repository.
This file configures how the distribution is built and what it contains.

Setting up a new environment, selecting an appropriate DB, etc.. is beyond the scope of this document and is covered in
the SAP Commerce setup documentation.

### Building the Artifacts

The artifacts to deploy are built in either a dev or CI environment.
To build the deployment artifacts run the following `ant` command from the `core-customize/hybris/bin/platform` folder.

```bash
ant clean all production
```

The artifact will be output to `core-customize/hybris/temp/hybris/hybrisServer`. The following files will be created:
* `hybrisServer-AllExtensions.zip`
* `hybrisServer-Config.zip`
* `hybrisServer-Licence.zip`
* `hybrisServer-Platform.zip`

### Deployment (On-Prem)

The artifacts above need to be copied to the test server. `rsync` or `scp` are good ways of copying these files up to
the test server. The files should be copied into the `/home/hybris/artifacts` folder on the test server.

On the test server the SAP Commerce instance is running as the `hybris` user and the application is deployed into the
`/home/hybris/hybris/` folder.

Stop the current instance of SAP Commerce and remove the old binaries:

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

Append any environment specific configuration to the SAP Commerce config file.

Run the `ant` build script (yes as part of deployment):

```bash
cd /home/hybris/hybris/bin/platform
. ./setantenv.sh
ant clean all
```

Fix the user used to run the SAP Commerce application server:

```bash
sed -i "s/#RUN_AS_USER=/RUN_AS_USER=hybris/" /home/hybris/hybris/bin/platform/tomcat/bin/wrapper.sh
```

Start the SAP Commerce application server:

```bash
cd /home/hybris/hybris/bin/platform
./hybrisserver.sh start
```

Wait for the application server to start:

```bash
curl --output /dev/null --silent --connect-timeout 30 --max-time 240 http://localhost:9001/
```


## Documentation

 * [ Install Guide ](docs/Amplience-For-SAP-Commerce-Install-Guide.md)
 * [ Integration Guide](docs/Amplience-For-SAP-Commerce-Integration.md)
 * See also the [ Amplience Customer Hub ](http://hub.amplience.com/display/DEV/SAP+Hybris+Commerce)


## Changelog

* For version history see the [Changelog](CHANGELOG.md)


## Contribution guidelines

There are two ways you can contribute to this project:

1. File an `Issue` in GitHub using the `Issues` facility in the Navigation Menu. There are no guarantees that issues
that are filed will be addressed or fixed within a certain time frame, but logging issues is an important step in
improving the quality of this integrations.

2. If you have a suggested code fix, please fork this repository and issue a `pull request` against it. Amplience will
evaluate the pull request, test it, and possibly integrate it back into the main code branch. Even if the Amplience
does not choose to adopt your pull request, the pull request is now logged with this repository where other integrators
can see it and any of them can choose to adopt your suggested changes.

Thank you for helping improve the quality of this integration!


## License and Copyright

Copyright and included software attribution: See [ NOTICE ](NOTICE.md)

This software is licensed under the Apache License, Version 2.0. See the [ License file ](LICENSE).
