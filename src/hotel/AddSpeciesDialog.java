package hotel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

public class AddSpeciesDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1729539319623512487L;
	private final JPanel contentPanel = new JPanel();
	private JTextField newSpeciesTextField;

	public AddSpeciesDialog(final Species species, final Thread t) {
		setTitle("Dodaj gatunek");
		setVisible(true);
		setBounds(100, 100, 253, 130);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel lblNowyGatunek = new JLabel("Nowy gatunek:");
		lblNowyGatunek.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		newSpeciesTextField = new JTextField();
		newSpeciesTextField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		newSpeciesTextField.setColumns(10);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(lblNowyGatunek)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(newSpeciesTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(240, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNowyGatunek)
						.addComponent(newSpeciesTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(194, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String newSpecie = newSpeciesTextField.getText();
						if(newSpecie == null || newSpecie.equals("")) {
							JOptionPane.showMessageDialog(contentPanel, 
									"Nie podano nazwy nowego gatunku!",
									"B��d",
									JOptionPane.ERROR_MESSAGE);
						} else {
							String[] speciesTable = species.readSpeciesFromFile();
							String[] tempTable = new String[speciesTable.length + 1];
							for(int i = 0; i < speciesTable.length; i++) 
								tempTable[i] = speciesTable[i];
							tempTable[tempTable.length - 1] = newSpecie;
							species.setSpecies(tempTable);
							species.saveSpeciesToFile();
							t.interrupt();
							dispose();
						}
						
					}
				});
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
			}
		}
	}
}
