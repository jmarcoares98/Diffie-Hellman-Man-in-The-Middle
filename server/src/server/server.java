package server;
import java.net.*; 
import java.io.*; 

public class server {
	public static void main(String[] args) throws IOException
	{
		try {
			// define variables
			int port = 8088;
			int privateKey = 3;
			double clientP, clientG, clientPublicKey, sendData, secretKey;
			String dataStr;
			
			// established the connection
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("Waiting for client..." + serverSocket.getLocalPort() + "...");
			
			Socket server = serverSocket.accept();
			System.out.println("Connected to" + server.getRemoteSocketAddress());
			
			// server private key
			System.out.println("From Server : Private Key = " + privateKey); 

			// accepting data
			DataInputStream in = new DataInputStream(server.getInputStream()); 
			  
	        clientP = Integer.parseInt(in.readUTF()); // to accept client 
	        System.out.println("From Client : P = " + clientP); 
	  
	        clientG = Integer.parseInt(in.readUTF()); // to accept client
	        System.out.println("From Client : G = " + clientG); 
	  
	        clientPublicKey = Double.parseDouble(in.readUTF()); // to accept publicKey
	        System.out.println("From Client : Public Key = " + clientPublicKey);
	        
	        // calculate the data that will be sent to client
	        sendData = ((Math.pow(clientG, privateKey)) % clientP);
	        // convert it to string
	        dataStr = Double.toString(sendData);
	        
	        // send to client
	        OutputStream output = server.getOutputStream();
	        DataOutputStream out = new DataOutputStream(output);
	        
	        // send data
	        out.writeUTF(dataStr);
	        
	        // calculate secret key
	        secretKey = ((Math.pow(clientPublicKey, privateKey)) % clientP);
	        
	        System.out.println("Secret Key : " + secretKey);
	        
			server.close();
			serverSocket.close();
			in.close();
		}
		
		catch(SocketTimeoutException s) {
			System.out.println("Socket timed out!");
		}
		catch(IOException e) {
		}
		
	}
}
