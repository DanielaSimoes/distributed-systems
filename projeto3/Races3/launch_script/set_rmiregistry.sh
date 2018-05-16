#!/usr/bin/env bash
source config.bash
rmiregistry -J-Djava.rmi.server.codebase="http://$registry_host/$group/classes/"\
	    -J-Djava.rmi.server.useCodebaseOnly=true $1
