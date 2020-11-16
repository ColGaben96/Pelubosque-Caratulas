package co.edu.unbosque.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@ManagedBean(name="user")
@SessionScoped
@Entity
@Table(name="Users")
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int role;
	private String name;
	@Id
	private String username;
	private String email;
	private String genre;
	private String password;
	private String address;
	private String phone;
	private String birthday;
	private String toid;
	private String noid;
	private String sessionID;
	private ArrayList<String> paymentMethod;
	private ArrayList<String> Appointments;
	private ArrayList<String> clients;
	
	public User() {
		
	}
	
	public User(int role, String name, String username, String email, String genre, String password, String address, String phone,
			String birthday, String toid, String noid, ArrayList<String> paymentMethod, ArrayList<String> Appointments,
			ArrayList<String> clients, String sessionID) {
		super();
		this.role = role;
		this.name = name;
		this.username = username;
		this.email = email;
		this.genre = genre;
		this.password = password;
		this.address = address;
		this.phone = phone;
		this.birthday = birthday;
		this.toid = toid;
		this.noid = noid;
		this.paymentMethod = paymentMethod;
		this.Appointments = Appointments;
		this.clients = clients;
		this.sessionID = sessionID;
	}
	
	@SuppressWarnings("unchecked")
	public void checkUser() {
		SessionFactory factory = new Configuration()
				.configure()
				.buildSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		List<User> users = session.createQuery("from User").list();
		for (int i = 0; i < users.size(); i++) {
			if(!(username == users.get(i).getUsername()) && !(password == users.get(i).getPassword())) {
				if(users.get(i).getRole() == 1) {
					FacesContext context = FacesContext.getCurrentInstance();
					HttpSession facesSession = (HttpSession) context.getExternalContext().getSession(true);
					try {
						context.getExternalContext().redirect("index.xhtml");
						facesSession.setAttribute("username", name);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if(users.get(i).getRole() == 2) {
					FacesContext context = FacesContext.getCurrentInstance();
					HttpSession facesSession = (HttpSession) context.getExternalContext().getSession(true);
					try {
						context.getExternalContext().redirect("index.xhtml");
						facesSession.setAttribute("username", name);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error","Ha ocurrido un error inesperado.");
						FacesContext.getCurrentInstance().addMessage("badLogin", message);
					}
				} else {
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "El usuario y/o contraseña están errados. Por favor verifica e intenta nuevamente.",null);
					FacesContext.getCurrentInstance().addMessage("badLogin", message);
				}
			} else {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "El usuario y/o contraseña están errados. Por favor verifica e intenta nuevamente.",null);
				FacesContext.getCurrentInstance().addMessage("badLogin", message);
			}
		}
		session.getTransaction().commit();
		session.close();
	}
	
	@SuppressWarnings("unchecked")
	public void checkAdmin() {
		SessionFactory factory = new Configuration()
				.configure()
				.addAnnotatedClass(User.class)
				.buildSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		List<User> users = session.createQuery("from User").list();
		for (int i = 0; i < users.size(); i++) {
			if(!(username == users.get(i).getUsername()) && !(password == users.get(i).getPassword())) {
				if(users.get(i).getRole() == 0) {
					FacesContext context = FacesContext.getCurrentInstance();
					HttpSession facesSession = (HttpSession) context.getExternalContext().getSession(true);
					try {
						context.getExternalContext().redirect("index.xhtml");
						facesSession.setAttribute("username", name);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error","Ha ocurrido un error inesperado.");
						FacesContext.getCurrentInstance().addMessage("badLogin", message);
					}
				} else {
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error","El usuario y/o contraseña están errados. Por favor verifica e intenta nuevamente.");
					FacesContext.getCurrentInstance().addMessage("badLogin", message);
				}
			} else {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cargando...","El usuario y/o contraseña están errados. Por favor verifica e intenta nuevamente.");
				FacesContext.getCurrentInstance().addMessage("badLogin", message);
			}
		}
		session.getTransaction().commit();
		session.close();
	}
	
	public void createUser() {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cargando...",null);
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession facesSession = (HttpSession) context.getExternalContext().getSession(true);
		context.addMessage("messages", message);
		String sessionID = context.getExternalContext().getSessionId(true);
		SessionFactory factory = new Configuration()
				.configure()
				.addAnnotatedClass(User.class)
				.buildSessionFactory();
		Session session = factory.getCurrentSession();
		var newUser = new User(1, name, username, email, genre, password, null, phone,
				birthday, toid, noid, null, null, null, sessionID);
		session.beginTransaction();
		session.save(newUser);
		try {
			Emailer sendHelloEmail = new Emailer();
			if(genre.equals("Hombre")) {
				sendHelloEmail.sendEmail(email, Emailer.HELLO_MALE, "<!DOCTYPE html>\n"
						+ "<html>\n"
						+ "    <head>\n"
						+ "        <meta charset=\"UTF-8\">\n"
						+ "    </head>\n"
						+ "    <body>\n"
						+ "        <h1>Hola "+name+"</h1><br>\n"
						+ "        <p>En nombre del equipo de Pelubosque te damos la mas cordial bienvenida al servicio mas grande de diseño de imagen del pais. <br>\n"
						+ "        Haz <a href=\"http://localhost:8080/PelubosqueCaratulas/faces/signup/continue.xhtml/?sessionID="+sessionID+"\">clic aquí</a> o ingresa a este link: http://localhost:8080/PelubosqueCaratulas/faces/signup/continue.xhtml?sessionID="+sessionID+"</p><br>\n"
						+ "        <p>Recuerda que no hay personas feas, sino mal arregladas.</p> <br>\n"
						+ "        <p>Quedamos atentos,</p><br>\n"
						+ "        <p>El equipo de Pelubosque y Carátulas</p>\n"
						+ "    </body>\n"
						+ "</html>");
			}
			if(genre.equals("Mujer")) {
				sendHelloEmail.sendEmail(email, Emailer.HELLO_FEMALE, "<!DOCTYPE html>\n"
						+ "<html>\n"
						+ "    <head>\n"
						+ "        <meta charset=\"UTF-8\">\n"
						+ "    </head>\n"
						+ "    <body>\n"
						+ "        <h1>Hola "+name+"</h1><br>\n"
						+ "        <p>En nombre del equipo de Pelubosque te damos la mas cordial bienvenida al servicio mas grande de diseño de imagen del pais. <br>\n"
						+ "        Haz <a href=\"http://localhost:8080/PelubosqueCaratulas/faces/signup/continue.xhtml/?sessionID="+sessionID+"\">clic aquí</a> o ingresa a este link: http://localhost:8080/PelubosqueCaratulas/faces/signup/continue.xhtml?sessionID="+sessionID+"</p><br>\n"
						+ "        <p>Recuerda que no hay personas feas, sino mal arregladas.</p> <br>\n"
						+ "        <p>Quedamos atentos,</p><br>\n"
						+ "        <p>El equipo de Pelubosque y Carátulas</p>\n"
						+ "    </body>\n"
						+ "</html>");
			}
			if(genre.equals("Prefiero no decir")) {
				sendHelloEmail.sendEmail(email, Emailer.HELLO_TRANSFORMER, "<!DOCTYPE html>\n"
						+ "<html>\n"
						+ "    <head>\n"
						+ "        <meta charset=\"UTF-8\">\n"
						+ "    </head>\n"
						+ "    <body>\n"
						+ "        <h1>Hola "+name+"</h1><br>\n"
						+ "        <p>En nombre del equipo de Pelubosque te damos la mas cordial bienvenida al servicio mas grande de diseño de imagen del pais. <br>\n"
						+ "        Haz <a href=\"http://localhost:8080/PelubosqueCaratulas/faces/signup/continue.xhtml/?sessionID="+sessionID+"\">clic aquí</a> o ingresa a este link: http://localhost:8080/PelubosqueCaratulas/faces/signup/continue.xhtml?sessionID="+sessionID+"</p><br>\n"
						+ "        <p>Recuerda que no hay personas feas, sino mal arregladas.</p> <br>\n"
						+ "        <p>Quedamos atentos,</p><br>\n"
						+ "        <p>El equipo de Pelubosque y Carátulas</p>\n"
						+ "    </body>\n"
						+ "</html>");
			}
			context.getExternalContext().redirect("confirmPage1.xhtml");
			Thread.sleep(2000);
			facesSession.setAttribute("correo", email);
		} catch (IOException | InterruptedException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.getTransaction().commit();
		session.close();
	}
	
	public void updateSessionID() {
		FacesContext context = FacesContext.getCurrentInstance();
		SessionFactory factory = new Configuration()
				.configure()
				.addAnnotatedClass(User.class)
				.buildSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		var newSession = new User();
		newSession.setSessionID(context.getExternalContext().getSessionId(true));
		session.update(newSession);
		session.getTransaction().commit();
		session.close();
	}
	
	@SuppressWarnings("unchecked")
	public void validateSessionID() {
		FacesContext context = FacesContext.getCurrentInstance();
		SessionFactory factory = new Configuration()
				.configure()
				.addAnnotatedClass(User.class)
				.buildSessionFactory();
		HttpSession facesSession = (HttpSession) context.getExternalContext().getSession(true);
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		List<User> users = session.createQuery("from User where sessionID = '"+sessionID+"'").list();
		
		if (!(users.size() >= 1)) {
			try {
				context.getExternalContext().redirect("oopsMyBad.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					context.getExternalContext().responseSendError(403, "");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		facesSession.setAttribute("username", users.get(0).getName());
		session.getTransaction().commit();
		session.close();
		
	}
	
	public void newUserUploadAddress() {
		FacesContext context = FacesContext.getCurrentInstance();
		SessionFactory factory = new Configuration()
				.configure()
				.addAnnotatedClass(User.class)
				.buildSessionFactory();
		HttpSession facesSession = (HttpSession) context.getExternalContext().getSession(true);
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		var updatedUser = new User();
		updatedUser.setAddress(address);
		session.get(updatedUser.getClass(), sessionID);
		session.getTransaction().commit();
		session.close();
		try {
			context.getExternalContext().redirect("page3.xhtml?sessionID="+sessionID);
			facesSession.setAttribute("username", name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void forgotPassword() {
		FacesContext context = FacesContext.getCurrentInstance();
		SessionFactory factory = new Configuration()
				.configure()
				.addAnnotatedClass(User.class)
				.buildSessionFactory();
		HttpSession facesSession = (HttpSession) context.getExternalContext().getSession(true);
		Session session = factory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	public void checkPermissions() {
		FacesContext context = FacesContext.getCurrentInstance();
		SessionFactory factory = new Configuration()
				.configure()
				.addAnnotatedClass(User.class)
				.buildSessionFactory();
		HttpSession facesSession = (HttpSession) context.getExternalContext().getSession(true);
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		List<User> users = session.createQuery("from User where sessionID = '"+sessionID+"'").list();
		if(users.get(0).role == 1) {
			facesSession.setAttribute("userRole", "true");
			facesSession.setAttribute("professionalRole", "false");
		}
		if(users.get(0).role == 2) {
			facesSession.setAttribute("userRole", "false");
			facesSession.setAttribute("professionalRole", "true");
		}
		session.getTransaction().commit();
		session.close();
	}
	
	public void logout() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			context.getExternalContext().redirect("../PelubosqueCaratulas/");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRole() {
		return role;
	}
	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public void setRole(int role) {
		this.role = role;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getToid() {
		return toid;
	}
	public void setToid(String toid) {
		this.toid = toid;
	}
	public String getNoid() {
		return noid;
	}
	public void setNoid(String noid) {
		this.noid = noid;
	}
	public ArrayList<String> getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(ArrayList<String> paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public ArrayList<String> getAppointments() {
		return Appointments;
	}
	public void setAppointments(ArrayList<String> appointments) {
		Appointments = appointments;
	}
	public ArrayList<String> getClients() {
		return clients;
	}
	public void setClients(ArrayList<String> clients) {
		this.clients = clients;
	}
}
