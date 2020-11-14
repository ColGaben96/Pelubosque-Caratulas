package co.edu.unbosque.model;

import javax.faces.bean.ManagedBean;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@ManagedBean(name="locations")
@Entity
@Table(name="Location")
public class Locations {
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Id
	private String name;
	private String address;
	private int actualCapacity;
	private int maxCapacity;
	
	public Locations() {
		
	}
	
	public Locations(String name, String address, int actualCapacity, int maxCapacity) {
		this.name = name;
		this.address = address;
		this.actualCapacity = actualCapacity;
		this.maxCapacity = maxCapacity;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getActualCapacity() {
		return actualCapacity;
	}
	public void setActualCapacity(int actualCapacity) {
		this.actualCapacity = actualCapacity;
	}
	public int getMaxCapacity() {
		return maxCapacity;
	}
	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}
	
	
	
}
