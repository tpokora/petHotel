package hotel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataStorageManager {
	private static PetsManager petsManager;
	private static PrintWriter writer = null;
	private static FileReader fileReader = null;
	private static BufferedReader bufferedReader = null;
	private static Matcher matcher = null;
	private static Pattern pattern = null;
	
	public PetsManager getPetsManager() {
		return petsManager;
	}
	
	public static void savePetsData(ArrayList<Pet> petsList) {
		try {
			writer = new PrintWriter("pets.txt", "Cp1250");
			for(Pet pet : petsList)
				writer.println(pet.petRow());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}
	}
	
	public static ArrayList<Pet> readPetsData() {
		String namePatterStr = "#N:(.*?);";
		String descPatternStr = "#D:(.*?);";
		String adPatternStr = "#AD:(.*?);";
		String rdPatternStr = "#RD:(.*?);";
		String s1PatternStr = "#S1:(.*?);";
		String s2PatternStr = "#S2:(.*?);";
		String s3PatternStr = "#S3:(.*?);";
		ArrayList<Pet> petsList = new ArrayList<Pet>();
		try {
			fileReader = new FileReader("pets.txt");
			bufferedReader = new BufferedReader(fileReader);
			String petLine;
			while((petLine = bufferedReader.readLine()) != null) {
				byte text[] = petLine.getBytes("Cp1250");
				petLine = new String(text, "Cp1250");
				String name = readStringData(namePatterStr, petLine);
				String description = readStringData(descPatternStr, petLine);
				Date ad = readDate(adPatternStr, petLine);
				Date rd = readDate(rdPatternStr, petLine);
				Double stake1 = readStake(s1PatternStr, petLine);
				Double stake2 = readStake(s2PatternStr, petLine);
				Double stake3 = readStake(s3PatternStr, petLine);
				petsList.add(new Pet(name, description, ad, rd,stake1, stake2, stake3));
			}
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		petsManager = new PetsManager(petsList);
		System.out.println("Data PetM: " + petsManager.getPetsList().size());
		return petsManager.getPetsList();
	}
	
	private static String readStringData(String patternStr, String petLine) {
		pattern = Pattern.compile(patternStr);
		matcher = pattern.matcher(petLine);
		String str = "";
		if(matcher.find()) {
			str = matcher.group(1);
		}
		return str;
	}
	
	private static Date readDate(String patternStr, String petLine) {
		pattern = Pattern.compile(patternStr);
		matcher = pattern.matcher(petLine);
		Date date = null;
		if(matcher.find()) {
			if(!matcher.group(1).equals("nieodebrano")) {
				try {
					date = new SimpleDateFormat("yyyy-MM-dd").parse(matcher.group(1));
					
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}	
		return date;
	}
	
	private static double readStake(String patternStr, String petLine) {
		pattern = Pattern.compile(patternStr);
		matcher = pattern.matcher(petLine);
		double stake = 0.0;
		if(matcher.find()) {
			stake = Double.parseDouble(matcher.group(1).replace(",", "."));
		}
		return stake;
	}
	
	public static void main(String[] args) {
		ArrayList<Pet> pets = readPetsData();
	}
}
