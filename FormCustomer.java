import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class FormCustomer extends JFrame {

    JLabel title, subtitle, lnama, lalamat, ltelepon, lsearch, footer;
    JTextField txtNama, txtAlamat, txtTelepon, txtSearch;
    JButton btnSimpan, btnHapus;
    JTable table;
    DefaultTableModel model;
    String selectedId = "";

    public FormCustomer() {
        setTitle("TOKO BERKAH JAYA - CUSTOMER");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(245, 247, 250));
        add(panel);

        title = new JLabel("DATA CUSTOMER");
        title.setBounds(430, 20, 400, 45);
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(new Color(44, 62, 80));
        panel.add(title);

        subtitle = new JLabel("Kelola Data Customer Toko Berkah Jaya");
        subtitle.setBounds(390, 65, 450, 30);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subtitle.setForeground(Color.GRAY);
        panel.add(subtitle);

        JPanel cardForm = new JPanel();
        cardForm.setLayout(null);
        cardForm.setBounds(40, 120, 420, 470);
        cardForm.setBackground(Color.WHITE);
        cardForm.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        panel.add(cardForm);

        lnama = new JLabel("Nama Customer");
        lnama.setBounds(30, 50, 120, 25);
        cardForm.add(lnama);

        txtNama = new JTextField();
        txtNama.setBounds(160, 50, 220, 38);
        cardForm.add(txtNama);

        lalamat = new JLabel("Alamat");
        lalamat.setBounds(30, 130, 120, 25);
        cardForm.add(lalamat);

        txtAlamat = new JTextField();
        txtAlamat.setBounds(160, 130, 220, 38);
        cardForm.add(txtAlamat);

        ltelepon = new JLabel("Telepon");
        ltelepon.setBounds(30, 210, 120, 25);
        cardForm.add(ltelepon);

        txtTelepon = new JTextField();
        txtTelepon.setBounds(160, 210, 220, 38);
        cardForm.add(txtTelepon);

        btnSimpan = new JButton("SIMPAN");
        btnSimpan.setBounds(70, 340, 120, 45);
        btnSimpan.setBackground(new Color(46, 204, 113));
        btnSimpan.setForeground(Color.WHITE);
        cardForm.add(btnSimpan);

        btnHapus = new JButton("HAPUS");
        btnHapus.setBounds(220, 340, 120, 45);
        btnHapus.setBackground(new Color(231, 76, 60));
        btnHapus.setForeground(Color.WHITE);
        cardForm.add(btnHapus);

        JPanel cardTable = new JPanel();
        cardTable.setLayout(null);
        cardTable.setBounds(500, 120, 640, 470);
        cardTable.setBackground(Color.WHITE);
        panel.add(cardTable);

        lsearch = new JLabel("Cari Customer");
        lsearch.setBounds(20, 20, 120, 25);
        cardTable.add(lsearch);

        txtSearch = new JTextField();
        txtSearch.setBounds(140, 20, 460, 38);
        cardTable.add(txtSearch);

        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nama Customer");
        model.addColumn("Alamat");
        model.addColumn("Telepon");

        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20, 80, 590, 340);
        cardTable.add(scroll);

        footer = new JLabel("© TOKO BERKAH JAYA 2026");
        footer.setBounds(510, 620, 250, 20);
        panel.add(footer);

        tampilData();

        btnSimpan.addActionListener(e -> {
            simpanData();
        });

        btnHapus.addActionListener(e -> {
            hapusData();
        });

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cariCustomer();
            }
        });

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ambilData();
            }
        });
    }

    private void tampilData() {
        try {
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();

            Connection conn = Koneksi.configDB();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM tb_customer ORDER BY id_customer ASC");

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id_customer"),
                    rs.getString("nama_customer"),
                    rs.getString("alamat"),
                    rs.getString("telepon")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    // Fungsi baru untuk menghasilkan ID berurutan secara otomatis (C001, C002, dll)
    private String generateIdCustomer() {
        String newId = "C001";
        try {
            Connection conn = Koneksi.configDB();
            Statement st = conn.createStatement();
            // Mengambil ID terbesar/terakhir
            ResultSet rs = st.executeQuery("SELECT id_customer FROM tb_customer ORDER BY id_customer DESC LIMIT 1");
            
            if (rs.next()) {
                String lastId = rs.getString("id_customer"); // Contoh: "C005"
                // Mengambil angka setelah huruf 'C'
                int idNum = Integer.parseInt(lastId.substring(1)); 
                idNum++; // Ditambah 1 menjadi 6
                // Memformat kembali menjadi string dengan 3 digit angka padding (C006)
                newId = String.format("C%03d", idNum);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal membuat ID: " + e.getMessage());
        }
        return newId;
    }

    private void simpanData() {
        // Validasi: Memastikan field tidak kosong
        if (txtNama.getText().trim().isEmpty() || txtAlamat.getText().trim().isEmpty() || txtTelepon.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua field harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // VALIDASI ANGKA: Memastikan nomor telepon hanya berisi angka menggunakan Regex
        if (!txtTelepon.getText().matches("[0-9]+")) {
            JOptionPane.showMessageDialog(null, "Nomor telepon harus berupa angka!", "Validasi Eror", JOptionPane.ERROR_MESSAGE);
            return; // Menghentikan eksekusi, data tidak akan tersimpan
        }

        try {
            Connection conn = Koneksi.configDB();
            
            // Menggunakan method generator ID otomatis yang baru
            String idCustomer = generateIdCustomer();

            String sql = "INSERT INTO tb_customer VALUES(?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, idCustomer);
            pst.setString(2, txtNama.getText());
            pst.setString(3, txtAlamat.getText());
            pst.setString(4, txtTelepon.getText());

            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Customer Berhasil Disimpan dengan ID: " + idCustomer);

            tampilData();
            clearForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void ambilData() {
        int baris = table.getSelectedRow();
        if (baris != -1) {
            selectedId = table.getValueAt(baris, 0).toString();
            txtNama.setText(table.getValueAt(baris, 1).toString());
            txtAlamat.setText(table.getValueAt(baris, 2).toString());
            txtTelepon.setText(table.getValueAt(baris, 3).toString());
        }
    }

    private void hapusData() {
        if (selectedId.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Silahkan pilih data pada tabel terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Connection conn = Koneksi.configDB();
            String sql = "DELETE FROM tb_customer WHERE id_customer=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, selectedId);

            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");

            tampilData();
            clearForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void cariCustomer() {
        try {
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();

            Connection conn = Koneksi.configDB();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM tb_customer WHERE nama_customer LIKE '%" + txtSearch.getText() + "%' ORDER BY id_customer ASC");

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id_customer"),
                    rs.getString("nama_customer"),
                    rs.getString("alamat"),
                    rs.getString("telepon")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void clearForm() {
        txtNama.setText("");
        txtAlamat.setText("");
        txtTelepon.setText("");
        selectedId = ""; // Reset ID yang terpilih
    }
}