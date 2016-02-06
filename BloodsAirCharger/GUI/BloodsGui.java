package scripts.BloodsAirCharger.GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.event.ChangeListener;

import scripts.BloodsAirCharger.API.References;
import scripts.BloodsAirCharger.*;

import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import java.awt.TextField;
import java.awt.event.TextListener;
import java.awt.event.TextEvent;

public class BloodsGui extends JFrame implements KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String getFoodName;
	public boolean endGui = false;
	JTextField foodString = new JTextField(15);

	private ScriptMain script;
	
	public BloodsGui(ScriptMain script) {
		this.script = script;
		initComponents();
		
	}
	
	/**
	 * Create the frame.
	 * @return 
	 */
	private void initComponents() { 
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		useFoodCheck = new JCheckBox("Don't use Food");
		useFoodCheck.setBounds(6, 182, 97, 23);
		contentPane.add(useFoodCheck);

		chckbxUseStaminas = new JCheckBox();
		chckbxUseStaminas.setBounds(6, 156, 97, 23);
		chckbxUseStaminas.setText("Use Stamina's?");
		contentPane.add(chckbxUseStaminas);

		lblNewLabel = new JLabel("Name of Food");
		lblNewLabel.setFont(new Font("Bodoni MT", Font.BOLD, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(142, 132, 129, 14);
		contentPane.add(lblNewLabel);

		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel.setBounds(6, 11, 418, 74);
		contentPane.add(panel);
		panel.setLayout(null);

		lblBloodflavorsAirOrbs = new JLabel("BloodFlavor's Air Orb's");
		lblBloodflavorsAirOrbs.setBounds(10, 11, 396, 51);
		panel.add(lblBloodflavorsAirOrbs);
		lblBloodflavorsAirOrbs.setFont(new Font("Poor Richard", Font.PLAIN, 32));
		lblBloodflavorsAirOrbs.setHorizontalAlignment(SwingConstants.CENTER);

		btnNewButton = new JButton("Start");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnNewButtonActionPerformed(evt);
			}
		});
		btnNewButton.setBounds(142, 215, 135, 23);
		contentPane.add(btnNewButton);

		lblNewLabel_1 = new JLabel("Start at Edgeville\r\n bank with a \r\nStaff of Air \r\nequipped and\r\n Glory(Charged) \r\nequipped.");
		lblNewLabel_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(16, 96, 408, 29);
		contentPane.add(lblNewLabel_1);

		lblCaseSensitive = new JLabel("Case sensitive");
		lblCaseSensitive.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblCaseSensitive.setBounds(174, 141, 76, 19);
		contentPane.add(lblCaseSensitive);

		lblNewLabel_2 = new JLabel("Amount of Food ");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Bodoni MT", Font.BOLD, 16));
		lblNewLabel_2.setBounds(281, 128, 129, 23);
		contentPane.add(lblNewLabel_2);

		amountOfFood = new JSlider();
		amountOfFood.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				References.get().foodToTake = amountOfFood.getValue();
			}
		});
		amountOfFood.setPaintLabels(true);
		amountOfFood.setSnapToTicks(true);
		amountOfFood.setBackground(SystemColor.menu);
		amountOfFood.setMinorTickSpacing(1);
		amountOfFood.setMajorTickSpacing(2);
		amountOfFood.setValue(3);
		amountOfFood.setMaximum(10);
		amountOfFood.setBounds(281, 156, 135, 38);
		contentPane.add(amountOfFood);

		TextField textField = new TextField();
		textField.addTextListener(new TextListener() {
			public void textValueChanged(TextEvent e) {
				References.get().food = textField.getText();
			}
		});
		textField.setBounds(153, 166, 103, 23);
		contentPane.add(textField);



	}
	private void btnNewButtonActionPerformed(java.awt.event.ActionEvent evt) {
		if(!amountOfFood.getValueIsAdjusting()) {
			References.get().foodToTake = amountOfFood.getValue();
		}
		if(chckbxUseStaminas.isSelected()) {
			References.get().letsUseStaminas = true;
		}else {
			References.get().letsUseStaminas = false;
		}
		if(useFoodCheck.isSelected()) {
			References.get().useFood = false;
		}else {
			References.get().useFood = true;
		}
		if(getFoodName != null) {
			References.get().food = getFoodName;
		}
		References.get().foodToTake = amountOfFood.getValue();
		References.get().mode = Mode.WALKTOBANK;
		script.TASK_MANAGER.addAppropriateTasks();
		References.get().guiComplete = true;
		this.dispose();

	}
	private javax.swing.JCheckBox chckbxUseStaminas;
	private javax.swing.JCheckBox useFoodCheck;
	private javax.swing.JButton btnNewButton;
	private JLabel lblBloodflavorsAirOrbs;
	private JSlider amountOfFood;
	private JLabel lblNewLabel_2;
	private JLabel lblCaseSensitive;
	private JLabel lblNewLabel_1;
	private JPanel panel;
	private JLabel lblNewLabel;
	private JLabel textHolder;

	@Override
	public void keyTyped(KeyEvent e) {
		References.get().food = e.getKeyText(15);

	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
