package co.edu.unbosque.model.util;


import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="date")
@SessionScoped
public class DateMaker {
	public String date2String(Date date) {
		var dateformat = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println(dateformat.format(date));
		return dateformat.format(date);
	}
	
	public String date2exp(Date date) {
		var dateformat = new SimpleDateFormat("MM/yyyy");
		System.out.println(dateformat.format(date));
		return dateformat.format(date);
	}
}
