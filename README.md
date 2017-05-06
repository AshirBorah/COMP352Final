## Final Project for CS352: Computer Networks
**Authors** : Ashir Borah and Daniel Ngo  
Parts of code used for COMP 352: Computer Networks (2017, Dickinson College)  
**Instructor** : Professor Farhan Siddiqui</center>


### Introduction
Hangman is a classic word guessing game in which a player has a set amount of chances to guess the secret word before their turn ends. The less the number of chances to guess correctly, the harder the game gets.

### Project Details
The project uses concepts taught in the course and because of the nature of the game chooses to use the client server model to implement the game. The server provides the clients with words and maintains the score board of the game.

### Class functionality
Please refer to the individual classes for detailed comments. In general, the following classes are used:
- **WebServer**: The class implements the multi-threaded server and allows more than one client to connect to simultaneously play the game.
- **Client**: The class implements the core functionality of the game and takes care of the client side processing like running the game or updating the server with the score of the player for the round. It also plays sounds according to the outcome of the round.
- **HttpRequest**: This class takes care of the communication between the client and the server and updates the server's name database and the score board. It also provides the client with words

### Message exchange
The message exchange takes place between the server and the clients using Strings. The client sends the server two kinds of messages.
- A name registration: This message starts with the character *n* and the has the name of the player.

- Score update: The client sends this message by just sending the server an integer. This integer in string format is the score of the player for the given round and the corresponding enter in the scoreboard is updated.

The server communicates with the client by sending it the word to guess followed by the total score board all as a single string. This is sent in the following format:
word p1name p1score p2name p2score......p<sub>N</sub>score p<sub>N</sub>Score
The client uses the word for the round and prints the scoreboard.

The protocols used is TCP/IP protocol.


### Usage
The project in its current state runs from the command line/Terminal. The server needs to be started before running the clients. The usages is:
- Client: webserver address webserver port number
- Server: port number

Note: The sound effects and the wordlist were taken from the Internet and the authors do not claim authorship of the same.
