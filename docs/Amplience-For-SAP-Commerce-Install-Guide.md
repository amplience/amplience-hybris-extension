# Amplience Extension Installation Guide


## Introduction

These are the steps required to install SAP Commerce 2005 with the Amplience Extension on a local system using the
default embedded HSQLDB database and an embedded Solr index.

These steps assume that you are using the SAP Commerce Accelerator Apparel Store responsive storefront with the turnkey
`hybrisext` Amplience account. See the relevant section belows on how to setup your own Amplience account.

### Conventions

Where there are differences in the commands to be executed on Windows, Linux, and macOS these are shown separately.


### Prerequisites

* SAP Commerce 2005
* Amplience Extension 3 for SAP Commerce
* Java 11



## Installation Steps

##### 1. Setup the local project

* Create a new folder for the project.
* Unzip SAP Commerce Suite 2005 into the project folder.
* Unzip Amplience Extension 3 for SAP Commerce into the project folder.

> Note that the Amplience extensions live in the `hybris/bin/ext-amplience` path under the project folder.

##### 2. Prepare the local project configuration

* Open a command window on the project folder and navigate to the `hybris/bin/platform` folder:

```shell script
# Windows
cd hybris\bin\platform

# Linux / macOS
cd hybris/bin/platform
```

* Setup the SAP Commerce ant build environment by running this command

```shell script
# Windows
setantenv.bat

# Linux / macOS
. ./setantenv.sh
```

* Run ant to create the default SAP Commerce configuration:

```shell script
ant
```
* When prompted to select the configuration template to use press Enter to select the default `develop` template.

##### 3. Add SAP Commerce properties

* Edit the SAP Commerce configuration file `hybris/config/local.properties`
* Generate your own new password for the `admin` user and update the `local.properties` file to set it in the
`initialpassword.admin` property.

```properties
# Admin password for local install
initialpassword.admin=[new-random-password]
```

##### 4. Add the Amplience Extension to the `localextensions.xml`

* Edit the SAP Commerce configuration file `hybris/config/localextensions.xml`
* Add the `ampliencedm` and `ampliencedmaddon` extensions

```xml
    <!-- Amplience DM extensions -->
    <extension name="ampliencedm"/>
    <extension name="ampliencedmaddon"/>
```

* When running the SAP Commerce Accelerator Apparel Store with the SAP sample product data use the
`ampliencedmacceleratordemo` extension. This extension supports the Apparel Store's product data model and provides
additional sample product data.

> The `ampliencedmacceleratordemo` extension is only compatible with the Apparel sample product data model.
> Do not use with a customised product data model. See the documentation guide for more details.

The complete copy of the `hybris/config/localextensions.xml` is provided here:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<hybrisconfig xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="resources/schemas/extensions.xsd">
	<extensions>
		<path dir="${HYBRIS_BIN_DIR}"/>

		<extension name='acceleratorstorefrontcommons'/>
		<extension name='adaptivesearchsolr'/>
		<extension name='apparelstore'/>
		<extension name='cmsbackoffice'/>
		<extension name='pcmbackoffice'/>
		<extension name='yacceleratorbackoffice'/>
		<extension name='yacceleratorinitialdata'/>
		<extension name='yacceleratorstorefront'/>

		<!-- Amplience DM extensions -->
		<extension name="ampliencedm"/>
		<extension name="ampliencedmaddon"/>

		<!-- Amplience Accelerator Apparel Demo extension -->
		<extension name="ampliencedmacceleratordemo"/>

	</extensions>
</hybrisconfig>
```

##### 5. Install the storefront addon

* Install the Amplience DM SAP Commerce Accelerator addon into the yacceleratorstorefront by running this command:

```shell script
ant addoninstall -Daddonnames="ampliencedmaddon" -DaddonStorefront.yacceleratorstorefront="yacceleratorstorefront"
```
> If you have run `modulegen` to create your project from the hybris accelerator templates then you would specify your
> own storefront extension in the command above. See the hybris documentation for the `addoninstall` command for more
> details

##### 6. Build SAP Commerce

* Build your SAP Commerce project by running this command:

```shell script
ant clean all
```

##### 7. Initialize the SAP Commerce system

* Initialize the SAP Commerce system and database by running this command:

```shell script
ant initialize
```

##### 8. Local host name mappings

* The SAP Commerce Accelerator uses the hostnames `appareluk.local` and `apparelde.local` which need to map to the
local machine running SAP Commerce.
* If you have not already done so add the standard SAP Commerce Accelerator host mappings to your local hosts file.
* The following approaches should work on most systems:

```shell script
# Windows PowerShell
powershell Start-Process "notepad.exe %WINDIR%\System32\drivers\etc\hosts" -Verb runAs

# Windows in a UAC elevated Command Prompt
notepad.exe %WINDIR%\System32\drivers\etc\hosts

# Linux / macOS run the following command as root
vi /etc/hosts
```

* Add the following host file entry to the bottom of the hosts file:

```shell script
127.0.0.1 apparel-uk.local. apparel-de.local.
```

##### 9. Start the SAP Commerce

* Start the SAP Commerce application server by running this command:

```shell script
# Windows
hybrisserver.bat

