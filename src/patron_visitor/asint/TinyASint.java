package patron_visitor.asint;

import java.util.Vector;

public class TinyASint {
    
    public static abstract class Exp extends NodoAST{
       public Exp() {
    	   super();
       }   
       public abstract int prioridad();
       public abstract void procesa(Procesamiento procesamiento);
    }
    
    public static class StringLocalizado {
     private String s;
     private int fila;
     private int col;
     public StringLocalizado(String s, int fila, int col) {
         this.s = s;
         this.fila = fila;
         this.col = col;
     }
     
     public StringLocalizado(String s) {
    	 this.s = s;
     }
     
     public void setFilaCol(int fila, int col) {
    	 this.col = col;
    	 this.fila = fila;
     }
     public int fila() {return fila;}
     public int col() {return col;}
     public String toString() {
       return s;
     }
     public boolean equals(Object o) {
         return (o == this) || (
                (o instanceof StringLocalizado) &&
                (((StringLocalizado)o).s.equals(s)));                
     }
     public int hashCode() {
         return s.hashCode();
     }
  }

    
    private static abstract class ExpBin extends Exp {
        private Exp arg0;
        private Exp arg1;
        public Exp arg0() {return arg0;}
        public Exp arg1() {return arg1;}
        public ExpBin(Exp arg0, Exp arg1) {
            super();
            this.arg0 = arg0;
            this.arg1 = arg1;
        }
    }
    
    private static abstract class ExpUnario extends Exp {
        private Exp arg;
        public Exp arg0() {return arg;}
        public ExpUnario(Exp arg) {
            super();
            this.arg = arg;
        }
        public int prioridad() {
        	return 4;
        }
    }
    
    
    
    
    
    // Nivel 0: Los operadores relacionales. Todos ellos son operadores binarios,
    // infijos, no asociativos.
    private static abstract class ExpRel extends ExpBin {
        public ExpRel(Exp arg0, Exp arg1) {
            super(arg0,arg1);
        }
        public final int prioridad() {
            return 0;
        }
    }
    
    public static class Mayor extends ExpRel {
        public Mayor(Exp arg0, Exp arg1) {
            super(arg0,arg1);
        }
        public void procesa(Procesamiento p) {
           p.procesa(this); 
        }     
    }
    
    public static class Menor extends ExpRel {
        public Menor(Exp arg0, Exp arg1) {
            super(arg0,arg1);
        }
        public void procesa(Procesamiento p) {
           p.procesa(this); 
        }     
    }
    
    public static class Igual extends ExpRel {
        public Igual(Exp arg0, Exp arg1) {
            super(arg0,arg1);
        }
        public void procesa(Procesamiento p) {
           p.procesa(this); 
        }     
    }
    
    public static class Distinto extends ExpRel {
        public Distinto(Exp arg0, Exp arg1) {
            super(arg0,arg1);
        }
        public void procesa(Procesamiento p) {
           p.procesa(this); 
        }     
    }
    
    public static class MayorIgual extends ExpRel {
        public MayorIgual(Exp arg0, Exp arg1) {
            super(arg0,arg1);
        }
        public void procesa(Procesamiento p) {
           p.procesa(this); 
        }     
    }
    
    public static class MenorIgual extends ExpRel {
        public MenorIgual(Exp arg0, Exp arg1) {
            super(arg0,arg1);
        }
        public void procesa(Procesamiento p) {
           p.procesa(this); 
        }     
    }
    
    
    
    // Nivel 1: + no asocia, - (binario) asocia a izquierdas.
        
    private static abstract class ExpAditiva extends ExpBin {
        public ExpAditiva(Exp arg0, Exp arg1) {
            super(arg0,arg1);
        }
        public final int prioridad() {
            return 0;
        }
    }
    
    public static class Suma extends ExpAditiva {
        public Suma(Exp arg0, Exp arg1) {
            super(arg0,arg1);
        }
        public void procesa(Procesamiento p) {
           p.procesa(this); 
        }     
    }
    public static class Resta extends ExpAditiva {
        public Resta(Exp arg0, Exp arg1) {
            super(arg0,arg1);
        }
        public void procesa(Procesamiento p) {
           p.procesa(this); 
        }     
    }
    
    
    
    // Nivel 2: and no asocia, y or asocia a derechas. 
    
    private static abstract class ExpLog extends ExpBin {
        public ExpLog(Exp arg0, Exp arg1) {
            super(arg0,arg1);
        }
        public final int prioridad() {
            return 0;
        }
    }
    
