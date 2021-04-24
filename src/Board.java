import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class Board extends JComponent {
 	
	private static final long serialVersionUID = 1L;
	private int nLines;
	private int nCol;
	
	private boolean started;

	private ImageIcon flag = new ImageIcon("flag.png");
	private Aviao[] plane;
	private Aeroporto[] airport;
	private Celula[][] celula;
	private int dx;
	private int dy;
	private Point Flag = new Point(-1, -1);

	private boolean selected;
	private Color azul = new Color(160, 186, 215);
	private Color beje = new Color(200, 210, 200);
	
	public Board(int nLines, int nCol) {
		super();
		this.nLines = nLines;
		this.nCol = nCol;	
	}
 
	
	@Override
	protected void paintComponent(Graphics g) {
	
		super.paintComponent(g);
	
		dx = getWidth() / (nCol);
		dy = getHeight() / (nLines);
		
		//desenha linhas
		for (int i=0; i<nLines; i++) {	
			g.drawLine(0, i*dy, getWidth(), i*dy);
		}
		//desenha colunas
		for (int i=0; i<nCol; i++) {
			g.drawLine(i*dx, 0, i*dx, getHeight());	
		}
		//desenha caracteristicas celulas
		for (int i=0; i<nLines; i++) {
			for (int j=0; j<nCol; j++) {
				
				int x = (int)((j % nCol)*dx)+1;
				int y = (int)((i % nCol)*dy)+1;	
				celula[i][j].setPosition(x, y);
				g.setColor(azul);
				g.fillRect(x+1, y+1, dx-2, dy-2);
				g.setColor(beje);
				String label = j + ", " + i + "";
				int w = g.getFontMetrics().stringWidth(label) / 2;
				int h = g.getFontMetrics().getDescent();
				int a = (int)((x+dx/2) - w);
				int b = (int)((y+dy/2) + h);
				g.drawString(label, a, b);
				
				if(Flag.x>=0 && Flag.y>=0){
					if(celula[i][j]==celula[Flag.y][Flag.x]){
						g.drawImage(flag.getImage(), x, y, dx-1, dy-1, null);
					}
				}
			}
		}
			
		if(started){
			
			//desenha avioes
			for (int i=0; i<Jogo.NUM_PLANES; i++){ 
				
				if(plane[i].getSelected() && selected){
					g.setColor(Color.ORANGE);
					g.drawOval((int)plane[i].getPosicao().getX(), (int)plane[i].getPosicao().getY(), dx, dy);
				}
				
				int c = (plane[i].getCombustivel()*(dx-1)) / 1000;
				g.setColor(Color.GREEN);
				g.fillRect((int)plane[i].getPosicao().getX(), (int)plane[i].getPosicao().getY()+(dy-4), c, 2);
				g.drawImage(plane[i].getIcon().getImage(), (int)plane[i].getPosicao().getX(), (int)plane[i].getPosicao().getY(), dx-1, dy-1, null);
			}
			//desenha aeroportos
			for (int i=0; i<Jogo.NUM_AIRPORTS; i++){
				airport[i].setPosition();
				g.drawImage(airport[i].getIcon().getImage(), (int)airport[i].getPosicao().getX(), (int)airport[i].getPosicao().getY(), dx-1, dy-1, null);
			}
		}
		
	}
	
	public int getDX(){
		return dx;
	}
	public int getDY(){
		return dy;
	}

	public void setStarted(boolean n){
		started = n;
	}
	public void setSelected(boolean selected){
		this.selected = selected;
	}
	public void setFlag(Point point){
		Flag = point;
	}
	
	
	public void setPlanes(Aviao[] plane){
		this.plane = plane;
	}
	public void setAirports(Aeroporto[] airport){
		this.airport = airport;
	}
	public void setCelulas(Celula[][] celula){
		this.celula = celula;
	}
	public Celula[][] getCelulas(){
		return celula;
	}

	

}