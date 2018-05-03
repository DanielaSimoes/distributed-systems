#!/usr/bin/env python

import os
import sys
import json
import paramiko


# colors
CRED = '\033[91m'
CEND = '\033[0m'
# end colors


hosts = [
    {
        "host": "l040101-ws01.ua.pt",
        "user": "sd0405",
        "password": "reverse"
    },
    {
        "host": "l040101-ws02.ua.pt",
        "user": "sd0405",
        "password": "reverse"
    },
    {
        "host": "l040101-ws03.ua.pt",
        "user": "sd0405",
        "password": "reverse"
    },
    {
        "host": "l040101-ws04.ua.pt",
        "user": "sd0405",
        "password": "reverse"
    },
    {
        "host": "l040101-ws05.ua.pt",
        "user": "sd0405",
        "password": "reverse"
    },
    {
        "host": "l040101-ws06.ua.pt",
        "user": "sd0405",
        "password": "reverse"
    },
    {
        "host": "l040101-ws07.ua.pt",
        "user": "sd0405",
        "password": "reverse"
    },
    {
        "host": "l040101-ws09.ua.pt",
        "user": "sd0405",
        "password": "reverse"
    },
    {
        "host": "l040101-ws10.ua.pt",
        "user": "sd0405",
        "password": "reverse"
    }
]

jars = sorted([

    {
        "class": "NodeSetts",
        "package": "settings",
        "type": "server",
        "order": 1,
        "command": "java -cp 'Races.jar:libs/*' {} &> output"
    },
    {
        "class": "Log",
        "package": "GeneralRepository",
        "type": "server",
        "order": 2,
        "command": "java -cp 'Races.jar:libs/*' {} &> output"
    },
    {
        "class": "Races",
        "package": "GeneralRepository",
        "type": "server",
        "order": 3,
        "command": "java -cp 'Races.jar:libs/*' {} &> output"
    },
    {
        "class": "BettingCentre",
        "package": "shared",
        "type": "server",
        "order": 4,
        "command": "java -cp 'Races.jar:libs/*' {} &> output"
    },
    {
        "class": "ControlCentre",
        "package": "shared",
        "type": "server",
        "order": 5,
        "command": "java -cp 'Races.jar:libs/*' {} &> output"
    },
    {
        "class": "Paddock",
        "package": "shared",
        "type": "server",
        "order": 6,
        "command": "java -cp 'Races.jar:libs/*' {} &> output"
    },
    {
        "class": "RacingTrack",
        "package": "shared",
        "type": "server",
        "order": 7,
        "command": "java -cp 'Races.jar:libs/*' {} &> output"
    },
    {
        "class": "Stable",
        "package": "shared",
        "type": "server",
        "order": 8,
        "command": "java -cp 'Races.jar:libs/*' {} &> output"
    },
    {
        "class": "HorseJockey",
        "package": "entities",
        "type": "client",
        "order": 9,
        "command": "java -cp 'Races.jar:libs/*' {} &> output_horsejockeys"
    },
    {
        "class": "Spectators",
        "package": "entities",
        "type": "client",
        "order": 10,
        "command": "java -cp 'Races.jar:libs/*' {} &> output_spectators"
    },
    {
        "class": "Broker",
        "package": "entities",
        "type": "client",
        "order": 11,
        "command": "java -cp 'Races.jar:libs/*' {} &> output_broker"
    }
], key=lambda jar_host: jar_host["order"])

ssh = paramiko.SSHClient()
ssh.load_system_host_keys()
ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())


def send_jar(host, jar):
    print("Jar: " + jar["class"])
    print("Sending the proper jar to the workstation")

    ssh.connect(host["host"], username=host["user"], password=host["password"])
    ssh.exec_command("rm -rf *")
    sftp = ssh.open_sftp()

    while u'libs\n' not in ssh.exec_command("ls")[1].readlines():
        ssh.exec_command("mkdir libs")

    ssh.exec_command("killall java")
    sftp.put(os.getcwd() + "/dist/Races.jar", "Races.jar")
    sftp.put(os.getcwd() + "/libs/json-simple-1.1.jar", "libs/json-simple-1.1.jar")
    sftp.put(os.getcwd() + "/libs/java-json.jar", "libs/java-json.jar")

    return [{
        "class": jar,
        "host": host
    }]


