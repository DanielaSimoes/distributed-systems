#!/usr/bin/env python

import os
import time
import subprocess

jars = [

    {
        "class": "NodeSetts",
        "package": "settings",
        "type": "server",
        "order": 1,
        "command": "java -cp 'Races.jar:libs/*' {}"
    },
    {
        "class": "Log",
        "package": "GeneralRepository",
        "type": "server",
        "order": 2,
        "command": "java -cp 'Races.jar:libs/*' {}"
    },
    {
        "class": "Races",
        "package": "GeneralRepository",
        "type": "server",
        "order": 3,
        "command": "java -cp 'Races.jar:libs/*' {}"
    },
    {
        "class": "BettingCentre",
        "package": "shared",
        "type": "server",
        "order": 4,
        "command": "java -cp 'Races.jar:libs/*' {}"
    },
    {
        "class": "ControlCentre",
        "package": "shared",
        "type": "server",
        "order": 5,
        "command": "java -cp 'Races.jar:libs/*' {}"
    },
    {
        "class": "Paddock",
        "package": "shared",
        "type": "server",
        "order": 6,
        "command": "java -cp 'Races.jar:libs/*' {}"
    },
    {
        "class": "RacingTrack",
        "package": "shared",
        "type": "server",
        "order": 7,
        "command": "java -cp 'Races.jar:libs/*' {}"
    },
    {
        "class": "Stable",
        "package": "shared",
        "type": "server",
        "order": 8,
        "command": "java -cp 'Races.jar:libs/*' {}"
    },
    {
        "class": "HorseJockey",
        "package": "entities",
        "type": "client",
        "order": 9,
        "command": "java -cp 'Races.jar:libs/*' {}"
    },
    {
        "class": "Spectators",
        "package": "entities",
        "type": "client",
        "order": 10,
        "command": "java -cp 'Races.jar:libs/*' {}"
    },
    {
        "class": "Broker",
        "package": "entities",
        "type": "client",
        "order": 11,
        "command": "java -cp 'Races.jar:libs/*' {}"
    }
]

"""
    
    
    
"""


def number_processes():
    return int(subprocess.check_output("ps aux | grep \"java -cp\" |  wc -l", shell=True))


def run():
    jars_hosts = sorted(jars, key=lambda jar_host: jar_host["order"])

    # run in parallel
    processes = []

    for jar in jars_hosts:
        print("Starting " + jar["class"] + "...")

        cmd = jar["command"].format((jar["package"] + "." + jar["class"] + "Run"))
        processes.append(subprocess.Popen(cmd, shell=True))

        time.sleep(1)

    # do other things here..
    # wait for completion
    for p in processes:
        p.wait()


if __name__ == '__main__':
    os.system("cp dist/Races.jar Races.jar")

    N_RUNS = 10

    try:
        for i in range(0, N_RUNS):
            print("Running ", i, "/", N_RUNS)
            run()
            os.system("pkill -f  \"java -cp\"")
            print("All end! \n\n")
    except KeyboardInterrupt:
        os.system("pkill -f  \"java -cp\"")
