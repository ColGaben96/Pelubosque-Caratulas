package co.edu.unbosque.model.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;

import co.edu.unbosque.model.User;

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
	
	public ArrayList<String> getAllStates() {
		var msg = new ArrayList<String>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String user = "ImmnUr2vPP";
			String pass = "MLSgcm7woJ";
			con = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/" + user, user, pass);
			statement = con.createStatement();
			rs = statement.executeQuery("SELECT * FROM `divipolaState` ORDER BY name ASC");
			while(rs.next()) {
				msg.add(rs.getString(3));
			}
			con.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return msg;
	}
	
	public ArrayList<String> getTowns() {
		var msg = new ArrayList<String>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String user = "ImmnUr2vPP";
			String pass = "MLSgcm7woJ";
			con = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/" + user, user, pass);
			statement = con.createStatement();
			rs = statement.executeQuery("SELECT * FROM `divipolaTown` ORDER BY name ASC");
			while(rs.next()) {
				msg.add(rs.getString(3));
			}
			con.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return msg;
	}
	
	public ArrayList<String> getTownsWithState() {
		var msg = new ArrayList<String>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String user = "ImmnUr2vPP";
			String pass = "MLSgcm7woJ";
			con = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/" + user, user, pass);
			statement = con.createStatement();
			rs = statement.executeQuery("SELECT * FROM divipolaTown");
			while(rs.next()) {
				msg.add(rs.getString(3)+","+rs.getString(4));
			}
			con.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return msg;
	}
	
	public ArrayList<String> getTownsFromSelectedState(String state) {
		var msg = new ArrayList<String>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String user = "ImmnUr2vPP";
			String pass = "MLSgcm7woJ";
			con = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/" + user, user, pass);
			statement = con.createStatement();
			rs = statement.executeQuery("SELECT * FROM `divipolaTown` WHERE state = '"+state+"'");
			while(rs.next()) {
				msg.add(rs.getString(3));
			}
			con.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return msg;
	}
	
	public void updateUserAddress(User userobj, String address) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String user = "ImmnUr2vPP";
			String pass = "MLSgcm7woJ";
			con = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/" + user, user, pass);
			statement = con.createStatement();
			int result = statement.executeUpdate("UPDATE `Users` SET"
					+ "`username`='"+userobj.getUsername()+"',"
					+ "`Appointments`='"+userobj.getAppointments()+"',"
					+ "`address`='"+address+"',"
					+ "`birthday`='"+userobj.getBirthday()+"',"
					+ "`clients`='"+userobj.getClients()+"',"
					+ "`email`='"+userobj.getEmail()+"',"
					+ "`genre`='"+userobj.getGenre()+"',"
					+ "`id`="+userobj.getId()+","
					+ "`name`='"+userobj.getName()+"',"
					+ "`noid`='"+userobj.getNoid()+"',"
					+ "`nomenAddress`= null,"
					+ "`password`='"+userobj.getPassword()+"',"
					+ "`paymentMethod`='"+userobj.getPaymentMethod()+"',"
					+ "`phone`='"+userobj.getPhone()+"',"
					+ "`role`="+userobj.getRole()+","
					+ "`sessionID`='"+userobj.getSessionID()+"',"
					+ "`state`= null,"
					+ "`toid`='[value-18]',"
					+ "`town`= null");
			System.out.println("Operation Status Code: "+result);
			con.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
