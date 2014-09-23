package hotel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PetsManager {
	private ArrayList<Pet> petsList = new ArrayList<Pet>();
	
	public PetsManager(ArrayList<Pet> petsList) {
		this.petsList = petsList;
	}
	
	public ArrayList<Pet> getPetsList() {
		sortList();
		return petsList;
	}
	
	public String calculateTotalprice() {
		double totalprice = 0;
		for(Pet pet : petsList)
			totalprice += Double.parseDouble(pet.getPrice().replace(",", "."));
		return String.valueOf(totalprice).replace(",", ".");
	}
	
	private void sortList() {
		Collections.sort(petsList, new Comparator<Pet>() {
			@Override
			public int compare(Pet a, Pet b) {
				return b.getAcceptDate().compareTo(a.getAcceptDate());
			}
		});
	}
}
