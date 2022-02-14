package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import javax.swing.JLabel;

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
		txtQuestion.setEditable(false);
		txtQuestion.setBounds(5, 5, 440, 19);
		txtQuestion.setText("Question");
		contentPane.add(txtQuestion);
		txtQuestion.setColumns(10);
		
		JButton btnAnswerQuestion = new JButton("Answer Question");
		btnAnswerQuestion.setBounds(5, 246, 440, 25);
		contentPane.add(btnAnswerQuestion);
		
		panel = new JPanel();
		panel.setBounds(27, 71, 411, 64);
		contentPane.add(panel);
		panel.setLayout(null);
		
		btnNewButton = new JButton("New button");
		btnNewButton.setBounds(0, 12, 117, 40);
		panel.add(btnNewButton);
		
		btnNewButton_1 = new JButton("New button");
		btnNewButton_1.setBounds(140, 12, 117, 40);
		panel.add(btnNewButton_1);
		
		btnNewButton_2 = new JButton("New button");
		btnNewButton_2.setBounds(284, 12, 117, 40);
		panel.add(btnNewButton_2);
		
		textField = new JTextField();
		textField.setBounds(172, 178, 114, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblBetAmount = new JLabel("Bet amount");
		lblBetAmount.setBounds(51, 180, 104, 15);
		contentPane.add(lblBetAmount);
	}
}
