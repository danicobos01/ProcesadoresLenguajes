package patron_visitor.procesamientos;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import patron_visitor.asint.*;
import patron_visitor.asint.TinyASint.Dec;
import patron_visitor.asint.TinyASint.Decs_muchas;
import patron_visitor.asint.TinyASint.Decs_una;
import patron_visitor.asint.TinyASint.Prog;
import patron_visitor.asint.TinyASint.StringLocalizado;



public class Vinculacion extends ProcesamientoPorDefecto {

	private Stack<Map<String, Nodo>> ts;
	
	public Vinculacion() {
		ts = new Stack<Map<String, Nodo>>();
		ts.push(new HashMap<String, Nodo>());
	}
	
	public Dec getDec(StringLocalizado id) {
		for (Map<String, Nodo> t : ts) {
			if (t.containsKey(id.toString()))
				return t.get(id.toString()).d;
		}

		return null;
	}

	public boolean idDuplicadoTodos(StringLocalizado id) {
		for (Map<String, Nodo> t : ts) {
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
	
	public void aniade(StringLocalizado id, Dec d) {
		ts.peek().put(id.toString(), new Nodo(d, id));
	}
	
	public void recolectaTodos(StringLocalizado id, Dec d) {
		if (idDuplicadoTodos(id)) {
			printError(id, tError.IdYaDeclarado);
		} else {
			aniade(id, d);
		}
	}

	public void recolectaAct(StringLocalizado id, Dec d) {
		if (idDuplicadoAct(id)) {
			printError(id, tError.IdYaDeclarado);
		} else {
			aniade(id, d);
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
			StringLocalizado id = dec.id();
	
			recolectaAct(id, dec);
			anida();
			dec.pforms().procesa(this);
			dec.bloque().procesa(this);
			desanida();
		}
	
		@Override
		public void procesa(DecTipo dec) {
			StringLocalizado id = dec.id();
			dec.val().procesa(this);
	
			recolectaAct(id, dec);
		}
	
		@Override
		public void procesa(DecVar dec) {
			dec.val().procesa(this);
	
			StringLocalizado id = dec.id();
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
	