package hotel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class Frame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JPanel petDataPanel = new JPanel();
	
	private JComboBox<String> petComboBox = new JComboBox<String>();
	private JTextField descTextField;
	private JDatePickerImpl acceptDateField;
	
	JButton addNewSpeciesButton;
	JButton addNewPetButton;
	
	private PetsManager petsManager;
	
	private JTable table;
	private static Species species = new Species();
	private JTextField totalpriceTextField;
	double totalprice = 0;
	
	JPopupMenu popupMenu;
	JMenuItem menuReleasePet;
	JMenuItem menuRemovePet;
	JMenuItem menuStakeChange; 
	
	public Frame() {
		createGUI();
		readDataFromFile();
		setButtonsListeners();
		setMenuActionListeners();
	}
	
	private void readDataFromFile() {
		if(new File("pets.txt").isFile()) {
			BufferedReader br;
			boolean isEmpty = false;
			try {
				br = new BufferedReader(new FileReader("pets.txt"));
				if(br.readLine() == null)
					isEmpty = true;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			System.out.println(isEmpty);
			if(!isEmpty) {
				petsManager = new PetsManager(DataStorageManager.readPetsData());
				DataStorageManager.savePetsData(petsManager.getPetsList());
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				for(Pet pet : petsManager.getPetsList())
				model.addRow(new Object[]{pet.getName(), pet.getDescription(),
						pet.getAcceptDate(), 
						pet.getReleaseDate().equals("0000-00-00") ? "nieodebrano" : pet.getReleaseDate(),
						pet.getStayTime(), pet.getPrice()});
				totalpriceTextField.setText(petsManager.calculateTotalprice());
			}
		} else {
			File f = new File("pets.txt");
			try {
				f.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private PetsManager getPetListFromTable(DefaultTableModel model) {
		PetsManager pm = new PetsManager(DataStorageManager.readPetsData());
		for(int i = 0; i < model.getRowCount(); i++) {
			Pet pet = new Pet();
			pet.setStake1(pm.getPetsList().get(i).getStake1());
			pet.setStake2(pm.getPetsList().get(i).getStake2());
			pet.setStake3(pm.getPetsList().get(i).getStake3());
			pet.setName(String.valueOf(model.getValueAt(i, 0)));
			pet.setDescription(String.valueOf(model.getValueAt(i, 1)));
			String acceptDateString = String.valueOf(model.getValueAt(i, 2));
			Date acceptDate = null;
			try {
				acceptDate = new SimpleDateFormat("yyyy-MM-dd").parse(acceptDateString);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			pet.setAcceptDate(acceptDate);
			String releaseDateString = String.valueOf(model.getValueAt(i, 3)).equals("nieodebrano") ? 
					"nieodebrano" : String.valueOf(model.getValueAt(i, 3));
			Date releaseDate = null;
			if(releaseDateString.equals("nieodebrano")) {
				
			} else {
				try {
					releaseDate = new SimpleDateFormat("yyyy-MM-dd").parse(releaseDateString);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
			pet.setReleaseDate(releaseDate);
			String st = String.valueOf(model.getValueAt(i, 4));
			pet.setStayTime(st.equals("-") ? 0 : Integer.parseInt(st));
			String price = String.valueOf(model.getValueAt(i, 5));
			pet.setPrice(st.equals("-") ? 0 : Double.parseDouble(price.replace(",", ".")));
			petsManager.getPetsList().add(pet);
			totalpriceTextField.setText(petsManager.calculateTotalprice());
		}
		return petsManager;
	}
	
	private void createGUI() {
		setTitle("Hotel dla zwierz\u0105t");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		contentPane = new JPanel();
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		
		totalpriceTextField = new JTextField();
		
		JLabel lblNewLabel_1 = new JLabel("\u0141\u0105cznie:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addContainerGap(1031, Short.MAX_VALUE)
							.addComponent(lblNewLabel_1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(totalpriceTextField, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addGap(4)
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 1164, Short.MAX_VALUE))
						.addComponent(petDataPanel, GroupLayout.PREFERRED_SIZE, 770, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(petDataPanel, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
					.addGap(42)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(totalpriceTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nazwa", "Opis", "Data przyj\u0119cia", "Data odbioru", "Ilo\u015B\u0107 dni", "Cena"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(80);
		table.getColumnModel().getColumn(1).setPreferredWidth(120);
		table.getColumnModel().getColumn(2).setPreferredWidth(80);
		table.getColumnModel().getColumn(4).setPreferredWidth(55);
		table.getColumnModel().getColumn(5).setMinWidth(60);
		table.getColumnModel().getColumn(5).setMaxWidth(75);
		((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer())
			.setHorizontalAlignment(JLabel.CENTER);
		
		popupMenu = new JPopupMenu();
		menuReleasePet = new JMenuItem("Odebrano");
		menuRemovePet = new JMenuItem("Usuñ rekord");
		menuStakeChange = new JMenuItem("Zmieñ stawke");
		popupMenu.add(menuReleasePet);
		popupMenu.add(menuRemovePet);
		popupMenu.add(menuStakeChange);
		table.setComponentPopupMenu(popupMenu);
		
		JLabel petNameLabel = new JLabel("Gatunek:");
		petNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel acceptDate = new JLabel("Data przyj\u0119cia:");
		acceptDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		UtilDateModel acceptModel = new UtilDateModel();
		JDatePanelImpl acceptDatePanel = new JDatePanelImpl(acceptModel);;
		
		JLabel lblNewLabel = new JLabel("Opis:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		descTextField = new JTextField();
		descTextField.setColumns(10);
		
		acceptDateField = new JDatePickerImpl(acceptDatePanel);
		
		addNewSpeciesButton = new JButton("Dodaj gatunek");
		addNewSpeciesButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		addNewPetButton = new JButton("Dodaj zwierzaka");
		addNewPetButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		GroupLayout gl_petDataPanel = new GroupLayout(petDataPanel);
		gl_petDataPanel.setHorizontalGroup(
			gl_petDataPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_petDataPanel.createSequentialGroup()
					.addGroup(gl_petDataPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_petDataPanel.createSequentialGroup()
							.addGap(52)
							.addGroup(gl_petDataPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(petNameLabel)
								.addComponent(lblNewLabel)))
						.addGroup(gl_petDataPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(acceptDate)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_petDataPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_petDataPanel.createSequentialGroup()
							.addGroup(gl_petDataPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(petComboBox, 0, 215, Short.MAX_VALUE)
								.addComponent(descTextField, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE))
							.addGap(18)
							.addGroup(gl_petDataPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(addNewSpeciesButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(addNewPetButton, GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE))
							.addGap(137))
						.addGroup(gl_petDataPanel.createSequentialGroup()
							.addComponent(acceptDateField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
		);
		gl_petDataPanel.setVerticalGroup(
			gl_petDataPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_petDataPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_petDataPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(petNameLabel)
						.addComponent(petComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(addNewSpeciesButton))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_petDataPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(descTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(addNewPetButton))
					.addGap(12)
					.addGroup(gl_petDataPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(acceptDate)
						.addComponent(acceptDateField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(56, Short.MAX_VALUE))
		);
		petComboBox.setModel(new DefaultComboBoxModel<String>(species.readSpeciesFromFile()));
		petDataPanel.setLayout(gl_petDataPanel);
		contentPane.setLayout(gl_contentPane);
	}
	
	private void setButtonsListeners() {
		ButtonsListenerGen buttonsListenerGen = new ButtonsListenerGen();
		addNewSpeciesButton.addActionListener(buttonsListenerGen
				.setButtonsListener(addNewSpeciesButton.getText()));
		addNewPetButton.addActionListener(buttonsListenerGen
				.setButtonsListener(addNewPetButton.getText()));
	}
	
	private class ButtonsListenerGen {
		public ActionListener setButtonsListener(String buttonText) {
			ActionListener action = null;
			switch(buttonText) {
				case "Dodaj zwierzaka": 
					action = new AddNewPetListener();
					break;
				case "Dodaj gatunek":
					action = new AddNewSpecieListener();
					break;
				default:
					break;
			}
			return action;
		}
		
		class AddNewPetListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = String.valueOf(petComboBox.getSelectedItem());
				String description = descTextField.getText();
				Date acceptDate = (Date) acceptDateField.getModel().getValue();
				if(description.equals("") || description == null) {
					JOptionPane.showMessageDialog(contentPane, 
							"Nie podano opisu",
							"B³¹d",
							JOptionPane.ERROR_MESSAGE);
				} else {
					if(acceptDate == null) {
						JOptionPane.showMessageDialog(contentPane, 
								"Nie podanno daty",
								"B³¹d daty",
								JOptionPane.ERROR_MESSAGE);
					} else {
						Pet newPet = new Pet(name, description, acceptDate);
						petsManager.getPetsList().add(newPet);
						DataStorageManager.savePetsData(petsManager.getPetsList());
						petsManager = new PetsManager(DataStorageManager.readPetsData());
						DefaultTableModel model = (DefaultTableModel) table.getModel();
						if(model.getRowCount() > 1) {
							for(int i = model.getRowCount() - 1; i > -1; i--)
								model.removeRow(i);
						}
						System.out.println("petsManagersize: " + petsManager.getPetsList().size());
						for(Pet pet : petsManager.getPetsList()) {
							model.addRow(new Object[]{pet.getName(), pet.getDescription(), 
									pet.getAcceptDate(), "nieodebrano", pet.getStayTime(), 
									pet.getPrice()});
						}
						}
					}
				}
		}
		
		class AddNewSpecieListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				Thread t = new Thread() {
					public void run() {
						try {
							while(true) {
								TimeUnit.MILLISECONDS.sleep(1);
							}
						} catch(InterruptedException e) {
							System.out.println("Przerwano czekanie");
							petComboBox.setModel(new DefaultComboBoxModel<String>(species.getSpecies()));
						}
					}
				};
				t.start();
				new AddSpeciesDialog(species, t);
			}
		}
	}
	
	private void setMenuActionListeners() {
		MenuActionListenerGen menuActionListenerGen = new MenuActionListenerGen();
		menuReleasePet.addActionListener(menuActionListenerGen.setMenuListener(menuReleasePet.getText()));
		menuRemovePet.addActionListener(menuActionListenerGen.setMenuListener(menuRemovePet.getText()));
		menuStakeChange.addActionListener(menuActionListenerGen.setMenuListener(menuStakeChange.getText()));
	}
	
	private class MenuActionListenerGen {
		public ActionListener setMenuListener(String optionName) {
			ActionListener action = null;
			switch(optionName) {
				case "Odebrano":
					action = new ReleasePetListener();
					break;
				case "Usuñ rekord":
					action = new RemovePetListener();
					break;
				case "Zmieñ stawke":
					action = new ChangeStakeListener();
					break;
				default:
					break;
			}
			return action;
		}
		
		class ReleasePetListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				petsManager = new PetsManager(DataStorageManager.readPetsData());
				int selectedRow = table.getSelectedRow();
				//System.out.println("Przed odebraniem:\n" + petsManager.getPetsList().get(selectedRow).getStake1() + " " +  
				//		petsManager.getPetsList().get(selectedRow).getStake2() + " " +  
				//		petsManager.getPetsList().get(selectedRow).getStake3());
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				String acceptDateString = String.valueOf(model.getValueAt(selectedRow, 2));
				Date acceptDate = null;
				try {
					acceptDate = new SimpleDateFormat("yyyy-MM-dd").parse(acceptDateString);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				
				petsManager.getPetsList().get(selectedRow).setAcceptDate(acceptDate);
				petsManager.getPetsList().get(selectedRow).relaseTime();
				model.setValueAt(petsManager.getPetsList().get(selectedRow)
						.getReleaseDate(), selectedRow, 3);
				model.setValueAt(petsManager.getPetsList().get(selectedRow)
						.getStayTime(), selectedRow, 4);
				model.setValueAt(petsManager.getPetsList().get(selectedRow)
						.getPrice(), selectedRow, 5);
				//System.out.println(petsManager.getPetsList().get(selectedRow).getStake1());
				//System.out.println(petsManager.getPetsList().get(selectedRow).getStake2());
				//System.out.println(petsManager.getPetsList().get(selectedRow).getStake3());
				//System.out.println(model.getValueAt(selectedRow, 5));
				petsManager = getPetListFromTable(model);
				DataStorageManager.savePetsData(petsManager.getPetsList());
				totalpriceTextField.setText(petsManager.calculateTotalprice());
			}
		}
		
		class RemovePetListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.removeRow(selectedRow);
				petsManager = getPetListFromTable(model);
				DataStorageManager.savePetsData(petsManager.getPetsList());
				totalpriceTextField.setText(petsManager.calculateTotalprice());
			}
		}
		
		class ChangeStakeListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				System.out.println(petsManager.getPetsList().get(selectedRow).getStake1() + " " +  
						petsManager.getPetsList().get(selectedRow).getStake2() + " " +  
						petsManager.getPetsList().get(selectedRow).getStake3());
				new StakeChangeDialog(petsManager, selectedRow);
				System.out.println("Po zmianie\n" + petsManager.getPetsList().get(selectedRow).getStake1() + " " +  
						petsManager.getPetsList().get(selectedRow).getStake2() + " " +  
						petsManager.getPetsList().get(selectedRow).getStake3());
			}
		}
	}
}

