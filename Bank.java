package hello;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
public class Bank {
	static class Account {
        String name;
        String password;
        double balance;

        Account(String name, String password, double balance) {
            this.name = name;
            this.password = password;
            this.balance = balance;
        }
    }

    private HashMap<String, Account> accounts = new HashMap<>();
    private JFrame frame;
    private JTextField nameField;
    private JPasswordField passField;
    private JTextField amtField;
    private JLabel msg;

    public Bank() {
        buildUI();
    }

    private void buildUI() {
        frame = new JFrame("Online Banking System");
        frame.setSize(420, 360);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 12, 12));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel nameLbl = new JLabel("Account Name:");
        nameField = new JTextField(12);

        JLabel passLbl = new JLabel("Password:");
        passField = new JPasswordField(12);

        JLabel amtLbl = new JLabel("Amount (₹):");
        amtField = new JTextField(8);

        JButton createBtn = new JButton("Create Account");
        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton balanceBtn = new JButton("Check Balance");

        msg = new JLabel("Welcome to Online Banking!");
        msg.setForeground(Color.BLUE);

        // Focus validation
        nameField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                if (nameField.getText().trim().length() < 3) {
                    msg.setText("⚠️ Name must be at least 3 characters!");
                    msg.setForeground(Color.RED);
                }
            }
        });

        passField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                if (new String(passField.getPassword()).length() < 4) {
                    msg.setText("⚠️ Password must be at least 4 characters!");
                    msg.setForeground(Color.RED);
                }
            }
        });

        // Create account
        createBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String pass = new String(passField.getPassword()).trim();
            String amtText = amtField.getText().trim();

            if (name.isEmpty() || pass.isEmpty()) {
                msg.setText("⚠️ Enter both name and password!");
                msg.setForeground(Color.RED);
                return;
            }
            double amount = 0;
            if (!amtText.isEmpty()) {
                try {
                    amount = Double.parseDouble(amtText);
                } catch (NumberFormatException ex) {
                    msg.setText("⚠️ Invalid opening amount!");
                    msg.setForeground(Color.RED);
                    return;
                }
            }
            if (accounts.containsKey(name)) {
                msg.setText("❌ Account already exists!");
                msg.setForeground(Color.RED);
            } else {
                accounts.put(name, new Account(name, pass, amount));
                msg.setText("✅ Account created for " + name + " with ₹" + amount);
                msg.setForeground(new Color(0, 128, 0));
            }
        });

        // Deposit
        depositBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String pass = new String(passField.getPassword()).trim();
            String amtText = amtField.getText().trim();

            Account acc = accounts.get(name);
            if (acc == null) {
                msg.setText("❌ No such account!");
                msg.setForeground(Color.RED);
                return;
            }
            if (!acc.password.equals(pass)) {
                msg.setText("❌ Wrong password!");
                msg.setForeground(Color.RED);
                return;
            }
            try {
                double amt = Double.parseDouble(amtText);
                if (amt <= 0) {
                    msg.setText("⚠️ Enter a positive amount!");
                    msg.setForeground(Color.RED);
                    return;
                }
                acc.balance += amt;
                msg.setText("✅ Deposited ₹" + amt + ". New Balance: ₹" + acc.balance);
                msg.setForeground(new Color(0, 128, 0));
            } catch (Exception ex) {
                msg.setText("⚠️ Invalid amount!");
                msg.setForeground(Color.RED);
            }
        });

        // Withdraw
        withdrawBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String pass = new String(passField.getPassword()).trim();
            String amtText = amtField.getText().trim();

            Account acc = accounts.get(name);
            if (acc == null) {
                msg.setText("❌ No such account!");
                msg.setForeground(Color.RED);
                return;
            }
            if (!acc.password.equals(pass)) {
                msg.setText("❌ Wrong password!");
                msg.setForeground(Color.RED);
                return;
            }
            try {
                double amt = Double.parseDouble(amtText);
                if (amt <= 0) {
                    msg.setText("⚠️ Enter a positive amount!");
                    msg.setForeground(Color.RED);
                    return;
                }
                if (amt > acc.balance) {
                    msg.setText("⚠️ Insufficient balance!");
                    msg.setForeground(Color.RED);
                    return;
                }
                acc.balance -= amt;
                msg.setText("✅ Withdrawn ₹" + amt + ". New Balance: ₹" + acc.balance);
                msg.setForeground(new Color(0, 128, 0));
            } catch (Exception ex) {
                msg.setText("⚠️ Invalid amount!");
                msg.setForeground(Color.RED);
            }
        });

        // Check balance
        balanceBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String pass = new String(passField.getPassword()).trim();
            Account acc = accounts.get(name);
            if (acc == null) {
                msg.setText("❌ Account not found!");
                msg.setForeground(Color.RED);
                return;
            }
            if (!acc.password.equals(pass)) {
                msg.setText("❌ Wrong password!");
                msg.setForeground(Color.RED);
                return;
            }
            msg.setText("💰 Balance for " + name + ": ₹" + acc.balance);
            msg.setForeground(Color.BLUE);
        });

        // Add components
        frame.add(nameLbl);
        frame.add(nameField);
        frame.add(passLbl);
        frame.add(passField);
        frame.add(amtLbl);
        frame.add(amtField);
        frame.add(createBtn);
        frame.add(depositBtn);
        frame.add(withdrawBtn);
        frame.add(balanceBtn);
        frame.add(msg);

        frame.setVisible(true);
    }
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Bank());

	}

}