    public static class And extends ExpLog {
        public And(Exp arg0, Exp arg1) {
            super(arg0,arg1);
        }
        public void procesa(Procesamiento p) {
           p.procesa(this); 
        }     
    }
    
    public static class Or extends ExpLog {
        public Or(Exp arg0, Exp arg1) {
            super(arg0,arg1);
        }
        public void procesa(Procesamiento p) {
           p.procesa(this); 
        }     
    }
    
    // Nivel 3: *, / y %. Operadores binarios, infijos, asociativos a izquierdas.
    
    private static abstract class ExpMultiplicativa extends ExpBin {
        public ExpMultiplicativa(Exp arg0, Exp arg1) {
            super(arg0,arg1);
        }
        public final int prioridad() {
            return 1;
        }
    }
    
    public static class Mul extends ExpMultiplicativa {
        public Mul(Exp arg0, Exp arg1) {
            super(arg0,arg1);
        }
        public void procesa(Procesamiento p) {
           p.procesa(this); 
        }     
    }
    public static class Div extends ExpMultiplicativa {
        public Div(Exp arg0, Exp arg1) {
            super(arg0,arg1);
        }
        public void procesa(Procesamiento p) {
           p.procesa(this); 
        }     
    }
    
    public static class Mod extends ExpMultiplicativa {
        public Mod(Exp arg0, Exp arg1) {
            super(arg0,arg1);
        }
        public void procesa(Procesamiento p) {
           p.procesa(this); 
        }     
    }
    
    
    // Nivel 4: - (unario) y not. Operadores unarios, prefijos, asociativos
    public static class MenosUnario extends ExpUnario {
        public MenosUnario(Exp arg) {
            super(arg);
        }
        public void procesa(Procesamiento p) {
           p.procesa(this); 
        }     
    }
    
    public static class Not extends ExpUnario {
        public Not(Exp arg) {
            super(arg);
        }
        public void procesa(Procesamiento p) {
           p.procesa(this); 
        }     
    }
    
    
    // Nivel 5: Operadores de indexaci�n, de acceso a registro y de indirecci�n.
    
    
    
    public static class NumEnt extends Exp {
        private StringLocalizado numEnt;
        public NumEnt(StringLocalizado numEnt) {
            super();
            this.numEnt = numEnt;
        }
        public StringLocalizado num() {return numEnt;}
        public void procesa(Procesamiento p) {
           p.procesa(this); 
        }     
    }
    
    public static class NumReal extends Exp {
        private StringLocalizado numReal;
        public NumReal(StringLocalizado numReal) {
            super();
            this.numReal = numReal;
        }
        public StringLocalizado num() {return numReal;}
        public void procesa(Procesamiento p) {
           p.procesa(this); 
        }     
    }
    
    public static class Id extends Exp {
        private StringLocalizado id;
        public Id(StringLocalizado id) {
            super();
            this.id = id;
        }
        public StringLocalizado id() {return id;}
        public void procesa(Procesamiento p) {
           p.procesa(this); 
        }     
    }
    
    public enum EnumTipo {
		INT, REAL, STRING, BOOL, ID, POINTER, ARRAY, RECORD, ERROR, OK, NULL
	}
    
    public static abstract class Tipo {
    	EnumTipo tipo;
    	public Tipo(EnumTipo tipo) {
    		this.tipo = tipo;
    	}
    	
    	public abstract void procesa(Procesamiento p);
    	
    	public abstract EnumTipo getTipo();
    }
    
    public static class Int extends Tipo{
    	
    	public Int() {
    		super(EnumTipo.INT);
    	}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}

