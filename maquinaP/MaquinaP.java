package maquinaP;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;


public class MaquinaP {
   public static class EAccesoIlegitimo extends RuntimeException {} 
   public static class EAccesoAMemoriaNoInicializada extends RuntimeException {
      public EAccesoAMemoriaNoInicializada(int pc,int dir) {
         super("pinst:"+pc+" dir:"+dir); 
      } 
   } 
   public static class EAccesoFueraDeRango extends RuntimeException {} 
   
   private GestorMemoriaDinamica gestorMemoriaDinamica;
   private GestorPilaActivaciones gestorPilaActivaciones;
    
   private class Valor {
      public int valorInt() {throw new EAccesoIlegitimo();}  
      public boolean valorBool() {throw new EAccesoIlegitimo();} 
      public String valorString() {throw new EAccesoIlegitimo();}
      public double valorReal() {throw new EAccesoIlegitimo();}
   } 
   private class ValorInt extends Valor {
      private int valor;
      public ValorInt(int valor) {
         this.valor = valor; 
      }
      public int valorInt() {return valor;}
      public String toString() {
        return String.valueOf(valor);
      }
   }
   private class ValorBool extends Valor {
      private boolean valor;
      public ValorBool(boolean valor) {
         this.valor = valor; 
      }
      public boolean valorBool() {return valor;}
      public String toString() {
        return String.valueOf(valor);
      }
   }
   
   // Aniadimos strings y reales:
   
   private class ValorString extends Valor{
	   private String valor;
	   public ValorString(String valor) {
		   this.valor = valor;
	   }
	   public String valorString() {return valor;}
	   public String toString() {
		   return String.valueOf(valor);
	   }
   }
   
   private class ValorReal extends Valor{
	   private double valor;
	   public ValorReal(double valor) {
		   this.valor = valor;
	   }
	   public double valorReal() {return valor;}
	   public String toString() {
		   return String.valueOf(valor);
	   }
   }
   
   
   private List<Instruccion> codigoP;
   private Stack<Valor> pilaEvaluacion;
   private Valor[] datos; 
   private int pc;

   public interface Instruccion {
      void ejecuta();  
   }
   