# Linux / macOS
./hybrisserver.sh
```

* Wait for the application startup to complete, when it is ready you should see console log message like this:

```log
INFO: Server startup in 120106 ms
```

##### 10. Open the storefront in a browser

* Open the SAP Commerce Accelerator storefront in a browser using this URL: <https://appareluk.local:9002/yacceleratorstorefront/>


## Configuring SAP Commerce to send emails

If you want SAP Commerce to be able to send emails, for example the Amplience missing images report email, then it
needs to be configured to use an SMTP server.

* Add the following properties to the hybris/config/local.properties file:

```properties
mail.smtp.server=my.smpt.server.name
mail.smtp.port=25
```

* Specify the following properties for SMTP servers that require authentication:

```properties
mail.smtp.user=username
mail.smtp.password=password
mail.use.tls=true or false
mail.use.smtps=true or false
```

*. Support for SMTPS secure connections to an SMTP server:

```properties
mail.use.smtps=true or false
```


# Using your own Amplience Account


## Configuring SAP Commerce to use your Amplience Account

By default the Amplience Extension 3 for SAP Commerce is configured to use the turnkey `hybrisext` Amplience account.
This account can be used for demo and familiarization purposes. To host your own media data in Amplience you will need
a separate account. You can request an account from Amplience.

Once you have your Amplience account details you can configure the Amplience Extension in a couple of different ways.

### Single Amplience Account

If you have a single Amplience account configuration that you want to use for all your sites then you can set set the
following properties in your `hybris/config/local.properties` file:

```properties
amplience.dm.config.accountIdentifier=
amplience.dm.config.imageHostname=
```

> The `amplience.dm.config.imageHostname` property defaults to `cdn.media.amplience.net`

### Multiple Amplience Accounts

If you have multiple sites in your SAP Commerce solution that need to use different Amplience account details then
these settings can be specified on each SAP Commerce `BaseSite`. The settings on the `BaseSite` take priority over the
values set in the `hybris/config/local.properties` file.


## Exporting Accelerator Sample Product Images

Initially your Amplience account will not contain any product media. The Amplience Extension 3 for SAP Commerce includes
a script to export the SAP Commerce product images. This script works with the SAP Commerce Accelerator Apparel Store
product model and may need to be customised if your product model is different.

The script exports the images using a naming convention that is supported by Amplience. Check with Amplience Customer
Success to ensure that your account is setup with the standard `hybrisext` import script. Execute the script from the
SAP Commerce administration console’s (hac) Scripting Languages Console. The script file is in the
`ampliencedmacceleratordemo` extension, so in your project folder look in:

```
hybris/bin/ext-amplience/ampliencedmacceleratordemo/resources/ampliencedmacceleratordemo/script/exportImages.groovy
```

## Deleting Product Images from Hybris

Once the product images have been exported from SAP Commerce and uploaded into Amplience they are no longer needed in
SAP Commerce. These product images can be deleted using a script in the `ampliencedmacceleratordemo` extension

This delete images script can be run in the same way as the export script above. The script is in the same location but
called `deleteImages.groovy`:

```
hybris/bin/ext-amplience/ampliencedmacceleratordemo/resources/ampliencedmacceleratordemo/script/deleteImages.groovy
```

The script will delete the images from both the staged and online products. Only do this once you have confirmed that
your storefront images are being correctly served from Amplience.

## Uploading Support Images

The Amplience Extension 3 for SAP Commerce requires a small number of support images to exist in your Amplience account.
These images exist in the turnkey `hybrisext` account but if you are using your own account you will need to upload
these images via your Amplience FTP account (Amplience Customer Success can give you your FTP details).

The images are included in the `ampliencedmaddon` extension and are located in at the following location:

```
hybris/bin/ext-amplience/ampliencedmaddon/resources/ampliencedmaddon/upload-images
```

> After uploading these images manually into your Amplience account make sure that they are published.
> We suggest you put these images into a separate asset store in Amplience.

## Setting up your Amplience Account

Your Amplience account should be setup with the same locales and transformation templates as the standard `hybrisext`
account.

### Locales

Amplience supports multiple locales, however each locale needs to be setup in your account.
You do this from the Amplience site through the user menu in the top right corner of the page.

### Transformation templates

The following transformation templates should exist in your account.

#### Transformation templates that match SAP Commerce image formats:

| Transformation Name | Transformation Template |
| --- | --- |
| product     | `w=300&h=300&sm=MC&upscale=true&filter=l&img404=missing_product` |
| thumbnail   | `w=96&h=96&sm=MC&upscale=true&filter=l&img404=missing_product` |
| cartIcon    | `w=65&h=65&sm=MC&upscale=true&filter=l&img404=missing_product` |
| styleSwatch | `w=30&h=30&sm=MC&upscale=true&filter=l&img404=missing_product` |
| zoom        | `w=1200&h=1200&sm=MC&upscale=true&filter=l&img404=missing_product` |

#### Additional transformation templates:

| Transformation Name | Transformation Template |
| --- | --- |
| roundel     | `layer0=[w=1000]&layer1=[src=/i/hybrisext/badge_sale&w=400&right=10&top=10&anchor=TR&visible=$sale]&layer2=[src=/i/hybrisext/badge_new&w=400&left=10&top=10&anchor=TL&visible=$new]&layer3=[src=/i/hybrisext/badge_low_stock&w=400&left=10&bottom=10&anchor=BL&visible=$stock]` |
| poi   | `poi={($this.metadata.pointOfInterest.w==0)?0.5:$this.metadata.pointOfInterest.x},{($this.metadata.pointOfInterest.w==0)?0.5:$this.metadata.poi]ntOfInterest.y},{$this.metadata.pointOfInterest.w},{$this.metadata.pointOfInterest.h}&scaleFit=poi&sm=aspect&aspect=1:1` |
| backoffice    | `productType=0` |

> If you create any new transformation templates in your account or change any existing ones then make sure that you
> publish the changes.
