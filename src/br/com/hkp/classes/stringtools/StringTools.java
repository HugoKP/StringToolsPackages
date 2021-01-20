/*
arquivo StringTools.java criado a partir de 25 de julho de 2018
*/
package br.com.hkp.classes.stringtools;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import br.com.hkp.classes.localetools.LocaleTools;

/**
 * Metodos utilitarios para manipulacao de Strings. 
 * 
 * @author Hugo Kaulino Pereira
 * @since 1.0
 * @version 1.0 
 */
public class StringTools
{
   
    /**
     * Constante representando o caractere quebra de linha.
     */
    public static final String LINEFEED = "\n";

    /**
     * Constante para o caractere backspace
     */
    public static final String BACKSPACE = "\b";

    /**
     * Constante para o caractere quebra de formulario
     */
    public static final String FORMFEED = "\f";

    /**
     * Constante para o caractere retorno de carro.
     */
    public static final String CR = "\r";

    /**
     * Constante para o caractere de tabulacaoj
     */
    public static final String TAB ="\t";
    
    /**
     * Retorna uma String composta por howManyTimes repeticoes do caractere c
     * 
     * @param c O caractere 
     * @param howManyTimes Quantos caracteres compoem a String
     * @return A String com howManyTimes caracteres c
     * 
     * @since 1.0
     */
    /*[01]----------------------------------------------------------------------
    *   Retorna uma String composta por howManyTimes repeticoes do caractere c
    --------------------------------------------------------------------------*/
    public static String repeat(char c, int howManyTimes)
    {
        char[] array = new char[howManyTimes];
        Arrays.fill(array,c);
        return new String(array); 
    }//fim de repeat()
 
    /**
     * Retorna a String passada como argumento repetida howManyTimes
     * <p>
     * Exemplo repeat("bla",3) retorna "blablabla"
     * 
     * @param s A String
     * @param howManyTimes Quantas repeticoes
     * @return Repeticoes da String passada como argumento para o metodo.
     * 
     * @since 1.0
     */
    /*[02]----------------------------------------------------------------------
    *   Concatena uma determinada string howManyTimes em uma string vazia
    --------------------------------------------------------------------------*/
    public static String repeat(String s, int howManyTimes)
    {
        StringBuilder result = new StringBuilder(s.length()*howManyTimes);
        for (int c = 1; c <= howManyTimes; c++)
            result.append(s);
        return result.toString(); 
    }//fim de repeat()
    
   /**
    * O metodo recebe uma String <b>s</b> e retorna uma copia com seus 
    * caracteres agrupados em sequencias de <b>g</b> caracteres, separados pelo 
    * caractere  passado como argumento ao paramentro <b>separator</b>.
    * <p>
    * Exemplo: para <b>s</b> = "123456", com ponto como <b>separator</b> e
    * <b>g</b> = 3, o metodo retornara "123.456", agrupando de 3 em 3, comecando
    * da esquerda para a direita. 
    * <p>
    * Para que os agrupamentos sejam feitos da direita para a esquerda, o valor
    * do argumento <b>g</b> deve ser negativo. De forma que -3 agruparia de 3 em
    * 3 a partir do ultimo caractere da String retornada.
    * <p>
    * Exemplo: para <b>s</b> = "12345678", com ponto como <b>separator</b> e
    * <b>g</b> = -3, o metodo retornara "12.345.678", agrupando de 3 em 3, 
    * comecando da direita e indo para a esquerda. Agrupando da esquerda para a
    * direita obteriamos "123.456.78"
    * <p>
    * Se <b>g</b> for igual a zero ou maior ou igual ao numero de caracteres
    * da String <b>s</b>, entao o metodo retorna a propria String <b>s</b>
    * 
    * @param s A String a ser formatada
    * @param g O numero de caracteres em cada agrupamento. Valor negativo agrupa
    * da direita para a esquerda.
    * @param separator O caractere que sera usado para separar os agrupamentos
    * 
    * @return Uma copia da String <b>s</b> formatada.
    * 
    * @since 1.0
    */ 
   /*[03]----------------------------------------------------------------------
   *               Formata com agrupamentos uma string 
   ---------------------------------------------------------------------------*/
   public static String group(String s, int g , char separator)
   {
       int length = s.length();
       //se o numero de caracteres em cada agrupamento eh maior ou igual
       //que o numero de caracteres em s entao nao ha o que agrupar. Ou
       //se o numero de caracteres em cada agrupamento eh passado como
       //zero ou se a string s eh vazia.
       if ((g == 0) || (length-Math.abs(g)) <= 0) return s;
       
       StringBuilder sb = new StringBuilder(s);
       
       //define a posicao de insercao do ultimo separador (mais a direita)
       int r = length % g; 
       if ((g < 0) || (r == 0))
       {
           if (g < 0) g *= -1;
           r = g;
       }
       
       //insere separadores comecando do ultimo (mais a direita) ate o primeiro
       for (int i = (length - r); i > 0; i -= g) sb.insert(i, separator);
             
       return sb.toString();
       
   }//fim de group()
   
