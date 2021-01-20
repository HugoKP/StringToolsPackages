//Arquivo StringMask.java criado a partir de 17 de julho de 2018 - JDK1.8
package br.com.hkp.classes.stringtools;

/**
 * Esta classe implementa um meio de configurar uma string (configuravel no 
 * construtor da classe) que serve como uma "mascara de formatacao" de outras
 * strings de caracteres.
 * <p>
 * Exemplo: suponha que seja necessario formatar o numero de tel. celular 
 * 21977553311 na string "+55(xx)x xxxx-xxxx" para que se obtenha 
 * "+55(21)9 7755-3311"... Ou seja, um numero de telefone cel. formatado de 
 * modo que facilite a leitura e visualizacao. Um objeto dessa classe fornece os
 * meios para que se faca isso. Assim como numeros de CEP, CPF, CNPJ, codigo de
 * barras, e o que mais se queira.
 * <p>
 * Uma string de configuracao eh passada para um dos construtores da classe.Esta 
 * string configura a mascara que ira formatar os argumentos strings passados 
 * para o metodo merge() {@link #merge(java.lang.String)}.
 * No construtor que recebe apenas uma string de formatacao, para indicar os 
 * caracteres que nao devem ser sobrescritos na mascara, deve-se escreve-los na
 * string de formatacao entre '%' ( simbolos de porcentagem ). Todos os outros 
 * caracteres fora dos delimitadores ('%') serao substituidos pelos da string
 * passada como argumento ao metodo merge()
 * <p>
 * Exemplo: para criar uma mascara como "+55(__)______-____", poderia ser 
 * passado ao construtor a seguinte string de formatacao:
 * <p>
 * <b>"%+55(%__%)%_%_%____%-%____"</b>
 * <p>
 * Obs: o caractere "sublinhado" (_) esta representando o espaco em branco.
 * <p>
 * Essa formatacao especifica caracteres que nao serao sobrescritos quando
 * a mascara for mesclada com qualquer outra string, e tambem os caracteres
 * que serao sobrescritos quando for realizada a mescla pelo metodo merge().
 * <p>
 * O caractere % marca o inicio de um bloco nao-sobrescritivel, e tambem o 
 * fim do bloco. Assim o boclo de caracteres "%+55(%", na string acima, 
 * configura um bloco nao-sobrescritivel na mascara do objeto. Eh sucedido,
 * no exemplo acima, por dois espacos que serao sobrescritos quando a mascara
 * for mesclada, e depois por %)%, indicando um parenteses que nao pode ser
 * sobrescrito.   
 * <p>
 * Para a mascara configurada por essa string, se o metodo merge() fosse chamado
 * como o argumento "21977553311", retornaria "+55(21)9 7755-331"
 * Porem qualquer caractere fora dos campos delimitados por % seria substituido
 * pelos caracteres do argumento enviado ao metodo merge(). 
 * <p>
 * Por exemplo, a string de configuracao:
 * <p>
 * <b>"%+55(%xx%)%x% %xxxx%-%xxxx"</b> resultaria no mesmo do exemplo anterior.
 * <p>
 * Todos os 'x' seriam substituidos pelos caracteres da string "21977553311",
 * se esta fosse passada como argumento ao metodo merge()
 * <p>
 * Caso se deseje que o proprio caractere delimitador de campo nao-sobrescretivel
 * - % - , seja ele mesmo incluido em um campo nao-sobrescretivel da mascara,
 * entao ele deve ser escrito na string de configuracao de mascara precedido do
 * caractere # ( também conhecido como tralha ). Dessa forma para configurar a
 * mascara "  % do total", poderia ser passado ao construtor a string:
 * <p>
 * <b>"xx%#% do total%" ou apenas "xx%#% do total"</b> 
 * <p>
 * Ambas configurariam a mesma mascara, e  merge("45") retornaria "45% do total".
 * <p>
 * Tambem se pode incluir o proprio # ( tralha ) em um campo nao-sobrescretivel
 * se ele for precedido de um outro # ( tralha ).
 * <p>
 * O outro construtor recebe como argumentos, uma String de configuracao e um 
 * caractere que indica os locais da mascara que serao sobrescritos pelos 
 * caracteres da String que sera mesclada com a mascara.
 * <p>
 * Exemplo: usando a String "(xx)x xxxx-xxxx" como mascara e o caractere 'x'
 * para indicar as posicoes da mascara que serao sobrescritas, um merge com 
 * a String "21911112222" resultaria em "(21)9 1111-2222"
 *   
 * @author Hugo Kaulino Pereira 
 * @version 1.0
 * @since 1.0
 */
