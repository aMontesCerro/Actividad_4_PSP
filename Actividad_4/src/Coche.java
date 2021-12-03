import java.io.Serializable;

public class Coche implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String matricula, marca, modelo;
	private double precio;
	
	public Coche(String matricula, String marca, String modelo, double precio) {
		this.matricula=matricula;
		this.marca= marca;
		this.modelo= modelo;
		this.precio= precio;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

}
