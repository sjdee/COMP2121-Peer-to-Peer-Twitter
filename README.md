# COMP2121-Peer-to-Peer-Twitter

A distributed version of twitter based in Java where different nodes, local or remote, can communicate with each other i.e. whenever one node or user makes a "status update" the other nodes/users receive an update and the update is printed on the screen. Moreover, the application is robust can handle the termination or instantation of nodes in the ecosystem i.e. if a user goes offline, this can be detected by other peers as they are continuously exchanging the status. The status of offline user will be set to idle after 10 seconds on other peers' machines. The status update of the offline user will be removed after 20 seconds.

The project is a customised P2P Twitter network running on UDP but with built-in mechanisms to ensure in-order delivery and integrity of messages.

## How to Compile and run the Code?
### Inside command line, type
```
javac -cp . *.java
java P2PTwitter test1111
```
*Notice 'test1111' is an arbitrary identifier given by you.
*This needs to be the same as one of the unikeys that are listed in the participants.properties file.

## How to test the code?
The P2PTwitter consists of a **client thread** and a **server thread**.

The **server thread** will run in the background when the program starts.

The **client thread** will continuously prompt the user to enter his/her status and then send it to all the peers.

To test the code you will need more than 1 PC under your control.

### 1) Create your own "participants.properties" file.

Follow the template given below:
```
# participants.properties

participants=peer1,peer2

peer1.ip=127.0.0.1
peer1.pseudo=test1
peer1.unikey=test1111
peer1.port=7014

peer2.ip=127.0.0.1
peer2.pseudo=test2
peer2.unikey=test2222
peer2.port=7015
```
### 2) Set up the peers
Open two terminal windows and navigate to the directory of the program.
In the first terminal, type
```
java P2PTwitter test2222
```
In the second terminal, type
```
java P2PTwitter test1111
```
In both windows, you will see a prompt from the program as follows:
```
Status:
```
### 3) Test empty status and status that exceed the maximum length(140 characters)
In one of the terminals, click return / enter key straightaway.
You should see this:
```
Status:
Status is empty. Retry.
```
Type a message that is longer than 140 characters. You should get:
```
Status: 0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890
Status is too long, 140 characters max. Retry.
```
### 4) Test normal status
In terminal 1, type:
```
Status: Not too bad today.
```
Terminal 1 outputs:
```
Status: Not too bad today.
### P2P tweets ###
# [test1 (test1111) : not yet initialized]
# test2 (myself) : Not too bad today.
### End tweets ###
```
In terminal 2, type:
```
Status: It's ok.
```
Terminal 2 outputs:
```
Status: It's ok.
### P2P tweets ###
# test1 (myself) : It's ok.
# test2 (test2222) : Not too bad today.
### End tweets ###
```
### 5) Test status with colons
In terminal 1, type:
```
Status: ::This::is::a::status::with::a::lot::of::colons::
```
Terminal 1 outputs:
```
Status: ::This::is::a::status::with::a::lot::of::colons::
### P2P tweets ###
# test1 (test1111) : It's ok.
# test2 (myself) : ::This::is::a::status::with::a::lot::of::colons::
### End tweets ###
```
In terminal 2, type:
```
Status: fine.
```
Terminal 2 outputs:
```
Status: fine.
### P2P tweets ###
# test1 (myself) :fine.
# test2 (test2222) : ::This::is::a::status::with::a::lot::of::colons::
### End tweets ###
```
### 6) Test idle and disappear
In terminal 1, type:
```
Status: i am off now.
```
Terminal 1 outputs:
```
Status: i am off now.
### P2P tweets ###
# test1 (test1111) : fine.
# test2 (myself) : i am off now.
### End tweets ###
```
In terminal 2, type:
```
Status: ok.
```
Terminal 2 outputs:
```
Status: ok.
### P2P tweets ###
# test1 (myself) : ok.
# test2 (test2222) : i am off now.
### End tweets ###
```
Close the peer running in the first terminal by pressing **"Ctrl + c"**.
After 10 seconds, in terminal 2, type:
```
Status: fine
```
Terminal 2 outputs:
```
Status: fine
### P2P tweets ###
# test1 (myself) : fine
# [test2 (test2222) : idle]
### End tweets ###
```
After another10 seconds, in terminal 2, type:
```
Status: nvm
```
Terminal 2 outputs:
```
Status: nvm
### P2P tweets ###
# test1 (myself) : nvm
### End tweets ###
```
Notice the peer becomes idle after 10 seconds off-line and disappears after 20 seconds off-line
### 7) Test reappear
Restart the peer in the first terminal and input:
```
Status: I'm back.
```
Terminal 1 outputs:
```
Status: I'm back.
### P2P tweets ###
# test1 (test1111) : nvm
# test2 (myself) : I'm back.
### End tweets ###
```
In terminal 2 inputs:
```
Status: Good to hear that.
```
Terminal 2 outputs:
```
Status: Good to hear that.
### P2P tweets ###
# test1 (myself) : Good to hear that.
# test2 (test2222) : I'm back.
### End tweets ###
```
### 8) Test termination
Press **"Ctel + C"** in the first terminal and check the second terminal.
If the second terminal doesn't crash when the first peer is closed, the termination is successful.

Restart the peer in the first terminal and check the second terminal.
If the second terminal doesn't crash when the first peer is reopened, the restart is successful.
### 9) Test ordering
In the first terminal inputs the numbers from 1 to 10 as 10 status:
```
Status: 1
### P2P tweets ###
# test1 (test1111) : hi
# test2 (myself) : 1
### End tweets ###
Status: 2
### P2P tweets ###
# test1 (test1111) : hi
# test2 (myself) : 2
### End tweets ###
Status: 3
### P2P tweets ###
# test1 (test1111) : hi
# test2 (myself) : 3
### End tweets ###
Status: 4
### P2P tweets ###
# test1 (test1111) : hi
# test2 (myself) : 4
### End tweets ###
Status: 5
### P2P tweets ###
# test1 (test1111) : hi
# test2 (myself) : 5
### End tweets ###
Status: 6
### P2P tweets ###
# test1 (test1111) : hi
# test2 (myself) : 6
### End tweets ###
Status: 7
### P2P tweets ###
# test1 (test1111) : hi
# test2 (myself) : 7
### End tweets ###
Status: 8
### P2P tweets ###
# test1 (test1111) : hi
# test2 (myself) : 8
### End tweets ###
Status: 9
### P2P tweets ###
# test1 (test1111) : hi
# test2 (myself) : 9
### End tweets ###
Status: 10
### P2P tweets ###
# test1 (test1111) : hi
# test2 (myself) : 10
### End tweets ###
```
In the second terminal, type the following several times:
```
Status: hi
```
Terminal 2 outputs:
```
Status: hi
### P2P tweets ###
# test1 (myself) : hi
# test2 (test2222) : 10
### End tweets ###
Status: hi
### P2P tweets ###
# test1 (myself) : hi
# test2 (test2222) : 10
### End tweets ###
Status: hi
### P2P tweets ###
# test1 (myself) : hi
# test2 (test2222) : 10
### End tweets ###
```
This indicates when the peer receives a message of higher sequence number, the message is recorded.

If the message has a lower sequence number than the recorded message, the message is disregarded.
