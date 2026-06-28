import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class FormLogin extends JFrame {

    JLabel title, subtitle, user, pass, footer, logo, slogan;
    JTextField txtUser;
    JPasswordField txtPass;
    JButton btnLogin;

    public FormLogin() {
        setTitle("TOKO BERKAH JAYA");
        setSize(950, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(245, 247, 250));
        add(panel);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBounds(0, 0, 420, 550);
        leftPanel.setBackground(new Color(52, 152, 219));
        panel.add(leftPanel);

        logo = new JLabel("BJ");
        logo.setBounds(145, 70, 130, 130);
        logo.setOpaque(true);
        logo.setBackground(Color.WHITE);
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        logo.setVerticalAlignment(SwingConstants.CENTER);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 48));
        logo.setForeground(new Color(52, 152, 219));
        logo.setBorder(BorderFactory.createLineBorder(Color.WHITE, 8, true));
        leftPanel.add(logo);

        title = new JLabel("TOKO BERKAH JAYA");
        title.setBounds(40, 220, 350, 40);
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        leftPanel.add(title);

        subtitle = new JLabel("Sistem Informasi Penjualan");
        subtitle.setBounds(75, 265, 280, 30);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subtitle.setForeground(Color.WHITE);
        leftPanel.add(subtitle);

        slogan = new JLabel("Smart Cashier & Inventory System");
        slogan.setBounds(55, 300, 320, 30);
        slogan.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        slogan.setForeground(new Color(230, 230, 230));
        leftPanel.add(slogan);

        JLabel welcome = new JLabel("LOGIN ACCOUNT");
        welcome.setBounds(560, 80, 260, 40);
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 30));
        welcome.setForeground(new Color(44, 62, 80));
        panel.add(welcome);

        user = new JLabel("Username");
        user.setBounds(500, 180, 120, 25);
        user.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        panel.add(user);

        txtUser = new JTextField();
        txtUser.setBounds(500, 215, 320, 45);
        txtUser.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtUser.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        panel.add(txtUser);

        pass = new JLabel("Password");
        pass.setBounds(500, 290, 120, 25);
        pass.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        panel.add(pass);

        txtPass = new JPasswordField();
        txtPass.setBounds(500, 325, 320, 45);
        txtPass.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtPass.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        panel.add(txtPass);

        btnLogin = new JButton("LOGIN");
        btnLogin.setBounds(500, 410, 320, 50);
        btnLogin.setBackground(new Color(52, 152, 219));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnLogin.setFocusPainted(false);
        btnLogin.setBorder(BorderFactory.createEmptyBorder());
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(btnLogin);

        footer = new JLabel("© TOKO BERKAH JAYA 2026");
        footer.setBounds(600, 490, 220, 20);
        footer.setForeground(Color.GRAY);
        panel.add(footer);

        // --- Action Events ---
        
        // Klik tombol login menggunakan Mouse
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        // Fitur Tambahan: Tekan Enter di kolom password untuk langsung login
        txtPass.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    login();
                }
            }
        });
    }

    private void login() {
        // Validasi input kosong terlebih dahulu
        if (txtUser.getText().trim().isEmpty() || txtPass.getPassword().length == 0) {
            JOptionPane.showMessageDialog(null, "Username dan Password tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Connection conn = Koneksi.configDB();
            String sql = "SELECT * FROM tb_user WHERE username=? AND password=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            
            pst.setString(1, txtUser.getText().trim());
            // Mengubah char[] dari getPassword() menjadi String yang aman
            pst.setString(2, new String(txtPass.getPassword()));

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Login Berhasil! Selamat Datang.");
                
                // Membuka dashboard utama (FormMenu)
                new FormMenu().setVisible(true);
                // Menutup FormLogin agar tidak berjalan di background
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Username atau Password Salah!", "Login Gagal", JOptionPane.ERROR_MESSAGE);
                txtPass.setText("");
                txtPass.requestFocus();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Koneksi Database Gagal: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Mengatur agar form mengikuti standard thread Swing yang aman
        SwingUtilities.invokeLater(() -> {
            new FormLogin().setVisible(true);
        });
    }
}