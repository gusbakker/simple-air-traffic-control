import java.awt.Point;
import java.util.LinkedList;

import javax.swing.*;

public class Aeroporto extends Thread {
	
	public static final int MAX_AVIOES = 10;
	public static final int MAX_PASSAGEIROS = 5000;
	public static final int NR_PISTAS = 3;
	

	private int pistasUsadas;
	private int id;
	private Comunicador comunicador;	
	private Point position;
	private Celula celula;
	private LinkedList<Passageiro> passageiros = new LinkedList<Passageiro>();
	private LinkedList<Aviao> avioes = new LinkedList<Aviao>();
	private ImageIcon icon = new ImageIcon("airport.png");
	
	public Aeroporto(int id, Celula celula, Comunicador comunicador) {
		super();
		this.id = id;
		this.celula = celula;
		this.comunicador = comunicador;
		pistasUsadas=0;
		position = new Point();
	}
	
	@Override
	public void run() {
		try {
			
			while(comunicador.jogo.getStarted()){
				sleep(4000);
				if (passageiros.size() < MAX_PASSAGEIROS) {
					setPassageiros();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	

	public void setPassageiros(){
		int rP = 1+(int)(Math.random()*100);
		int rA = (int)(Math.random()*Jogo.NUM_AIRPORTS);
		for(int i=0; i<rP; i++){
			passageiros.addLast(new Passageiro(i, comunicador.jogo.getAirports()[rA]));
		}
	}
	
	
	public void addPassageiro(Passageiro passageiro){
		passageiros.addLast(passageiro);
	}
	public Passageiro pollPassageiro(){
		return passageiros.pollFirst();
	}
	
	
	
	public synchronized void aterrar(Aviao aviao){
	
		avioes.addLast(aviao);
		while(aviao.getNrPassageiros()>0) {
			aviao.pollPassageiro();
		}
	
	}
	public synchronized void descolar(Aviao aviao) throws InterruptedException{
		
		while(pistasUsadas >= NR_PISTAS){
			System.out.println("As pistas estão cheias!");
			wait();
		}
		
		for (int i=0; i<avioes.size(); i++) {
			if(avioes.get(i).getID()==aviao.getID()){
				avioes.remove(i);
			}
		}
		notifyAll();
	}
	
	
	public int getNrPassageiros(){
		return passageiros.size();
	}
	public void addAviao(Aviao aviao){
		avioes.addLast(aviao);
	}
	public void setPosition(){
		this.position.setLocation(celula.getPosition());
	}
	public Point getPosicao() {
		return position;
	}
	public Celula getCelula() {
		return celula;
	}
	public ImageIcon getIcon() {
		return icon;
	}
	public int getID() {
		return id;
	}
}
