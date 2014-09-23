package hotel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class StakeChangeDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3541476412035690442L;
	private final JPanel contentPanel = new JPanel();
	private JTextField stake1TextField;
	private JTextField stake2TextField;
	private JTextField stake3TextField;

	public StakeChangeDialog(final PetsManager petsManager, final int petindex) {
		setTitle("Zmiana stawki");
		setVisible(true);
		setBounds(100, 100, 266, 182);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.WEST);
		JLabel lblDoDni = new JLabel("Do 5 dni:");
		lblDoDni.setFont(new Font("Tahoma", Font.PLAIN, 14));
		JLabel lblDoDni_1 = new JLabel("Do 15 dni:");
		lblDoDni_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		JLabel lblPnadDni = new JLabel("Ponad 15 dni:");
		lblPnadDni.setFont(new Font("Tahoma", Font.PLAIN, 14));
		stake1TextField = new JTextField();
		stake1TextField.setColumns(10);
		stake1TextField.setText(String.valueOf(petsManager.getPetsList()
				.get(petindex).getStake1()));
		stake2TextField = new JTextField();
		stake2TextField.setColumns(10);
		stake2TextField.setText(String.valueOf(petsManager.getPetsList()
				.get(petindex).getStake2()));
		stake3TextField = new JTextField();
		stake3TextField.setColumns(10);
		stake3TextField.setText(String.valueOf(petsManager.getPetsList()
				.get(petindex).getStake3()));
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblDoDni_1)
						.addComponent(lblDoDni)
						.addComponent(lblPnadDni))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(stake1TextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(stake2TextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(stake3TextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(238, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDoDni)
						.addComponent(stake1TextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDoDni_1)
						.addComponent(stake2TextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPnadDni)
						.addComponent(stake3TextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(136, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						petsManager.getPetsList().get(petindex)
							.setStake1(Double.parseDouble(stake1TextField.getText()));
						petsManager.getPetsList().get(petindex)
							.setStake2(Double.parseDouble(stake2TextField.getText()));
						petsManager.getPetsList().get(petindex)
							.setStake3(Double.parseDouble(stake3TextField.getText()));
						System.out.println("Po zmianie\n" + petsManager.getPetsList().get(petindex).getDescription() + ": " +
								petsManager.getPetsList().get(petindex).getStake1() + " " +  
								petsManager.getPetsList().get(petindex).getStake2() + " " +  
								petsManager.getPetsList().get(petindex).getStake3());
						DataStorageManager.savePetsData(petsManager.getPetsList());
						JOptionPane.showMessageDialog(contentPanel, 
								"Po zmianie stawek poszczeg�lnych zwierz�t\n"
										+ "pami�taj o ponownym uruchomieniu aplikacji!",
								"Przypomnienie",
								JOptionPane.INFORMATION_MESSAGE);
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Anuluj");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				
			}
		}
	}
}
