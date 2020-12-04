# Diffie-Hellman-Man-in-The-Middle
In this project, I first created a server and client in java that interacts with each other through the Diffie-Hellman Key Exchange. They would then generate their own secret keys with their own public keys(P & G) and their own private keys. I have implement my server.java and client.java from GeeksforGeeks.

https://www.geeksforgeeks.org/implementation-diffie-hellman-algorithm/

I then added a Man in the Middle Attack. The Diffie-Hellman is vulnerable to this because it knows the algorithm being used between the exchanges. When the server is running and waiting for a client, the maninthemiddle.java would then intercept the following and would then sabotage the exchange, creating its own private key between the server. And then it would create a fake server that is running for the client to have an exchange with and generate another set of different keys.
