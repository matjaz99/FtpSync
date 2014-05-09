#!/bin/bash

version=$(<lib/version.txt)

echo Starting FtpSync v$version ...

if [ -z $1 ]; then
	java -jar lib/ftpsync-$version.jar ftpsync.properties
else
	java -jar lib/ftpsync-$version.jar $1
fi