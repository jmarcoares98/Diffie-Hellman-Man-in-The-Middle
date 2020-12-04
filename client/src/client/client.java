package client;
import java.net.*; 
import java.io.*; 

public class client {
	public static void main(String[] args) throws IOException
	{
		try {
			// define variable
			int port = 8088;
			String serverName = "localhost";
			String Pstr, Gstr, dataStr;
			
			int P = 25, G = 13, privateKey = 4;
			double secretKey, serverPublicKey, sendData;

			// Established the connection 
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
		}

		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
