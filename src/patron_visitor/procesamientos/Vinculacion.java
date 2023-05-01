package procesamientos;



public class Vinculacion extends ProcesamientoPorDefecto {

	private Stack<Map<String, Nodo>> ts;
	
	public Vinculacion() {
		ts = new Stack<Map<String, Nodo>>();
		ts.push(new HashMap<String, Nodo>());
	}
	
	public Declaracion getDec(StringLocalizado id) {
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
	
	public void aniade(StringLocalizado id, Declaracion d) {
		ts.peek().put(id.toString(), new Nodo(d, id));
	}
	
	public void recolectaTodos(StringLocalizado id, Declaracion d) {
		if (idDuplicadoTodos(id)) {
			printError(id, tError.IdYaDeclarado);
		} else {
			aniade(id, d);
		}
	}

	public void recolectaAct(StringLocalizado id, Declaracion d) {
		if (idDuplicadoAct(id)) {
			printError(id, tError.IdYaDeclarado);
		} else {
			aniade(id, d);
		}
	}
	
	private static class Nodo {
		public int fila;
		public int col;
		public Declaracion d;

		public Nodo(Declaracion d, StringLocalizado s) {
			this.d = d;
			this.fila = s.fila();
			this.col = s.col();
		}

		public String toString() {
			return Integer.toString(fila) + "-" + Integer.toString(col);
		}
	}
	
	private class VinculacionPointer extends ProcesamientoPorDefecto {
		
		public void procesa(Decs_muchas decs_muchas) {
			decs_muchas.declaraciones().procesa(this);
			decs_muchas.declaracion().procesa(this);
		}

		public void procesa(Decs_una decs_una) {
			decs_una.declaracion().procesa(this);
		}

		public void procesa(DecVar var) {
			var.val().procesa(this);
		}

		public void procesa(DecTipo type) {
			type.val().procesa(this);
		}

		public void procesa(Int t) {
		}
		
		public void procesa(Real t) {
		}

	public void procesa(Programa prog) {
		if (prog.declaraciones() != null) {
			prog.declaraciones().procesa(this);
			prog.declaraciones().procesa(new VinculacionPointer());
		}
		prog.instrucciones().procesa(this);
	}
	
	public void procesa(Decs_muchas decs) {
		decs.declaraciones().procesa(this);
		decs.declaracion().procesa(this);
	}

	public void procesa(Decs_una decs) {
		decs.declaracion().procesa(this);
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
	