   private ISuma ISUMA;
   private class ISuma implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorInt(opnd1.valorInt()+opnd2.valorInt()));
         pc++;
      } 
      public String toString() {return "suma";};
   }
   
   private ISumaReal ISUMAR;
   private class ISumaReal implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorReal(opnd1.valorReal()+opnd2.valorReal()));
         pc++;
      } 
      public String toString() {return "suma_r";};
   } 
   
   private IMul IMUL;
   private class IMul implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorInt(opnd1.valorInt()*opnd2.valorInt()));
         pc++;
      } 
      public String toString() {return "mul";};
   }
   
   private IMulReal IMULR;
   private class IMulReal implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorReal(opnd1.valorReal()*opnd2.valorReal()));
         pc++;
      } 
      public String toString() {return "mul_r";};
   }
   
   private IResta IRESTA;
   private class IResta implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorInt(opnd1.valorInt()-opnd2.valorInt()));
         pc++;
      } 
      public String toString() {return "resta";};
   }
   
   private IRestaReal IRESTAR;
   private class IRestaReal implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorReal(opnd1.valorReal()-opnd2.valorReal()));
         pc++;
      } 
      public String toString() {return "resta_r";};
   } 
   
   private IDiv IDIV;
   private class IDiv implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorInt(opnd1.valorInt()/opnd2.valorInt()));
         pc++;
      } 
      public String toString() {return "div";};
   }
   
   private IDivReal IDIVR;
   private class IDivReal implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorReal(opnd1.valorReal()/opnd2.valorReal()));
         pc++;
      } 
      public String toString() {return "div_r";};
   } 
   
   private IMod IMOD;
   private class IMod implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorInt(opnd1.valorInt()%opnd2.valorInt()));
         pc++;
      } 
      public String toString() {return "mod";};
   }
   
   private IModReal IMODR;
   private class IModReal implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorReal(opnd1.valorReal()%opnd2.valorReal()));
         pc++;
      } 
      public String toString() {return "mod_r";};
   } 
   
   
   
   private IAnd IAND;
   private class IAnd implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorBool(opnd1.valorBool()&&opnd2.valorBool()));
         pc++;
      } 
      public String toString() {return "and";};
   }
   
   private IOr IOR;
   private class IOr implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorBool(opnd1.valorBool()||opnd2.valorBool()));
         pc++;
      } 
      public String toString() {return "or";};
   }
   
   private INot INOT;
   private class INot implements Instruccion {
	   public void ejecuta() {
		 Valor opnd1 = pilaEvaluacion.pop();
		 pilaEvaluacion.push(new ValorBool(!opnd1.valorBool()));
		 pc++;
	  }
	  public String toString() {return "not";};
   }
   
   private IIgual IIGUAL;
   private class IIgual implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorBool(opnd1.valorInt() == opnd2.valorInt()));
         pc++;
      } 
      public String toString() {return "igual";};
   }
   
   private IIgualReal IIGUALR;
   private class IIgualReal implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorBool(opnd1.valorReal() == opnd2.valorReal()));
         pc++;
      } 
      public String toString() {return "igual_real";};
   }
   
   private IIgualString IIGUALS;
   private class IIgualString implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorBool(opnd1.valorString().compareTo(opnd2.valorString()) == 0));
         pc++;
      } 
      public String toString() {return "igual_string";};
   }
   
   private IMenor IMENOR;
   private class IMenor implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorBool(opnd1.valorInt() < opnd2.valorInt()));
         pc++;
      } 
      public String toString() {return "menor";};
   }
   
   private IMenorReal IMENORR;
   private class IMenorReal implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorBool(opnd1.valorReal() < opnd2.valorReal()));
         pc++;
      } 
      public String toString() {return "menor_real";};
   }
   
   private IMenorString IMENORS;
   private class IMenorString implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorBool(opnd1.valorString().compareTo(opnd2.valorString()) < 0));
         pc++;
      } 
      public String toString() {return "menor_string";};
   }
   
   private IMayor IMAYOR;
   private class IMayor implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorBool(opnd1.valorInt() > opnd2.valorInt()));
         pc++;
      } 
      public String toString() {return "mayor";};
   }
   
   private IMayorReal IMAYORR;
   private class IMayorReal implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorBool(opnd1.valorReal() > opnd2.valorReal()));
         pc++;
      } 
      public String toString() {return "mayor_real";};
   }
   
   private IMayorString IMAYORS;
   private class IMayorString implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorBool(opnd1.valorString().compareTo(opnd2.valorString()) > 0));
         pc++;
      } 
      public String toString() {return "mayor_string";};
   }
   
   
   private IMenorIgual IMENORIGUAL;
   private class IMenorIgual implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorBool(opnd1.valorInt() <= opnd2.valorInt()));
         pc++;
      } 
      public String toString() {return "menor_igual";};
   }
   
   private IMenorIgualReal IMENORIGUALR;
   private class IMenorIgualReal implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorBool(opnd1.valorReal() <= opnd2.valorReal()));
         pc++;
      } 
      public String toString() {return "menor_igual_real";};
   }
   
   private IMenorIgualString IMENORIGUALS;
   private class IMenorIgualString implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorBool(opnd1.valorString().compareTo(opnd2.valorString()) <= 0));
         pc++;
      } 
      public String toString() {return "menor_igual_string";};
   }
   
   private IMayorIgual IMAYORIGUAL;
   private class IMayorIgual implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorBool(opnd1.valorInt() >= opnd2.valorInt()));
         pc++;
      } 
      public String toString() {return "mayor_igual";};
   }
   
   private IMayorIgualReal IMAYORIGUALR;
   private class IMayorIgualReal implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorBool(opnd1.valorReal() >= opnd2.valorReal()));
         pc++;
      } 
      public String toString() {return "mayor_igual_real";};
   }
   
   private IMayorIgualString IMAYORIGUALS;
   private class IMayorIgualString implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorBool(opnd1.valorString().compareTo(opnd2.valorString()) >= 0));
         pc++;
      } 
      public String toString() {return "mayor_igual_string";};
   }
   
   private IWrite IWRITE;
   private class IWrite implements Instruccion {
      public void ejecuta() {
         Valor opnd1 = pilaEvaluacion.pop(); 
         System.out.print(opnd1.valorInt());
         pc++;
      } 
      public String toString() {return "write_int";};
   }
   
   private IWriteReal IWRITER;
   private class IWriteReal implements Instruccion {
      public void ejecuta() {
         Valor opnd1 = pilaEvaluacion.pop(); 
         System.out.print(opnd1.valorReal());
         pc++;
      } 
      public String toString() {return "write_real";};
   }
   
   private IWriteString IWRITES;
   private class IWriteString implements Instruccion {
      public void ejecuta() {
         Valor opnd1 = pilaEvaluacion.pop(); 
         System.out.print(opnd1.valorString());
         pc++;
      } 
      public String toString() {return "write_string";};
   }
   
   private IWriteBool IWRITEB;
   private class IWriteBool implements Instruccion {
      public void ejecuta() {
         Valor opnd1 = pilaEvaluacion.pop(); 
         System.out.print(opnd1.valorBool());
         pc++;
      } 
      public String toString() {return "write_bool";};
   }
   
   private Scanner in;
   
   private IRead IREAD;
   private class IRead implements Instruccion {
      public void ejecuta() {
    	 pilaEvaluacion.push(new ValorInt(in.nextInt()));
		 pc++;
      } 
      public String toString() {return "read_int";};
   }
   
   private IReadReal IREADR;
   private class IReadReal implements Instruccion {
      public void ejecuta() {
    	 pilaEvaluacion.push(new ValorReal(in.nextDouble()));
		 pc++;
      } 
      public String toString() {return "read_double";};
   }
   private IReadString IREADS;
   private class IReadString implements Instruccion {
      public void ejecuta() {
    	 pilaEvaluacion.push(new ValorString(in.nextInt())); // ¿¿ nextString??
 		 pc++;
      } 
      public String toString() {return "write_string";};
   }  
   
   private class IApilaInt implements Instruccion {
      private int valor;
      public IApilaInt(int valor) {
        this.valor = valor;  
      }
      public void ejecuta() {
         pilaEvaluacion.push(new ValorInt(valor)); 
         pc++;
      } 
      public String toString() {return "apilaInt("+valor+")";};
   }
   
   private class IApilaReal implements Instruccion {
	  private double valor;
	  public IApilaReal(double valor) {
	    this.valor = valor;  
	  }
	  public void ejecuta() {
	     pilaEvaluacion.push(new ValorReal(valor)); 
	     pc++;
	  } 
	  public String toString() {return "apilaReal("+valor+")";};
   }
   
   private class IApilaString implements Instruccion {
	  private String valor;
	  public IApilaString(String valor) {
		this.valor = valor;  
	  }
	  public void ejecuta() {
		pilaEvaluacion.push(new ValorString(valor)); 
		pc++;
	  } 
	  public String toString() {return "apilaString("+valor+")";};
   }

   private class IApilaBool implements Instruccion {
      private boolean valor;
      public IApilaBool(boolean valor) {
        this.valor = valor;  
      }
      public void ejecuta() {
         pilaEvaluacion.push(new ValorBool(valor)); 
         pc++;
      } 
      public String toString() {return "apilaBool("+valor+")";};
   }
   
   

   private class IIrA implements Instruccion {
      private int dir;
      public IIrA(int dir) {
        this.dir = dir;  
      }
      public void ejecuta() {
            pc=dir;
      } 
      public String toString() {return "ira("+dir+")";};
   }

   private class IIrF implements Instruccion {
      private int dir;
      public IIrF(int dir) {
        this.dir = dir;  
      }
      public void ejecuta() {
         if(! pilaEvaluacion.pop().valorBool()) { 
            pc=dir;
         }   
         else {
            pc++; 
         }
      } 
      public String toString() {return "irf("+dir+")";};
   }
   
   private class IMueve implements Instruccion {
      private int tam;
      public IMueve(int tam) {
        this.tam = tam;  
      }
      public void ejecuta() {
            int dirOrigen = pilaEvaluacion.pop().valorInt();
            int dirDestino = pilaEvaluacion.pop().valorInt();
            if ((dirOrigen + (tam-1)) >= datos.length)
                throw new EAccesoFueraDeRango();
            if ((dirDestino + (tam-1)) >= datos.length)
                throw new EAccesoFueraDeRango();
            for (int i=0; i < tam; i++) 
                datos[dirDestino+i] = datos[dirOrigen+i]; 
            pc++;
      } 
      public String toString() {return "mueve("+tam+")";};
   }
   
   private IApilaind IAPILAIND;
   private class IApilaind implements Instruccion {
      public void ejecuta() {
        int dir = pilaEvaluacion.pop().valorInt();
        if (dir >= datos.length) throw new EAccesoFueraDeRango();
        if (datos[dir] == null)  throw new EAccesoAMemoriaNoInicializada(pc,dir);
        pilaEvaluacion.push(datos[dir]);
        pc++;
      } 
      public String toString() {return "apilaind";};
   }

   private IDesapilaind IDESAPILAIND;
   private class IDesapilaind implements Instruccion {
      public void ejecuta() {
        Valor valor = pilaEvaluacion.pop();
        int dir = pilaEvaluacion.pop().valorInt();
        if (dir >= datos.length) throw new EAccesoFueraDeRango();
        datos[dir] = valor;
        pc++;
      } 
      public String toString() {return "desapilaind";};
   }

   private class IAlloc implements Instruccion {
      private int tam;
      public IAlloc(int tam) {
        this.tam = tam;  
      }
      public void ejecuta() {
        int inicio = gestorMemoriaDinamica.alloc(tam);
        pilaEvaluacion.push(new ValorInt(inicio));
        pc++;
      } 
      public String toString() {return "alloc("+tam+")";};
   }

   private class IDealloc implements Instruccion {
      private int tam;
      public IDealloc(int tam) {
        this.tam = tam;  
      }
      public void ejecuta() {
        int inicio = pilaEvaluacion.pop().valorInt();
        gestorMemoriaDinamica.free(inicio,tam);
        pc++;
      } 
      public String toString() {return "dealloc("+tam+")";};
   }
   
   private class IActiva implements Instruccion {
       private int nivel;
       private int tamdatos;
       private int dirretorno;
       public IActiva(int nivel, int tamdatos, int dirretorno) {
           this.nivel = nivel;
           this.tamdatos = tamdatos;
           this.dirretorno = dirretorno;
       }
       public void ejecuta() {
          int base = gestorPilaActivaciones.creaRegistroActivacion(tamdatos);
          datos[base] = new ValorInt(dirretorno);
          datos[base+1] = new ValorInt(gestorPilaActivaciones.display(nivel));
          pilaEvaluacion.push(new ValorInt(base+2));
          pc++;
       }
       public String toString() {
          return "activa("+nivel+","+tamdatos+","+dirretorno+")";                 
       }
   }
   
   private class IDesactiva implements Instruccion {
       private int nivel;
       private int tamdatos;
       public IDesactiva(int nivel, int tamdatos) {
           this.nivel = nivel;
           this.tamdatos = tamdatos;
       }
       public void ejecuta() {
          int base = gestorPilaActivaciones.liberaRegistroActivacion(tamdatos);
          gestorPilaActivaciones.fijaDisplay(nivel,datos[base+1].valorInt());
          pilaEvaluacion.push(datos[base]); 
          pc++;
       }
       public String toString() {
          return "desactiva("+nivel+","+tamdatos+")";                 
       }

   }
   
   private class IDesapilad implements Instruccion {
       private int nivel;
       public IDesapilad(int nivel) {
         this.nivel = nivel;  
       }
       public void ejecuta() {
         gestorPilaActivaciones.fijaDisplay(nivel,pilaEvaluacion.pop().valorInt());  
         pc++;
       }
       public String toString() {
          return "setd("+nivel+")";                 
       }
   }
   private IDup IDUP;
   private class IDup implements Instruccion {
       public void ejecuta() {
           pilaEvaluacion.push(pilaEvaluacion.peek());
           pc++;
       }
       public String toString() {
          return "dup";                 
       }
   }
   private Instruccion ISTOP;
   private class IStop implements Instruccion {
       public void ejecuta() {
           pc = codigoP.size();
       }
       public String toString() {
          return "stop";                 
       }
   }
   
   
   private class IApilad implements Instruccion {
       private int nivel;
       public IApilad(int nivel) {
         this.nivel = nivel;  
       }
       public void ejecuta() {
         pilaEvaluacion.push(new ValorInt(gestorPilaActivaciones.display(nivel)));
         pc++;
       }
       public String toString() {
          return "apilad("+nivel+")";                 
       }

   }
   
   private Instruccion IIRIND;
   private class IIrind implements Instruccion {
       public void ejecuta() {
          pc = pilaEvaluacion.pop().valorInt();  
       }
       public String toString() {
          return "irind";                 
       }
   }

   public Instruccion suma() {return ISUMA;}
   public Instruccion sumaR() {return ISUMAR;}
   public Instruccion mul() {return IMUL;}
   public Instruccion mulR() {return IMULR;}
   public Instruccion resta() {return IRESTA;}
   public Instruccion restaR() {return IRESTAR;}
   public Instruccion div() {return IDIV;}
   public Instruccion divR() {return IDIVR;}
   public Instruccion mod() {return IDIV;}
   public Instruccion modR() {return IDIVR;}
   public Instruccion and() {return IAND;}
   public Instruccion not() {return INOT;}
   public Instruccion mayor() {return IMAYOR;}
   public Instruccion mayorR() {return IMAYORR;}
   public Instruccion mayorS() {return IMAYORS;}
   public Instruccion menor() {return IMENOR;}
   public Instruccion menorR() {return IMENORR;}
   public Instruccion menorS() {return IMENORS;}
   public Instruccion igual() {return IIGUAL;}
   public Instruccion igualR() {return IIGUALR;}
   public Instruccion igualS() {return IIGUALS;}
   public Instruccion mayorIgual() {return IMAYORIGUAL;}
   public Instruccion mayorIgualR() {return IMAYORIGUALR;}
   public Instruccion mayorIgualS() {return IMAYORIGUALS;}
   public Instruccion menorIgual() {return IMENORIGUAL;}
   public Instruccion menorIgualR() {return IMENORIGUALR;}
   public Instruccion menorIgualS() {return IMENORIGUALS;}
   public Instruccion write() {return IWRITE;}
   public Instruccion writeR() {return IWRITER;}
   public Instruccion writeS() {return IWRITES;}
   public Instruccion read() {return IREAD;}
   public Instruccion readR() {return IREADR;}
   public Instruccion readS() {return IREADS;}
   public Instruccion apilaInt(int val) {return new IApilaInt(val);}
   public Instruccion apilaBool(boolean val) {return new IApilaBool(val);}
   public Instruccion apilaReal(double val) {return new IApilaReal(val);}
   public Instruccion apilaString(String val) {return new IApilaString(val);}
   public Instruccion apilad(int nivel) {return new IApilad(nivel);}
   public Instruccion apilaInd() {return IAPILAIND;}
   public Instruccion desapilaInd() {return IDESAPILAIND;}
   public Instruccion mueve(int tam) {return new IMueve(tam);}
   public Instruccion irA(int dir) {return new IIrA(dir);}
   public Instruccion irF(int dir) {return new IIrF(dir);}
   public Instruccion irInd() {return IIRIND;}
   public Instruccion alloc(int tam) {return new IAlloc(tam);} 
   public Instruccion dealloc(int tam) {return new IDealloc(tam);} 
   public Instruccion activa(int nivel,int tam, int dirretorno) {return new IActiva(nivel,tam,dirretorno);}
   public Instruccion desactiva(int nivel, int tam) {return new IDesactiva(nivel,tam);}
   public Instruccion desapilad(int nivel) {return new IDesapilad(nivel);}
   public Instruccion dup() {return IDUP;}
   public Instruccion stop() {return ISTOP;}
   public void ponInstruccion(Instruccion i) {
      codigoP.add(i); 
   }

   private int tamdatos;
   private int tamheap;
   private int ndisplays;
   public MaquinaP(int tamdatos, int tampila, int tamheap, int ndisplays) {
      this.tamdatos = tamdatos;
      this.tamheap = tamheap;
      this.ndisplays = ndisplays;
      this.codigoP = new ArrayList<>();  
      pilaEvaluacion = new Stack<>();
      datos = new Valor[tamdatos+tampila+tamheap];
      this.pc = 0;
      ISUMA = new ISuma();
      IAND = new IAnd();
      IMUL = new IMul();
      IAPILAIND = new IApilaind();
      IDESAPILAIND = new IDesapilaind();
      IIRIND = new IIrind();
      IDUP = new IDup();
      ISTOP = new IStop();
      gestorPilaActivaciones = new GestorPilaActivaciones(tamdatos,(tamdatos+tampila)-1,ndisplays); 
      gestorMemoriaDinamica = new GestorMemoriaDinamica(tamdatos+tampila,(tamdatos+tampila+tamheap)-1);
   }
   public void ejecuta() {
      while(pc != codigoP.size()) {
          codigoP.get(pc).ejecuta();
      } 
   }
   public void muestraCodigo() {
     System.out.println("CodigoP");
     for(int i=0; i < codigoP.size(); i++) {
        System.out.println(" "+i+":"+codigoP.get(i));
     }
   }
   public void muestraEstado() {
     System.out.println("Tam datos:"+tamdatos);  
     System.out.println("Tam heap:"+tamheap); 
     System.out.println("PP:"+gestorPilaActivaciones.pp());      
     System.out.print("Displays:");
     for (int i=1; i <= ndisplays; i++)
         System.out.print(i+":"+gestorPilaActivaciones.display(i)+" ");
     System.out.println();
     System.out.println("Pila de evaluacion");
     for(int i=0; i < pilaEvaluacion.size(); i++) {
        System.out.println(" "+i+":"+pilaEvaluacion.get(i));
     }
     System.out.println("Datos");
     for(int i=0; i < datos.length; i++) {
        System.out.println(" "+i+":"+datos[i]);
     }
     System.out.println("PC:"+pc);
   }
   
   public static void main(String[] args) {
       MaquinaP m = new MaquinaP(5,10,10,2);
        
          /*
            int x;
            proc store(int v) {
             x = v
            }
           &&
            call store(5)
          */
            
       
       m.ponInstruccion(m.activa(1,1,8));
       m.ponInstruccion(m.dup());
       m.ponInstruccion(m.apilaInt(0));
       m.ponInstruccion(m.suma());
       m.ponInstruccion(m.apilaInt(5));
       m.ponInstruccion(m.desapilaInd());
       m.ponInstruccion(m.desapilad(1));
       m.ponInstruccion(m.irA(9));
       m.ponInstruccion(m.stop());
       m.ponInstruccion(m.apilaInt(0));
       m.ponInstruccion(m.apilad(1));
       m.ponInstruccion(m.apilaInt(0));
       m.ponInstruccion(m.suma());
       m.ponInstruccion(m.mueve(1));
       m.ponInstruccion(m.desactiva(1,1));
       m.ponInstruccion(m.irInd());       
       m.ejecuta();
       m.muestraEstado();
   }
}
