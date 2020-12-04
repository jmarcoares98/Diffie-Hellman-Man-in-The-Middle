import java.net.*; 
import java.io.*; 

public class maninthemiddle {
    public static void main(String[] args) throws IOException
    {
        try{
        int port = 8088;
        String serverName = "localhost";
        String Pstr, Gstr, dataStr;
        int P = 23, G = 15, privateKey = 5;
        double secretKey, serverPublicKey, sendData;
        double clientP, clientG, clientPublicKey;
        
        //Client...
        // Established the connection 
        System.out.println("MAN IN THE MIDDLE (CLIENT)"); 
        System.out.println("Connecting to " + serverName + " on port " + port); 
							   
        Socket client = new Socket(serverName, port); 
        System.out.println("Just connected to " + client.getRemoteSocketAddress()); 

        // send data to client
        OutputStream outputServer = client.getOutputStream();
        DataOutputStream out = new DataOutputStream(outputServer);

        // sending data P
		Pstr = Integer.toString(P);
		out.writeUTF(Pstr);

		// sending data G
		Gstr = Integer.toString(G);
        out.writeUTF(Gstr);
        
        // calculate what to send
		sendData = ((Math.pow(G, privateKey)) % P);
		dataStr = Double.toString(sendData);
        out.writeUTF(dataStr);
        
        // Clients private key
		System.out.println("From Client : Private Key = " + privateKey);

		// Accept the data from server
		DataInputStream in = new DataInputStream(client.getInputStream());
		serverPublicKey = Double.parseDouble(in.readUTF());
		System.out.println("From Server : Public Key = " + serverPublicKey);

		// calculate secret key
		secretKey = ((Math.pow(serverPublicKey, privateKey)) % P);

		System.out.println("Secret Key : " + secretKey); 
        client.close();
        in.close();

        // Server...
        // established the connection
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("MAN IN THE MIDDLE (SERVER)"); 
		System.out.println("Waiting for client..." + serverSocket.getLocalPort() + "...");
			
		Socket server = serverSocket.accept();
		System.out.println("Connected to" + server.getRemoteSocketAddress());
			
		// server private key
		System.out.println("From Server : Private Key = " + privateKey); 

		// accepting data
        DataInputStream in2 = new DataInputStream(server.getInputStream());
        
        clientP = Integer.parseInt(in2.readUTF()); // to accept client 
	    System.out.println("From Client : P = " + clientP); 
	  
	    clientG = Integer.parseInt(in2.readUTF()); // to accept client
        System.out.println("From Client : G = " + clientG); 
	  
	    clientPublicKey = Double.parseDouble(in2.readUTF()); // to accept publicKey
	    System.out.println("From Client : Public Key = " + clientPublicKey);
	        
        // calculate the data that will be sent to client
	    sendData = ((Math.pow(clientG, privateKey)) % clientP);
        // convert it to string
	    dataStr = Double.toString(sendData);
	        
	    // send to client
	    OutputStream output = server.getOutputStream();
	    DataOutputStream out2 = new DataOutputStream(output);
	        
	    // send data
	    out2.writeUTF(dataStr);
	        
	    // calculate secret key
	    secretKey = ((Math.pow(clientPublicKey, privateKey)) % clientP);
	        
	    System.out.println("Secret Key : " + secretKey);
	        
        server.close();
        }

        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
