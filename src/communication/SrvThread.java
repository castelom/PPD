package communication;

import java.net.ServerSocket;
import java.net.Socket;

import model.Usuario;
import shisima.GUI.Shisima;

public class SrvThread extends Thread{
	ServerSocket serverSocket = null;
	  Socket socket = null;
	  static int port = 9090;
	  
	  SrvThread(){
		
		
	    try {
	      serverSocket = new ServerSocket(port);
	      System.out.println("Aguardando conexão...");
	      socket = serverSocket.accept();
	      System.out.println("Conexão Estabelecida.");
	      
	      this.start();
	      
	      Usuario user1 = new Usuario("usuario1", 1, 0);
	      user1.setTurno(true);
	  	  Shisima jogo = new Shisima(user1, socket);
	  	  jogo.setVisible(true);
	    } 
	    
	    catch(Exception e){
	      System.out.println(e);
	    }
	  }

	  public static void main(String args[]){
	    new SrvThread();
	  }
}

