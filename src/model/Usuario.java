package model;

public class Usuario {
	private String nome;
	private int tipo_pecas;
	private int pontos;
	private boolean turno;
	
	public Usuario(String nome, int tipo_pecas, int pontos){
		this.nome = nome;
		this.tipo_pecas = tipo_pecas;
		this.pontos = pontos;
	}
	
	public int getTipo_pecas() {
		return tipo_pecas;
	}
	public void setTipo_pecas(int tipo_pecas) {
		this.tipo_pecas = tipo_pecas;
	}
	public int getPontos() {
		return pontos;
	}
	public void setPontos(int pontos) {
		this.pontos = pontos;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isTurno() {
		return turno;
	}

	public void setTurno(boolean turno) {
		this.turno = turno;
	}
}
