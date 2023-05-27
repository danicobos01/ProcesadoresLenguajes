package patron_visitor.asint;

import patron_visitor.asint.TinyASint.*;

public class NodoAST {

	public SL s;
	public EnumTipo tipo;
	public NodoAST vinculo;
	public int nivel;
	public int dir;
	public int tam;

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
	
	public void setNivel(int i) {
		this.nivel = i;
	}
	
	public int getNivel() {
		return this.nivel;
	}
	
	public void setDir(int i) {
		this.dir = i;
	}
	
	public int getDir() {
		return this.dir;
	}
	
	public void setTam(int i) {
		this.tam = i;
	}
	
	public int getTam() {
		return this.tam;
	}

	public String toString() {
		return "Nodo: " + s.toString(); // Integer.toString(s.fila()) + "-" + Integer.toString(s.col());
	}
}