   /**
    * O metodo aplica uma mascara a uma string. Por exemplo, se a string for
    * um numero de telefone com codigo de area como 21977775555 e a mascara 
    * uma formatacao para o numero, como "(xx)x xxxx-xxxx", o merge produzira
    * (21)9 7777-5555. Com 'x' na mascara sendo os espacos que seriam 
    * substituidos pelos caracteres do argumento do parametro <b>st</b>, e sendo
    * passado ao metodo atraves do parametro <b>holdPlace</b>
    * <p>
    * Caso a string nao preencha todo o campo da mascara os espacos nao
    * preenchidos ficarao como brancos. Exemplo: "21" para a mascara acima 
    * produziria (21)______-____ ( O sublinhado representando os brancos )
    * <p>
    * Porem se a string transbordar a mascara, o merge sera truncado se o 
    * argumento do parametro <b>trunc</b> for passado como true, e tranbordara
    * se este argumento for passado como false.
    * Exemplo: "21900001111nnnn"  retornaria (21)9 0000-1111 para <b>trunc</b>
    * igual a true e retrornaria (21)9 0000-1111nnnn para o mesmo argumento 
    * passado como false.
    * <p>
    * Se <b>st</b> for vazia a propria mascara eh retornada. Se o argumento do
    * parametro <b>mask</b> nao contiver nenhum caractere igual ao passado pelo
    * argumento de <b>holdPlace</b>, entao essa mascara nao possui espacos a 
    * serem preenchidos, portanto o metodo retornaria a propria mascara se o 
    * argumento de <b>trunc</b> for true, ou, para <b>trunc</b> false, a mascara
    * ooncatenada a string passada pelo argumento do paramentro <b>st</b>.
    * Exemplo: para <b>holdPlace</b> = 'x' , <b>mask</b> = "MASCARA" e <b>st</b>
    * = "STRING", o metodo retornara "MASCARA" para <b>trunc</b> true e 
    * "MASCARASTRING" para <b>trunc</b> false.
    * 
    * @param holdPlace O caracetere que deve ser substituido na mascara pelos
    * caracteres da String passada como argumento do paramentro <b>st</b>
    * @param mask A mascara 
    * @param st A string a ser mesclada com a mascara
    * @param trunc Se true, o merge eh truncado apos o ultimo caractere da 
    * mascara. Se false o merge retorna a mascara mais os caracteres do 
    * argumento passado ao parametro <b>st</b> que transbordarem da mascara.
    * 
    * @return A mescla da mascara com a String passada como argumento ao
    * parametro <b>st</b>
    * 
    * @since 1.0
    */
   /*[04]----------------------------------------------------------------------
   *           Formata uma String com uma outra String como mascara
   ---------------------------------------------------------------------------*/
   public static String merge(char holdPlace, String mask, String st,
                              boolean trunc)
   {
       int maskLength = mask.length(); 
       //cria uma StringBuilder onde caiba o merge da mascara com st
       StringBuilder sb = new StringBuilder( maskLength + st.length() );
       
       sb.append(st); int sbLength = sb.length();
      
       for (int i = 0; i < maskLength; i++)
       {
           //insere caractere de mascara
           if (mask.charAt(i) != holdPlace) 
           {
               sb.insert(i, mask.charAt(i)); sbLength++;
           }
           //quando a String st nao preenche toda a mascara os espacos vazios
           //sao preenchidos com brancos
           else if (i == sbLength)
           {
               sb.insert(i,' '); sbLength++;
           }
       }//fim do for
       
       //se trunc = true exlcui os caracteres de st que transbordaram da
       //mascara. Caso tenha havido transbordamento.
       if (trunc) sb.setLength(maskLength);
            
       return sb.toString();
   }//fim de merge()
    
