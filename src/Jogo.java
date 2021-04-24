import java.awt.*;


public class Jogo {

	public static final int NUN_LIN = 10;
	public static final int NUM_COL = 20;
	public static final int NUM_PLANES = 15;
	public static final int NUM_AIRPORTS = 4;
	
    
    private Comunicador comunicador;
    
    private Aviao[] plane;
    private Aeroporto[] airport;
    private Celula[][] celula;
    private boolean started;
    private int pontos;
    
    private int p1;
    private int p2;
 
    private int dx;
    private int dy;
    private Point Flag = new Point(-1, -1);
    private int idPlane;
    private boolean selected;
    
	
	public Jogo(Comunicador comunicador){
		super();
		this.comunicador = comunicador;
		
		airport = new Aeroporto[NUM_AIRPORTS];
		plane = new Aviao[NUM_PLANES];
		celula = new Celula[NUN_LIN][NUM_COL];
		
		for (int i=0; i<NUN_LIN; i++) {
			for (int j=0; j<NUM_COL; j++) {
				celula[i][j] = new Celula(j, i);
			}
		}	
	}

	public void start(){
		
		pontos=0;
		setStarted(true);
		
		for(int i=0; i<NUM_AIRPORTS; i++){
			p1 = (int)(Math.random()*(NUN_LIN-1));
			p2 = (int)(Math.random()*(NUM_COL-1));
			airport[i] = new Aeroporto(i, celula[p1][p2], comunicador);
			celula[p1][p2].setAeroporto(airport[i]);
			airport[i].setPassageiros();
			airport[i].start();
		}


		for(int i=0; i<NUM_PLANES; i++){
			int r = (int)(Math.random()*(NUM_AIRPORTS-1));
			plane[i] = new Aviao(i, comunicador);
			plane[i].setOrigem(airport[r]);
			plane[i].start();
			
		}
		comunicador.updateInterface(); 
		comunicador.InterfaceStarted(true);
	
	}
	
	public void stop(){
		
		comunicador.InterfaceStarted(false);
		for(int i=0; i<NUM_PLANES; i++){
			plane[i].interrupt();
		}
		for(int i=0; i<NUM_AIRPORTS; i++){
			airport[i].interrupt();
		}
	}
	
	public void setSelected(Point point){
		
		comunicador.updateJogo();
		int x = (int)(point.getX() / dx);
		int y = (int)(point.getY() / dy);

		for (int i=0; i<NUM_PLANES; i++){
			if(plane[i].getCelula().getX()==x && plane[i].getCelula().getY()==y){	
				plane[idPlane].setSelected(false);
				idPlane = plane[i].getID();
				plane[i].setSelected(true);
				selected = true;
			}
		}
		if (Flag.x == x && Flag.y == y){
			Flag = new Point(-1, -1);
		}
		else{	
			Flag = new Point(x, y);
		}
		comunicador.updateInterface();
	}
	

	public void setPontos(int n){
		
		int c = (int)(n*10) / 1000;
		pontos += 10 + c;
		comunicador.updateInterface();
	}
	public void setPontosNegativos(int n){
		pontos -= n;
		if(pontos<0){
			stop();
		}
		comunicador.interfac.text.setText("Gameover");
	}
	
	public int getPontos(){
		return pontos;
	}
	public Comunicador getComunicador(){
		return comunicador;
	}
	
	public Aviao[] getPlanes(){
		return plane;
	}
	public Aeroporto[] getAirports(){
		return airport;
	}
	public Celula[][] getCelulas(){
		return celula;
	}
	
	public void setStarted(boolean started){
		this.started = started;
	}
	public boolean getStarted(){
		return started;
	}
	public void setDX(int dx){
		this.dx = dx; 
	}
	public void setDY(int dy){
		this.dy = dy;
	}
	public boolean getSelected(){
		return selected;
	}
	public Point getFlag(){
		return Flag;
	}

	
}
