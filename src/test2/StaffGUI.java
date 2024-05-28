package test2;

import javax.swing.*;

public class StaffGUI extends JFrame {
    public StaffGUI() {
        setTitle("Staff Interface");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        
        // Add your staff-specific components and logic here
        JLabel label = new JLabel("Welcome, Staff Member!");
        add(label);
    }
}