     /**
    * O metodo pode retornar uma string como s porem filtrada sem os caracteres
    * contidos no conjunto charSet ou, ao contario, uma string como s mas SO
    * com os caracteres que tambem estao contidos no conjunto. Ou seja,
    * filtrada dos caracteres que nao pertencam ao conjunto charSet.
    * <p>
    * O parametro includeWhenInCharSet determina se os caracteres contidos em
    * charset serao retirados da copia da string s, ou se serao retirados  
    * apenas caracteres que NAO pertencerem a charSet. Quando true apenas os
    * caracteres de s que tambem pertencerem a charSet serao copiados para a
    * string retornada. Quando false apenas os caracteres que NAO pertencerem
    * a charSet serao copiados. Exemplo: para s = "A0B1C2" e charset = "012"
    * se includeWhenInCharSet = true retornaria "012", se false retornaria
    * "ABC".
    * 
    * @param s A string a ser filtrada
    * @param charSet HashSet com o conjunto de caracteres que serao retirados 
    * ou mantidos na string retornada pelo metodo
    * @param includeWhenInCharSet Quando true apenas os caracteres de s que 
    * tambem pertencerem a charSet serao copiados para a string retornada.
    * Quando false apenas os caracteres que NAO pertencerem a charSet serao 
    * copiados. Exemplo: para s = "A0B1C2" e charset = "012" se
    * includeWhenInCharSet = true retornaria "012", se false retornaria "ABC".
    * @return A string "filtrada" pelos caracteres de charSet.
    * 
    * @since 1.0
    */
    /*[06]----------------------------------------------------------------------
    *            Filtra um conjunto de caracteres de uma string s. 
    --------------------------------------------------------------------------*/
    public static String filterString
                         ( 
                            String s,
                            HashSet < Character > charSet,
                            boolean includeWhenInCharSet
                         )
    {
        if (charSet.isEmpty())
             if (includeWhenInCharSet)
                return "";
            else
                return s;
        
        char[] includedChars = new char[s.length()];
       
        int index = 0;
        for (int i = 0; i < includedChars.length; i++)
        {
            char c = s.charAt(i);
            boolean cInSet = charSet.contains(c);
            if (
                   (cInSet && includeWhenInCharSet) 
                              || 
                   !(cInSet || includeWhenInCharSet)
                )
                includedChars[index++] = c;
        } 

        return ( new String(includedChars).substring(0,index) );
    }//fim de filterString()
    
