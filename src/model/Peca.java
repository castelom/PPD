package model;

import java.util.ArrayList;

import javax.swing.JButton;

public class Peca extends JButton{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int status; // 0 = vazio, 1 = peca pertence ao user 1, 2 = peca pertence ao user 2.
	private ArrayList<Integer> vizinhos;
	private boolean selecionada;
	
	public Peca(int id, int status){
		this.id = id;
		this.status = status;
		selecionada = false;
		vizinhos = new ArrayList<>();
		
		if(id != 0){
			vizinhos.add(0); // Peca do centro e vizinho de todos.
			if(id-1 <= 0)
				vizinhos.add(8);
			else
				vizinhos.add(id-1);
			if(id+1 >=9)
				vizinhos.add(1);
			else
				vizinhos.add(id+1);
		}
		else{
			for(int i = 1; i < 8; i++){
				vizinhos.add(i);
			}
		}
			
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public ArrayList<Integer> getVizinhos() {
		return vizinhos;
	}
	public void setVizinhos(ArrayList<Integer> vizinhos) {
		this.vizinhos = vizinhos;
	}

	public boolean isSelecionada() {
		return selecionada;
	}

	public void setSelecionada(boolean selecionada) {
		this.selecionada = selecionada;
	}
	
}
