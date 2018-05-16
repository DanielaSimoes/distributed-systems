#!/usr/bin/env bash
java -Djava.rmi.server.codebase="file://$(pwd)/"\
     -Djava.security.policy=java.policy\
     generalRepository.races.RacesRun