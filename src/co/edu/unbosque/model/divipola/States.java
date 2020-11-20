package co.edu.unbosque.model.divipola;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="divipolaState")
public class States {
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Id
	private String code;
	private String name;
	
	public States() {
		
	}
	
	public States(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
