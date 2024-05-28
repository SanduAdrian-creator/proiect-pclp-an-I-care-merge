package test2;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GUI extends JFrame implements ActionListener {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    JComboBox<String> productList;
    JSlider quantitySlider;
    JButton addButton, submitButton;
    List<String> selectedProducts;
    List<Integer> productQuantities;
    String nume, prenume;
    boolean isStaff;

    public GUI(String nume, String prenume, boolean isStaff) {
        this.nume = nume;
        this.prenume = prenume;
        this.isStaff = isStaff;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(nume + " " + prenume + " is logged in");
        setResizable(true);

        productList = new JComboBox<>();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        add(panel, BorderLayout.CENTER);

        fetchProductNames();

        productList.addActionListener(this);
        panel.add(productList);

        quantitySlider = new JSlider(JSlider.HORIZONTAL, 0, 10, 0);
        quantitySlider.setMajorTickSpacing(1);
        quantitySlider.setMinorTickSpacing(1);
        quantitySlider.setPaintTicks(true);
        quantitySlider.setPaintLabels(true);
        panel.add(quantitySlider);

        addButton = new JButton("Add Product");
        addButton.addActionListener(this);
        panel.add(addButton);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        panel.add(submitButton);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        selectedProducts = new ArrayList<>();
        productQuantities = new ArrayList<>();
    }

    private void fetchProductNames() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
            stmt = conn.createStatement();
            String sql = "SELECT nume FROM produs";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String productName = rs.getString("nume");
                productList.addItem(productName);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            addProduct();
        } else if (e.getSource() == submitButton) {
            submitShoppingList();
        }
    }

    private void addProduct() {
        String selectedProduct = (String) productList.getSelectedItem();
        int quantity = quantitySlider.getValue();

        if (selectedProduct != null && quantity > 0) {
            selectedProducts.add(selectedProduct);
            productQuantities.add(quantity);
            JOptionPane.showMessageDialog(this, "Product added to shopping cart.");
        } else {
            JOptionPane.showMessageDialog(this, "Please select a product and specify the quantity.");
        }
    }

    private int getHighestCustomerId() throws SQLException {
        int highestCustomerId = 0;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
            String sql = "SELECT MAX(id) AS max_id FROM cos";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                highestCustomerId = rs.getInt("max_id");
            }
        } finally {
        	
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return highestCustomerId;
    }
    
    private void submitShoppingList() {
        if (selectedProducts.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Shopping cart is empty. Please add products.");
            return;
        }

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");

            int highestCustomerId = getHighestCustomerId();
            int previousCustomerId = highestCustomerId + 1;

            String insertSql = "INSERT INTO cos (id, nrProduse, total) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertSql);

            int nrProduse = selectedProducts.size();
            int total = calculateTotal();

            pstmt.setInt(1, previousCustomerId);
            pstmt.setInt(2, nrProduse);
            pstmt.setInt(3, total);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Shopping list submitted successfully.");
            dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error submitting shopping list.");
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private int calculateTotal() {
        int total = 0;
        for (int i = 0; i < selectedProducts.size(); i++) {
            String productName = selectedProducts.get(i);
            int quantity = productQuantities.get(i);
            int price = getProductPrice(productName); 
            total += price * quantity;
        }
        return total;
    }

    private int getProductPrice(String productName) {
        int price = 0;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
            String sql = "SELECT pret FROM produs WHERE nume = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, productName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                price = rs.getInt("pret");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return price;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        });
    }
}