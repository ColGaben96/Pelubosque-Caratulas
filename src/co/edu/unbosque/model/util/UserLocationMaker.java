
package co.edu.unbosque.model.util;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import co.edu.unbosque.model.persistence.DatabaseConnection;

@ManagedBean(name="userlocation")
@SessionScoped
public class UserLocationMaker {
		
	private String address;
	private String state;
	private String town;
	private String code;
	private String completeAddress;
	private ArrayList<String> wholeTown;
	private ArrayList<String> states;
	private ArrayList<String> towns;

	public UserLocationMaker() {
	
	}
	
	public UserLocationMaker(String address, String state, String town, String code) {
		this.address = address;
		this.state = state;
		this.town = town;
		this.code = code;
	}
	
	public void initialize() {
		DatabaseConnection db = new DatabaseConnection();
		wholeTown = db.getTownsWithState();
		states = db.getAllStates();
		towns = db.getTowns();
	}
	
	public ArrayList<String> getAllStates() {
		
		return states;
	}
	
	public ArrayList<String> getTowns() {
		
		return towns;
	}
	
	public String makeLocation(String address, String town, String state) {
		completeAddress = address+", "+town+", "+state;
		System.out.println(completeAddress);
		return completeAddress;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public ArrayList<String> getWholeTown() {
		return wholeTown;
	}

	public void setWholeTown(ArrayList<String> wholeTown) {
		this.wholeTown = wholeTown;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