public class StringMask
{
    private static final char HOLD_PLACE_CHAR = 0;
   
    private static final char SPECIAL_CHAR_1 = '%';
    private static final char SPECIAL_CHAR_2 = '%';
    private static final char SPECIAL_CHAR_3 = '#';
    private static final char BEGIN_NONOVERRIDE_INSERTING = SPECIAL_CHAR_1;
    private static final char END_NONOVERRIDE_INSERTING = SPECIAL_CHAR_2;
    private static final char INSERTING_SPECIAL_CHAR_FLAG = SPECIAL_CHAR_3;
    
    private static enum States 
    {
        INSERTING_HOLD_PLACE_CHARS,
        INSERTING_NONOVERRIDE_CHARS,
        INSERTING_SPECIAL_CHAR
    }

    private final String stringMask;
    /*[01]----------------------------------------------------------------------
    *       Construtor da classe que processa a string de formatacao
    --------------------------------------------------------------------------*/
    /**
     * Uma string de configuracao <b>formatter</b> eh passada para o  construtor
     * da classe.
     * A string configura a mascara que ira formatar os argumentos passados 
     * para o metodo merge() {@link #merge(java.lang.String)}. Para indicar os 
     * caracteres que nao devem ser sobrescritos na mascara, deve-se escreve-los na
     * string de formatacao entre '%' ( simbolos de porcentagem ). Todos os outros 
     * caracteres fora dos delimitadores ('%') serao substituidos pelos da string
     * passada como argumento ao metodo merge()
     * <p>
     * Exemplo: para criar uma mascara como "+55(__)______-____", poderia ser 
     * passado ao construtor a seguinte string de formatacao:
     * <p>
     * <b>"%+55(%__%)%_%_%____%-%____"</b>
     * <p>
     * Obs: o caractere "sublinhado" (_) esta representando o espaco em branco.
     * <p>
     * Essa formatacao especifica caracteres que nao serao sobrescritos quando
     * a mascara for mesclada com qualquer outra string, e tambem os caracteres
     * que serao sobrescritos quando for realizada a mescla pelo metodo merge().
     * <p>
     * O caractere % marca o inicio de um bloco nao-sobrescritivel, e tambem o 
     * fim do bloco. Assim o boclo de caracteres "%+55(%", na string acima, 
     * configura um bloco nao-sobrescritivel na mascara do objeto. Eh sucedido,
     * no exemplo acima, por dois espacos que serao sobrescritos quando a mascara
     * for mesclada, e depois por %)%, indicando um parenteses que nao pode ser
     * sobrescrito.   
     * <p>
     * Para a mascara configurada por essa string, se o metodo merge() fosse chamado
     * como o argumento "21977553311", retornaria "+55(21)9 7755-331"
     * Porem qualquer caractere fora dos campos delimitados por % seria substituido
     * pelos caracteres do argumento enviado ao metodo merge(). 
     * <p>
     * Por exemplo, a string de configuracao:
     * <p>
     * <b>"%+55(%xx%)%x% %xxxx%-%xxxx"</b> resultaria no mesmo do exemplo anterior.
     * <p>
     * Todos os 'x' seriam substituidos pelos caracteres da string "21977553311",
     * se esta fosse passada como argumento ao metodo merge()
     * <p>
     * Caso se deseje que o proprio caractere delimitador de campo nao-sobrescretivel
     * - % - , seja ele mesmo incluido em um campo nao-sobrescretivel da mascara,
     * entao ele deve ser escrito na string de configuracao de mascara precedido do
     * caractere # ( também conhecido como tralha ). Dessa forma para configurar a
     * mascara "  % do total", poderia ser passado ao construtor a string:
     * <p>
     * <b>"xx%#% do total%" ou apenas "xx%#% do total"</b> 
     * <p>
     * Ambas configurariam a mesma mascara, e  merge("45") retornaria "45% do total".
     * <p>
     * Tambem se pode incluir o proprio # ( tralha ) em um campo nao-sobrescretivel
     * se ele for precedido de um outro # ( tralha ). 
     * 
     * @param formatter A string que eh processada pelo construtor para definir
     * a mascara de formatacao.
     * @since 1.0
     */
    public StringMask (String formatter)
    {
        int formatterLength = formatter.length();
        int insertionPosition = 0;
        
        char[] mask = new char[formatterLength];

        char h;
        States state = States.INSERTING_HOLD_PLACE_CHARS;

        for (int c = 0; c < formatterLength; c++)
        {
           h = formatter.charAt(c);

           switch (state)
           {
                case INSERTING_HOLD_PLACE_CHARS:

                    if (h == BEGIN_NONOVERRIDE_INSERTING)
                        state = States.INSERTING_NONOVERRIDE_CHARS;
                    else 
                    { 
                        mask[insertionPosition] = HOLD_PLACE_CHAR;
                        insertionPosition++;
                    };
                    break;

                case INSERTING_NONOVERRIDE_CHARS:

                    if (h == END_NONOVERRIDE_INSERTING)
                        state = States.INSERTING_HOLD_PLACE_CHARS;
                    else if (h == INSERTING_SPECIAL_CHAR_FLAG)
                        state = States.INSERTING_SPECIAL_CHAR;
                    else 
                    {
                        mask[insertionPosition] = h;
                        insertionPosition++; };
                    break;

                case INSERTING_SPECIAL_CHAR:

                    mask[insertionPosition] = h;
                    insertionPosition++;
                    state = States.INSERTING_NONOVERRIDE_CHARS;

           } // fim de swicth  

        }// fim do for
                 
        stringMask =  String.valueOf(mask).substring(0,insertionPosition);

    }// fim do construtor StringMask
    
