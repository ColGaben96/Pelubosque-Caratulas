package co.edu.unbosque.model;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="UserCC")
@SessionScoped
public class CreditCard {
	private String number;
	private String ccv;
	private String BelongsTo;
	private String Franchise;
	private String expDate;
	public static final String VISA = "Visa";
	public static final String MASTERCARD = "MasterCard";
	public static final String AMERICANEXPRESS = "American Express";
	
	public CreditCard() {
		
	}
	
	public CreditCard(String number, String ccv, String BelongsTo, String Franchise, String expDate) {
		this.number = number;
		this.ccv = ccv;
		this.BelongsTo = BelongsTo;
		this.Franchise = Franchise;
		this.expDate = expDate;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCcv() {
		return ccv;
	}

	public void setCcv(String ccv) {
		this.ccv = ccv;
	}

	public String getBelongsTo() {
		return BelongsTo;
	}

	public void setBelongsTo(String belongsTo) {
		BelongsTo = belongsTo;
	}

	public String getFranchise() {
		return Franchise;
	}

	public void setFranchise(String franchise) {
		Franchise = franchise;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
}
