package patron_visitor.asint;

import java.util.Vector;

public class TinyASint {
    
    public static abstract class Exp extends NodoAST{
       public Exp() {
    	   super();
       }   
       // public abstract int prioridad();
       public abstract void procesa(Procesamiento procesamiento);
    }
    
    public static class SL {
     private String s;
     private int fila;
     private int col;
     public SL(String s, int fila, int col) {
         this.s = s;
         this.fila = fila;
         this.col = col;
     }
     
     public SL(String s) {
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
                (o instanceof SL) &&
                (((SL)o).s.equals(s)));                
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
        public Exp getFirst() {
        	return this.arg0;
        }
        public Exp getSecond() {
        	return this.arg1;
        }
    }
    
    private static abstract class ExpUnario extends Exp {
        private Exp arg;
        public Exp arg0() {return arg;}
        public ExpUnario(Exp arg) {
            super();
            this.arg = arg;
        }
        public Exp getExp() {
        	return this.arg;
        }
    }   
    
    /*
    public static class Exp_muchas extends Exp{
    	Exp exp;
    	Exp_muchas exps;
    	
    	public Exp_muchas(Exp exp, Exp_muchas exps) {
    		this.exp = exp;
    		this.exps = exps;
    	}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
		public Exp getExp();
    }
    
    public static class Exp_una extends Exp{
    	Exp exp;
    	public Exp_una(Exp exp) {
    		this.exp = exp;
    	}
    	public void procesa(Procesamiento p) {
    		p.procesa(this);
    	}
    	public Exp getExp() {
    		return this.exp;
    	}
    }
    
    public static class Exp_vacia extends Exp{
    	public Exp_vacia() {
    		
    	}
    	public void procesa(Procesamiento p) {
    		p.procesa(this);
    	}
    }
    */
    
    
    
    // Nivel 0: Los operadores relacionales. Todos ellos son operadores binarios,
    // infijos, no asociativos.
    private static abstract class ExpRel extends ExpBin {
        public ExpRel(Exp arg0, Exp arg1) {
            super(arg0,arg1);
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
    
    
    // Nivel 5: Operadores de indexaci�n, de acceso a registro y de fci�n.
    
    public static class Index extends ExpBin{ // [ E ]
    	
    	public Index(Exp arg0, Exp arg1) {
    		super(arg0, arg1);
    	}
    	
    	public void procesa(Procesamiento p) {
    		p.procesa(this);
    	}
    	
    }
    
    public static class AccesoRegistro extends ExpUnario { // .c
		private SL id;

		public AccesoRegistro(Exp exp, SL id) {
			super(exp);
			this.id = id;
		}

		public SL getId() {
			return this.id;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
    }
    
    public static class Indireccion extends ExpUnario{ // ^
		// private SL id;

		public Indireccion(Exp exp /* SL id */) {
			super(exp);
			// this.id = id;
		}

		/*
		public SL getId() {
			return this.id;
		}
		*/

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
    }
    
    public static class NumEnt extends Exp {
        private SL numEnt;
        public NumEnt(SL numEnt) {
            super();
            this.numEnt = numEnt;
        }
        public SL num() {return numEnt;}
        public void procesa(Procesamiento p) {
           p.procesa(this); 
        }     
    }
    
    public static class NumReal extends Exp {
        private SL numReal;
        public NumReal(SL numReal) {
            super();
            this.numReal = numReal;
        }
        public SL num() {return numReal;}
        public void procesa(Procesamiento p) {
           p.procesa(this); 
        }     
    }
    
    public static class Id extends Exp {
        private SL id;
        public Id(SL id) {
            super();
            this.id = id;
        }
        public SL id() {return id;}
        public void procesa(Procesamiento p) {
           p.procesa(this); 
        }   
        
        public String toString() {
        	return this.id.toString();
        }
    }
    
    public static class TrueExp extends Exp {
        private SL exp = new SL("True");
        public TrueExp() {
            super();
        }
        public String num() {return exp.toString();}
        public void procesa(Procesamiento p) {
           p.procesa(this); 
        }     
    }
    
    public static class FalseExp extends Exp {
        private SL exp = new SL("False");
        public FalseExp() {
            super();
        }
        public String num() {return exp.toString();}
        public void procesa(Procesamiento p) {
           p.procesa(this); 
        }     
    }
    
    public static class NullExp extends Exp {
        private SL exp = new SL("Null");
        public NullExp() {
            super();
        }
        public String num() {return exp.toString();}
        public void procesa(Procesamiento p) {
           p.procesa(this); 
        }     
    }
    
    public static class StringExp extends Exp {
        private SL str;
        public StringExp(SL str) {
            super();
            this.str = str;
        }
        public String num() {return str.toString();}
        public void procesa(Procesamiento p) {
           p.procesa(this); 
        }     
    }
    
    
    public enum EnumTipo {
		INT, REAL, STRING, BOOL, ID, POINTER, ARRAY, RECORD, ERROR, OK, NULL, REF
	}
    
    public static abstract class Tipo extends NodoAST{
    	EnumTipo tipo;
    	public Tipo(EnumTipo tipo) {
    		this.tipo = tipo;
    	}
    	
    	public abstract void procesa(Procesamiento p);
    	
    	public abstract EnumTipo getEnumTipo();
    	public abstract Tipo getTipo();
    }
    
    public static class Int extends Tipo{
    	
    	public Int() {
    		super(EnumTipo.INT);
    	}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}

		public EnumTipo getEnumTipo() {
			return EnumTipo.INT;
		}

		public Int getTipo() {
			return this;
		}
    }
    
    public static class Real extends Tipo{
    	
    	public Real() {
    		super(EnumTipo.REAL);
    	}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}

		public EnumTipo getEnumTipo() {
			return EnumTipo.REAL;
		}
		public Real getTipo() {
			return this;
		}
    }
    
