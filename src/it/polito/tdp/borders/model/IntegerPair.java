package it.polito.tdp.borders.model;

public class IntegerPair {
	
	private int numero1;
	private int numero2;
	
	/**
	 * @param numero1
	 * @param numero2
	 */
	public IntegerPair(int numero1, int numero2) {
		super();
		this.numero1 = numero1;
		this.numero2 = numero2;
	}

	/**
	 * @return the numero1
	 */
	public int getNumero1() {
		return numero1;
	}

	/**
	 * @param numero1 the numero1 to set
	 */
	public void setNumero1(int numero1) {
		this.numero1 = numero1;
	}

	/**
	 * @return the numero2
	 */
	public int getNumero2() {
		return numero2;
	}

	/**
	 * @param numero2 the numero2 to set
	 */
	public void setNumero2(int numero2) {
		this.numero2 = numero2;
	}

}
