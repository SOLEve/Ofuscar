import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;
 
public class Main { 
 
    static String charMin = "cdghklopstwxabefijmnqruvyz"; 
    static String charMay = "WXSTOPKLGHCDEFIJMNQRUVYZAB"; 
    static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in)); 
    @SuppressWarnings("rawtypes")
	static ArrayList vector = new ArrayList();
    static Properties preferences = new Properties();
    static String[] listaDirectorio;
    static File directorio;
    static String str;
    
	@SuppressWarnings("deprecation")
	public static void main(String[] args)
    { 	    
    	System.out.println("**********Inicio**********");
    	
    	try 
        {
    		// leer archivos palabras.doc 
    		metodo1();    		
	        // escribir archivos Ofuscado.doc
    		metodo2();
	       /*crea el archivo .ini*/
    		FileOutputStream out = new FileOutputStream ("myProg.ini");
 	        preferences.save(out, "Preferences"); 	        
 	        // lee el numero de archivos en ula carpeta clases_fuentes
	        metodo3();
	        
	        
	        //Find and replace archivo
	        for (int pos=0;pos<listaDirectorio.length;pos++)
	        {	    
		        String codigoClase = readFile("./Clases_fuentes/"+listaDirectorio[pos]);		        
		        BufferedWriter bw = null;		        
		        
		        Set<Object> keys = preferences.keySet();
		        
		        for (Object key : keys) 
		        {  		        	
		        	int cont=0,x = 0, y = 0;    

		            String palabraOriginal 	= (String) key;  
		            String palabraOfuscada 	= (String) preferences.get(key);            
		            
		            while ((x = codigoClase.indexOf(palabraOriginal, y)) > -1) 
		            {  
		            	y	=	x	+	palabraOriginal.length();
						cont++;
		            }  
	 
		            if(cont>0)
		            {	
		            	//aqui lo que hace es ofuscar el codigo dentro de la clase
		            	if((palabraOriginal.compareToIgnoreCase("LibE105Linkit")!=0)&&(palabraOriginal.compareToIgnoreCase("iJackCommandLinkit")!=0))
            			{
		            		codigoClase = codigoClase.replace(palabraOriginal,palabraOfuscada);
            			}		            	
		            	//aqui lo que hace es crear el nuevo archivo y decide si ofuscar el nombre o no
		            	if(palabraOriginal.compareTo(listaDirectorio[pos].substring(0,(listaDirectorio[pos].length()-5)))==0)
		            	{
            				if((palabraOriginal.compareToIgnoreCase("LibE105Linkit")==0)||(palabraOriginal.compareToIgnoreCase("iJackCommandLinkit")==0))
			            	{ 
			            		System.out.println(palabraOriginal);
			            		bw = new BufferedWriter(new FileWriter("./Clases_ofuscadas/"+listaDirectorio[pos]));
			            	}            				
		            		else
		            		{
		            			System.out.println(palabraOriginal);
		            			bw = new BufferedWriter(new FileWriter("./Clases_ofuscadas/"+palabraOfuscada+".java"));
		            		}
		            	}		            	
		            }
		        }  
		        bw.write(codigoClase);  
		        bw.close();
	        }         
	        
	       }catch (Exception e){ System.out.print("Valor no valido."); } 
   	
        System.out.println("***********Fin*****************");
    } 
     
	@SuppressWarnings("unchecked")
	public static void metodo1() throws IOException
	{
		String texto = "";	        
        FileReader lector=new FileReader("Palabras.txt");
        BufferedReader contenido=new BufferedReader(lector);

        while((texto=contenido.readLine())!=null)
        {
        	vector.add(texto);
        }
        contenido.close();
	}
	
	public static void metodo2() throws IOException
	{
		File archivoAbierto=new File("Ofuscado.txt");	      
        Writer output = new BufferedWriter(new FileWriter(archivoAbierto));
        try 
        {
        	output.write("PALABRAS OFUSCADAS");
    		output.write("\r\n\n");
    		
        	for(int y=1;y<vector.size();y++)
        	{
        		output.write(y+") "+vector.get(y).toString()+"\t"+cifCesar(vector.get(y).toString(),Integer.parseInt(vector.get(0).toString())));
        		preferences.put(""+vector.get(y).toString(),""+cifCesar(vector.get(y).toString(),Integer.parseInt(vector.get(0).toString())));
        		output.write("\r\n");        		
        	}
        }
        finally
        {
          output.close();
        }
	}
	
	public static void metodo3()
	{
		directorio = new File("./Clases_fuentes");
        listaDirectorio = directorio.list();
        if (listaDirectorio == null)
        {
              System.out.println("No hay ficheros en el directorio especificado");
        }else
        {
           for (int x=0;x<listaDirectorio.length;x++)
           {
                //System.out.println(listaDirectorio[x]);
           }
        }
	}
	
    public static String readFile(String file) throws IOException {  
        @SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(new FileReader(file));  
        String line = null;  
        StringBuilder stringBuilder = new StringBuilder();  
        String ls = System.getProperty("line.separator");  
        while ((line = reader.readLine()) != null) {  
            stringBuilder.append(line);  
            stringBuilder.append(ls);  
        }  
        return stringBuilder.toString();  
    } 
    
    private static String cifCesar(String entrada,int desp)
    { 
        String salida = ""; 
        for(int i = 0;i<entrada.length();i++)
        { 
            if((charMin.indexOf(entrada.charAt(i)) != -1) || (charMay.indexOf(entrada.charAt(i)) != -1)) 
                salida += (charMin.indexOf(entrada.charAt(i)) != -1) ? charMin.charAt( ( (charMin.indexOf(entrada.charAt(i)) )+desp)%charMin.length() ) : charMay.charAt(( charMay.indexOf(entrada.charAt(i)) +desp)%charMay.length()); 
            else 
                salida += entrada.charAt(i); 
        } 
        return salida; 
    } 
}
 