import java.awt.Point;


public class Celula {
		
		private Aeroporto airport;
		private Point position;
		private int x;
		private int y;
		private boolean ocupado;
		
		public Celula(int x, int y) {
			
			this.x = x;
			this.y = y;
			position = new Point();
		}
		
		
		public synchronized void setOcupado(Aviao aviao) throws InterruptedException{
			while(ocupado){
				System.out.println("A celula está ocupada!");
				wait();
			}
			aviao.movePlane();
			notifyAll();
		}


		public void setPosition(int x, int y){
			position.setLocation(x, y);
		}
		public Point getPosition(){
			return position;
		}		
		public int getX(){
			return x;
		}
		public int getY(){
			return y;
		}
		public void setAeroporto(Aeroporto airport){
			this.airport = airport;
		}
		public Aeroporto getAeroporto(){
			return airport;
		}


}
