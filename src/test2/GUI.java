package test2;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class GUI extends JFrame implements ActionListener{
	int optionCount = 1;
	JButton button;
    JTextField textField = new JTextField();
	JLabel label = new JLabel("Option 1");
    
	public GUI() {
        setSize(500, 500); // Set frame size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Small Window with Small Button");
        setLayout(new GridLayout(15, 1, 2, 10)); // Disable layout manager
        setVisible(true);
        
        label.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(label);
        
        textField.setBorder(new EmptyBorder(0, 10, 0, 10));
        textField.setPreferredSize(new Dimension(250, 40));
        add(textField);
        
        button = new JButton("submit");
        button.setBounds(50, 50, 100, 30); // Set button bounds (x, y, width, height)
        button.addActionListener(this);
        add(button);
        pack();
	}

    public static void main(String[] args) {
        new GUI();
    }

	@Override
	public void actionPerformed(ActionEvent e) {
	    if (e.getSource() == button) {
	        if (textField.getText().equals("")) {
	            textField.setText(""); // Clear the text field
	        } else {
	        	remove(button);
	        	textField = new JTextField();
	        	textField.setPreferredSize(new Dimension(250, 40));
	        	JLabel label = new JLabel("Option " + (++optionCount));
	        	
	        	add(label);
	            add(textField);
	            add(button);
	            revalidate();
                repaint();
	        }
	    }
	}
}