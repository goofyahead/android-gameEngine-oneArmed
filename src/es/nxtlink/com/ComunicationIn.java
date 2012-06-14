package es.nxtlink.com;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import es.nxtlink.modelos.Elemento;
import es.nxtlink.persistence.ObjectMapper;

import android.util.Log;

public class ComunicationIn extends Thread{
	private String received;
	private String [] coords;
	private Elemento contrario = ObjectMapper.getContrario();
	
	@Override
	public void run() {
		
		DatagramSocket mySocket = null;
		
		try {
			
			mySocket = new DatagramSocket(4451);
			
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		Log.d("MI_DEP", "Activo! esperando...");

		byte [] buf = new byte [10];
		
		DatagramPacket packet = new DatagramPacket(buf, buf.length);
		
		while (true){
		try {
			mySocket.receive(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		received =  new String (packet.getData(),0, packet.getLength());
		
		coords = received.split(":");
		
		contrario.setX(Integer.parseInt(coords[0]));
		contrario.setY(Integer.parseInt(coords[1]));
		
		Log.d("MI_DEP", "Contenido: " + received );
		
		Log.d("MI_DEP", "Ip: " + packet.getAddress().getHostAddress());

		}

	}
 
	
}