    public static class Bool extends Tipo {
    	
    	public Bool() {
    		super(EnumTipo.BOOL);
    	}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}

		public EnumTipo getEnumTipo() {
			return EnumTipo.BOOL;
		}
		public Bool getTipo() {
			return this;
		}
    }
    
    public static class StringTipo extends Tipo{
    	
    	public StringTipo() {
    		super(EnumTipo.STRING);
    	}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}

		public EnumTipo getEnumTipo() {
			return EnumTipo.STRING;
		}
		public StringTipo getTipo() {
			return this;
		}
    }
    
    public static class Null extends Tipo{
    	public Null(){
    		super(EnumTipo.NULL);
    	}
    	public void procesa(Procesamiento p){
    		p.procesa(this);
    	}
    	public EnumTipo getEnumTipo() {
    		return EnumTipo.NULL;
    	}
    	public Null getTipo() {
			return this;
		}
    }
    
    public static class Array extends Tipo{
    	int nElems;
    	Tipo tipoElems;
    	public Array(int n, Tipo tipo) {
    		super(EnumTipo.ARRAY);
    		this.nElems = n;
    		this.tipoElems = tipo;
    	}
    	public void procesa(Procesamiento p) {
    		p.procesa(this);
    	}
    	public EnumTipo getEnumTipo() {
    		return EnumTipo.ARRAY;
    	}
    	public Array getTipo() {
			return this;
		}
    	public Tipo getTipoElems() {
    		return this.tipoElems;
    	}
    	public int getNElems() {
    		return this.nElems;
    	}
    }
    
    public static class RecordTipo extends Tipo{
    	Campos campos; 
		public RecordTipo(Campos campos) {
			super(EnumTipo.RECORD);
			this.campos = campos;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}

		public EnumTipo getEnumTipo() {
			return EnumTipo.RECORD;
		}
		public RecordTipo getTipo() {
			return this;
		}
		public Campos getCampos() {
			return this.campos;
		}
    	
    }
    
    
    public static class Pointer extends Tipo {
    	Tipo apuntado;
    	public Pointer(Tipo tipo) {
    		super(EnumTipo.POINTER);
    		this.apuntado = tipo;
 
    	}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
		public EnumTipo getEnumTipo() {
			return EnumTipo.POINTER;
		}
		public Tipo getTipo() {
			return this.apuntado.getTipo();
		}
    	public Tipo getApuntado() {
    		return this.apuntado;
    	}
    	public EnumTipo getTipoApuntado() {
    		return apuntado.getEnumTipo();
    	}
    }
    
    public static class Ref extends Tipo{
    	SL id;
    	public Ref(SL id) {
    		super(EnumTipo.REF);
    		this.id = id;
    	}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}

		public EnumTipo getEnumTipo() {
			return EnumTipo.REF;
		}
		public Ref getTipo() {
			return this;
		}
		public SL getId() {
			return this.id;
		}
		
    }
    
    public static class Campo extends NodoAST {
    	private SL id; 
    	private Tipo tipo;
    	public Campo(SL id, Tipo tipo) {
    		this.id = id; 
    		this.tipo = tipo;
    	}
    	public SL getId() {
    		return this.id;
    	}
    	public Tipo getTipo() {
    		return this.tipo;
    	}
    	public EnumTipo getEnumTipo() {
    		return this.tipo.getEnumTipo();
    	}
    	public void procesa(Procesamiento p) {
    		p.procesa(this);
    	}
    }
    
    public static abstract class Campos extends Vector<Campo>{
    	public Campos() {
        }
        public abstract void procesa(Procesamiento p);
    }
    
    public static class Campos_uno extends Campos{
    	Campo campo;
		public Campos_uno(Campo campo) {
			this.campo = campo;
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);			
		}
		public Campo getCampo() {
			return this.campo;
		}
    }
    
    public static class Campos_muchos extends Campos{
    	Campo campo;
    	Campos campos;
		public Campos_muchos(Campos campos, Campo campo) {
			this.campo = campo;
			this.campos = campos;
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);			
		}
		public Campos getCampos() {
			return this.campos;
		}
		public Campo getCampo() {
			return this.campo;
		}
    }
    
    
    
    public static class Dec extends NodoAST {
        private SL id;
        public Dec(SL id) {
            this.id = id;
        }
        public SL id() {return id;}
        public void procesa(Procesamiento p) {
           p.procesa(this); 
        }     
    }
    
    public static abstract class Decs extends NodoAST {
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
    
    public static class Decs_vacia extends Decs{
    	public Decs_vacia() {
    		super();
    	}
    	public void procesa(Procesamiento p) {
    		p.procesa(this);
    	}
    }
    
    public static class DecVar extends Dec { 
    	Tipo tipo;
    	public DecVar(Tipo tipo, SL id) {
    		super(id);
    		this.tipo = tipo;
    	}
    	
    	public Tipo getTipo() {
    		return this.tipo;
    	}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
    	
    }
    
    public static class DecTipo extends Dec { 
    	Tipo tipo;
    	public DecTipo(SL id, Tipo tipo) {
    		super(id);
    		this.tipo = tipo;
    	}
    	public Tipo getTipo() {
    		return this.tipo;
    	}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
    	
    }
    
    public static class DecProc extends Dec {
    	Pforms pforms;
    	Instrucciones ins;
    	Decs decs;
    	public DecProc(SL id, Pforms pforms, Instrucciones ins, Decs decs) {
    		super(id);
    		this.pforms = pforms;
    		this.ins = ins;
    		this.decs = decs;
    	}	
    	public Pforms getPforms() {
    		return this.pforms;
    	}
    	
    	public Instrucciones getIns() {
    		return this.ins;
    	}
    	
    	public Decs getDecs() {
    		return this.decs;
    	}
    	
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
    	
    }
    
    
    public static abstract class Instruccion extends NodoAST {
    	public Instruccion() {
    	}
    	public abstract void procesa(Procesamiento p);
    }
    
    
    
    public static abstract class Instrucciones extends NodoAST{
    	public Instrucciones() {
    	}
    	public abstract void procesa(Procesamiento p);
    }
    
    public static class Ins_muchas extends Instrucciones{
    	private Instrucciones ins;
    	private Instruccion in;
    	
		public Ins_muchas(Instrucciones ins, Instruccion in) {
			super();
			this.ins = ins;
			this.in = in;
		}
		
		public Instrucciones ins() { return this.ins; }
		
		public Instruccion in() { return this.in; }
		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
    	
    }
    
    public static class Ins_una extends Instrucciones{
    	private Instruccion in;
    	
		public Ins_una(Instruccion in) {
			super();
			this.in = in;
		}
		
		public Instruccion in() { return this.in; }

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
    
    
    public static class Asignacion extends Instruccion{
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
    
    public static class If_then extends Instruccion{
    	private Exp arg;
    	private Instrucciones ins;
    	
    	public If_then(Exp arg, Instrucciones ins) {
    		this.arg = arg;
    		this.ins = ins;
    	}
    	
    	public Exp getExp() {
    		return this.arg;
    	}
    	
    	public Instrucciones getIns() {
    		return this.ins;
    	}
		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
    }
    
    public static class If_then_else extends Instruccion{
    	private Exp arg;
    	private Instrucciones ins1, ins2;
    	
    	public If_then_else(Exp arg, Instrucciones ins1, Instrucciones ins2) {
    		this.arg = arg;
    		this.ins1 = ins1;
    		this.ins2 = ins2;
    	}
    	
    	public Exp getExp() {
    		return this.arg;
    	}
    	
    	public Instrucciones getIns1() {
    		return this.ins1;
    	}
    	
    	public Instrucciones getIns2() {
    		return this.ins2;
    	}
		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
    }
    
    public static class While extends Instruccion{
    	private Exp arg;
    	private Instrucciones ins;
    	
    	public While(Exp arg, Instrucciones ins) {
    		this.arg = arg;
    		this.ins = ins;
    	}
    	
    	public Exp getExp() {
    		return this.arg;
    	}
    	
    	public Instrucciones getIns() {
    		return this.ins;
    	}
		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
    }
    
    public static class Read extends Instruccion{
    	private Exp exp;
    	
    	public Read(Exp exp) {
    		this.exp = exp;
    	}
    	
    	public Exp getExp() {
    		return this.exp;
    	}
		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
    }
    
    public static class Write extends Instruccion{
    	private Exp exp;
    	
    	public Write(Exp exp) {
    		this.exp = exp;
    	}
    	
    	public Exp getExp() {
    		return this.exp;
    	}
		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
    }
    
    public static class NewLine extends Instruccion{
    	public NewLine() {}
 
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
    }
    
    public static class New extends Instruccion{
    	private Exp exp;
    	
    	public New(Exp exp) {
    		this.exp = exp;
    	}
    	
    	public Exp getExp() {
    		return this.exp;
    	}
		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
    }
    
    public static class Delete extends Instruccion{
    	private Exp exp;
    	
    	public Delete(Exp exp) {
    		this.exp = exp;
    	}
    	
    	public Exp getExp() {
    		return this.exp;
    	}
		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
    }
    
    public static class Seq extends Instruccion{
    	private Decs decs;
    	private Instrucciones ins;
    	
    	public Seq(Decs decs, Instrucciones ins) {
    		this.decs = decs;
    		this.ins = ins;
    	}
    	
    	public Decs getDecs() {
    		return this.decs;
    	}
    	
    	public Instrucciones getIns() {
    		return this.ins;
    	}
		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
    }
    
    public static class Invoc_proc extends Instruccion{
    	private SL id;
    	private PReales preales;
    	
    	public Invoc_proc(SL id, PReales preales) {
    		this.id = id;
    		this.preales = preales;
    	}
    	
    	public SL getId() {
    		return this.id;
    	}
    	
    	public PReales getPreales() {
    		return this.preales;
    	}
    	
		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
    }
          
    
    public static abstract class Pf extends NodoAST{
    	private SL id;
    	private Tipo tipo;
    	public Pf(SL id, Tipo tipo) {
    		this.id = id;
    		this.tipo = tipo;
    	}
    	public SL getId() {
    		return this.id;
    	}
    	public Tipo getTipo() {
    		return this.tipo;
    	}
    	public abstract void procesa(Procesamiento p);
    }
    
    public static class Pf_ref extends Pf{
		public Pf_ref(SL id, Tipo tipo) {
			super(id, tipo);
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
    }
    
    public static class Pf_valor extends Pf{
		public Pf_valor(SL id, Tipo tipo) {
			super(id, tipo);
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
    }    
    
    
    public static abstract class Pforms extends NodoAST {
		public Pforms() {
		}
		public abstract void procesa(Procesamiento p);
    }
    
    public static class Pf_muchos extends Pforms{
    	Pforms pforms;
    	Pf pform;
		public Pf_muchos(Pforms pforms, Pf pform) {
			this.pforms = pforms;
			this.pform = pform;
		}
		public Pforms getPforms() {
			return this.pforms;
		}
		public Pf getPf() {
			return this.pform;
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
    	
    }
    
    public static class Pf_uno extends Pforms{
    	Pf pform;
		public Pf_uno(Pf pform) {
			this.pform = pform;
		}
		public Pf getPf() {
			return this.pform;
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
    }
    
    public static class Pf_vacio extends Pforms{
		public Pf_vacio() {
	
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
    }
    
    public static class Pr extends Exp{
    	private Exp exp;
    	public Pr(Exp exp) {
    		this.exp = exp;
    	}
    	public Exp getExp() {
    		return this.exp;
    	}
    	public void procesa(Procesamiento p) {
    		p.procesa(this);
    	}
    }
    
    public abstract static class PReales extends Decs{
		public PReales() {
		}
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
    }
    
    public static class Pr_muchos extends PReales{
    	PReales preales;
    	Pr preal;
		public Pr_muchos(PReales preales, Pr preal) {
			this.preales = preales;
			this.preal = preal;
		}
		public PReales getPreales() {
			return this.preales;
		}
		public Pr getPr() {
			return this.preal;
		}
    	
    }
    
    public static class Pr_uno extends PReales{
    	Pr preal;
		public Pr_uno(Pr preal) {
			this.preal = preal;
		}
		public Pr getPr() {
			return this.preal;
		}
    }
    
    public static class Pr_vacio extends PReales{
		public Pr_vacio() {
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
       public Prog_con_decs(Decs decs, Instrucciones ins) {
          super(decs, ins);
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
    public SL strl(String s, int fila, int col) {
        return new SL(s,fila,col);
    }
    public SL strl(String s) {
        return new SL(s);
    }
    
    public Prog prog_con_decs(Instrucciones ins, Decs decs) {
        return new Prog_con_decs(decs, ins);
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
    public Exp numEnt(SL num) {
        return new NumEnt(num);
    }
    public Exp numReal(SL num) {
        return new NumReal(num);
    }
    public Exp id(SL num) {
        return new Id(num);
    }
    public Dec dec(SL id, SL val) {
        return new Dec(id);
    }
    public Decs decs_una(Dec dec) {
        return new Decs_una(dec);
    }
    public Decs decs_muchas(Decs decs, Dec dec) {
        return new Decs_muchas(decs,dec);
    }
    
    public Dec decVar(SL id, Tipo tipo) {
    	return new DecVar(tipo, id);
    }
    
    public Dec decTipo(SL id, Tipo tipo) {
    	return new DecTipo(id, tipo);
    }
    
    public Dec decProc(SL id, Pforms pforms, Instrucciones ins, Decs decs) {
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
    
    public Instruccion asignacion(Exp arg0, Exp arg1) {
    	return new Asignacion(arg0, arg1);
    }
    
}
