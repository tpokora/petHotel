package hotel;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class PetTest {
	private Pet pet;

	@Before
	public void setUp() throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2014);
		cal.set(Calendar.MONTH, 6);
		cal.set(Calendar.DAY_OF_MONTH, 2);
		Date acceptDate = cal.getTime();
		cal.set(Calendar.YEAR, 2014);
		cal.set(Calendar.MONTH, 7);
		cal.set(Calendar.DAY_OF_MONTH, 2);
		Date releaseDate = cal.getTime();
		pet = new Pet("TestPet", "Lab rat", acceptDate, releaseDate, 3, 4, 5);
	}

	@Test
	public void testGetName() {
		assertEquals("getName() should return 'TestPet'", "TestPet", pet.getName());
	}

	@Test
	public void testGetDescription() {
		assertEquals("getDescription() return 'Lab rat'", "Lab rat", pet.getDescription());
	}

	@Test
	public void testGetAcceptDate() {
		assertEquals("getAcceptDate() should return '2014-07-02'", 
				"2014-07-02", pet.getAcceptDate());
	}

	@Test
	public void testGetReleaseDate() {
		assertEquals("getReleaseDate() should return '2014-08-02'", 
				"2014-08-02", pet.getReleaseDate());
	}
	
	@Test
	public void testGetReleaseDateNull() {
		pet.setReleaseDate(null);
		assertEquals("getReleaseDate() should return 'nieodebrano'", "nieodebrano",
				pet.getReleaseDate());
	}

	@Test
	public void testGetStayTime() {
		assertEquals("getStayTime() should return '31'", "31", pet.getStayTime());
	}
	
	@Test
	public void testGetStayTimeWithReleaseDateLessThenOneDay() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2014);
		cal.set(Calendar.MONTH, 6);
		cal.set(Calendar.DAY_OF_MONTH, 2);
		Date releaseDate = cal.getTime();
		pet.setReleaseDate(releaseDate);
		pet.calculateStayTime();
		assertEquals("getStayTime() should return '-'", "-", pet.getStayTime());
	}
	
	@Test
	public void testGestStayTimeWithReleaseDateNullLessThenOneDay() {
		Date date = new Date();
		pet.setReleaseDate(null);
		pet.setAcceptDate(date);
		pet.calculateStayTime();
		assertEquals("getStayTime() should return '-'", "-", pet.getStayTime());
	}
	
	@Test
	public void testGestStayTimeWithReleaseDateNullMoreThenOneDay() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -10);
		date = cal.getTime();
		pet.setReleaseDate(null);
		pet.setAcceptDate(date);
		pet.calculateStayTime();
		assertEquals("getStayTime() should return '10'", "10", pet.getStayTime());
	}
	
	@Test
	public void testGetPriceForStayTimeLessThen6() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2014);
		cal.set(Calendar.MONTH, 6);
		cal.set(Calendar.DAY_OF_MONTH, 2);
		Date acceptDate = cal.getTime();
		cal.set(Calendar.YEAR, 2014);
		cal.set(Calendar.MONTH, 6);
		cal.set(Calendar.DAY_OF_MONTH, 7);
		Date releaseDate = cal.getTime();
		pet = new Pet("TestPet", "Lab rat", acceptDate, releaseDate, 3, 4, 5);
		assertEquals("getPrice() should return '15.00'", "15.00", pet.getPrice());
	}
	
	@Test
	public void testGetPriceForStayTimeLessThen15() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2014);
		cal.set(Calendar.MONTH, 6);
		cal.set(Calendar.DAY_OF_MONTH, 2);
		Date acceptDate = cal.getTime();
		cal.set(Calendar.YEAR, 2014);
		cal.set(Calendar.MONTH, 6);
		cal.set(Calendar.DAY_OF_MONTH, 12);
		Date releaseDate = cal.getTime();
		pet = new Pet("TestPet", "Lab rat", acceptDate, releaseDate, 3, 4, 5);
		assertEquals("getPrice() should return '40.00'", "40.00", pet.getPrice());
	}
	
	@Test
	public void testGetPriceForStayTimeMoreThen15() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2014);
		cal.set(Calendar.MONTH, 6);
		cal.set(Calendar.DAY_OF_MONTH, 2);
		Date acceptDate = cal.getTime();
		cal.set(Calendar.YEAR, 2014);
		cal.set(Calendar.MONTH, 6);
		cal.set(Calendar.DAY_OF_MONTH, 18);
		Date releaseDate = cal.getTime();
		pet = new Pet("TestPet", "Lab rat", acceptDate, releaseDate, 3, 4, 5);
		assertEquals("getPrice() should return '80.00'", "80.00", pet.getPrice());
	}

	@Test
	public void testPetRow() {
		assertEquals("petRow() should return "
				+"'#N:TestPet;#D:Lab rat;#AD:2014-07-02;#RD:2014-08-02;#ST:31;#S1:3.0;#S2:4.0;#S3:5.0;#P:155.00;'", 
				"#N:TestPet;#D:Lab rat;#AD:2014-07-02;#RD:2014-08-02;#ST:31;#S1:3.0;#S2:4.0;#S3:5.0;#P:155.00;", 
				pet.petRow());
	}

}
