#!/usr/bin/env bash
java -Djava.rmi.server.codebase="file://$(pwd)/"\
     -Djava.rmi.server.useCodebaseOnly=false\
     entities.horseJockey.HorseJockeyRun