    /*[02]----------------------------------------------------------------------
    *                      Construtor da classe
    --------------------------------------------------------------------------*/
    /**
     * Esse construtor recebe como argumentos, uma String de configuracao e um 
     * caractere que indica os locais da mascara que serao sobrescritos pelos 
     * caracteres da String que sera mesclada com a mascara.
     * <p>
     * Exemplo: usando a String "(xx)x xxxx-xxxx" como mascara e o caractere 'x'
     * para indicar as posicoes da mascara que serao sobrescritas, um merge com 
     * a String "21911112222" resultaria em "(21)9 1111-2222"
     * 
     * @param formatter A mascara
     * @param holdPlace Indica qual caractere na mascara sera sobrescrito.
     * @since 1.0
     */
    public StringMask(String formatter, char holdPlace)
    {
        stringMask = formatter.replace(holdPlace,HOLD_PLACE_CHAR);
    }//fim do construtor StringMask
    
   /*[03]----------------------------------------------------------------------
    *       Mescla a string passada com a mascara do objeto StringMask
    --------------------------------------------------------------------------*/
    /**
     * Retorna uma string com o argumento <b>s</b> formatado pela mascara 
     * configurada pelo construtor da classe. Se o argumento passado tiver
     * menos caracteres que os espacos disponiveis para serem preenchidos na
     * mascara, entao espacos disponiveis restantes ficarao em branco. Caso o
     * argumento passado tenha mais caracteres que os espacos a serem preenchidos
     * na mascara, entao o argumento sera truncado quando mesclado com a mascara.
     * <p>
     * Exemplo:
     * <p>
     * Para a mascara configurada com <b>"%[%xxxx%]%"</b> , merge("1234")
     * retornaria "[1234]"
     * <p>
     * Porem merge("12") retornaria "[12__]"
     * <p>
     * E merge("123456789") retornaria "[1234]"
     * 
     * @param s O argumento a ser formatado pela mascara
     * @return  Uma string com o argumento s formatado.
     * @since 1.0
     */
    public String merge(String s)
    {
       return StringTools.merge(HOLD_PLACE_CHAR, stringMask, s, true);
    }//fim de merge()
    
