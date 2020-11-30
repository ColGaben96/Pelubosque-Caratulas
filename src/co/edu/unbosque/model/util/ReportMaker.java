package co.edu.unbosque.model.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.mail.MessagingException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;

import co.edu.unbosque.model.Appointments;
import co.edu.unbosque.model.Emailer;
import co.edu.unbosque.model.Locations;
import co.edu.unbosque.model.User;

@ManagedBean(name = "reporter")
@SessionScoped
public class ReportMaker {
	private String email;
	private BarChartModel barModel;
	private File res = new File(System.getProperty("user.home")+"/Pelubosque/resources/");
	private File output = new File(System.getProperty("user.home")+"/Pelubosque/output/");

	@PostConstruct
	public void init() {
		getStatics();
		res = new File(System.getProperty("user.home")+"/Pelubosque/resources/");
		output = new File(System.getProperty("user.home")+"/Pelubosque/output/");
	}
		
	public ReportMaker() {

	}

	public int getAccounts() {
		return new User().userQuantity();
	}

	public int getPOS() {
		return new Locations().locationQuantity();
	}

	public int getAppointments() {
		return new Appointments().appointmentSize();
	}
	
	public void getStatics() {
		var date = new Date();
		var dformat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		barModel = new BarChartModel();
		var chartData = new ChartData();
		var barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Reporte de " + dformat2.format(date));
        List<Number> values = new ArrayList<>();
        values.add(getAccounts());
        values.add(getPOS());
        values.add(getAppointments());
        barDataSet.setData(values);
        List<String> bgColor = new ArrayList<>();
        bgColor.add("rgba(255, 99, 132, 0.2)");
        bgColor.add("rgba(255, 159, 64, 0.2)");
        bgColor.add("rgba(255, 205, 86, 0.2)");
        barDataSet.setBackgroundColor(bgColor);
        List<String> labels = new ArrayList<>();
        labels.add("Cuentas");
        labels.add("Puntos de Venta");
        labels.add("Citas Creadas");
        chartData.setLabels(labels);
        barModel.setData(chartData);
        List<String> borderColor = new ArrayList<>();
        borderColor.add("rgb(255, 99, 132)");
        borderColor.add("rgb(255, 159, 64)");
        borderColor.add("rgb(255, 205, 86)");
        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);
         
        chartData.addChartDataSet(barDataSet);
      //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);
         
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Made with Primefaces");
        options.setTitle(title);
 
        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontStyle("bold");
        legendLabels.setFontColor("#2980B9");
        legendLabels.setFontSize(24);
        legend.setLabels(legendLabels);
        options.setLegend(legend);
 
        barModel.setOptions(options);
	}
	
	public void generateAndSend() {
		System.out.println("Checking Paths");
		checkFolders();
		System.out.println("Generating PDF");
		makePDF();
		System.out.println("Sending Email");
		sendFile();
		System.out.println("Done!");
	}
	
	public void checkFolders() {
		System.out.println("Resources exists: "+res.exists());
		System.out.println("Output exists: "+output.exists());
		if(!res.exists()) {
			res.mkdir();
		}
		System.out.println("Resources exists: "+res.exists());
		System.out.println("Output exists: "+output.exists());	
		if(!output.exists()) {
			output.mkdir();
		}
		System.out.println("Resources exists: "+res.exists());
		System.out.println("Output exists: "+output.exists());
			
	}

	@SuppressWarnings("deprecation")
	public void makePDF() {
		var date = new Date();
		var dformat = new SimpleDateFormat("ddMMyyHHmm");
		var dformat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		var document = new PDDocument();
		var page = new PDPage();
		document.addPage(page);
		try {
			//var segoeUI = PDType0Font.load(document, new File(res+"/segoeui.ttf"));
			//var segoeUIl = PDType0Font.load(document, new File(res+"/segoeuil.ttf"));
			var content = new PDPageContentStream(document, page);
			content.beginText();
			content.moveTextPositionByAmount(10, 700);
			//content.setFont(segoeUI, 23);
			PDFont fontB = PDType1Font.HELVETICA_BOLD;
			PDFont font = PDType1Font.HELVETICA;
			content.setFont(fontB, 28);
			content.showText("Pelubosque: Reporte de " + dformat2.format(date));
			content.endText();
			content.beginText();
			content.moveTextPositionByAmount(10, 650);
			//content.setFont(segoeUIl, 12);
			content.setFont(font, 12);
			content.showText("Estos son los números hasta el momento:");
			content.endText();
			content.beginText();
			content.moveTextPositionByAmount(10, 630);
			//content.setFont(segoeUIl, 12);
			content.setFont(font, 12);
			content.showText("Cuentas: " + getAccounts() + " desde el principio de los tiempos.");
			content.endText();
			content.beginText();
			content.moveTextPositionByAmount(10, 610);
			//content.setFont(segoeUIl, 12);
			content.setFont(font, 12);
			content.showText("Puntos de Venta: " + getPOS() + " desde el principio de los tiempos.");
			content.endText();
			content.beginText();
			content.moveTextPositionByAmount(10, 590);
			//content.setFont(segoeUIl, 12);
			content.setFont(font, 12);
			content.showText("Citas Creadas: " + getAppointments() + " desde el principio de los tiempos.");
			content.endText();
			content.close();
			document.save(new File(output + "/" + dformat.format(date) + ".pdf"));
			document.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendFile() {
		var date = new Date();
		var dformat = new SimpleDateFormat("ddMMyyHHmm");
		var dformat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Emailer email = new Emailer();
		try {
			email.sendEmailWithAttachments(this.email, 
					"Pelubosque: Reporte de "+dformat2.format(date), 
					"<!DOCTYPE html>\n"
							+ "<html>\n"
							+ "    <head>\n"
							+ "        <meta charset=\"UTF-8\">\n"
							+ "    </head>\n"
							+ "    <body>\n"
							+ "        <p>Aqui está el reporte que solicitaste. <br>\n"
							+ "    </body>\n"
							+ "</html>", 
					new File(output + "/" + dformat.format(date) + ".pdf"));
		} catch (MessagingException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BarChartModel getBarModel() {
		return barModel;
	}

	public void setBarModel(BarChartModel barModel) {
		this.barModel = barModel;
	}
}
