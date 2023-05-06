package patron_visitor.asint;

import patron_visitor.asint.TinyASint.*;

public class NodoAST {

	public StringLocalizado s;
	public EnumTipo tipo;
	public Dec vinculo;

	public NodoAST() {
		
	}
	
	public Dec getVinculo() {
		return this.vinculo;
	}
	
	public void setVinculo(Dec vinculo) {
		this.vinculo = vinculo;
	}
	
	public EnumTipo getTipoNodo() {
		return this.tipo;
	}
	
	public void setTipoNodo(EnumTipo tipo) {
		this.tipo = tipo;
	}
	

	public String toString() {
		return "Nodo: " + Integer.toString(s.fila()) + "-" + Integer.toString(s.col());
	}
}

