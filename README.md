# Races Distributed Simulation

## Brief explanation

Events portray the activities that go by during a typical afternoon at a hippic center, somewhere at the outskirts of Aveiro. There are four main locations: the track where the races take place, the stable where the horses rest waiting their turn to enter the competition, the paddock where the horses are paraded before the spectators, and the betting center where the spectators place their bets on the winning horse.
There are three kinds of intervening entities: the pairs horse / jockey participating in the races, the spectators watching the races and placing bets on the horse they hope to win and the broker who accepts the bets, pays the dividends in case of victory and manages in a general manner all the operations that take place.
K races are run during the afternoon, each with N competitors. M spectators are present. The activities are organized as described below

* the broker announces the next race;
* the participating horses are paraded at the paddock by the jockeys;
* the spectators, after observing the horses and thinking about their winning chances, go to the
betting center to place their bet;
* the race takes place and one or more horses are declared winners;
* when somebody wins, he or she goes to the betting center to collect the gain

At the end of the afternoon, the spectators meet at the bar to have a drink and talk about the events that took place.
Each race is composed of a sequence of position increments of the intervening horse / jockey pairs according to the following rules

* the track distance for the race k, with k = 0, 1, ... , K-1, is Dk units of length;
* each horse/jockey Cnk, with n=0,1,...,N-1 and k =0,1,...,K-1 carries out a single position increment per iteration by moving randomly 1 to Pnk length units along its path – the maximum value Pnk is specific of a given horse, because they are not all equal, some being more agile and
faster than others;
* the horse / jockey pairs move in parallel paths and may be side by side or overtake one another;
* the winner is the pair horse/jockey Cnk, with n=0,1,...,N-1 and k=0,1,...,K-1, which, after
the completion of an iteration and having overtaken the finishing line, has a position with the
highest value;
* in case of a draw, all the horse / jockey pairs with the highest position value are declared winners;
the dividends to be received are inversely proportional to their number and rounded to unity.

Assume there are five races, each having four competitors and that the number of spectators is also four. Write a simulation of the life cycle of the horse / jockey pairs, the spectators and the broker using one of the models for thread communication and synchronization which have been studied: monitors or semaphores and shared memory.
One aims for a distributed solution with multiple information sharing regions, written in Java, run in Linux and which terminates. A logging file, that describes the evolution of the internal state of the problem in a clear and precise way, must be included.

## Developed solution

There are three solutions to answer this problem:

1. Using java’s reentrant locks for managing shared variables and concurrency. (explicit monitors) *(branch Monitors)*
2. Implemented a client-server strategy that active entities trade messages with passive entities (paddock, stable, betting centre, control centre, racing track) -  This solution tries to demonstrate a simple simulation how java’s RMI works. *(branch Messages)*
3. Implementation of a full java RMI solution where shared areas are registered and jockey/horse pairs *(branch RMI-Distributed)*
## Docs
Lifecycles are all described in DI folder.
## Requirements
This simulation was tested using Java 8.
## How to run
To run the first simulation just run in any IDE, in the next two solutions you must replace the machines.py file to your virtual machines.

