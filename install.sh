#!/usr/bin/env bash

DIR=`dirname $0`
cd ${DIR}/hybris/bin/platform


. ./setantenv.sh

ant clean

# Install the ampliencedmaddon into the yacceleratorstorefront
# only do this once
ant addoninstall -Daddonnames="ampliencedmaddon" -DaddonStorefront.yacceleratorstorefront="yacceleratorstorefront"

# Build everything
ant clean all

# Initialise the hybris DB
ant initialize
