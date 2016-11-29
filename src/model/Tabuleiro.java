package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import communication.Comandos;

public class Tabuleiro extends Thread{

	private ArrayList<Peca> pecas;
	private ImageIcon espacoLivre = new ImageIcon("C:\\Users\\Desenvolvimento\\workspace\\Shisima\\imagens\\EspacoLivre.jpg");
	private ImageIcon pecaPreta   = new ImageIcon("C:\\Users\\Desenvolvimento\\workspace\\Shisima\\imagens\\PecaPreta.jpg");
	private ImageIcon pecaBranca  =new ImageIcon("C:\\Users\\Desenvolvimento\\workspace\\Shisima\\imagens\\PecaBranca.jpg");
	private Usuario user;
	private Socket socket;
	private static DataOutputStream ostream = null;
	private DataInputStream istream  = null;
	private String comando;
	private boolean moverPeca;
	private JLabel labelUserPontos;
	private JLabel labelAdvPontos = new JLabel("0");
	private int advPontos;
	
	public Tabuleiro(Usuario user, Socket socket){
		pecas = new ArrayList<>();
		moverPeca = false;
		this.user = user;
		this.socket = socket;
		setLabelUserPontos(new JLabel("0"));
		setLabelAdvPontos(new JLabel("0"));
		setAdvPontos(0);
//		
		
		try {	
			ostream = new DataOutputStream(socket.getOutputStream());
		    istream = new DataInputStream(socket.getInputStream());
		    
		    this.start();
		    
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	 public void run(){
		    try {
		      while(true){
		        comando = istream.readUTF();
		        ExecutaComando(comando);
		        user.setTurno(true);
		        Turno();
		      }
		    } catch(Exception e) {
		      System.out.println(e);
		    }
		  }
	
	public ArrayList<Peca> getPecas() {
		return pecas;
	}

	public void setPecas(ArrayList<Peca> pecas) {
		this.pecas = pecas;
	}

	public JLabel getLabelUserPontos() {
		return labelUserPontos;
	}

	public void setLabelUserPontos(JLabel labelUserPontos) {
		this.labelUserPontos = labelUserPontos;
	}

	public JLabel getLabelAdvPontos() {
		return labelAdvPontos;
	}

	public void setLabelAdvPontos(JLabel labelAdvPontos) {
		this.labelAdvPontos = labelAdvPontos;
	}

	public Usuario getUser(){
		return user;
	}
	public void setUser(Usuario user) {
		this.user = user;
	}

	public int getAdvPontos() {
		return advPontos;
	}

	public void setAdvPontos(int advPontos) {
		this.advPontos = advPontos;
	}

	public Peca CriaPeca(int id, int status){
		Peca nova_peca = new Peca(id,status);
		EventoPeca(nova_peca);
		
		if(status == 0)
			nova_peca.setIcon(espacoLivre);
		else if(status == 1)
			nova_peca.setIcon(pecaBranca);
		else
			nova_peca.setIcon(pecaPreta);
		
		pecas.add(nova_peca);
		return nova_peca;
	}
	
	private void DisabilitaPecas(){
		for(int i = 0; i < 9; i++){
			pecas.get(i).setEnabled(false);
		}
	}
	
	public void ChecaVizinhos(int peca_id){
		for (Integer peca : pecas.get(peca_id).getVizinhos()) {
			if(pecas.get(peca.intValue()).getStatus() == 0)
				pecas.get(peca.intValue()).setEnabled(true);
			else
				pecas.get(peca.intValue()).setEnabled(false);
		}
	}
	
	public void HabilitaTabuleiro(){
		DisabilitaPecas();
		for (Peca peca : pecas) {
			if(peca.getStatus() == user.getTipo_pecas()) //Mudar ##########################################
				peca.setEnabled(true);
		}
	}
	
	public void Turno(){
		if(user.isTurno()){
			DisabilitaPecas();
			HabilitaTabuleiro();
		}
		else
			DisabilitaPecas();
	}
	
	private void EventoPeca(Peca nova_peca){
		nova_peca.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(moverPeca == false){
					nova_peca.setSelecionada(true);
					DisabilitaPecas();
					nova_peca.setEnabled(true);
					ChecaVizinhos(nova_peca.getId());
					moverPeca = true;
				}
				else{
					if(nova_peca.isSelecionada()){
						nova_peca.setSelecionada(false);
						moverPeca = false;
						HabilitaTabuleiro();
					}
					else{
						int posicao_velha = Mover(nova_peca.getId());
						String comando = Comandos.mover + posicao_velha + " " +nova_peca.getId();
						EnviaComando(comando);
						HabilitaTabuleiro();
						user.setTurno(false);
						moverPeca = false;
						Turno();
						Venceu();
					}
				}
			}
		});
	}
	
	private int Mover(int nova_posicao){
		int id = -1;
		for (Peca peca : pecas) {
			if(peca.isSelecionada()){
				peca.setSelecionada(false);
				peca.setStatus(0);
				peca.setIcon(espacoLivre);
				id = peca.getId();
				break;
			}
		}
		
		pecas.get(nova_posicao).setStatus(user.getTipo_pecas());
		
		if(user.getTipo_pecas() == 1)
			pecas.get(nova_posicao).setIcon(pecaBranca);
		else
			pecas.get(nova_posicao).setIcon(pecaPreta);
		
		
		return id;
	}
	
	private String RecuperaComando(String informacao){
		String comando = "";
		
		for(int i=0; i < informacao.length(); i++){
			comando += informacao.charAt(i);
			if(informacao.charAt(i) == ' ')
				return comando;
		}
		return comando;
	}
	private void ExecutaComando(String informacao){
		String comando = RecuperaComando(informacao);
		if(comando.equals(Comandos.mover)){
			String posicoes  = informacao.replace(Comandos.mover, ""); // Retira o comando sobrando apenas as posicoes.
			
			//Conversao automatica pelo compilador.
			int posicao_velha = Integer.parseInt(String.valueOf(posicoes.charAt(0))); //Posicao onde a peca estava.
			int posicao_nova  = Integer.parseInt(String.valueOf(posicoes.charAt(2))); // Nova Posicao.
			
			pecas.get(posicao_velha).setStatus(0);
			pecas.get(posicao_velha).setIcon(espacoLivre);
			
			if(user.getTipo_pecas() == 1){
				pecas.get(posicao_nova).setIcon(pecaPreta);
				pecas.get(posicao_nova).setStatus(2);
			}
			else{
				pecas.get(posicao_nova).setIcon(pecaBranca);
				pecas.get(posicao_nova).setStatus(1);
			}
		}
		else if(comando.equals(Comandos.venceu)){
			JOptionPane.showMessageDialog(null, "Você Perdeu :(");
			setAdvPontos(getAdvPontos() + 1);
			labelAdvPontos.setText(Integer.toString(getAdvPontos()));
			Reiniciar();
		}
		
		else if(comando.equals(Comandos.reiniciar)){
			user.setTurno(true);
			Reiniciar();
		}
		
		else if(comando.equals(Comandos.desistir)){
			Reiniciar();
			user.setPontos(user.getPontos()+1);
			AtualizaPlacar();
		}
		
		else if(comando.equals(Comandos.sair)){
			JOptionPane.showMessageDialog(null, "Adversário saiu do jogo");
			System.exit(0);
		}
		
	}
	
	public void EnviaComando(String comando){
		try {
			
			ostream.writeUTF(comando);
	        ostream.flush();
	        
		} catch (Exception e) {
			// TODO: handle exception
		}	
	}
	
	private void Venceu(){
		if(pecas.get(0).getStatus() != 0){
			
			if((pecas.get(1).getStatus() == pecas.get(0).getStatus() && pecas.get(5).getStatus() == pecas.get(0).getStatus()) 
					|| (pecas.get(3).getStatus() == pecas.get(0).getStatus() && pecas.get(7).getStatus() == pecas.get(0).getStatus())
					|| (pecas.get(2).getStatus() == pecas.get(0).getStatus() && pecas.get(6).getStatus() == pecas.get(0).getStatus())
					|| (pecas.get(4).getStatus() == pecas.get(0).getStatus() && pecas.get(8).getStatus() == pecas.get(0).getStatus())){
				JOptionPane.showMessageDialog(null, "Você Ganhou :)");
				EnviaComando(Comandos.venceu);
				int pontos = user.getPontos() + 1;
				user.setPontos(pontos);
				//labelUserPontos.setText(Integer.toString(pontos));
				AtualizaPlacar();
				Reiniciar();
				
			}
		
		}
	}
	
	public void Reiniciar(){
		for (Peca peca : pecas) {
			if(peca.getId() == 1 || peca.getId() == 2 || peca.getId() == 8){
				peca.setStatus(1);
				peca.setIcon(pecaBranca);
			}
			else if(peca.getId() == 4 || peca.getId() == 5 || peca.getId() == 6){
				peca.setStatus(2);
				peca.setIcon(pecaPreta);
			}
			else{
				peca.setStatus(0);
				peca.setIcon(espacoLivre);
			}
			
			peca.setSelecionada(false);
			
		}
		
		moverPeca = false;
		DisabilitaPecas();
		Turno();
	}
	
	public void AtualizaPlacar(){
		labelAdvPontos.setText(Integer.toString(advPontos));
		labelUserPontos.setText(Integer.toString(user.getPontos()));
	}
}
