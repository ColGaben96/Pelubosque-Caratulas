package co.edu.unbosque.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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
	
	@SuppressWarnings("unchecked")
	public List<Locations> listAll() {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cargando...",null);
		FacesMessage Ready = new FacesMessage(FacesMessage.SEVERITY_INFO, "Punto agregado",null);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage("messages", message);
		SessionFactory factory = new Configuration()
				.configure()
				.addAnnotatedClass(Locations.class)
				.buildSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		var locationList = session.createQuery("from Locations").list();
		session.getTransaction().commit();
		session.close();
		context.addMessage("messages", Ready);
		return locationList;
	}
	
	public int locationQuantity() {
		SessionFactory factory = new Configuration()
				.configure()
				.addAnnotatedClass(Locations.class)
				.buildSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		var locationList = session.createQuery("from Locations").list().size();
		session.getTransaction().commit();
		session.close();
		return locationList;
	}
	
	public void createLocation() {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cargando...",null);
		FacesMessage Ready = new FacesMessage(FacesMessage.SEVERITY_INFO, "Punto agregado",null);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage("messages", message);
		SessionFactory factory = new Configuration()
				.configure()
				.addAnnotatedClass(Locations.class)
				.buildSessionFactory();
		Session session = factory.getCurrentSession();
		var newLocation = new Locations(name, address, 0, maxCapacity);
		session.beginTransaction();
		session.save(newLocation);
		session.getTransaction().commit();
		session.close();
		context.addMessage("messages", Ready);
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		try {
			ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
