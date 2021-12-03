import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.SealedObject;

public class MainAsimetrica {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int opcion;
		String frase = "";
		String fraseCifrada = "";
		boolean continuar = true;
		byte[] bytesFraseCifrada = null;

		try (Scanner sc = new Scanner(System.in)) {

			// Se crea el generador de claves.
			KeyPairGenerator generador = KeyPairGenerator.getInstance("RSA");

			// Se genera el par de claves
			KeyPair claves = generador.generateKeyPair();

			// Se crea el cifrador
			Cipher cifrador = Cipher.getInstance("RSA");

			while (continuar) {

				try {

					// Se configura el cifrador para que use la clave privada para asegurar
					// autenticidad
					cifrador.init(Cipher.ENCRYPT_MODE, claves.getPrivate());

					System.out.println("Por favor, seleccione una opción");
					System.out.println("1. Encriptar frase" + "\n" + "2. Mostrar frase encriptada." + "\n"
							+ "3. Desencriptar frase." + "\n" + "4. Encriptar coche" + "\n" + "5. Salir");

					opcion = sc.nextInt();

					switch (opcion) {
					case 1:

						System.out.println("Introduzca la frase a encriptar");
						if (frase.equals("")) {

							frase = sc.next();

						} else {
							frase = "";
							frase = sc.next();

						}

						// Convertimos la frase a bytes
						byte[] bytesFrase = frase.getBytes();

						// EL cifrador devuelve una cadena de bytes
						bytesFraseCifrada = cifrador.doFinal(bytesFrase);
						fraseCifrada = new String(bytesFraseCifrada);

						break;
					case 2:
						if (frase.equals("")) {
							System.out.println("Por favor introduzca una frase para descifrar.");
						} else {
							System.out.println("Frase desencriptada: " + fraseCifrada);
						}
						break;
					case 3:
						if (frase.length() == 0) {
							System.out.println("No hay frase para descifrar. Por favor introduzca una");
						} else {
							// Para evitar la excepción javax.crypto.badpaddingexception debida a la no
							// correspondencia de la longitud
							// de las cadenas de bytes al convertir el array de bytes a string y vice versa.
							String cipherText = Base64.getEncoder().encodeToString(bytesFraseCifrada);
							byte[] reCipherBytes = Base64.getDecoder().decode(cipherText);

							cifrador.init(Cipher.DECRYPT_MODE, claves.getPublic());
							byte[] bytesFraseDescifrada = cifrador.doFinal(reCipherBytes);

							String fraseDescifrada = new String(bytesFraseDescifrada);

							frase = "";
							System.out.println(fraseDescifrada);

						}
						break;

					case 4:
						System.out.println("Por favor, introduzca la matricula del coche.");
						String matricula = sc.next();
						System.out.println("Introduzca la marca del coche.");
						String marca = sc.next();
						System.out.println("Introduzca el modelo del coche.");
						String modelo = sc.next();
						System.out.println("Introduzca el precio del coche.");
						String precio = sc.next();

						Coche coche = new Coche(matricula, marca, modelo, Double.parseDouble(precio));

						SealedObject so = new SealedObject(coche, cifrador);

						System.out.println("El coche cifrado es " + so.toString());
						break;
					case 5:
						System.out.println("Deteniendo el programa");
						continuar = false;
						break;

					default:
						System.out.println("Petición incorrecta.");
					}
				} catch (NumberFormatException ex) {
					System.out.println("Formato no válido, recuerde que el precio es un número");
					System.out.println(ex.getMessage());
				} catch (GeneralSecurityException gse) {
					System.out.println("Algo ha fallado.." + gse.getMessage());
					gse.printStackTrace();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
