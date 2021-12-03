import java.security.GeneralSecurityException;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;



public class MainSimetrica {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int opcion;
		String frase = "";
		String fraseCifrada = "";
		boolean continuar=true;
		byte[] bytesFraseCifrada;
		
		try (Scanner sc = new Scanner(System.in)) {
			
			//Se crea el generador de claves.
			KeyGenerator generador = KeyGenerator.getInstance("DES");
			
			//Se genera la clave simétrica
			SecretKey clave = generador.generateKey();

			//Se crea el cifrador
			Cipher cifrador = Cipher.getInstance("DES");
			
			while(continuar) {
				
				//Se configura el cifrador para que use la clave simétrica
				cifrador.init(Cipher.ENCRYPT_MODE, clave);
				
				System.out.println("Por favor, seleccione una opción");
				System.out.println("1. Encriptar frase" + "\n"+
						"2. Mostrar frase encriptada."+ "\n"+
						"3. Desencriptar frase."+ "\n"+
						"4. Encriptar coche" + "\n"+
						"5. Salir");
				
				opcion= sc.nextInt();
				
				switch(opcion) {
				case 1:
					
					System.out.println("Por favor introduzca la frase a encriptar");
					if(frase.equals("")) {
						frase = sc.next();
						
					}else {
						frase= "";
						frase= sc.next();
						
					}
					
					//Convertimos la frase a bytes
					byte[] bytesFrase = frase.getBytes();
					
					//EL cifrador devuelve una cadena de bytes
					bytesFraseCifrada = cifrador.doFinal(bytesFrase);
					fraseCifrada = new String(bytesFraseCifrada);
					
					break;
				case 2:
					if(frase.equals("")) {
						System.out.println("Por favor introduzca una frase para descifrar.");
					}else {
						System.out.println("La frase cifrada es: "+ fraseCifrada);
					}
					break;
				case 3:
					if(frase.equals("")) {
						System.out.println("No hay frase para descifrar. Por favor introduzca una");
					}else {
						cifrador.init(Cipher.DECRYPT_MODE, clave);
						bytesFraseCifrada = fraseCifrada.getBytes();
						byte[] bytesFraseDescifrada = cifrador.doFinal(bytesFraseCifrada);
						
						String fraseDescifrada = new String (bytesFraseDescifrada);
						frase= "";
						
						System.out.println("Frase desencriptada: "+fraseDescifrada);
						
					}
					break;
					
				case 4:
					try {
						System.out.println("Por favor, introduzca la matricula del coche.");
						String matricula = sc.next();
						System.out.println("Introduzca la marca del coche.");
						String marca = sc.next();
						System.out.println("Introduzca el modelo del coche.");
						String modelo = sc.next();
						System.out.println("Introduzca el precio del coche.");
						String precio = sc.next();
						
						Coche coche = new Coche(matricula, marca, modelo , Double.parseDouble(precio));
						
						SealedObject so = new SealedObject (coche, cifrador);
						
						System.out.println("El coche cifrado es "+ so.toString());
					}catch(NumberFormatException ex) {
	                    System.out.println("Formato no válido, recuerde que el precio es un número");
	                    System.out.println(ex.getMessage());
					}catch(Exception e){
						//e.printStackTrace();
						
						System.out.println(e.getMessage());
						
					}
					break;
				case 5:
					System.out.println("Deteniendo el programa");
					continuar= false;
					break;
					
				default:
					System.out.println("Petición incorrecta");
				}
			}
			
			
		}catch(NumberFormatException ex) {
			System.out.println("Formato no válido, recuerde que el precio es un número");
	        System.out.println(ex.getMessage());
		
		}catch(GeneralSecurityException gse) {
			System.out.println("Algo ha fallado.." + gse.getMessage());
	        gse.printStackTrace();
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
