package co.edu.unbosque.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;

@ManagedBean(name="db")
public class DatabaseConnection {
	private Connection con;
	private Statement statement;
	private ResultSet rs;
	
	public ArrayList<String> getIDS() {
		var msg = new ArrayList<String>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String user = "ImmnUr2vPP";
			String pass = "MLSgcm7woJ";
			con = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/" + user, user, pass);
			statement = con.createStatement();
			rs = statement.executeQuery("SELECT * FROM IDS");
			while(rs.next()) {
				msg.add(rs.getString(2));
			}
			con.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return msg;
	}
	
	public ArrayList<String> getGENRES() {
		var msg = new ArrayList<String>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String user = "ImmnUr2vPP";
			String pass = "MLSgcm7woJ";
			con = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/" + user, user, pass);
			statement = con.createStatement();
			rs = statement.executeQuery("SELECT * FROM Genres");
			while(rs.next()) {
				msg.add(rs.getString(2));
			}
			con.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return msg;
	}
	
	public ArrayList<String> getUSERS() {
		var msg = new ArrayList<String>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String user = "ImmnUr2vPP";
			String pass = "MLSgcm7woJ";
			con = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/" + user, user, pass);
			statement = con.createStatement();
			rs = statement.executeQuery("SELECT * FROM User");
			while(rs.next()) {
				msg.add(rs.getString(2));
			}
			con.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return msg;
	}
	
	public ArrayList<String> getLOCATIONS() {
		var msg = new ArrayList<String>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String user = "ImmnUr2vPP";
			String pass = "MLSgcm7woJ";
			con = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/" + user, user, pass);
			statement = con.createStatement();
			rs = statement.executeQuery("SELECT * FROM Location");
			while(rs.next()) {
				msg.add(rs.getString(2));
			}
			con.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return msg;
	}
}