    /**
    * O metodo pode retornar uma string como s porem filtrada sem os caracteres
    * contidos no conjunto charset ou, ao contario, uma string como s mas SO
    * com os caracteres que tambem estao contidos no conjunto. Ou seja,
    * filtrada dos caracteres que nao pertencam ao conjunto charset.
    * <p>
    * O parametro includeWhenInCharset determina se os caracteres contidos em
    * charset serao retirados da copia da string s, ou se serao retirados  
    * apenas caracteres que NAO pertencerem a charset. Quando true apenas os
    * caracteres de s que tambem pertencerem a charset serao copiados para a
    * string retornada. Quando false apenas os caracteres que NAO pertencerem
    * a charset serao copiados. Exemplo: para s = "A0B1C2" e charset = "012"
    * se includeWhenInCharset = true retornaria "012", se false retornaria
    * "ABC".
    * 
    * @param s A string a ser filtrada
    * @param charset A string com o conjunto de caracteres que serao retirados 
    * ou mantidos na string retornada pelo metodo
    * @param includeWhenInCharset Quando true apenas os caracteres de s que 
    * tambem pertencerem a charset serao copiados para a string retornada.
    * Quando false apenas os caracteres que NAO pertencerem a charset serao 
    * copiados. Exemplo: para s = "A0B1C2" e charset = "012" se
    * includeWhenInCharset = true retornaria "012", se false retornaria "ABC".
    * @return A string "filtrada" pelos caracteres de charset.
    * 
    * @since 1.0
    */
    /*[06]----------------------------------------------------------------------
    *           Filtra um conjunto de caracteres de uma string s. 
    --------------------------------------------------------------------------*/
    public static String filterString
                         (
                             String s,
                             String charset,
                             boolean includeWhenInCharset
                         )
    {
        int charsetLength = charset.length();
          
        HashSet<Character> charSet = new HashSet<Character>(4 * charsetLength);
        
        for (int i = 0; i < charsetLength; i++)
            charSet.add(charset.charAt(i));

        return ( filterString(s, charSet, includeWhenInCharset) );
    }//fim de filterString()  
                         
   /**
    * O metodo pode retornar uma string como s porem filtrada sem o caractere
    * passado como argumento ao parametro c.
    * <p>
    * Exemplo: para s = "A0B0C0" e c = '0' retornaria "ABC".
    * 
    * @param s A string a ser filtrada
    * @param c O caractere a ser removido da String retornada. 
    *  
    * @since 1.0
    */
    /*[07]----------------------------------------------------------------------
    *              Filtra um caractere de uma string s. 
    --------------------------------------------------------------------------*/
    public static String filterString(String s, char c)
    {
                
        char[] includedChars = new char[s.length()];
       
        int index = 0;
        for (int i = 0; i < includedChars.length; i++)
        {
            char current = s.charAt(i);
            if (current != c) includedChars[index++] = current;
        } 

        return ( new String(includedChars).substring(0,index) );
       
    }//fim de filterString()  
       
    /**
     * Formata uma string representando um valor de ponto flutuante, agrupando
     * os digitos da parte inteira com um caractere separador definido pelo
     * Locale passado como argumento ao metodo. Se houver zeros a esquerda na
     * parte inteira e zeros a direita na parte decimal, serao excluidos na
     * string formatada.
     * <p>
     * No argumento s o ponto decimal deve ser representado por um ponto '.' ou
     * pelo caractere de ponto decimal definido pelo Locale passado como 
     * argumento ao metodo. Caso contrario sera lancada uma 
     * NumberFormatException. Isto eh, se o caractere de ponto decimal nao for
     * um ponto, tem que ser o caractere usado pelo Locale l passado ao metodo.
     * 
     * @param s A String representando um valor que possa ser converdito em 
     * double. 
     * @param l O Locale indicando quais caracteres de agrupamento e ponto 
     * decimal serao utilizados. Se o ponto decimal no argumento s nao for
     * representado por um ponto '.' entao deve ser pelo caractere de ponto
     * decimal definido pelo Locale l.
     * @return Uma String formatada com caracteres de agrupamento para a parte
     * inteira.
     * @throws NumberFormatException Se a String s nao puder nao puder ser 
     * convertida em double.
     * @throws NullPointerException Se s for null.
     */
    /*[08]----------------------------------------------------------------------
    *      Faz o parsing de uma string representando um double, formatando 
    *      com caracteres de agrupamento de milhares.
    --------------------------------------------------------------------------*/ 
    public static String formatDoubleStr(String s, Locale l)
        throws NumberFormatException, NullPointerException
    {
        s = s.replace(LocaleTools.decimalPoint(l), '.');
        
        int pointIndex = s.indexOf('.');
        int decimalPlaces = (pointIndex == -1)? 0 : s.length() - pointIndex - 1;
        
        /*
        Retira zeros a direita na parte decimal. Se houver.
        */
        if (decimalPlaces > 0)
        {
            int i = s.length() - 1;
            while (s.charAt(i) == '0')
            {
                decimalPlaces--;
                i--;
            }//fim do while
        }
        
        return String.format(l,"%,."+decimalPlaces+"f", Double.parseDouble(s));
        
    }//fim de formatDoubleStr()
    
