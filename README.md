# Overview

In this project, we implemented the code kata "Game Of Three", as defined in the PDF document `./game-of-three-code-kata.pdf`.

# Design

The specification is a bit loose about the soundness of the game: the main point is that it lacks a "game referee" which is entitled to independently judge whether the move of a player is compliant wrt the rules, and, of course, is entitled to judge which player won the game.

Moreover, the specification lacks details wrt the players must be always "online" to play the game, or they can go offline at any time and resume the game when they come back online.

In order to address these issues, we introduced the concept of a "Game Table":

* a player can invite another player to play at the Game Table
* the Game Table is entitled to be the referee for the game
* a player can leave the Game Table at any time, and later join again

So, we have implemented two Java programs:

* `game-of-three-server` which is a Rest Web Server daemon acting as "Game Table" (which is run as a Unix service in the *deb* packaging - see later)
* `play-game-of-three` which is a terminal operated command acting as player's interface

The programs behave as follow:

* `play-game-of-three` sends commands to the Game Table via a Rest/Json API and listens for events from the Game Table via an AMQP enabled queue service (namely RabbitMQ)
* conversely, `game-of-three-server` accepts commands (as a Game Table) via a Rest/Json API, and dispatches game events to the players via the aforementioned queue

One might argue that also the communication from the Players to the Game Table could go through the queue: it could be so, but for the time being, we assume that the Game Table is supposed to be always online and therefore it is enough to use the Rest API.

## UML

This is the - simplified - Class Diagram: ![Class Diagram](uml/class_diagram.png?raw=true "Class Diagram")

Lots of details and connections are missing: it is just meant to give an overview of the operations and relationships between the main domain classes.

## Implementation Details

The `game-of-three-server` has been implemented as a [Dropwizard](https://www.dropwizard.io) application; its API has a [Swagger](https://swagger.io/) end-point, and therefore the client is automatically generated via [`swagger-codegen`](https://github.com/swagger-api/swagger-codegen).

Several patterns have been employed in the design and implementation: decorator, strategy, factory, observer...

TDD has been employed to develop the core part of the system (with the help of mock objects - via the Mockito library).

# Build

Just run `mvn clean package`: a shaded jar will be built, and a **deb** package as well, suitable to be installed in an Ubuntu system (tested on Ubuntu 16.04.6 LTS).

## Generate the Rest Client

If you modify the Rest Apis, in order to re-generate the Rest client proceed as follow:

* checkout [`swagger-codegen`](https://github.com/swagger-api/swagger-codegen) and build it
* run the server via your IDE
* execute the following command (set the `<path_swagger_codegen>`, and change the port and host name if needed):

```
java -Dapis -DsupportingFiles -jar <path_swagger_codegen>\swagger-codegen-cli.jar generate -i http://127.0.0.1:8700/game_of_three_rest_api/swagger.json -l java --invoker-package kata.game_of_three.rest_api.client --api-package kata.game_of_three.rest_api.client --model-package kata.game_of_three.rest_api.client --library okhttp-gson --import-mappings Move=kata.game_of_three.Move,PlayerInvitation=kata.game_of_three.PlayerInvitation
```

Afterwards:

* in `kata.game_of_three.rest_api.client.GameofthreegametableApi.invitePlayerCall` modify variable `localVarAccepts` by adding `"application/json"`
* revert `.gitignore`

# Provisioning and deploy

Two basic Ansible scripts have been authored in order to:

* configure a build server
* configure a "production" server

You can customize:

* your inventory
* the `game-of-three-server` environment variables (e.g. the RabbitMQ host and port), by creating/modifying the `*_env.sh` file under `.ansible/playbooks/files/game-of-three/`
	* you can find the variables to be customized in the `./game_of_three_server.yml` Dropwizard configuration file

## Configuration

Obviously, both the Game Table server and the Players must use the very same RabbitMQ server.

A future possible improvement could aim at avoiding to configure the RabbitMQ server on the Players and let them refer only the Game Table server.

# `play-game-of-three` synopsis

In order to get help about the command, just run `play-game-of-three --help`.

Examples:

* two "human" players
	* Player "david" - starts the Game: `play-game-of-three --playerId david --opponentId goliath --gameTableRestApiUrl http://127.0.0.1:8700/game_of_three_rest_api --rabbitMQHost 127.0.0.1`
	* Player "goliath" - waits for an invitation to the Game: `play-game-of-three --playerId goliath --gameTableRestApiUrl http://127.0.0.1:8700/game_of_three_rest_api --rabbitMQHost 127.0.0.1`
* autonomous player
	* just use the `--autoPlay` flag
	* you can play any kinds of combinations - human vs auto, auto vs auto, human vs human
* set inception
	* just use the `--inception` parameter
* consider that a player that is started as a "waiter" can be invitated to play *multiple games* at the same time