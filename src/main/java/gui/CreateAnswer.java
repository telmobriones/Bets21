package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import java.awt.Font;

public class CreateAnswer extends JFrame {

	private JPanel contentPane;
	private JTextField txtQuestion;
	private JPanel panel;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateAnswer frame = new CreateAnswer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CreateAnswer() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtQuestion = new JTextField();
		txtQuestion.setHorizontalAlignment(SwingConstants.CENTER);
		txtQuestion.setFont(new Font("Dialog", Font.PLAIN, 18));
		txtQuestion.setEditable(false);
		txtQuestion.setBounds(5, 5, 440, 19);
		txtQuestion.setText("Question");
		contentPane.add(txtQuestion);
		txtQuestion.setColumns(10);
		
		JButton btnAnswerQuestion = new JButton("Answer Question");
		btnAnswerQuestion.setBounds(5, 246, 440, 25);
		contentPane.add(btnAnswerQuestion);
		
		panel = new JPanel();
		panel.setBounds(12, 102, 426, 64);
		contentPane.add(panel);
		panel.setLayout(null);
		
		btnNewButton = new JButton("1");
		btnNewButton.setBounds(0, 12, 136, 40);
		panel.add(btnNewButton);
		
		btnNewButton_1 = new JButton("x");
		btnNewButton_1.setBounds(148, 12, 129, 40);
		panel.add(btnNewButton_1);
		
		btnNewButton_2 = new JButton("2");
		btnNewButton_2.setBounds(289, 12, 125, 40);
		panel.add(btnNewButton_2);
		
		textField = new JTextField();
		textField.setBounds(172, 178, 114, 36);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblBetAmount = new JLabel("Bet amount");
		lblBetAmount.setFont(new Font("Dialog", Font.BOLD, 15));
		lblBetAmount.setBounds(51, 180, 104, 34);
		contentPane.add(lblBetAmount);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(15, 36, 423, 64);
		contentPane.add(panel_1);
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));
		
		Border border1 = BorderFactory.createLineBorder(Color.BLUE, 5);
		Border border2 = BorderFactory.createLineBorder(Color.CYAN, 5);
		
		JLabel lbl1 = new JLabel("2.5");
		lbl1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl1.setBorder(border1);
		panel_1.add(lbl1);
		
		JLabel lbl2 = new JLabel("1.7");
		lbl2.setHorizontalAlignment(SwingConstants.CENTER);
		lbl2.setBorder(border2);
		panel_1.add(lbl2);
		
		JLabel lbl3 = new JLabel("1.05");
		lbl3.setHorizontalAlignment(SwingConstants.CENTER);
		lbl3.setBorder(border1);
		panel_1.add(lbl3);
	}
}