    /**
     * Recebe uma string representando um double formatada pelo metodo
     * formatDoubleStr() e retorna a representacao dessa string
     * desformatada, isto eh, uma string que pode ser convertida em um double
     * se passada como argumento para o metodo Double.parseDouble(String str)
     * <p>
     * Caso a string nao seja uma representacao valida de algum valor numerico,
     * nenhuma excecao serah lancada e o metodo poderah retornar esta string
     * modificada ou nao.
     * 
     * @param s A String formatada. 
     * @param l O Locale que foi usado para determinar o caractere de ponto
     * decimal e do separador de agrupamento na formatacao.
     * @return Uma string representando o mesmo valor numerico porem 
     * "desformatada". Caso a string nao seja uma representacao valida de algum 
     * valor numerico, nenhuma excecao serah lancada e o metodo poderah retornar
     * esta string modificada ou nao.
     */
    /*[09]----------------------------------------------------------------------
    *      Recebe uma string representando um double formatada pelo metodo
    *      formatDoubleStr() e retorna a representacao dessa string
    *      desformatada, isto eh, uma string que pode ser convertida em um
    *      double se passada como argumento para o metodo 
    *      Double.parseDouble(String str)
    --------------------------------------------------------------------------*/ 
    public static String unformatDoubleStr(String s, Locale l)
    {
        HashSet <Character> separator = new HashSet <Character>(1);
        separator.add(LocaleTools.separator(l));
        return filterString
               (
                   s,
                   separator ,
                   false
               ).replace(LocaleTools.decimalPoint(l), '.');
    }//fim de unformatDoubleStr()
    
    
  /**
   * Mostra exemplos de uso dos metodos da classe.
   * 
   * @param args Nao utilizado.
   */
   public static void main(String[] args)
   {
       System.out.println(repeat('-',80));
       System.out.println(repeat("bla",6));
       System.out.println(group("ABCDEFGHIJLMNOPQRSTUVXZ",1,'/'));
       System.out.println(group("1234567890",-3,'.'));
       System.out.println(merge('x',"xx)x xxxx-xxxx","21",false));
       System.out.println(merge('x',"xx)x xxxx-xxxx","219",false));
       System.out.println(merge('x',"xx)x xxxx-xxxx","219788158",false));
       System.out.println(merge('x',"xx)x xxxx-xxxx","2197881586",false));
       System.out.println(merge('x',"xx)x xxxx-xxxx","21978815858",false));
       System.out.println(merge('x',"xx)x xxxx-xxxx","2196565852101",false));
       System.out.println(merge('x',"MASCARA","STRING",false));
       System.out.println(filterString("ABCDEFGHIJJJJJKKK","AJ",true));
       System.out.println(filterString("ABCDEFGHIJJJJJKKK","AJ",false));
       System.out.println(filterString("ABCDEFGHIJJJJJKKK","",true));
       System.out.println(filterString("ABCDEFGHIJJJJJKKK","",false));
       System.out.println(formatDoubleStr("12345,0100",LocaleTools.PT_BR));   
       System.out.println
                  (
                      unformatDoubleStr
                              (
                                  formatDoubleStr
                                  (
                                      "123456,789000",
                                      LocaleTools.PT_BR
                                  ),
                                  LocaleTools.PT_BR
                              )
                  );   
       
   }//fim de main()
    
}//fim da classe StringTools
