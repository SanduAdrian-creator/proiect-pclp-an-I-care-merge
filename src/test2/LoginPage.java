package test2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginPage extends JFrame implements ActionListener {
    JTextField numeField, prenumeField;
    JButton loginButton, addUserButton;
    JLabel infoLabel;

    public LoginPage() {
        setTitle("Login Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        infoLabel = new JLabel("Enter your login details:");
        infoLabel.setFont(infoLabel.getFont().deriveFont(Font.BOLD, 14f));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(infoLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Nume:"), gbc);

        numeField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(numeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Prenume:"), gbc);

        prenumeField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(prenumeField, gbc);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(loginButton, gbc);

        addUserButton = new JButton("Add New User");
        addUserButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(addUserButton, gbc);

        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String nume = numeField.getText();
            String prenume = prenumeField.getText();
            if (nume.isEmpty() || prenume.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both nume and prenume.");
            } else {
                verifyLogin(nume, prenume);
            }
        } else if (e.getSource() == addUserButton) {
            showAddUserDialog();
        }
    }

    private void verifyLogin(String nume, String prenume) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
            
            String queryAngajati = "SELECT * FROM angajati WHERE nume = ? AND prenume = ?";
            PreparedStatement pstmtAngajati = conn.prepareStatement(queryAngajati);
            pstmtAngajati.setString(1, nume);
            pstmtAngajati.setString(2, prenume);
            ResultSet rsAngajati = pstmtAngajati.executeQuery();

            if (rsAngajati.next()) {
                dispose();
                new EmployeeGUI();
                return;
            }
            
            String queryClienti = "SELECT * FROM clienti WHERE nume = ? AND prenume = ?";
            PreparedStatement pstmtClienti = conn.prepareStatement(queryClienti);
            pstmtClienti.setString(1, nume);
            pstmtClienti.setString(2, prenume);
            ResultSet rsClienti = pstmtClienti.executeQuery();

            if (rsClienti.next()) {
                dispose(); 
                new GUI(nume, prenume, false);
                return;
            }

            JOptionPane.showMessageDialog(this, "Login failed. Please check your credentials.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: Unable to connect to database.");
        }
    }

    private void showAddUserDialog() {
        JTextField numeField = new JTextField();
        JTextField prenumeField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {
                "Nume:", numeField,
                "Prenume:", prenumeField,
                "Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Add New User", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String nume = numeField.getText();
            String prenume = prenumeField.getText();
            char[] password = passwordField.getPassword();

            if (new String(password).equals("palmier")) {
                addEmployee(nume, prenume);
            } else {
                addCustomer(nume, prenume);
            }
        }
    }

    private void addEmployee(String nume, String prenume) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
            String getMaxIdQuery = "SELECT MAX(id) FROM angajati";
            PreparedStatement getMaxIdStmt = conn.prepareStatement(getMaxIdQuery);
            ResultSet rs = getMaxIdStmt.executeQuery();
            int maxId = 0;
            if (rs.next()) {
                maxId = rs.getInt(1);
            }

            int newId = maxId + 1;

            String sql = "INSERT INTO angajati (id, nume, prenume, functie, salariu) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, newId);
            pstmt.setString(2, nume);
            pstmt.setString(3, prenume);
            pstmt.setString(4, "Random Function");
            pstmt.setInt(5, 5000);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Employee added successfully with ID: " + newId);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void addCustomer(String nume, String prenume) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
            String getMaxIdQuery = "SELECT MAX(id) FROM clienti";
            PreparedStatement getMaxIdStmt = conn.prepareStatement(getMaxIdQuery);
            ResultSet rs = getMaxIdStmt.executeQuery();
            int maxId = 0;
            if (rs.next()) {
                maxId = rs.getInt(1);
            }

            int newId = maxId + 1;

            String sql = "INSERT INTO clienti (id, nume, prenume) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, newId);
            pstmt.setString(2, nume);
            pstmt.setString(3, prenume);
            pstmt.executeUpdate();

            String createCosSql = "INSERT INTO cos (id, nrProduse, total) VALUES (?, ?, ?)";
            PreparedStatement createCosStmt = conn.prepareStatement(createCosSql);
            createCosStmt.setInt(1, newId);
            createCosStmt.setInt(2, 0); 
            createCosStmt.setInt(3, 0);
            createCosStmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Customer added successfully with ID: " + newId);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}