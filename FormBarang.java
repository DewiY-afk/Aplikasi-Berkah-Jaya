import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class FormBarang extends JFrame {

    JLabel title, lkategori, lnama, lsatuan, lharga, lstok, lsearch;
    JComboBox<String> cbKategori;
    // UPDATE: txtSatuan diganti menjadi cbSatuan (JComboBox)
    JComboBox<String> cbSatuan; 
    JTextField txtNama, txtHarga, txtStok, txtSearch;
    JButton btnSimpan, btnHapus, btnSort, btnTambahKategori; 
    JTable table;
    DefaultTableModel model;
    String selectedId = "";

    public FormBarang() {
        setTitle("TOKO BERKAH JAYA - DATA BARANG");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(245, 247, 250));
        add(panel);

        title = new JLabel("TOKO BERKAH JAYA");
        title.setBounds(360, 20, 500, 50);
        title.setFont(new Font("Segoe UI", Font.BOLD, 38));
        panel.add(title);

        JPanel cardForm = new JPanel();
        cardForm.setLayout(null);
        cardForm.setBounds(40, 100, 420, 470);
        cardForm.setBackground(Color.WHITE);
        panel.add(cardForm);

        lkategori = new JLabel("Kategori");
        lkategori.setBounds(30, 40, 120, 25);
        cardForm.add(lkategori);

        cbKategori = new JComboBox<>();
        cbKategori.setBounds(150, 40, 170, 35); 
        cardForm.add(cbKategori);

        btnTambahKategori = new JButton("+");
        btnTambahKategori.setBounds(330, 40, 42, 35);
        btnTambahKategori.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnTambahKategori.setBackground(new Color(52, 152, 219));
        btnTambahKategori.setForeground(Color.WHITE);
        cardForm.add(btnTambahKategori);

        lnama = new JLabel("Nama Barang");
        lnama.setBounds(30, 100, 120, 25);
        cardForm.add(lnama);

        txtNama = new JTextField();
        txtNama.setBounds(150, 100, 220, 35);
        cardForm.add(txtNama);

        lsatuan = new JLabel("Satuan");
        lsatuan.setBounds(30, 160, 120, 25);
        cardForm.add(lsatuan);

        // UPDATE: Konstruktor komponen menu pilihan satuan (ComboBox)
        cbSatuan = new JComboBox<>();
        cbSatuan.setBounds(150, 160, 220, 35);
        cbSatuan.addItem("pcs");
        cbSatuan.addItem("kg");
        cbSatuan.addItem("box");
        cbSatuan.addItem("pack");
        cbSatuan.addItem("botol");
        cardForm.add(cbSatuan);

        lharga = new JLabel("Harga");
        lharga.setBounds(30, 220, 120, 25);
        cardForm.add(lharga);

        txtHarga = new JTextField();
        txtHarga.setBounds(150, 220, 220, 35);
        cardForm.add(txtHarga);

        lstok = new JLabel("Stok");
        lstok.setBounds(30, 280, 120, 25);
        cardForm.add(lstok);

        txtStok = new JTextField();
        txtStok.setBounds(150, 280, 220, 35);
        cardForm.add(txtStok);

        btnSimpan = new JButton("SIMPAN");
        btnSimpan.setBounds(40, 390, 110, 45);
        cardForm.add(btnSimpan);

        btnHapus = new JButton("HAPUS");
        btnHapus.setBounds(165, 390, 110, 45);
        cardForm.add(btnHapus);

        btnSort = new JButton("SORT HARGA");
        btnSort.setBounds(290, 390, 120, 45);
        cardForm.add(btnSort);

        JPanel cardTable = new JPanel();
        cardTable.setLayout(null);
        cardTable.setBounds(500, 100, 640, 470);
        cardTable.setBackground(Color.WHITE);
        panel.add(cardTable);

        lsearch = new JLabel("Cari Barang");
        lsearch.setBounds(20, 20, 100, 25);
        cardTable.add(lsearch);

        txtSearch = new JTextField();
        txtSearch.setBounds(120, 20, 480, 35);
        cardTable.add(txtSearch);

        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Kategori");
        model.addColumn("Nama Barang");
        model.addColumn("Satuan");
        model.addColumn("Harga");
        model.addColumn("Stok");

        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20, 70, 590, 360);
        cardTable.add(scroll);

        tampilKategori();
        tampilData();

        btnTambahKategori.addActionListener(e -> {
            tambahKategoriLangsung();
        });

        btnSimpan.addActionListener(e -> {
            simpanData();
        });

        btnHapus.addActionListener(e -> {
            hapusData();
        });

        btnSort.addActionListener(e -> {
            sortHarga();
        });

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cariBarang();
            }
        });

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ambilData();
            }
        });
    }

    private void tampilKategori() {
        try {
            cbKategori.removeAllItems(); 
            Connection conn = Koneksi.configDB();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM tb_kategori");
            while (rs.next()) {
                cbKategori.addItem(rs.getString("nama_kategori"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void tambahKategoriLangsung() {
        String namaKategoriBaru = JOptionPane.showInputDialog(this, "Masukkan Nama Kategori Baru:", "Tambah Kategori", JOptionPane.QUESTION_MESSAGE);
        
        if (namaKategoriBaru == null) return; 
        if (namaKategoriBaru.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama kategori tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Connection conn = Koneksi.configDB();
            String sql = "INSERT INTO tb_kategori (nama_kategori) VALUES (?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, namaKategoriBaru.trim());
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Kategori '" + namaKategoriBaru + "' Berhasil Ditambahkan!");
            tampilKategori();
            cbKategori.setSelectedItem(namaKategoriBaru.trim());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menambah kategori: " + e.getMessage());
        }
    }

    private int getIdKategori() {
        int id = 0;
        try {
            Connection conn = Koneksi.configDB();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM tb_kategori WHERE nama_kategori='"
                    + cbKategori.getSelectedItem() + "'");
            if (rs.next()) {
                id = rs.getInt("id_kategori");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return id;
    }

    private String generateIdBarang() {
        String newId = "BRG001";
        try {
            Connection conn = Koneksi.configDB();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT id_barang FROM tb_barang ORDER BY id_barang DESC LIMIT 1");
            if (rs.next()) {
                String lastId = rs.getString("id_barang");
                int idNum = Integer.parseInt(lastId.substring(3));
                idNum++;
                newId = String.format("BRG%03d", idNum);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal generate ID Barang: " + e.getMessage());
        }
        return newId;
    }

    private void simpanData() {
        if (cbKategori.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Silahkan tambah/pilih kategori terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // UPDATE: Validasi pengecekan cbSatuan agar tidak null/kosong
        if (txtNama.getText().trim().isEmpty() || cbSatuan.getSelectedItem() == null || 
            txtHarga.getText().trim().isEmpty() || txtStok.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua data wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!txtHarga.getText().matches("[0-9]+")) {
            JOptionPane.showMessageDialog(null, "Input Harga harus berupa angka!", "Validasi Eror", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!txtStok.getText().matches("[0-9]+")) {
            JOptionPane.showMessageDialog(null, "Input Stok harus berupa angka!", "Validasi Eror", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Connection conn = Koneksi.configDB();
            String idBarang = generateIdBarang();

            String sql = "INSERT INTO tb_barang VALUES(?,?,?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, idBarang);
            pst.setInt(2, getIdKategori());
            pst.setString(3, txtNama.getText().trim());
            // UPDATE: Mengambil value string dari cbSatuan
            pst.setString(4, cbSatuan.getSelectedItem().toString()); 
            pst.setString(5, txtHarga.getText().trim());
            pst.setString(6, txtStok.getText().trim());

            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan dengan ID: " + idBarang);

            tampilData();
            clearForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void tampilData() {
        try {
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();

            Connection conn = Koneksi.configDB();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM tb_barang ORDER BY id_barang ASC");

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id_barang"),
                    rs.getString("id_kategori"),
                    rs.getString("nama_barang"),
                    rs.getString("satuan"),
                    rs.getString("harga_jual"),
                    rs.getString("stok")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void ambilData() {
        int baris = table.getSelectedRow();
        if (baris != -1) {
            selectedId = table.getValueAt(baris, 0).toString();
            txtNama.setText(table.getValueAt(baris, 2).toString());
            // UPDATE: Menyesuaikan item terpilih di JComboBox Satuan sesuai dengan isi baris tabel
            String satuanDariTabel = table.getValueAt(baris, 3).toString();
            cbSatuan.setSelectedItem(satuanDariTabel);
            
            txtHarga.setText(table.getValueAt(baris, 4).toString());
            txtStok.setText(table.getValueAt(baris, 5).toString());
        }
    }

    private void hapusData() {
        if (selectedId.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Silahkan pilih data pada tabel terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Connection conn = Koneksi.configDB();
            String sql = "DELETE FROM tb_barang WHERE id_barang=?";
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

    private void sortHarga() {
        try {
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();

            Connection conn = Koneksi.configDB();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM tb_barang ORDER BY harga_jual ASC");

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id_barang"),
                    rs.getString("id_kategori"),
                    rs.getString("nama_barang"),
                    rs.getString("satuan"),
                    rs.getString("harga_jual"),
                    rs.getString("stok")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void cariBarang() {
        try {
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();

            Connection conn = Koneksi.configDB();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM tb_barang WHERE nama_barang LIKE '%" 
                    + txtSearch.getText() + "%' ORDER BY id_barang ASC");

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id_barang"),
                    rs.getString("id_kategori"),
                    rs.getString("nama_barang"),
                    rs.getString("satuan"),
                    rs.getString("harga_jual"),
                    rs.getString("stok")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void clearForm() {
        txtNama.setText("");
        txtHarga.setText("");
        txtStok.setText("");
        if (cbKategori.getItemCount() > 0) {
            cbKategori.setSelectedIndex(0);
        }
        // UPDATE: Reset pilihan satuan kembali ke indeks pertama (pcs) saat form dibersihkan
        if (cbSatuan.getItemCount() > 0) {
            cbSatuan.setSelectedIndex(0);
        }
        selectedId = "";
    }
}