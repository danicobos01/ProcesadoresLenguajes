package patron_visitor.asint;

import patron_visitor.asint.TinyASint.*;

public class NodoAST {

	public SL s;
	public EnumTipo tipo;
	public NodoAST vinculo;

	public NodoAST() {
		
	}
	
	public NodoAST getVinculo() {
		return this.vinculo;
	}
	
	public void setVinculo(NodoAST vinculo) {
		this.vinculo = vinculo;
	}
	
	public EnumTipo getTipoNodo() {
		return this.tipo;
	}
	
	public void setTipoNodo(EnumTipo tipo) {
		this.tipo = tipo;
	}
	

	public String toString() {
		return "Nodo: " + s.toString(); // Integer.toString(s.fila()) + "-" + Integer.toString(s.col());
	}
}

