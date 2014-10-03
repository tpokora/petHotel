package hotel;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Pet {
	private String name;
	private String description;
	private Date acceptDate;
	private Date releaseDate;
	private int stayTime = 0;
	private final SimpleDateFormat DATE_FORMAT = 
			new SimpleDateFormat("yyyy-MM-dd");
	private double price = 0;
	private double stake1 = 5;
	private double stake2 = 4;
	private double stake3 = 3;
	
	public Pet() {}
	public Pet(String name, String description, Date acceptDate) {
		this.name = name;
		this.description = description;
		this.acceptDate = acceptDate;
		calculateStayTime();
		calculatePrice();
	}
	public Pet(String name, String description, Date acceptDate,
			Date realeseDate, double stake1, double stake2, double stake3) {
		this.name = name;
		this.description = description;
		this.acceptDate = acceptDate;
		this.releaseDate = realeseDate;
		this.stake1 = stake1;
		this.stake2 = stake2;
		this.stake3 = stake3;
		calculateStayTime();
		calculatePrice();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAcceptDate() {
		return DATE_FORMAT.format(acceptDate);
	}
	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}
	public String getReleaseDate() {
		return releaseDate == null ? "nieodebrano" : DATE_FORMAT.format(releaseDate);
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public void setStayTimeFromFile(int stayTime) {
		this.stayTime = stayTime;
	}
	public void setStayTime(int stayTime) {
		this.stayTime = stayTime;
	}
	public String getStayTime() {
		return stayTime == 0  ? "-" : String.valueOf(stayTime);
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public void relaseTime() {
		releaseDate = new Date();
		calculateStayTime();
		calculatePrice();
	}
	public void calculateStayTime() {
		
		if(releaseDate == null) {
			Date currentDate = new Date();
			stayTime = (int)((currentDate.getTime() - acceptDate.getTime()) / (1000 * 60 * 60 * 24));
		}
		else 
			stayTime = (int)((releaseDate.getTime() - acceptDate.getTime()) / (1000 * 60 * 60 * 24));
	}
	
	public double getStake1() {
		return stake1;
	}
	
	public void setStake1(double steak1) {
		this.stake1 = steak1;
	}
	
	public double getStake2() {
		return stake2;
	}
	
	public void setStake2(double steak2) {
		this.stake2 = steak2;
	}
	
	public double getStake3() {
		return stake3;
	}
	
	public void setStake3(double steak3) {
		this.stake3 = steak3;
	}
	
	private void calculatePrice() {
		if(stayTime < 6) {
			price = stayTime * stake1;
		} else if(stayTime < 15) {
			price = stayTime * stake2;
		} else {
			price = stayTime * stake3;
		}
	}
	public String getPrice() {
		DecimalFormat df = new DecimalFormat("#.00");
		return price == 0 ? "0" : df.format(price);
	}
	
	public String petRow() {
		return "#N:" + name + ";#D:" + description + ";#AD:" + getAcceptDate() +
				";#RD:" + getReleaseDate() + ";#ST:" + getStayTime() + ";#S1:" 
				+ getStake1() + ";#S2:" + getStake2() + ";#S3:" + getStake3() +";#P:" + 
				getPrice() + ";";
	}
	
	
}
