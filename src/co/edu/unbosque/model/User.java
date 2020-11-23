package co.edu.unbosque.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import co.edu.unbosque.model.util.DateMaker;
import co.edu.unbosque.model.util.UserLocationMaker;

@ManagedBean(name="user")
@SessionScoped
@Entity
@Table(name="Users")
public class User {
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id", nullable=false)
	private int id;
	@Column(name="role", nullable=false)
	private int role;
	@Column(name="name")
	private String name;
	@Id
	@Column(name="username", nullable=false)
	private String username;
	@Column(name="email")
	private String email;
	@Column(name="genre")
	private String genre;
	@Column(name="password")
	private String password;
	@Column(name="address")
	private String address;
	private String nomenAddress;
	private String town;
	private String state;
	@Column(name="phone")
	private String phone;
	@Column(name="birthday")
	private String birthday;
	@Column(name="toid")
	private String toid;
	@Column(name="noid")
	private String noid;
	@Column(name="sessionID", nullable = false)
	private String sessionID;
	@Column(name="paymentMethod")
	private ArrayList<CreditCard> paymentMethod;
	@Column(name="Appointments")
	private ArrayList<Appointments> Appointments;
	@Column(name="clients")
	private ArrayList<User> clients;
	@Column(name="Active")
	private boolean active;
	@Column(name="DoneRegistration")
	private boolean doneRegistration;
	private String cardnumber;
	private String cardccv;
	private String cardBelongsTo;
	private String cardFranchise;
	private String cardexpDate;
	public User() {
		
	}
	
	public User(int role, String name, String username, String email, String genre, String password, String address, String phone,
			String birthday, String toid, String noid, ArrayList<CreditCard> paymentMethod, ArrayList<Appointments> Appointments,
			ArrayList<User> clients, String sessionID, boolean active, boolean doneRegistration) {
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
		this.active = active;
		this.doneRegistration = doneRegistration;
	}
	
