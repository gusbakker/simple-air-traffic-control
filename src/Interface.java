import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;

public class Interface {

	public Board board;
	private JFrame frame;
	public JTextField text;
	public JTextField text1, text2, text3, text4, text5;
	private JButton start, stop;
	// for the chat room


	
	private Comunicador comunicador;

	public Interface(Comunicador comunicador) {

		
		this.comunicador = comunicador;
		board = new Board(Jogo.NUN_LIN, Jogo.NUM_COL);
		frame = new JFrame("Aeroporto");
		Container c = frame.getContentPane();
		Border blackline = BorderFactory.createLineBorder(Color.black);
		

		
		//Sul
		JPanel southPanel = new JPanel();
		start = new JButton("Start");
		stop = new JButton("Stop");

		southPanel.add(start);
		southPanel.add(stop);
		c.add(southPanel, BorderLayout.SOUTH);
		
		//Painel Lateral
		JPanel lateralPanel = new JPanel(new GridLayout(4,1));
		c.add(lateralPanel, BorderLayout.EAST);
		
		JPanel pontuacao = new JPanel(new GridLayout(4,2));
		pontuacao.setBorder(BorderFactory.createTitledBorder("Pontuação"));
		text = new JTextField();
		pontuacao.add(new JLabel("Pontos: "));
		pontuacao.add(text);
		lateralPanel.add(pontuacao);
			
		JPanel infoAviao = new JPanel(new GridLayout(5,2));
		infoAviao.setBorder(BorderFactory.createTitledBorder("Info Avião"));
		text1 = new JTextField();
		text2 = new JTextField();
		text3 = new JTextField();
		text4 = new JTextField();
		text5 = new JTextField();
		infoAviao.add(new JLabel("ID: "));
		infoAviao.add(text1);
		infoAviao.add(new JLabel("Direçao: "));
		infoAviao.add(text2);
		infoAviao.add(new JLabel("Destino: "));
		infoAviao.add(text3);
		infoAviao.add(new JLabel("Nº Passageiros: "));
		infoAviao.add(text4);
		infoAviao.add(new JLabel("Combustivel: "));
		infoAviao.add(text5);
		lateralPanel.add(infoAviao);
		
		
		//Centro
		board.setBorder(blackline);
		c.add(board, BorderLayout.CENTER);
		board.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent arg0) {
				// quadro.setFin(e.getPoint());
				
			}
			
			public void mousePressed(MouseEvent arg0) {
				// quadro.setInit(e.getPoint());
				
			}
			
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			public void mouseClicked(MouseEvent e) {
				Interface.this.comunicador.jogo.setSelected(e.getPoint());
				
			}
		});
		
		
		start.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				Interface.this.comunicador.JogoStarted(true);
				
			}
		});
		
	stop.addActionListener(new ActionListener() {
		
		public void actionPerformed(ActionEvent arg0) {
			Interface.this.comunicador.JogoStarted(false);
			
		}
	});

		
		frame.setSize(1200, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void init() {
		frame.setVisible(true);
	}


}
