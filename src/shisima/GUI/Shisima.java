package shisima.GUI;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import communication.Comandos;
import model.Peca;
import model.Tabuleiro;
import model.Usuario;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

import javax.swing.UIManager;

public class Shisima extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldChat;
	private JPanel panelChat;
	private JTextArea areaChat;
	private JButton btnEnviar;
	private Tabuleiro tab;
	private JPanel panelTabuleiro;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//Shisima frame = new Shisima();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Shisima(Usuario user, Socket socket) {
		
		tab = new Tabuleiro(user, socket);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 667, 332);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel labelPlacar = new JLabel("Voc\u00EA    X    Advers\u00E1rio");
		labelPlacar.setFont(new Font("Tahoma", Font.BOLD, 16));
		labelPlacar.setBounds(189, 11, 178, 14);
		contentPane.add(labelPlacar);
		/*
		JLabel userPontos = new JLabel("0");
		userPontos.setFont(new Font("Tahoma", Font.PLAIN, 16));
		userPontos.setBounds(199, 34, 22, 14);
		contentPane.add(userPontos);
		
		JLabel advPontos = new JLabel("0");
		advPontos.setFont(new Font("Tahoma", Font.PLAIN, 16));
		advPontos.setBounds(291, 36, 22, 14);
		contentPane.add(advPontos);*/
		
		CriaPainelChat();
		CriaTabuleiro();
		CriaPainelMenu();
		tab.HabilitaTabuleiro();
		tab.Turno();
		
		tab.getLabelUserPontos().setFont(new Font("Tahoma", Font.PLAIN, 16));
		tab.getLabelUserPontos().setBounds(199, 34, 22, 14);
		contentPane.add(tab.getLabelUserPontos());
		
		tab.getLabelAdvPontos().setFont(new Font("Tahoma", Font.PLAIN, 16));
		tab.getLabelAdvPontos().setBounds(291, 36, 22, 14);
		contentPane.add(tab.getLabelAdvPontos());
	}
	
	private void CriaPainelChat(){
		panelChat = new JPanel();
		panelChat.setBounds(428, 11, 223, 283);
		contentPane.add(panelChat);
		panelChat.setLayout(null);
		
		areaChat = new JTextArea();
		areaChat.setBackground(UIManager.getColor("Button.highlight"));
		areaChat.setBounds(0, 37, 223, 201);
		panelChat.add(areaChat);
		
		textFieldChat = new JTextField();
		textFieldChat.setBounds(0, 252, 142, 31);
		panelChat.add(textFieldChat);
		textFieldChat.setColumns(10);
		
		btnEnviar = new JButton();
		btnEnviar.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnEnviar.setText("Enviar");
		btnEnviar.setBounds(142, 249, 81, 34);
		panelChat.add(btnEnviar);
		
		JLabel lblChat = new JLabel("Chat");
		lblChat.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblChat.setBounds(96, 12, 46, 14);
		panelChat.add(lblChat);
	}
	
	private void CriaPainelMenu(){
		
		JPanel panelMenu = new JPanel();
		panelMenu.setBackground(UIManager.getColor("Button.darkShadow"));
		panelMenu.setBounds(0, 48, 99, 246);
		
		contentPane.add(panelMenu);
		panelMenu.setLayout(null);
		
		JButton btnReiniciar = new JButton("Reiniciar");
		btnReiniciar.setBounds(0, 32, 89, 23);
		EventoBtnReiniciar(btnReiniciar);
		panelMenu.add(btnReiniciar);
		
		JButton btnDesistir = new JButton("Desistir");
		btnDesistir.setBounds(0, 66, 89, 23);
		EventoBtnDesistir(btnDesistir);
		panelMenu.add(btnDesistir);
		
		JButton btnSair = new JButton("Sair");
		btnSair.setBounds(0, 102, 89, 23);
		EventoBtnSair(btnSair);
		panelMenu.add(btnSair);
		
		JLabel labelMenu = new JLabel("Menu");
		labelMenu.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelMenu.setBounds(0, 7, 46, 14);
		panelMenu.add(labelMenu);
	}
	
	private void CriaTabuleiro(){
		
		panelTabuleiro = new JPanel();
		panelTabuleiro.setBackground(new Color(51, 204, 204));
		panelTabuleiro.setBounds(99, 48, 328, 246);
		contentPane.add(panelTabuleiro);
		panelTabuleiro.setLayout(null);
		
		Peca btnPeca0 = tab.CriaPeca(0, 0);
		btnPeca0.setBounds(129, 108, 45, 48);
		panelTabuleiro.add(btnPeca0);
		
		Peca btnPeca1 = tab.CriaPeca(1, 1);
		btnPeca1.setBounds(129, 11, 45, 48);
		panelTabuleiro.add(btnPeca1);
		
		Peca btnPeca2 = tab.CriaPeca(2, 1);
		btnPeca2.setBounds(186, 54, 45, 48);
		panelTabuleiro.add(btnPeca2);
		
		Peca btnPeca3 = tab.CriaPeca(3, 0);
		btnPeca3.setBounds(235, 108, 45, 48);
		panelTabuleiro.add(btnPeca3);
		
		Peca btnPeca4 = tab.CriaPeca(4, 2);
		btnPeca4.setBounds(186, 157, 45, 48);
		panelTabuleiro.add(btnPeca4);
		
		Peca btnPeca5 = tab.CriaPeca(5, 2);
		btnPeca5.setBounds(129, 197, 45, 48);
		panelTabuleiro.add(btnPeca5);
		
		Peca btnPeca6 = tab.CriaPeca(6, 2);
		btnPeca6.setBounds(64, 157, 45, 48);
		panelTabuleiro.add(btnPeca6);
		
		Peca btnPeca7 = tab.CriaPeca(7, 0);
		btnPeca7.setBounds(23, 108, 45, 48);
		panelTabuleiro.add(btnPeca7);
		
		Peca btnPeca8 = tab.CriaPeca(8, 1);
		btnPeca8.setBounds(64, 54, 45, 48);
		panelTabuleiro.add(btnPeca8);
	}
	
	public void EventoBtnReiniciar(JButton btnReiniciar){
		btnReiniciar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(tab.getUser().isTurno()){
					tab.getUser().setTurno(false);
					tab.Reiniciar();
					tab.EnviaComando(Comandos.reiniciar);
				}
				else
					JOptionPane.showMessageDialog(null, "Espere seu turno para reiniciar");
			}
		});	
	}
	
	public void EventoBtnDesistir(JButton btnDesistir){
		btnDesistir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(tab.getUser().isTurno()){
					tab.getUser().setTurno(false);
					tab.Reiniciar();
					tab.setAdvPontos(tab.getAdvPontos()+1);
					tab.AtualizaPlacar();
					tab.EnviaComando(Comandos.desistir);
				}
				else
					JOptionPane.showMessageDialog(null, "Espere seu turno para desistir da partida");
			}
		});
	}
	
	public void EventoBtnSair(JButton btnSair){
		btnSair.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				tab.EnviaComando(Comandos.sair);
				System.exit(0);
			}
		});
	}
}
