#!/usr/bin/env bash

#
# dist.sh script to create release distribution
#

DIR="$(cd "${BASH_SOURCE%/*}" && pwd)"

if [ -d "${DIR}/core-customize/hybris/temp/hybris/dists" ]; then
	rm -fr "${DIR}/core-customize/hybris/temp/hybris/dists/"
fi

cd "${DIR}/core-customize/hybris/bin/platform"

. ./setantenv.sh
ant dist "-Ddist.properties.file=${DIR}/dist.properties"

cd "${DIR}/core-customize/hybris/temp/hybris/dists/"
mv -v ampliencedm-hybris-*.zip "${DIR}/"
