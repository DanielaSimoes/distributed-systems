#!/usr/bin/env bash
java -Djava.rmi.server.codebase="file://$(pwd)/"\
     -Djava.security.policy=java.policy\
     shared.stable.StableRun