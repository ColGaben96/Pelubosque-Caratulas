package co.edu.unbosque.model.divipola;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="divipolaTown")
public class Towns {
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Id
	private String code;
	private String state;
	private String name;
	
	public Towns() {
		
	}
	
	public Towns(String code, String state, String name) {
		this.code = code;
		this.state = state;
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