	@SuppressWarnings("unchecked")
	public void checkUser() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession facesSession = (HttpSession) context.getExternalContext().getSession(true);
		SessionFactory factory = new Configuration()
				.configure()
				.buildSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		List<User> users = session.createQuery("from User").list();
		for (int i = 0; i < users.size(); i++) {
			if(!(username == users.get(i).getUsername()) && !(password == users.get(i).getPassword())) {
				if(users.get(i).isDoneRegistration()) {
					if(users.get(i).isActive()) {
						if(users.get(i).getRole() == 1) {
							try {
								context.getExternalContext().redirect("index.xhtml?sessionID="+users.get(i).getSessionID());
								facesSession.setAttribute("username", name);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else if(users.get(i).getRole() == 2) {
							try {
								context.getExternalContext().redirect("index.xhtml?sessionID="+users.get(i).getSessionID());
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
						users.get(0).setActive(true);
					}
				} else {
					try {
						context.getExternalContext().redirect("../signup/continue.xhtml?sessionID="+users.get(i).getSessionID());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "El usuario y/o contraseña están errados. Por favor verifica e intenta nuevamente.",null);
				FacesContext.getCurrentInstance().addMessage("badLogin", message);
			}
		}
		session.getTransaction().commit();
		session.close();
		factory.close();
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
					try 	{
						context.getExternalContext().redirect("index.xhtml?sessionID="+users.get(i).getSessionID());
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
		factory.close();
	}
	
	@SuppressWarnings("deprecation")
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
				new DateMaker().date2String(Date.parse(birthday)), toid, noid, null, null, null, sessionID, false, false);
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
		factory.close();
	}
	
	@SuppressWarnings("unchecked")
	public void updateSessionID() {
		FacesContext context = FacesContext.getCurrentInstance();
		SessionFactory factory = new Configuration()
				.configure()
				.addAnnotatedClass(User.class)
				.buildSessionFactory();
		HttpSession facesSession = (HttpSession) context.getExternalContext().getSession(true);
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		var newSession = new User(role, name, username, email, genre, password, address, phone,
				birthday, toid, noid, paymentMethod, Appointments,
				clients, sessionID, active, doneRegistration);
		newSession.setSessionID(context.getExternalContext().getSessionId(true));
		List<User> users = session.createQuery("from User where sessionID = '"+sessionID+"'").list();
		users.get(0).setSessionID(facesSession.getId());
		session.getTransaction().commit();
		session.close();
		factory.close();
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
		facesSession.setAttribute("username", users.get(0).getName());
		session.getTransaction().commit();
		session.close();
		
	}
	
	@SuppressWarnings("unchecked")
	public void newUserUploadAddress() {
		FacesContext context = FacesContext.getCurrentInstance();
		SessionFactory factory = new Configuration()
				.configure()
				.addAnnotatedClass(User.class)
				.buildSessionFactory();
		HttpSession facesSession = (HttpSession) context.getExternalContext().getSession(true);
		Session session = factory.getCurrentSession();	
		session.beginTransaction();
		List<User> users = session.createQuery("from User where sessionID = '"+sessionID+"'").list();
		users.get(0).setAddress(new UserLocationMaker().makeLocation(nomenAddress, town, state));
		session.update(users.get(0));
		session.getTransaction().commit();
		
		try {
			context.getExternalContext().redirect("page3.xhtml?sessionID="+sessionID);
			facesSession.setAttribute("username", name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.clear();
		session.close();
		factory.close();
		
	}
	
	@SuppressWarnings("unchecked")
	public void doneRegistration() {
		Emailer email = new Emailer();
		FacesContext context = FacesContext.getCurrentInstance();
		SessionFactory factory = new Configuration()
				.configure()
				.addAnnotatedClass(User.class)
				.buildSessionFactory();
		Session session = factory.getCurrentSession();	
		session.beginTransaction();
		List<User> users = session.createQuery("from User where sessionID = '"+sessionID+"'").list();
		users.get(0).setDoneRegistration(true);
		users.get(0).setActive(true);
		session.update(users.get(0));
		try {
			email.sendEmail(users.get(0).getEmail(), "Activaste tu cuenta", "<!DOCTYPE html>\n"
					+ "<html>\n"
					+ "    <head>\n"
					+ "        <meta charset=\"UTF-8\">\n"
					+ "    </head>\n"
					+ "    <body>\n"
					+ "        <h1>Hola "+name+"</h1><br>\n"
					+ "        <p>Queremos informarte que activaste tu cuenta exitosamente y a partir de este momento ya puedes utilizar nuestros servicios.</p> <br>\n"
					+ "        <p>Cualquier inconformidad puedes contactar a gblancol@unbosque.edu.co</p><br>\n"
					+ "        <p>Quedamos atentos,</p><br>\n"
					+ "        <p>El equipo de Pelubosque y Carátulas</p>\n"
					+ "    </body>\n"
					+ "</html>");
			context.getExternalContext().redirect("../Aplicacion/index.xhtml?sessionID="+users.get(0).getSessionID());
		} catch (IOException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.getTransaction().commit();
		session.close();
		factory.close();
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
		factory.close();
	}
	
	@SuppressWarnings("unchecked")
	public void deactivateAccount() {
		Emailer email = new Emailer();
		FacesContext context = FacesContext.getCurrentInstance();
		SessionFactory factory = new Configuration()
				.configure()
				.addAnnotatedClass(User.class)
				.buildSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		List<User> users = session.createQuery("from User where sessionID = '"+sessionID+"'").list();
		users.get(0).setActive(false);
		session.update(users.get(0));
		session.getTransaction().commit();
		session.close();
		factory.close();
		try {
			context.getExternalContext().redirect("${pageContext.request.contextPath}/PelubosqueCaratulas/");
			email.sendEmail(users.get(0).getEmail(), "Desactivaste tu cuenta", "<!DOCTYPE html>\n"
					+ "<html>\n"
					+ "    <head>\n"
					+ "        <meta charset=\"UTF-8\">\n"
					+ "    </head>\n"
					+ "    <body>\n"
					+ "        <h1>Hola "+name+"</h1><br>\n"
					+ "        <p>Queremos informarte que desactivaste tu cuenta exitosamente. Por políticas internas no podemos eliminar los servicios que has utilizado. Ya que la Ley 1582 de 2012 ampara a ti y a nosotros, solo usaremos tus datos para los servicios utilizados anteriormente.</p> <br>\n"
					+ "        <p>Cualquier inconformidad puedes contactar a gblancol@unbosque.edu.co</p><br>\n"
					+ "        <p>Quedamos atentos,</p><br>\n"
					+ "        <p>El equipo de Pelubosque y Carátulas</p>\n"
					+ "    </body>\n"
					+ "</html>");
		} catch (IOException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void checkUserData() {
		FacesContext context = FacesContext.getCurrentInstance();
		SessionFactory factory = new Configuration()
				.configure()
				.addAnnotatedClass(User.class)
				.buildSessionFactory();
		HttpSession facesSession = (HttpSession) context.getExternalContext().getSession(true);
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		List<User> users = session.createQuery("from User where sessionID = '"+sessionID+"'").list();
		facesSession.setAttribute("userid", users.get(0).getUsername());
		facesSession.setAttribute("usergenre", users.get(0).getGenre());
		facesSession.setAttribute("userbirthday", users.get(0).getBirthday());
		facesSession.setAttribute("useraddress", users.get(0).getAddress());
		facesSession.setAttribute("userIdentifiactionType", users.get(0).getToid());
		facesSession.setAttribute("userIdentifiactionNumber", users.get(0).getNoid());
		session.getTransaction().commit();
		session.close();
		factory.close();
	}
	
	@SuppressWarnings("unchecked")
	public void newRegistrationUserCard() {
		Emailer emailer = new Emailer();
		FacesContext context = FacesContext.getCurrentInstance();
		SessionFactory factory = new Configuration()
				.configure()
				.addAnnotatedClass(User.class)
				.buildSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		List<User> users = session.createQuery("from User where sessionID = '"+sessionID+"'").list();
		if(cardnumber.startsWith("34") 
				|| cardnumber.startsWith("37")) {
			var newNumber = "terminada en ";
			var newCardNumber = cardnumber.split("");
			for (int i = newCardNumber.length; i >= 10; i--) {
				newNumber += newCardNumber[i];
			}
			CreditCard newcc = new CreditCard(newNumber, cardccv, cardBelongsTo, CreditCard.AMERICANEXPRESS, cardexpDate);
			paymentMethod.add(newcc);
			users.get(0).setPaymentMethod(paymentMethod);
			
		} else if(cardnumber.startsWith("4")) {
			var newNumber = "terminada en ";
			var newCardNumber = cardnumber.split("");
			for (int i = newCardNumber.length; i >= 11; i--) {
				newNumber += newCardNumber[i];
			}
			CreditCard newcc = new CreditCard(newNumber, cardccv, cardBelongsTo, CreditCard.VISA, cardexpDate);
			paymentMethod.add(newcc);
			users.get(0).setPaymentMethod(paymentMethod);
			
		} else if(cardnumber.startsWith("51") 
				|| cardnumber.startsWith("52") 
				|| cardnumber.startsWith("53") 
				|| cardnumber.startsWith("54") 
				|| cardnumber.startsWith("55")) {
			var newNumber = "terminada en ";
			var newCardNumber = cardnumber.split("");
			for (int i = newCardNumber.length; i >= 11; i--) {
				newNumber += newCardNumber[i];
			}
			CreditCard newcc = new CreditCard(newNumber, cardccv, cardBelongsTo, CreditCard.MASTERCARD, cardexpDate);
			paymentMethod.add(newcc);
			users.get(0).setPaymentMethod(paymentMethod);
			
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error","La tarjeta no es válida");
			FacesContext.getCurrentInstance().addMessage("badLogin", message);
		}
		session.getTransaction().begin();
		session.close();
		factory.close();
		var newFranchise = "";
		for (int i = 0; i < paymentMethod.size(); i++) {
			newFranchise = paymentMethod.get(i).getFranchise();
		}
		var newCardNumber = "";
		for (int i = 0; i < paymentMethod.size(); i++) {
			newCardNumber = paymentMethod.get(i).getNumber();
		}
		try {
			emailer.sendEmail(email, "Registramos tu "+newFranchise+" "+newCardNumber+" exitosamente", "<!DOCTYPE html>\n"
					+ "<html>\n"
					+ "    <head>\n"
					+ "        <meta charset=\"UTF-8\">\n"
					+ "    </head>\n"
					+ "    <body>\n"
					+ "        <h1>Hola "+name+"</h1><br>\n"
					+ "        <p>Queremos informarte que desactivaste tu tarjeta "+newFranchise+" "+newCardNumber+" ha sido registrada exitosamente.</p> <br>\n"
					+ "        <p>Cualquier inconformidad puedes contactar a gblancol@unbosque.edu.co</p><br>\n"
					+ "        <p>Quedamos atentos,</p><br>\n"
					+ "        <p>El equipo de Pelubosque y Carátulas</p>\n"
					+ "    </body>\n"
					+ "</html>");
			context.getExternalContext().redirect("../Aplicacion/index.xhtml");
		} catch (IOException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void newUserCard() {
		Emailer emailer = new Emailer();
		SessionFactory factory = new Configuration()
				.configure()
				.addAnnotatedClass(User.class)
				.buildSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		List<User> users = session.createQuery("from User where sessionID = '"+sessionID+"'").list();
		if(cardnumber.startsWith("34") 
				|| cardnumber.startsWith("37")) {
			var newNumber = "terminada en ";
			var newCardNumber = cardnumber.split("");
			for (int i = newCardNumber.length; i >= 10; i--) {
				newNumber += newCardNumber[i];
			}
			CreditCard newcc = new CreditCard(newNumber, cardccv, cardBelongsTo, CreditCard.AMERICANEXPRESS, cardexpDate);
			paymentMethod.add(newcc);
			users.get(0).setPaymentMethod(paymentMethod);
			
		} else if(cardnumber.startsWith("4")) {
			var newNumber = "terminada en ";
			var newCardNumber = cardnumber.split("");
			for (int i = newCardNumber.length; i >= 11; i--) {
				newNumber += newCardNumber[i];
			}
			CreditCard newcc = new CreditCard(newNumber, cardccv, cardBelongsTo, CreditCard.VISA, cardexpDate);
			paymentMethod.add(newcc);
			users.get(0).setPaymentMethod(paymentMethod);
			
		} else if(cardnumber.startsWith("51") 
				|| cardnumber.startsWith("52") 
				|| cardnumber.startsWith("53") 
				|| cardnumber.startsWith("54") 
				|| cardnumber.startsWith("55")) {
			var newNumber = "terminada en ";
			var newCardNumber = cardnumber.split("");
			for (int i = newCardNumber.length; i >= 11; i--) {
				newNumber += newCardNumber[i];
			}
			CreditCard newcc = new CreditCard(newNumber, cardccv, cardBelongsTo, CreditCard.MASTERCARD, cardexpDate);
			paymentMethod.add(newcc);
			users.get(0).setPaymentMethod(paymentMethod);
			
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error","La tarjeta no es válida");
			FacesContext.getCurrentInstance().addMessage("badLogin", message);
		}
		session.getTransaction().begin();
		session.close();
		factory.close();
		var newFranchise = "";
		for (int i = 0; i < paymentMethod.size(); i++) {
			newFranchise = paymentMethod.get(i).getFranchise();
		}
		var newCardNumber = "";
		for (int i = 0; i < paymentMethod.size(); i++) {
			newCardNumber = paymentMethod.get(i).getNumber();
		}
		try {
			emailer.sendEmail(email, "Registramos tu "+newFranchise+" "+newCardNumber+" exitosamente", "<!DOCTYPE html>\n"
					+ "<html>\n"
					+ "    <head>\n"
					+ "        <meta charset=\"UTF-8\">\n"
					+ "    </head>\n"
					+ "    <body>\n"
					+ "        <h1>Hola "+name+"</h1><br>\n"
					+ "        <p>Queremos informarte que desactivaste tu tarjeta "+newFranchise+" "+newCardNumber+" ha sido registrada exitosamente.</p> <br>\n"
					+ "        <p>Cualquier inconformidad puedes contactar a gblancol@unbosque.edu.co</p><br>\n"
					+ "        <p>Quedamos atentos,</p><br>\n"
					+ "        <p>El equipo de Pelubosque y Carátulas</p>\n"
					+ "    </body>\n"
					+ "</html>");
			
		} catch ( MessagingException e) {
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
	public ArrayList<CreditCard> getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(ArrayList<CreditCard> paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public ArrayList<Appointments> getAppointments() {
		return Appointments;
	}
	public void setAppointments(ArrayList<Appointments> appointments) {
		Appointments = appointments;
	}
	public ArrayList<User> getClients() {
		return clients;
	}
	public void setClients(ArrayList<User> clients) {
		this.clients = clients;
	}

	public String getNomenAddress() {
		return nomenAddress;
	}

	public void setNomenAddress(String nomenAddress) {
		this.nomenAddress = nomenAddress;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isDoneRegistration() {
		return doneRegistration;
	}

	public void setDoneRegistration(boolean doneRegistration) {
		this.doneRegistration = doneRegistration;
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	public String getCardccv() {
		return cardccv;
	}

	public void setCardccv(String cardccv) {
		this.cardccv = cardccv;
	}

	public String getCardBelongsTo() {
		return cardBelongsTo;
	}

	public void setCardBelongsTo(String cardBelongsTo) {
		this.cardBelongsTo = cardBelongsTo;
	}

	public String getCardFranchise() {
		return cardFranchise;
	}

	public void setCardFranchise(String cardFranchise) {
		this.cardFranchise = cardFranchise;
	}

	public String getCardexpDate() {
		return cardexpDate;
	}

	public void setCardexpDate(String cardexpDate) {
		this.cardexpDate = cardexpDate;
	}
}
