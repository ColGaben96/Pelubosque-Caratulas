package co.edu.unbosque.model;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@ManagedBean(name="Appointments")
@SessionScoped
@Entity
@Table(name="Appointments")
public class Appointments {
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private int id;
	private String name;
	private String location;
	private String professional;
	private String client;
	private double value;
	private String paymentMethod;
	private String status;
	
	public Appointments() {
		
	}

	public Appointments(String name, String location, String professional, String client, double value, String paymentMethod, String status) {
		this.name = name;
		this.location = location;
		this.professional = professional;
		this.client = client;
		this.value = value;
		this.paymentMethod = paymentMethod;
		this.status = status;
	}
	
	
	public void newAppointment() {
		SessionFactory factory = new Configuration()
				.configure()
				.addAnnotatedClass(User.class)
				.buildSessionFactory();
		Session session = factory.getCurrentSession();
		Appointments newAppointment = new Appointments(name, location, professional, client, value, paymentMethod, "Registrada");
		session.beginTransaction();
		session.save(newAppointment);
		session.getTransaction().commit();
		session.close();
		factory.close();
	}
	
	public int appointmentSize() {
		SessionFactory factory = new Configuration()
				.configure()
				.addAnnotatedClass(User.class)
				.buildSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		int appointment = session.createQuery("from Appointments").list().size();
		session.getTransaction().commit();
		session.close();
		factory.close();
		return appointment;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getProfessional() {
		return professional;
	}

	public void setProfessional(String professional) {
		this.professional = professional;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
