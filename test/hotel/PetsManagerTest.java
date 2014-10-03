package hotel;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class PetsManagerTest {
	private PetsManager pm;
	@Before
	public void setUp() throws Exception {
		ArrayList<Pet> pets = new ArrayList<Pet>();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2014);
		cal.set(Calendar.MONTH, 6);
		cal.set(Calendar.DAY_OF_MONTH, 2);
		Date acceptDate = cal.getTime();
		cal.set(Calendar.YEAR, 2014);
		cal.set(Calendar.MONTH, 7);
		cal.set(Calendar.DAY_OF_MONTH, 2);
		Date releaseDate = cal.getTime();
		Pet pet = new Pet("TestPet", "Lab rat", acceptDate, releaseDate, 3, 4, 5);
		pets.add(pet);
		pets.add(pet);
		pets.add(pet);
		pm = new PetsManager(pets);
	}

	@Test
	public void testCalculateTotalprice() {
		System.out.println(pm.calculateTotalprice());
		assertEquals("calculateTotalPrice() should return '465.0'",
				"465.0", pm.calculateTotalprice());
	}

}
