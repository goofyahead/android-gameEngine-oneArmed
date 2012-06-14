package es.nxtlink.com;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ComunicationOut extends Thread {

	@Override
	public void run() {
		  DatagramSocket socket = null;
	        InetAddress serverAdress = null;
	        
	        try {
	        	socket = new DatagramSocket();
				
			} catch (SocketException e) {
			
				e.printStackTrace();
			}
			
			String hola = "hola androide";
			
			byte buf[] = hola.getBytes();
			
			int port = 4451;
			
			try {
				 serverAdress = InetAddress.getByName("192.168.43.145");	
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}		
			DatagramPacket packete = new DatagramPacket(buf, buf.length,serverAdress, port);
			try {
				socket.send(packete);
			} catch (IOException e) {	
				e.printStackTrace();
			}
	}

}
