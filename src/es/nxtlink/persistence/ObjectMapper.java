package es.nxtlink.persistence;

import java.util.LinkedList;
import java.util.List;

import es.nxtlink.modelos.Elemento;

public class ObjectMapper {
	
	private static List < Elemento > suelo = null;
	private static List < Elemento > objetos = null;
	private static List < Elemento > players = null;
	
	private static Elemento otroJugador;
	private static Elemento jugadorActual;
	
	public static List <Elemento> getSuelo(){
		if ( suelo == null){
			suelo = new LinkedList<Elemento>();
		}
		return suelo;
	}
	
	public static List <Elemento> getObjetos (){
		if (objetos == null){
			objetos = new LinkedList<Elemento>();
		}
		return objetos;
	}
	
	public static List <Elemento> getPlayers (){
		if (players == null){
			players = new LinkedList<Elemento>();
		}
		return players;
	}
	
	public static Elemento getContrario(){
		return otroJugador;
	}
	
	public static void addPlayer (Elemento player){
		if ( players == null){
			players = new LinkedList<Elemento>();
		}
		otroJugador = player;
		players.add(player);
	}
	
	public static void addElemenToFloor(Elemento floorTile){
		if ( suelo == null){
			suelo = new LinkedList<Elemento>();
		}
		suelo.add(floorTile);
	}
	
	public static void addObjects (Elemento objeto){
		if ( objetos == null){
			objetos = new LinkedList<Elemento>();
		}
		objetos.add(objeto);
	}
}
