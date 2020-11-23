#!/usr/bin/env bash

#
# install.sh script that installs the ampliencedmaddon into the yacceleratorstorefront
#

DIR="$(cd "${BASH_SOURCE%/*}" && pwd)"

cd "${DIR}/core-customize/hybris/bin/platform"

. ./setantenv.sh

ant clean

# Install the ampliencedmaddon into the yacceleratorstorefront
# only do this once
ant addoninstall "-Daddonnames=ampliencedmaddon" "-DaddonStorefront.yacceleratorstorefront=yacceleratorstorefront"

# Build everything
ant clean all

# Initialise the hybris DB
ant initialize
