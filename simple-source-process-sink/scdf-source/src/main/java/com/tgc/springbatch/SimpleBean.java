/**
 * 
 */
package com.tgc.springbatch;

/**
 * @author rtalapaneni
 *
 */
public class SimpleBean {
	private String name;
	
	public SimpleBean() { }
	
	public SimpleBean(String name) {this.name = name; }

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
