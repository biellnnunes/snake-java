package org.ufal.p3.controladores;

import org.ufal.p3.atores.Atores;

public class Facade {

	private static Facade instancia = new Facade();

	ControlColor color;
	
	public Facade() {
		color = ControlColor.getInstance();
	}

	public static Facade getInstancia() {
		return instancia;
	}
	
	public void confColor(){
		//color.confColor();
	}
	
	public void alterarCores(){
		color.alterarCores();
	}
	
	public void getColor(Atores ator){
		//return color.getColor(ator);
	}
		
}