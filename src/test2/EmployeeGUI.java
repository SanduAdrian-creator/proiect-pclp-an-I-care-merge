package test2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class EmployeeGUI extends JFrame implements ActionListener {
    JTextField numeField, valabilitateField, disponibilitateField, pretField, ziField, lunaField, anField, tipField;
    JButton addProductButton;

    public EmployeeGUI() {
        setTitle("Employee Interface");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Nume:"));
        numeField = new JTextField(20);
        panel.add(numeField);

        panel.add(new JLabel("Valabilitate:"));
        valabilitateField = new JTextField(20);
        panel.add(valabilitateField);

        panel.add(new JLabel("Disponibilitate:"));
        disponibilitateField = new JTextField(20);
        panel.add(disponibilitateField);

        panel.add(new JLabel("Pret:"));
        pretField = new JTextField(20);
        panel.add(pretField);

        panel.add(new JLabel("Zi:"));
        ziField = new JTextField(20);
        panel.add(ziField);

        panel.add(new JLabel("Luna:"));
        lunaField = new JTextField(20);
        panel.add(lunaField);

        panel.add(new JLabel("An:"));
        anField = new JTextField(20);
        panel.add(anField);

        panel.add(new JLabel("Tip:"));
        tipField = new JTextField(20);
        panel.add(tipField);

        addProductButton = new JButton("Add New Product");
        addProductButton.addActionListener(this);
        panel.add(addProductButton);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addProductButton) {
            addNewProduct();
        }
    }

    private void addNewProduct() {
        String nume = numeField.getText();
        String valabilitate = valabilitateField.getText();
        String disponibilitate = disponibilitateField.getText();
        String pretText = pretField.getText();
        String ziText = ziField.getText();
        String lunaText = lunaField.getText();
        String anText = anField.getText();
        String tip = tipField.getText();

        if (nume.isEmpty() || valabilitate.isEmpty() || disponibilitate.isEmpty() || pretText.isEmpty() ||
            ziText.isEmpty() || lunaText.isEmpty() || anText.isEmpty() || tip.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "")) {
            String getMaxIdSql = "SELECT MAX(ID) FROM produs";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getMaxIdSql);

            int nextId = 1; 
            if (rs.next()) {
                nextId = rs.getInt(1) + 1;
            }

            String sql = "INSERT INTO produs (ID, nume, valabilitate, disponibilitate, pret, zi, luna, an, tip) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, nextId);
            pstmt.setString(2, nume);
            pstmt.setString(3, valabilitate);
            pstmt.setString(4, disponibilitate);
            pstmt.setInt(5, Integer.parseInt(pretText));
            pstmt.setInt(6, Integer.parseInt(ziText));
            pstmt.setInt(7, Integer.parseInt(lunaText));
            pstmt.setInt(8, Integer.parseInt(anText));
            pstmt.setString(9, tip);

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "New product added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding product to the database.");
        }
    }

    public static void main(String[] args) {
        new EmployeeGUI();
    }
}