   /*[04]----------------------------------------------------------------------
    *    Retorna a mascara configurada para uma determinada instancia dessa
    *    classe.
    --------------------------------------------------------------------------*/
    /**
     * Retorna a mascara que foi configurada no construtor da classe.
     * 
     * @return A mascara que sera utizada pelo metodo merge()
     * 
     * @since 1.0
     */
    public String getMask()
    {
       return stringMask.replace((char)0 ,' ').trim();
    }//fim do metodo getMask
            
    /**
     * Roda um exemplo de utilizacao da classe
     * 
     * @param args Linha de comando
     */
    public static void main(String[] args)
    {
       StringMask sm;  StringMask sm2;  StringMask sm3;
       System.out.print("Cria um objeto StringMask com a mascara: ");
       System.out.println("+55 031(xx) x xxxx-xxxx");
       System.out.println();
       System.out.print("Usando a string de configuracao: ");
       System.out.println("new StringMask(\"%+55 031(%xx%) %x% %xxxx%-%xxxx\")");
       
       sm = new StringMask("%+55 031(%xx%) %x% %xxxx%-%xxxx");
       
       
       System.out.print("\nA mascara que foi configurada: ");
       System.out.println(sm.getMask());
       System.out.println();
       
       System.out.println("Exemplos de retorno de merge() para alguns argumentos...\n");
       System.out.println("merge(\"21977553311\") -> "+sm.merge("21977553311"));
       System.out.println("merge(\"21\") -> "+sm.merge("21"));
       System.out.println("merge(\"21977553311_000\") -> "+sm.merge("21977553311_000"));
       
       
       System.out.println("\nCria um objeto StringMask com a mascara vazia");
       
       sm2 = new StringMask("");
       
       System.out.println("\nEntao merge com qualquer argumento retorna string vazia.");
       System.out.println("merge(\"String qualquer\") -> " + sm2.merge("String qualquer"));
       
       
       System.out.println("\nCria objeto com mascara sem espacos sobrescretiveis...");
       System.out.print("\nUsando a string de configuracao: ");
       System.out.println("new StringMask(\"%Esta e a mascara que sera usada%\")");
       sm3 = new StringMask("%Esta e a mascara que sera usada%");
       
       System.out.println("\nEntao merge com qualquer argumento retorna a propria mascara");
       System.out.println("merge(\"String qualquer\") -> " + sm3.merge("String qualquer"));
       
       System.out.print("\n\nCria um objeto StringMask com a mascara: ");
       System.out.println("+55 031(xx) x xxxx-xxxx usando agora o segundo construtor"   );
       System.out.println();
       System.out.print("Usando a string de configuracao: ");
       System.out.println("new StringMask(\"+55 031(xx) x xxxx-xxxx\",'x')");
       
       sm = new StringMask("+55 031(xx) x xxxx-xxxx",'x');
       
       
       System.out.print("\nA mascara que foi configurada: ");
       System.out.println(sm.getMask());
       System.out.println();
       
       System.out.println("Exemplos de retorno de merge() para alguns argumentos...\n");
       System.out.println("merge(\"21977553311\") -> "+sm.merge("21977553311"));
       System.out.println("merge(\"21\") -> "+sm.merge("21"));
       System.out.println("merge(\"21977553311_000\") -> "+sm.merge("21977553311_000"));
       
       
       System.out.println("\nCria um objeto StringMask com a mascara vazia");
       
       sm2 = new StringMask("",'x');
       
       System.out.println("\nEntao merge com qualquer argumento retorna string vazia.");
       System.out.println("merge(\"String qualquer\") -> " + sm2.merge("String qualquer"));
       
       
       System.out.println("\nCria objeto com mascara sem espacos sobrescretiveis...");
       System.out.print("\nUsando a string de configuracao: ");
       System.out.println("new StringMask(\"Esta e a mascara que sera usada\",'x')");
       sm3 = new StringMask("Esta e a mascara que sera usada",'x');
       
       System.out.println("\nEntao merge com qualquer argumento retorna a propria mascara");
       System.out.println("merge(\"String qualquer\") -> " + sm3.merge("String qualquer"));
       
          
    }//fim de main()
  
          
}// fim da classe StringMask

