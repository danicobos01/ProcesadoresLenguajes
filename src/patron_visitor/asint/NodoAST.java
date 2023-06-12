package patron_visitor.asint;

import patron_visitor.asint.TinyASint.*;

public class NodoAST {

	public SL s;
	// public EnumTipo tipo;
	public Tipo tipo;
	public NodoAST vinculo;
	private int nivel;
	private int dir;
	private int tam;
	private int despl;
	private int ini; 
	private int sig;

	public NodoAST() {
		
	}
	
	public NodoAST getVinculo() {
		return this.vinculo;
	}
	
	public void setVinculo(NodoAST vinculo) {
		this.vinculo = vinculo;
	}
	
	public Tipo getTipoNodo() {
		return this.tipo;
	}
	
	public void setTipoNodo(Tipo tipo) {
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
	
	public void setDespl(int i) {
		this.despl = i;
	}

	public int getDespl() {
		return this.despl;
	}
	
	public void setIni(int i) {
		this.ini = i;
	}

	public int getIni() {
		return this.ini;
	}
	
	public void setSig(int i) {
		this.sig = i;
	}

	public int getSig() {
		return this.sig;
	}
	
	public String toString() {
		return "Nodo: " + s.toString(); // Integer.toString(s.fila()) + "-" + Integer.toString(s.col());
	}
}

