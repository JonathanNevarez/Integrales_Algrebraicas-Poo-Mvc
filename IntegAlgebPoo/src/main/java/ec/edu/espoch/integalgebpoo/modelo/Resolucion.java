package ec.edu.espoch.integalgebpoo.modelo;

public class Resolucion {
    //Atributos
    private String valorFuncion = "";
    private String[] terminosSeparados;
    private Integer numCoeficiente = 0;  
    private Integer denCoeficiente = 0;  
    private Integer numExponente = 0;
    private Integer denExponente = 0;
    private Integer newNumCoeficiente = 0; 
    private Boolean hayX;
    private String resultadaTermino = "";
    private String resultado = "";
    
    //Constructor
    public Resolucion() { 
    }
    
    //Getter
    public String getResultado() {
        return resultado;
    }

    public void setValorFuncion(String valorFuncion) {
        this.valorFuncion = valorFuncion;
    }
    
    //Metodos
    public static int calcularMCD(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    } 
    
    public void instanciarFuncion(){
        this.terminosSeparados = this.valorFuncion.split(" ");
    }    

    public void obtenerValores(){
        this.resultado = "";
        try {
            for (String term:terminosSeparados){
            hayX = false;
            if(term.contains("x")){
                hayX = true;
                String c = term.substring(0, term.indexOf("x"));
                if (c.isEmpty() || c.equals("+")) {
                    this.numCoeficiente = 1;
                    this.denCoeficiente = 1;  
                } else if (c.equals("-")) {
                    this.numCoeficiente = -1;
                    this.denCoeficiente = 1;
                } else {
                    if(c.contains("/")){
                        String[] partes = c.split("/");
                        this.numCoeficiente = Integer.valueOf(partes[0]);
                        this.denCoeficiente = Integer.valueOf(partes[1]); 
                    }else{
                        this.numCoeficiente = Integer.valueOf(c);
                        this.denCoeficiente = 1;
                    }
                }
                if (term.contains("^")) {
                    String e = term.substring(term.indexOf("^") + 1);
                    if(e.contains("/")){
                        String[] partes = e.split("/");
                        this.numExponente = Integer.valueOf(partes[0]);
                        this.denExponente = Integer.valueOf(partes[1]); 
                    }else{
                        this.numExponente = Integer.valueOf(e);
                        this.denExponente = 1;
                    }
                } else {
                    this.numExponente = 1;
                    this.denExponente = 1;
                }  
            }else{
                hayX = false;
                if(term.contains("/")){
                    String[] partes = term.split("/");
                    this.numCoeficiente = Integer.valueOf(partes[0]);
                    this.denCoeficiente = Integer.valueOf(partes[1]); 
                }else{
                    this.numCoeficiente = Integer.valueOf(term);
                    this.denCoeficiente = 1;
                }
            }
            integrarTermino();
            concatenarResultado();
        }
        } catch (NumberFormatException e) {
            this.resultado = "Error!! verifique la funcion ingresada";
        }       
    }
    
    public String procedimiento1(){
        return "∫(" + this.valorFuncion + ")dx";
    }
    
    public String procedimiento2(){
        String vProcedimiento2 = "";
        for(String term:terminosSeparados){
            switch (term.charAt(0)) {
                case '-' ->                     {
                        String newTerm = term.substring(1);
                        vProcedimiento2 += "-∫(" + newTerm + ")dx ";
                    }
                case '+' ->                     {
                        String newTerm = term.substring(1);
                        vProcedimiento2 += "+∫(" + newTerm + ")dx ";
                    }
                default -> vProcedimiento2 += "∫(" + term + ")dx ";
            }
        }
        return vProcedimiento2;
    }    
    
    public void integrarTermino(){
        if (hayX) {
            this.newNumCoeficiente = this.numCoeficiente * this.denExponente;
            int newNumExponente = this.numExponente + this.denExponente;
            int newDenCoeficiente = this.denCoeficiente * newNumExponente;
            int newDenExponente = this.denExponente;

            int mcdCoe = calcularMCD(this.newNumCoeficiente, newDenCoeficiente);
            this.newNumCoeficiente /= mcdCoe;
            newDenCoeficiente /= mcdCoe;
            
            int mcdExp = calcularMCD(newNumExponente, newDenExponente);
            newNumExponente /= mcdExp;
            newDenExponente /= mcdExp;

            this.newNumCoeficiente = (newDenCoeficiente < 0) ? -this.newNumCoeficiente : this.newNumCoeficiente*1;
            newDenCoeficiente = (newDenCoeficiente < 0) ? -newDenCoeficiente : newDenCoeficiente;
            newNumExponente = (newDenExponente < 0) ? -newNumExponente : newNumExponente;
            newDenExponente = (newDenExponente < 0) ? -newDenExponente : newDenExponente;
            
            String coeficienteParte = (this.newNumCoeficiente == 1) ? "" : (this.newNumCoeficiente == -1) ? "-" : this.newNumCoeficiente + "";
            String xParte = (newNumExponente == 1) ? "x" : "x^" + newNumExponente;

            if (newDenCoeficiente == 1) {
                if(newDenExponente == 1){
                    this.resultadaTermino = coeficienteParte + xParte;
                }else{
                    this.resultadaTermino = coeficienteParte + xParte + "/" + newDenExponente;

                }
            } else {
                if(newDenExponente == 1){
                    this.resultadaTermino = "(" + coeficienteParte + xParte + ")/" + newDenCoeficiente;
                }else{
                    this.resultadaTermino = "(" + coeficienteParte + xParte + "/" + newDenExponente + ")/" + newDenCoeficiente;
                }
            }
        }else{
            newNumCoeficiente = numCoeficiente;
            this.resultadaTermino = (numCoeficiente == 1 && denCoeficiente == 1)? "x" : (numCoeficiente == -1 && denCoeficiente == 1 || numCoeficiente == 1 && denCoeficiente == -1)? "-x" : (numCoeficiente != 1 && denCoeficiente == 1)? numCoeficiente + "x" : numCoeficiente + "x/" + denCoeficiente; 
        }
    }
    
    public void concatenarResultado(){
        if(this.newNumCoeficiente >0){
            this.resultado += "+" + this.resultadaTermino + "  ";   
        }else{
            String newResultadaTermino;
            if(this.resultadaTermino.contains("(")){
                newResultadaTermino = this.resultadaTermino.substring(0, 1)+ this.resultadaTermino.substring(2);
            }else{
                newResultadaTermino = this.resultadaTermino.substring(1);
            }
            this.resultado += "-" + newResultadaTermino + "  ";
        }
    } 
}
