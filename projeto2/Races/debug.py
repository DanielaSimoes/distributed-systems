#!/usr/bin/env python

import os
import time


jars = [
    {
        "class": "NodeSetts",
        "package": "settings",
        "type": "server",
        "order": 1,
        "command": "java -cp 'Races.jar:libs/*' {} &"
    },
    {
        "class": "Races",
        "package": "GeneralRepository",
        "type": "server",
        "order": 2,
        "command": "java -cp 'Races.jar:libs/*' {} &"
    },
    {
        "class": "Log",
        "package": "GeneralRepository",
        "type": "server",
        "order": 3,
        "command": "java -cp 'Races.jar:libs/*' {} &"
    },
    {
        "class": "BettingCentre",
        "package": "shared",
        "type": "server",
        "order": 4,
        "command": "java -cp 'Races.jar:libs/*' {} &"
    },
    {
        "class": "ControlCentre",
        "package": "shared",
        "type": "server",
        "order": 5,
        "command": "java -cp 'Races.jar:libs/*' {} &"
    },
    {
        "class": "Paddock",
        "package": "shared",
        "type": "server",
        "order": 6,
        "command": "java -cp 'Races.jar:libs/*' {} &"
    },
    {
        "class": "RacingTrack",
        "package": "shared",
        "type": "server",
        "order": 7,
        "command": "java -cp 'Races.jar:libs/*' {} &"
    },
    {
        "class": "Stable",
        "package": "shared",
        "type": "server",
        "order": 8,
        "command": "java -cp 'Races.jar:libs/*' {} &"
    },
]

"""


    {
        "class": "HorseJockey",
        "package": "entities",
        "type": "client",
        "order": 9,
        "command": "java -cp 'Races.jar:libs/*' {} &"
    },
    {
        "class": "Spectators",
        "package": "entities",
        "type": "client",
        "order": 10,
        "command": "java -cp 'Races.jar:libs/*' {} &"
    },
    {
        "class": "Broker",
        "package": "entities",
        "type": "client",
        "order": 11,
        "command": "java -cp 'Races.jar:libs/*' {} &"
    }
"""


if __name__ == '__main__':
    os.system("cp dist/Races.jar Races.jar")

    jars_hosts = sorted(jars, key=lambda jar_host: jar_host["order"])

    for jar in jars_hosts:
        print("Starting " + jar["class"] + "...")
        os.system(jar["command"].format((jar["package"] + "." + jar["class"] + "Run")))
        time.sleep(1)


    try:
        # waiting for keyboard interrupt
        while True:
            pass
    except KeyboardInterrupt:
        os.system("pkill -f  \"java -cp\"")