		public EnumTipo getTipo() {
			return EnumTipo.INT;
		}
    }
    
    public static class Real extends Tipo{
    	
    	public Real() {
    		super(EnumTipo.REAL);
    	}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}

		public EnumTipo getTipo() {
			return EnumTipo.REAL;
		}
    }
    
    public static class Bool extends Tipo {
    	
    	public Bool() {
    		super(EnumTipo.BOOL);
    	}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}

		public EnumTipo getTipo() {
			return EnumTipo.BOOL;
		}
    }
    
    public static class StringTipo extends Tipo{
    	
    	public StringTipo() {
    		super(EnumTipo.STRING);
    	}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}

		public EnumTipo getTipo() {
			return EnumTipo.STRING;
		}
    }
    
    // ??
    public static class int extends Exp{
    	
    }
    
    
    
    public static class Dec extends NodoAST {
        private StringLocalizado id;
        private StringLocalizado val;
        public Dec(StringLocalizado id, StringLocalizado val) {
            this.id = id;
            this.val = val;
        }
        public StringLocalizado id() {return id;}
        public StringLocalizado val() {return val;}
        public void procesa(Procesamiento p) {
           p.procesa(this); 
        }     
    }
    
    
    public static abstract class Decs extends NodoAST{
       public Decs() {
       }
       public abstract void procesa(Procesamiento p);
    }
    
    public static class Decs_una extends Decs {
       private Dec dec; 
       public Decs_una(Dec dec) {
          super();
          this.dec = dec;
       }   
       public Dec dec() {return dec;}
       public void procesa(Procesamiento p) {
           p.procesa(this); 
        }     
    }
    
    public static class Decs_muchas extends Decs {
       private Dec dec;
       private Decs decs;
       public Decs_muchas(Decs decs, Dec dec) {
          super();
          this.dec = dec;
          this.decs = decs;
       }
       public Dec dec() {return dec;}
       public Decs decs() {return decs;}
       public void procesa(Procesamiento p) {
           p.procesa(this); 
        }     
    }
    
    public static class DecVar extends Decs {
    	StringLocalizado id; 
    	Tipo tipo;
    	public DecVar(StringLocalizado id, Tipo tipo) {
    		this.id = id;
    		this.tipo = tipo;
    	}
    	
    	public StringLocalizado getId() {
    		return this.id;
    	}
    	
    	public Tipo getTipo() {
    		return this.tipo;
    	}
		@Override
		public void procesa(Procesamiento p) {
			p.procesa(this)
		}
    	
    }
    
    public static class DecTipo extends Decs {
    	StringLocalizado id; 
    	Tipo tipo;
    	public DecTipo(StringLocalizado id, Tipo tipo) {
    		this.id = id;
    		this.tipo = tipo;
    	}
    	
    	public StringLocalizado getId() {
    		return this.id;
    	}
    	
    	public Tipo getTipo() {
    		return this.tipo;
    	}
		@Override
		public void procesa(Procesamiento p) {
			p.procesa(this)
		}
    	
    }
    
    public static class DecProc extends Decs {
    	StringLocalizado id; 
    	Pforms pforms;
    	Vector<Instrucciones> ins;
    	Vector<Decs> decs;
    	public DecProc(StringLocalizado id, Pforms pforms, Vector<Instrucciones> ins, Vector<Decs> decs) {
    		this.id = id;
    		this.pforms = pforms;
    		this.ins = ins;
    		this.decs = decs;
    	}
    	
    	public StringLocalizado getId() {
    		return this.id;
    	}
    	
    	public Pforms getPforms() {
    		return this.pforms;
    	}
    	
    	public Vector<Instrucciones> getIns() {
    		return this.ins;
    	}
    	
    	public Vector<Decs> getDecs() {
    		return this.decs;
    	}
    	
		@Override
		public void procesa(Procesamiento p) {
			p.procesa(this)
		}
    	
    }
    
    
    
    
    
    
    /*
    public static abstract class Instruccion {
    	public Instruccion() {
    		
    	}
    	public abstract void procesa(Procesamiento p);
    }
    */
    
    
    public static abstract class Instrucciones extends NodoAST{
    	public Instrucciones() {

    	}
    	public abstract void procesa(Procesamiento p);
    }
    
    public static class Ins_muchas extends Instrucciones{
    	private Vector<Instrucciones> ins;
    	private Instrucciones in;
    	
		public Ins_muchas(Vector<Instrucciones> ins, Instrucciones in) {
			super();
			this.ins = ins;
			this.in = in;
		}
		
		public Vector<Instrucciones> ins() { return this.ins; }
		
		public Instrucciones in() { return this.in; }

		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
    	
    }
    
    public static class Ins_una extends Instrucciones{
    	private Instrucciones in;
    	
		public Ins_una(Instrucciones in) {
			super();
			this.in = in;
		}
		
		public Instrucciones in() { return this.in; }

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
    	
    }
    
    public static class Ins_vacia extends Instrucciones{
    	public Ins_vacia() {
    		super();
    	}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
    }
    
    
    public static class Asignacion extends Instrucciones{
    	private Exp arg0;
    	private Exp arg1;
    	
    	public Asignacion(Exp arg0, Exp arg1) {
    		this.arg0 = arg0;
    		this.arg1 = arg1;
    	}
    	
    	public Exp getFirst() {
    		return this.arg0;
    	}
    	
    	public Exp getSecond() {
    		return this.arg1;
    	}

		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
    	
    	
    }
    
    
    
    public static abstract class Pforms extends NodoAST {
    	private Tipo t;
		private StringLocalizado id;

		public Pforms(Tipo t, StringLocalizado id) {
			this.id = id;
			this.t = t;
		}

		public StringLocalizado id() {
			return id;
		}

		public Tipo tipo() {
			return t;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
    }
    
    public static abstract class Prog  extends NodoAST{
    	
    	private Decs decs;
    	private Instrucciones ins;
    	public Prog(Decs decs, Instrucciones ins) {
    		this.decs = decs;
    		this.ins = ins;
    	}   
    	
    	public Prog(Instrucciones ins) {
    		this.ins = ins;
    		this.decs = null;
    	}
    	
    	public Decs getDeclaraciones() {
    		return this.decs;
    	}
    	
    	public Instrucciones getInstrucciones() {
    		return this.ins;
    	}
       
       public abstract void procesa(Procesamiento p); 
    }
    public static class Prog_sin_decs extends Prog {
      private Instrucciones ins;
       public Prog_sin_decs(Instrucciones ins) {
          super(ins);
          this.ins = ins;
       }   
       public Instrucciones ins() {return this.ins;}
       public void procesa(Procesamiento p) {
           p.procesa(this); 
        }     
    }
    public static class Prog_con_decs extends Prog {
      private Instrucciones ins;
      private Decs decs;
       public Prog_con_decs(Instrucciones ins, Decs decs) {
          super(ins);
          this.ins = ins;
          this.decs = decs;
       }   
       public Instrucciones ins() {return this.ins;}
       public Decs decs() {return decs;}
       public void procesa(Procesamiento p) {
           p.procesa(this); 
        }     
    }

     // Constructoras   
    public StringLocalizado strl(String s, int fila, int col) {
        return new StringLocalizado(s,fila,col);
    }
    public StringLocalizado strl(String s) {
        return new StringLocalizado(s);
    }
    
    public Prog prog_con_decs(Instrucciones ins, Decs decs) {
        return new Prog_con_decs(ins,decs);
    }
    public Prog prog_sin_decs(Instrucciones ins) {
        return new Prog_sin_decs(ins);
    }
    public Exp suma(Exp arg0, Exp arg1) {
        return new Suma(arg0,arg1);
    }
    public Exp resta(Exp arg0, Exp arg1) {
        return new Resta(arg0,arg1);
    }
    public Exp mul(Exp arg0, Exp arg1) {
        return new Mul(arg0,arg1);
    }
    public Exp div(Exp arg0, Exp arg1) {
        return new Div(arg0,arg1);
    }
    
    public Exp mod(Exp arg0, Exp arg1) {
        return new Mod(arg0,arg1);
    }
    public Exp numEnt(StringLocalizado num) {
        return new NumEnt(num);
    }
    public Exp numReal(StringLocalizado num) {
        return new NumReal(num);
    }
    public Exp id(StringLocalizado num) {
        return new Id(num);
    }
    public Dec dec(StringLocalizado id, StringLocalizado val) {
        return new Dec(id,val);
    }
    public Decs decs_una(Dec dec) {
        return new Decs_una(dec);
    }
    public Decs decs_muchas(Decs decs, Dec dec) {
        return new Decs_muchas(decs,dec);
    }
    
    public Decs decVar(StringLocalizado id, Tipo tipo) {
    	return new DecVar(id, tipo);
    }
    
    public Decs decTipo(StringLocalizado id, Tipo tipo) {
    	return new DecTipo(id, tipo);
    }
    
    public Decs decProc(StringLocalizado id, Pforms pforms, Vector<Instrucciones> ins, Vector<Decs> decs) {
    	return new DecProc(id, pforms, ins, decs);
    }
    
    public Instrucciones ins_muchas(Instrucciones ins, Instruccion in) {
    	return new Ins_muchas(ins, in);
    }
    public Instrucciones ins_una(Instruccion in) {
    	return new Ins_una(in);
    }
    public Instrucciones ins_vacia() {
    	return new Ins_vacia();
    }
    
    public Instrucciones asignacion(Exp arg0, Exp arg1) {
    	return new Asignacion(arg0, arg1);
    }
    
}
