
public class Comunicador {
	
	public Interface interfac;
	public Jogo jogo;
	
	private int update = 0;
	
	public Comunicador(){
		
	}
	
	
	public void updateInterface(){	

		interfac.board.setPlanes(jogo.getPlanes());
		interfac.board.setAirports(jogo.getAirports());
		interfac.board.setCelulas(jogo.getCelulas());
		
		interfac.board.setSelected(jogo.getSelected());
		interfac.board.setFlag(jogo.getFlag());
		
		interfac.text.setText("" + jogo.getPontos());
		
		System.out.println("Update nº: " + update);
		update++;
	}
	public void updateJogo(){
		
		jogo.setDX(interfac.board.getDX());
		jogo.setDY(interfac.board.getDY());
	}
	public void JogoStarted(boolean started){
		if(started){
			jogo.start();
		}
		else{
			jogo.stop();
		}
	}
	public void InterfaceStarted(boolean started){
		interfac.board.setStarted(started);
	}
	
	
	public void setInterface(Interface interfac){
		this.interfac = interfac;
	}
	
	public void setJogo(Jogo jogo){
		this.jogo = jogo;
	}

}
