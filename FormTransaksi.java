import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class FormTransaksi extends JFrame {

    JLabel title, subtitle, ltanggal, lcustomer, lbarang, lharga, ljumlah, ltotal, footer;
    JTextField txtTanggal, txtHarga, txtJumlah, txtTotal;
    JComboBox<String> cbCustomer, cbBarang;
    JButton btnHitung, btnSimpan, btnHapus;
    JTable table;
    DefaultTableModel model;
    int selectedId = 0;
    int hargaBarang = 0;
    int stokBarang = 0;

    HashMap<String, String> customerMap = new HashMap<>();
    HashMap<String, String> barangMap = new HashMap<>();

    public FormTransaksi() {
        setTitle("TOKO BERKAH JAYA - TRANSAKSI");
        setSize(1300, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(245, 247, 250));
        add(panel);

        title = new JLabel("TRANSAKSI PENJUALAN");
        title.setBounds(400, 20, 500, 45);
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(new Color(44, 62, 80));
        panel.add(title);

        subtitle = new JLabel("Kelola Penjualan Toko Berkah Jaya");
        subtitle.setBounds(430, 65, 420, 30);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subtitle.setForeground(Color.GRAY);
        panel.add(subtitle);

        JPanel cardForm = new JPanel();
        cardForm.setLayout(null);
        cardForm.setBounds(40, 120, 450, 520);
        cardForm.setBackground(Color.WHITE);
        panel.add(cardForm);

        ltanggal = new JLabel("Tanggal");
        ltanggal.setBounds(40, 50, 120, 25);
        cardForm.add(ltanggal);

        txtTanggal = new JTextField();
        txtTanggal.setBounds(180, 50, 220, 40);
        txtTanggal.setEditable(false);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        txtTanggal.setText(sdf.format(new Date()));
        cardForm.add(txtTanggal);

        lcustomer = new JLabel("Customer");
        lcustomer.setBounds(40, 110, 120, 25);
        cardForm.add(lcustomer);

        cbCustomer = new JComboBox<>();
        cbCustomer.setBounds(180, 110, 220, 40);
        cardForm.add(cbCustomer);

        lbarang = new JLabel("Barang");
        lbarang.setBounds(40, 170, 120, 25);
        cardForm.add(lbarang);

        cbBarang = new JComboBox<>();
        cbBarang.setBounds(180, 170, 220, 40);
        cardForm.add(cbBarang);

        lharga = new JLabel("Harga");
        lharga.setBounds(40, 230, 120, 25);
        cardForm.add(lharga);

        txtHarga = new JTextField();
        txtHarga.setBounds(180, 230, 220, 40);
        txtHarga.setEditable(false);
        cardForm.add(txtHarga);

        ljumlah = new JLabel("Jumlah");
        ljumlah.setBounds(40, 290, 120, 25);
        cardForm.add(ljumlah);

        txtJumlah = new JTextField();
        txtJumlah.setBounds(180, 290, 220, 40);
        cardForm.add(txtJumlah);

        ltotal = new JLabel("Total");
        ltotal.setBounds(40, 350, 120, 25);
        cardForm.add(ltotal);

        txtTotal = new JTextField();
        txtTotal.setBounds(180, 350, 220, 40);
        txtTotal.setEditable(false);
        cardForm.add(txtTotal);

        btnHitung = new JButton("HITUNG");
        btnHitung.setBounds(40, 430, 110, 40);
        cardForm.add(btnHitung);

        btnSimpan = new JButton("SIMPAN");
        btnSimpan.setBounds(170, 430, 110, 40);
        cardForm.add(btnSimpan);

        btnHapus = new JButton("HAPUS");
        btnHapus.setBounds(300, 430, 110, 40);
        cardForm.add(btnHapus);

        JPanel cardTable = new JPanel();
        cardTable.setLayout(null);
        cardTable.setBounds(540, 120, 700, 520);
        cardTable.setBackground(Color.WHITE);
        panel.add(cardTable);

        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Tanggal");
        model.addColumn("Customer");
        model.addColumn("Barang");
        model.addColumn("Jumlah");
        model.addColumn("Total");

        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20, 20, 660, 470);
        cardTable.add(scroll);

        footer = new JLabel("© TOKO BERKAH JAYA 2026");
        footer.setBounds(540, 690, 250, 20);
        panel.add(footer);

        tampilCustomer();
        tampilBarang();
        tampilData();

        cbBarang.addActionListener(e -> {
            tampilHarga();
        });

        btnHitung.addActionListener(e -> {
            hitungTotal();
        });

        btnSimpan.addActionListener(e -> {
            simpanData();
        });

        btnHapus.addActionListener(e -> {
            hapusData();
        });

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ambilData();
            }
        });
    }

    private void tampilCustomer() {
        try {
            Connection conn = Koneksi.configDB();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM tb_customer ORDER BY nama_customer ASC");
            while (rs.next()) {
                String nama = rs.getString("nama_customer");
                String id = rs.getString("id_customer");
                cbCustomer.addItem(nama);
                customerMap.put(nama, id);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    // UPDATE: Menambahkan ORDER BY id_barang ASC agar urutan barang rapi sesuai ID-nya
    private void tampilBarang() {
        try {
            cbBarang.removeAllItems();
            barangMap.clear();
            
            Connection conn = Koneksi.configDB();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM tb_barang ORDER BY id_barang ASC");
            while (rs.next()) {
                String nama = rs.getString("nama_barang");
                String id = rs.getString("id_barang");
                barangMap.put(nama, id);
                cbBarang.addItem(nama);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void tampilHarga() {
        if (cbBarang.getSelectedItem() == null) return;
        try {
            Connection conn = Koneksi.configDB();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM tb_barang WHERE nama_barang='"
                    + cbBarang.getSelectedItem() + "'");
            if (rs.next()) {
                hargaBarang = rs.getInt("harga_jual");
                stokBarang = rs.getInt("stok");
                txtHarga.setText(String.valueOf(hargaBarang));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void hitungTotal() {
        try {
            if (txtJumlah.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Masukkan jumlah beli terlebih dahulu!");
                return;
            }
            int jumlah = Integer.parseInt(txtJumlah.getText().trim());
            if (jumlah > stokBarang) {
                JOptionPane.showMessageDialog(null, "Stok tidak mencukupi! Sisa stok: " + stokBarang);
                return;
            }
            int total = jumlah * hargaBarang;
            txtTotal.setText(String.valueOf(total));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Jumlah tidak valid (Harus Angka)");
        }
    }

    private void simpanData() {
        if (txtTotal.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Silakan hitung total belanjaan terlebih dahulu!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int jumlah = Integer.parseInt(txtJumlah.getText().trim());
            Connection conn = Koneksi.configDB();

            String customer = cbCustomer.getSelectedItem().toString();
            String barang = cbBarang.getSelectedItem().toString();

            String sql = "INSERT INTO tb_penjualan(tgl_transaksi,id_customer,id_barang,jumlah_beli,total_bayar,id_user) VALUES(?,?,?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, txtTanggal.getText());
            pst.setString(2, customerMap.get(customer));
            pst.setString(3, barangMap.get(barang));
            pst.setInt(4, jumlah);
            pst.setInt(5, Integer.parseInt(txtTotal.getText()));
            pst.setInt(6, 1);
            pst.executeUpdate();

            String sqlUpdate = "UPDATE tb_barang SET stok = stok - ? WHERE id_barang=?";
            PreparedStatement pstUpdate = conn.prepareStatement(sqlUpdate);
            pstUpdate.setInt(1, jumlah);
            pstUpdate.setString(2, barangMap.get(barang));
            pstUpdate.executeUpdate();

            JOptionPane.showMessageDialog(null, "Transaksi Berhasil");
            tampilData();
            tampilHarga(); // Refresh info stok terbaru
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
            String sql = "SELECT tb_penjualan.id_jual, tb_penjualan.tgl_transaksi, tb_customer.nama_customer, "
                    + "tb_barang.nama_barang, tb_penjualan.jumlah_beli, tb_penjualan.total_bayar "
                    + "FROM tb_penjualan "
                    + "JOIN tb_customer ON tb_penjualan.id_customer = tb_customer.id_customer "
                    + "JOIN tb_barang ON tb_penjualan.id_barang = tb_barang.id_barang "
                    + "ORDER BY tb_penjualan.id_jual DESC"; // Urutan transaksi terbaru di atas

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id_jual"),
                    rs.getString("tgl_transaksi"),
                    rs.getString("nama_customer"),
                    rs.getString("nama_barang"),
                    rs.getString("jumlah_beli"),
                    rs.getString("total_bayar")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void ambilData() {
        int baris = table.getSelectedRow();
        if (baris != -1) {
            selectedId = Integer.parseInt(table.getValueAt(baris, 0).toString());
        }
    }

    // UPDATE: Memperbaiki logika hapus agar otomatis mengembalikan/merestore stok barang ke semula
    private void hapusData() {
        if (selectedId == 0) {
            JOptionPane.showMessageDialog(null, "Silakan pilih data transaksi di tabel terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int konfirmasi = JOptionPane.showConfirmDialog(null, "Apakah anda yakin ingin menghapus transaksi ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        if (konfirmasi != JOptionPane.YES_OPTION) return;

        try {
            Connection conn = Koneksi.configDB();
            
            // 1. Ambil info id_barang dan jumlah_beli dari transaksi yang akan dihapus
            String sqlGet = "SELECT id_barang, jumlah_beli FROM tb_penjualan WHERE id_jual=?";
            PreparedStatement pstGet = conn.prepareStatement(sqlGet);
            pstGet.setInt(1, selectedId);
            ResultSet rsGet = pstGet.executeQuery();
            
            if (rsGet.next()) {
                String idBarangTrans = rsGet.getString("id_barang");
                int jmlBeli = rsGet.getInt("jumlah_beli");
                
                // 2. Kembalikan stok barang semula (stok ditambah kembali)
                String sqlRestoreStok = "UPDATE tb_barang SET stok = stok + ? WHERE id_barang=?";
                PreparedStatement pstRestore = conn.prepareStatement(sqlRestoreStok);
                pstRestore.setInt(1, jmlBeli);
                pstRestore.setString(2, idBarangTrans);
                pstRestore.executeUpdate();
            }

            // 3. Hapus data transaksi dari tb_penjualan
            String sqlDelete = "DELETE FROM tb_penjualan WHERE id_jual=?";
            PreparedStatement pstDelete = conn.prepareStatement(sqlDelete);
            pstDelete.setInt(1, selectedId);
            pstDelete.executeUpdate();

            JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus & Stok Dikembalikan");
            selectedId = 0;
            tampilData();
            tampilHarga(); // Refresh info kapasitas gudang/stok terkini
            clearForm();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void clearForm() {
        txtJumlah.setText("");
        txtTotal.setText("");
    }
}