/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package calculadora;

/**
 *
 * @author mariameraz
 */
public class PostFijo {
    //Atributo
    private String cadena;
    //Constructores
    public PostFijo(){
        
    }
    public PostFijo(String cadena){
        this.cadena = cadena;
    }
    //Metodos  
    public static String[] obtenTokens(String cadena){
        return cadena.split(" ");
        /*
         * Devuelve la cadena dentro de celdas con separacion en cada espacio 
        */
    }
    private static boolean noEsOperador(String dato){
        /*
        Evalua si los caracteres dentro de la cadena es o no un operador
        */
        return !dato.equals("+") && !dato.equals("-") && !dato.equals("*")
                && !dato.equals("/");
    }
    private static int prioridad(String dato){
        int resultado = 0; // En caso de que el dato sea un paréntesis izquierdo
        /*
        Evalua la prioridad del operador segun la jerarquía de operaciones
        */
        switch (dato.charAt(0)){
            case '+':
            case '-': resultado = 1;
                break;
            case '*':
            case '/': resultado = 2;
        }
        return resultado;
    }
    public static String[] conviertePostFija(String cadena){
        String[] elementos = obtenTokens(cadena);
        String[] postFija = new String[elementos.length];
        PilaADT <String> pila = new PilaA();
        int e, p, n;
        n= elementos.length;
        e=0;
        p=0;
        while(e<n){
            if(elementos[e].equals("(")){
                /*
                Si el elemento de la cadena es un parentesis de entrada,
                se introduce el caracter a la pila
                */ 
                 pila.push(elementos[e]);     
            }else if(elementos[e].equals(")")){
                /*
                En caso de no ser un parentesis de entrada, sino que uno 
                de salida, mientras no se halle en la pila un parentesis 
                de entrada, se introduce en la casilla correspondiente el ultimo
                caracter de la pila y se aumenta el numero de casillas ocupadas
                dentro del arreglo postFija
                */
                while (!pila.peek().equals("(")){
                        postFija[p] = pila.pop();
                        p++;
                    }
                    pila.pop();
            }else if(noEsOperador(elementos[e])){
                postFija[p] = elementos[e];
                p++;
            }else{
                while(!pila.isEmpty() && prioridad(pila.peek()) > prioridad(elementos[e])){
                        postFija[p] = pila.pop();
                        p++;
                }
                pila.push(elementos[e]);
            }
          e++;
        }
        while (!pila.isEmpty()){
            postFija[p] = pila.pop();
            p++;
        }
        return postFija;
    }
    public static double evalua(String postfija[]){
        PilaADT<Double> pila = new PilaA();
        double resul, op1, op2;
        int i;
        
        resul = 0;
        i = 0;
        while (i < postfija.length && postfija[i] != null){
            if (noEsOperador(postfija[i])) // Es operando
                pila.push(Double.valueOf(postfija[i]));
            else { // Es operador
                op2 = pila.pop();
                op1 = pila.pop();
                switch (postfija[i].charAt(0)){
                    case '+' -> resul = op1 + op2;
                    case '-' -> resul = op1 - op2;
                    case '*' -> resul = op1 * op2;
                    case '^' -> {
                        double pot = op1;
                        for(int j=0;j<=op2;j++){
                            pot = (pot*op1);
                        }
                        resul = pot;
                    }    
                    case '/' -> {
                        if (op2 == 0) // Si el denominador es 0 se lanza una excepción
                            throw new RuntimeException();
                        resul = op1 / op2;
                    }
                }
                pila.push(resul);                        
                }
            i++;
        }
          return pila.pop();            
        }
}
