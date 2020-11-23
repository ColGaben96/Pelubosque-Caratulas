package co.edu.unbosque.model.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="date")
@SessionScoped
public class DateMaker {
	public String date2String(long date) {
		SimpleDateFormat dateform = new SimpleDateFormat("dd/MM/yyyy");
		return dateform.format(date);
	}
}
