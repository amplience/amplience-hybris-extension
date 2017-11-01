#!/usr/bin/env bash

DIR=`dirname $0`
cd ${DIR}/hybris/bin/platform


. ./setantenv.sh

ant dist -Ddist.properties.file=../../../dist.properties