def upload(wait):
    global ssh

    print("Creating directory jars if it doesn't exist")

    print("See what hosts are up to calculate the architecture of the solution")

    for host in hosts:
        hostname = host["host"]
        response = os.system("ping -c 1 -W 1 " + hostname)

        if response != 0:
            hosts.remove(host)
            continue

        try:
            ssh.connect(host["host"], username=host["user"], password=host["password"])
            ssh.exec_command("echo \"Hello!\"")
        except Exception:
            hosts.remove(host)

    print("and sending them to the workstations")

    jar_i = 0

    jars_hosts = []

    if len(hosts) == 0:
        print("There are no machines active!")
        exit(1)
    elif len(jars) <= len(hosts):
        for host in hosts:
            jars_hosts += send_jar(host, jars[jar_i])

            jar_i += 1
            if jar_i == len(jars):
                break
    else:
        # there are more jars than hosts
        # machines 0 to 7 will have servers
        SERVERS = 7

        for i in range(0, SERVERS+1):
            jars_hosts += send_jar(hosts[i], jars[i])

        # machine 8 will have clients
        CLIENTS = 3

        for i in range(len(jars)-CLIENTS, len(jars)):
            jars_hosts += send_jar(hosts[len(hosts)-1], jars[i])

    print("Save the hosts in a JSON file to send it to the NodeSettsServer")

    to_file = []

    for host in jars_hosts:
        to_file += [{
            host["class"]["class"]: host["host"]["host"]
        }]

    with open('hosts.json', 'w') as outfile:
        json.dump(to_file, outfile)

    print("Upload hosts.json to all machines")

    for log_host in jars_hosts:
        if log_host["class"]["class"] == "Log":
            break

    for json_host in jars_hosts:
        try:
            ssh.connect(json_host["host"]["host"], username=json_host["host"]["user"],
                        password=json_host["host"]["password"])
            ssh.exec_command("echo \"Hello!\"")
        except Exception:
            continue

        sftp = ssh.open_sftp()
        sftp.put(os.getcwd() + "/hosts.json", "hosts.json")

    jars_hosts = sorted(jars_hosts, key=lambda jar_host: jar_host["class"]["order"])

    print("Executing each jar file on the proper workstation")

    log_connection = None

    for jars_host in jars_hosts:
        print("[%s]" % (jars_host["host"]["host"]))

        try:
            ssh.connect(jars_host["host"]["host"], username=jars_host["host"]["user"],
                        password=jars_host["host"]["password"])
            ssh.exec_command("echo \"Hello!\"")
        except Exception:
            continue

        # run
        print(jars_host["class"]["command"].format(
            jars_host["class"]["package"] + "." + jars_host["class"]["class"] + "Run"))
        stdin, stdout, stderr = ssh.exec_command(
            jars_host["class"]["command"].format(jars_host["class"]["package"] + "." + jars_host["class"]["class"] + "Run"))

        # when the log is over, everything is over
        if jars_host["class"]["class"] == "Log":
            log_connection = stdout.channel

    if len(wait) == 1 and wait[0] == "not":
        print("Bye! Don't forget to call 'python machines.py show_logs'\n" + \
              " and then: 'python machines.py get_log " + log_host["host"]["host"] + "'")
        exit(1)

    print("Waiting for the simulation end!")

    if log_connection.recv_exit_status() == 0:
        print("Simulation ended, copying log file to the local machine")

        get_log(log_host["host"]["host"])
    else:
        print("UPS! Something went wrong...")


def get_log(log_host_hostname):
    if len(log_host_hostname) == 1:
        log_host_hostname = log_host_hostname[0]

    for log_host in hosts:
        if log_host["host"] == log_host_hostname:
            break

    if not os.path.exists("logs"):
        os.makedirs("logs")
    try:
        ssh.connect(log_host["host"], username=log_host["user"], password=log_host["password"])
        ssh.exec_command("echo \"Hello!\"")
    except Exception:
        print("Unable to connect to the host: %s" % log_host["host"])
        return

    sftp = ssh.open_sftp()
    dir = sftp.listdir(".")

    # copy log file to localhost
    for f in dir:
        if str(f).endswith(".log"):
            log_file = str(f)
            sftp.get(log_file, "logs/" + log_file)


def kill_all():
    global ssh

    for host in hosts:
        hostname = host["host"]
        response = os.system("ping -c 1 -W 1 " + hostname)

        if response != 0:
            hosts.remove(host)
            continue
        try:
            ssh.connect(host["host"], username=host["user"], password=host["password"])
            ssh.exec_command("echo \"Hello!\"")
        except Exception:
            hosts.remove(host)

    for host in hosts:
        try:
            ssh.connect(host["host"], username=host["user"], password=host["password"])
            ssh.exec_command("echo \"Hello!\"")
        except Exception:
            continue
        #ssh.exec_command("rm -rf *")
        ssh.exec_command("killall java")
        print(host["host"] + ": killall java")


def show_logs(command="tail"):
    global ssh

    for host in hosts:
        hostname = host["host"]
        response = os.system("ping -c 1 -W 1 " + hostname)

        if response != 0:
            hosts.remove(host)
            continue
        try:
            ssh.connect(host["host"], username=host["user"], password=host["password"])
        except Exception:
            hosts.remove(host)

    print("\nSHOW LOGS\n")

    for host in hosts:
        try:
            ssh.connect(host["host"], username=host["user"], password=host["password"])
            ssh.exec_command("echo \"Hello!\"")
        except Exception:
            continue

        if len(command) == 1:
            stdin, stdout, stderr = ssh.exec_command(command[0]+" output*")
        else:
            stdin, stdout, stderr = ssh.exec_command(command+" output*")

        print(CRED + host["host"] + CEND)
        for line in stdout.readlines():
            print(line, end='', flush=True)


def command(command_to="tail"):
    if len(command_to) != 1:
        print("Please send the command that you want!")
        exit(1)

    global ssh

    for host in hosts:
        hostname = host["host"]
        response = os.system("ping -c 1 -W 1 " + hostname)

        if response != 0:
            hosts.remove(host)
            continue
        try:
            ssh.connect(host["host"], username=host["user"], password=host["password"])
            ssh.exec_command("echo \"Hello!\"")
        except Exception:
            hosts.remove(host)

    print("\nCOMMAND: " + command_to[0] + "\n")

    for host in hosts:
        try:
            ssh.connect(host["host"], username=host["user"], password=host["password"])
            ssh.exec_command("echo \"Hello!\"")
        except Exception:
            continue

        stdin, stdout, stderr = ssh.exec_command(command_to[0])

        print(CRED + host["host"] + CEND)
        for line in stdout.readlines():
            print(line, end='', flush=True)


if __name__ == '__main__':
    functions = {'upload': upload,
                 'killall': kill_all,
                 'show_logs': show_logs,
                 'get_log': get_log,
                 'command': command}

    if len(sys.argv) <= 1:
        print('Available functions are:\n' + repr(functions.keys()))
        exit(1)

    if sys.argv[1] in functions.keys():
        if len(sys.argv[2:]) == 0:
            functions[sys.argv[1]]()
        else:
            functions[sys.argv[1]](sys.argv[2:])