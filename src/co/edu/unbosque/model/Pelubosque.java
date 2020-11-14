package co.edu.unbosque.model;

import javax.faces.bean.ManagedBean;

@ManagedBean(name="server")
public class Pelubosque {
	private DatabaseConnection db = new DatabaseConnection();

	public DatabaseConnection getDb() {
		return db;
	}
	
}
