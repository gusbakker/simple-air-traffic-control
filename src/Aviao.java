import java.awt.Point;
import java.util.LinkedList;

import javax.swing.*;

public class Aviao extends Thread {

	public static final int MAX_PASSAGEIROS = 100;
	private int id;
	private Comunicador comunicador;

	private int combustivel = 1000;
	private int reserva = (int) (combustivel * 0.2);

	private boolean selected;
	private Aeroporto origem;
	private Aeroporto destino;
	private int direction;
	private Point position;
	private Celula celula;
	private Celula proximaCelula;
	private LinkedList<Celula> trajecto = new LinkedList<Celula>();
	private LinkedList<Passageiro> passageiros = new LinkedList<Passageiro>();

	private ImageIcon icon;
	private ImageIcon icon1 = new ImageIcon("airplane1.png");
	private ImageIcon icon2 = new ImageIcon("airplane2.png");
	private ImageIcon icon3 = new ImageIcon("airplane3.png");
	private ImageIcon icon4 = new ImageIcon("airplane4.png");
	private ImageIcon icon5 = new ImageIcon("red_airplane1.png");
	private ImageIcon icon6 = new ImageIcon("red_airplane2.png");
	private ImageIcon icon7 = new ImageIcon("red_airplane3.png");
	private ImageIcon icon8 = new ImageIcon("red_airplane4.png");

	public Aviao(int id, Comunicador comunicador) {
		super();
		this.id = id;
		this.comunicador = comunicador;
	}

	@Override
	public void run() {
		try {

			// int a=0;
			icon = new ImageIcon("airplane1.png");
			celula = origem.getCelula();
			position = new Point(celula.getPosition());
			int r = (int) (Math.random() * (Jogo.NUM_AIRPORTS - 1));
			destino = comunicador.jogo.getAirports()[r];
			setDestino(destino.getCelula());

			while (comunicador.jogo.getStarted()) {

				sleep(15);
				if (trajecto.size() > 0) {
					if (position.equals(proximaCelula.getPosition())) {
						celula = proximaCelula;
						proximaCelula = trajecto.pollFirst();
						if (proximaCelula != null) {
							setDirection();
						}
					}
					proximaCelula.setOcupado(this);
				} else { // Se a proxima celula for o Aeroporto destino entao
							// reabastece
							// a=0;
					if (proximaCelula.getAeroporto().getID() == destino.getID()) {
						celula = proximaCelula;
						destino.aterrar(this);
						comunicador.jogo.setPontos(combustivel);

						origem = destino;
						addCombustivel(1000);
						for (int i = 0; i < MAX_PASSAGEIROS; i++) {
							if (origem.getNrPassageiros() > 0)
								addPassageiro(origem.pollPassageiro());
						}
						r = (int) (Math.random() * (Jogo.NUM_AIRPORTS - 1));
						destino = comunicador.jogo.getAirports()[r];
						destino.descolar(this);
						setDestino(destino.getCelula());
						setDirection();

					} else {
						setDestino(destino.getCelula());
					}
				}

				combustivel--;
				if (combustivel == 0) {
					System.out.println("Aviao: " + id
							+ " ficou sem Combustivel!");
					comunicador.jogo.setPontosNegativos(100);
					interrupt();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void movePlane() {

		if (direction == 1) {
			if (combustivel <= reserva)
				icon = icon5;
			else
				icon = icon1;
			if (position.x <= proximaCelula.getPosition().x) {
				position.x++;
			}
		}
		if (direction == 3) {
			if (combustivel <= reserva)
				icon = icon7;
			else
				icon = icon3;
			if (position.x >= proximaCelula.getPosition().x) {
				position.x--;
			}
		}
		if (direction == 2) {
			if (combustivel <= reserva)
				icon = icon6;
			else
				icon = icon2;
			if (position.y <= proximaCelula.getPosition().y) {
				position.y++;
			}
		}
		if (direction == 4) {
			if (combustivel <= reserva)
				icon = icon8;
			else
				icon = icon4;
			if (position.y >= proximaCelula.getPosition().y) {
				position.y--;
			}
		}
		if (selected) {
			comunicador.interfac.text1.setText("" + id);
			comunicador.interfac.text2.setText("" + direction);
			comunicador.interfac.text3.setText("" + destino.getID());
			comunicador.interfac.text4.setText("" + passageiros.size());
			comunicador.interfac.text5.setText("" + combustivel);
		}
		comunicador.interfac.board.repaint();

	}

	public void moveInCirc() {

	}

	// Cria o trajeto de celulas ate ao destino
	// Caso o destino seja um Aeroporto, guarda na memória o aeroporto destino.
	public void setDestino(Celula destino) {
		trajecto.clear();
		proximaCelula = celula;
		if (destino.getAeroporto() != null) {
			this.destino = destino.getAeroporto();
		}

		while (proximaCelula != destino) {
			int dx = destino.getX() - proximaCelula.getX();
			int dy = destino.getY() - proximaCelula.getY();
			int j = proximaCelula.getX();
			int i = proximaCelula.getY();
			if (Math.abs(dx) > Math.abs(dy)) {
				if (dx > 0) {
					proximaCelula = comunicador.interfac.board.getCelulas()[i][j + 1];
				} else {
					proximaCelula = comunicador.interfac.board.getCelulas()[i][j - 1];
				}
			} else {
				if (dy > 0) {
					proximaCelula = comunicador.interfac.board.getCelulas()[i + 1][j];
				} else {
					proximaCelula = comunicador.interfac.board.getCelulas()[i - 1][j];
				}
			}
			trajecto.addLast(proximaCelula);
		}
		proximaCelula = celula;
	}

	// Define direcção do aviao com base na proxima Celula
	public void setDirection() {

		if (proximaCelula.getX() > celula.getX()) {
			direction = 1;
		}
		if (proximaCelula.getX() < celula.getX()) {
			direction = 3;
		} else {
			if (proximaCelula.getY() > celula.getY()) {
				direction = 2;
			}
			if (proximaCelula.getY() < celula.getY()) {
				direction = 4;
			}
		}
	}

	public void setOrigem(Aeroporto airport) {
		origem = airport;
		origem.addAviao(this);
	}

	public void addPassageiro(Passageiro passageiro) {
		passageiros.addLast(passageiro);
	}

	public Passageiro pollPassageiro() {
		return passageiros.pollFirst();
	}

	public int getNrPassageiros() {
		return passageiros.size();
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

	public void addCombustivel(int combustivel) {
		this.combustivel = combustivel;
	}

	public int getCombustivel() {
		return combustivel;
	}

	public boolean getSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
