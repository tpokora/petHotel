package hotel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Species {

	private ArrayList<String> list = new ArrayList<String>();
	private String[] species = { "Agama Brodata", "Agama B這tna", "Agama Kar這wata",
			"Bazyliszek P豉tkog這wy", "Gekon Lamparci", "Kameleon Jeme雟ki",
			"Po這z Amurski", "Pyton Kr鏊ewski", "Waran Kolczastoogonowy",
			"W嘀 Chi雟ki", "草逕 Stepowy" };
	
	public Species() {
		creatingBasicFile();
	}
	
	public String[] getSpecies() {
		return species;
	}
	
	public void setSpecies(String[] species) {
		this.species = species;
	}
	
	public void saveSpeciesToFile() {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("species.txt", "Cp1250");
			for(String str : species) {
				System.out.println(str);
				writer.println(str);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}
		System.out.println("Zapisano liste do pliku");
	}
	
	public String[] readSpeciesFromFile() {
		System.out.println("Czytanie");
		if(new File("species.txt").isFile()) {
			BufferedReader br = null;
			boolean isEmpty = false;
			try {
				br = new BufferedReader(new FileReader("species.txt"));
				if(br.readLine() == null)
					isEmpty = true;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if(!isEmpty) {
				list.clear();			
				try {
					br = new BufferedReader(new FileReader("species.txt"));
					String nameLine;
					while((nameLine = br.readLine()) != null) {
						list.add(nameLine);
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch(IOException e) {
					e.printStackTrace();
				} finally {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			creatingBasicFile();
		}
		int listSize = list.size();
		String[] temp = new String[listSize];
		for(int i = 0; i < listSize; i++)
			temp[i] = list.get(i);
		//for(String s : temp)
		//	System.out.println(s);
		return temp;
	}
	
	private void creatingBasicFile() {
		if(!new File("species.txt").isFile()) {
			PrintWriter writer = null;
			System.out.println("Utworzono podstawowa wersje listy");
			try {
				writer = new PrintWriter("species.txt", "Cp1250");
				for(String str : species)
					writer.println(str);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} finally {
				writer.close();
			}
		}
	}
}
