package patron_visitor.procesamientos;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import patron_visitor.asint.NodoAST;
import patron_visitor.asint.ProcesamientoPorDefecto;
import patron_visitor.asint.TinyASint.*;



public class Vinculacion extends ProcesamientoPorDefecto {

	private Stack<Map<String, NodoAST>> ts;
	
	public Vinculacion() {
		ts = new Stack<Map<String, NodoAST>>();
		ts.push(new HashMap<String, NodoAST>());
	}
	
	public enum tipoError {
		idDeclarado
	}
	
	public Dec getDec(StringLocalizado id) {
		for (Map<String, NodoAST> t : ts) {
			if (t.containsKey(id.toString())) {
				return t.get(id.toString()).getVinculo();
			}
		}
		return null;
	}

	public boolean idDuplicadoTodos(StringLocalizado id) {
		for (Map<String, NodoAST> t : ts) {
			if (t.containsKey(id.toString()))
				return true;
		}

		return false;
	}

	public boolean idDuplicadoAct(StringLocalizado id) {
		if (ts.peek().containsKey(id.toString()))
			return true;
		return false;
	}
	
	public void aniade(StringLocalizado id, Decs d) {
		ts.peek().put(id.toString(), new NodoAST()); // Que pasamos aquí ??
	}
	
	public void recolectaTodos(StringLocalizado id, Decs d) {
		if (idDuplicadoTodos(id)) {
			printError(id, tipoError.idDeclarado);
		} else {
			aniade(id, d);
		}
	}

	public void recolectaAct(StringLocalizado id, Decs d) {
		if (idDuplicadoAct(id)) {
			printError(id, tipoError.idDeclarado);
		} else {
			aniade(id, d);
		}
	}
	
	public void printError(StringLocalizado s, tipoError err) {
		if(err == tipoError.idDeclarado) {
			System.out.println("Error: " + s.toString() + " - id ya declarado");
		}
	}
	
	
	private class VinculacionPointer extends ProcesamientoPorDefecto {

		public void procesa(Prog prog) {
			if (prog.getDeclaraciones() != null) {
				prog.getDeclaraciones().procesa(this);
				prog.getDeclaraciones().procesa(new VinculacionPointer());
			}
			prog.getInstrucciones().procesa(this);
			
		}
		
		public void procesa(Decs_muchas decs) {
			decs.decs().procesa(this);
			decs.dec().procesa(this);
		}
	
		public void procesa(Decs_una decs) {
			decs.dec().procesa(this);
		}
	
		@Override
		public void procesa(DecProc dec) {
			StringLocalizado id = dec.getId();
	
			recolectaAct(id, dec);
			anida();
			dec.getPforms().procesa(this);
			dec.getIns().procesa(this); // Ojo con lo de los vectores aqui !!
			dec.getDecs().procesa(this);
			desanida();
		}
	
		@Override
		public void procesa(DecTipo dec) {
			StringLocalizado id = dec.getId();
			dec.getTipo().procesa(this);
	
			recolectaAct(id, dec);
		}
	
		@Override
		public void procesa(DecVar dec) {
			dec.getTipo().procesa(this);
	
			StringLocalizado id = dec.getId();
			recolectaAct(id, dec);
		}
	
	
		@Override
		public void procesa(Lista_exp_empty lista_exp_empty) {
		}
	
		@Override
		public void procesa(Exp_muchas exp_muchas) {
			exp_muchas.expresiones().procesa(this);
			exp_muchas.expresion().procesa(this);
		}
	
		@Override
		public void procesa(Exp_una exp_una) {
			exp_una.expresion().procesa(this);
		}
	
		public void procesa(Suma exp) {
			exp.arg0().procesa(this);
			exp.arg1().procesa(this);
		}
	}
}
	