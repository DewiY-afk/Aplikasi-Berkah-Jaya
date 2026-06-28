import javax.swing.*;
import java.awt.*;

public class FormMenu extends JFrame {

    JLabel title, subtitle, footer;
    JButton btnBarang;
    JButton btnCustomer;
    JButton btnKategori;
    JButton btnTransaksi;
    JButton btnLaporan;
    JButton btnLogout;

    public FormMenu() {
        setTitle("TOKO BERKAH JAYA");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        
        // Menggunakan DISPOSE_ON_CLOSE atau EXIT_ON_CLOSE sesuai arsitektur login kamu
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(245, 247, 250));
        add(panel);

        title = new JLabel("TOKO BERKAH JAYA");
        title.setBounds(350, 30, 600, 50);
        title.setFont(new Font("Segoe UI", Font.BOLD, 40));
        title.setForeground(new Color(44, 62, 80));
        panel.add(title);

        subtitle = new JLabel("Sistem Informasi Penjualan Modern");
        subtitle.setBounds(410, 80, 400, 30);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subtitle.setForeground(Color.GRAY);
        panel.add(subtitle);

        JPanel cardMenu = new JPanel();
        cardMenu.setLayout(null);
        cardMenu.setBounds(130, 150, 900, 420);
        cardMenu.setBackground(Color.WHITE);
        cardMenu.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        panel.add(cardMenu);

        btnBarang = new JButton("DATA BARANG");
        btnBarang.setBounds(70, 60, 300, 90);
        styleButton(btnBarang, new Color(52, 152, 219));
        cardMenu.add(btnBarang);

        btnCustomer = new JButton("DATA CUSTOMER");
        btnCustomer.setBounds(520, 60, 300, 90);
        styleButton(btnCustomer, new Color(46, 204, 113));
        cardMenu.add(btnCustomer);

        btnKategori = new JButton("DATA KATEGORI");
        btnKategori.setBounds(70, 220, 300, 90);
        styleButton(btnKategori, new Color(155, 89, 182));
        cardMenu.add(btnKategori);

        btnTransaksi = new JButton("TRANSAKSI");
        btnTransaksi.setBounds(520, 220, 300, 90);
        styleButton(btnTransaksi, new Color(241, 196, 15));
        cardMenu.add(btnTransaksi);

        btnLaporan = new JButton("LAPORAN");
        btnLaporan.setBounds(250, 340, 180, 45);
        styleButton(btnLaporan, new Color(231, 76, 60));
        cardMenu.add(btnLaporan);

        btnLogout = new JButton("LOGOUT");
        btnLogout.setBounds(470, 340, 180, 45);
        styleButton(btnLogout, Color.GRAY);
        cardMenu.add(btnLogout);

        footer = new JLabel("© TOKO BERKAH JAYA 2026");
        footer.setBounds(500, 620, 250, 20);
        footer.setForeground(Color.GRAY);
        panel.add(footer);

        // --- Action Listeners Sub-Form ---
        btnBarang.addActionListener(e -> {
            new FormBarang().setVisible(true);
        });

        btnCustomer.addActionListener(e -> {
            new FormCustomer().setVisible(true);
        });

        btnKategori.addActionListener(e -> {
            new FormKategori().setVisible(true);
        });

        btnTransaksi.addActionListener(e -> {
            new FormTransaksi().setVisible(true);
        });

        btnLaporan.addActionListener(e -> {
            new FormLaporan().setVisible(true);
        });

        btnLogout.addActionListener(e -> {
            dispose(); // Menutup FormMenu
            new FormLogin().setVisible(true); // Membuka kembali FormLogin
        });
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
    }
}