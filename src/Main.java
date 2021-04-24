
public class Main {
	
	
	public static void main(String[] args) {  
		
		Comunicador comunicador = new Comunicador();
		
		Jogo jogo = new Jogo(comunicador);
		Interface interfac = new Interface(comunicador);
		interfac.init();
		
		comunicador.setJogo(jogo);
		comunicador.setInterface(interfac);
		

		comunicador.updateJogo();
		comunicador.updateInterface();
		
	}

}
