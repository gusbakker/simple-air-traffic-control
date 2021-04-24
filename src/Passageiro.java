
public class Passageiro {
	
	private int id;
	private Aeroporto destino;
	
	public Passageiro(int id, Aeroporto destino){
		super();
		this.id = id;
		this.destino = destino;
	}
	
	public Aeroporto getDestino(){
		return destino;
	}
	public int getID(){
		return id;
	}

}
