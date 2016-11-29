package communication;

import java.net.Socket;

import model.Usuario;
import shisima.GUI.Shisima;

public class CliThread extends Thread{
	static String host = "";
	static int port = 9090;
	Socket socket = null;

	  CliThread(){
	    try {
	     socket = new Socket(host, port);
	     System.out.println("Conectado....");
	     this.start();
	     
	     
	     Usuario user2 = new Usuario("usuario2", 2, 0);
	     user2.setTurno(false);
	     Shisima jogo = new Shisima(user2, socket);
	 	 jogo.setVisible(true);
	 	 
	    } catch(Exception e) {System.out.println(e);}
	  }


	  public static void main(String args[]){
	    host = args.length == 0 ? "localhost" : args[0];
	    new CliThread(); 
	  }
}

