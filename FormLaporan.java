import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;

public class FormLaporan extends JFrame {

    JLabel title, subtitle, lsearch, footer, totalLabel;
    JTextField txtSearch;
    JTable table;
    DefaultTableModel model;
    JButton btnRefresh;

    // --- KOMPONEN PAPAN KASIR ---
    JPanel cardKasir;
    JLabel lUangBayar, lUangKembalian, txtTampilKembalian, txtTampilTotal;
    JTextField txtUangBayar;
    JButton btnBayarCetak;
    
    // Variabel menampung total seluruh transaksi untuk kasir
    int currentTotalBelanja = 0; 

    public FormLaporan() {
        setTitle("TOKO BERKAH JAYA - LAPORAN & KASIR");
        setSize(1300, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(245, 247, 250));
        add(panel);

        title = new JLabel("LAPORAN PENJUALAN & KASIR");
        title.setBounds(400, 20, 600, 45);
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(new Color(44, 62, 80));
        panel.add(title);

        subtitle = new JLabel("Laporan Data Penjualan Toko Berkah Jaya");
        subtitle.setBounds(460, 65, 500, 30);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subtitle.setForeground(Color.GRAY);
        panel.add(subtitle);

        // --- PANEL KIRI: TABEL DATA ---
        JPanel cardTable = new JPanel();
        cardTable.setLayout(null);
        cardTable.setBounds(40, 120, 780, 520); 
        cardTable.setBackground(Color.WHITE);
        cardTable.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        panel.add(cardTable);

        JLabel icon = new JLabel("📊");
        icon.setBounds(20, 15, 60, 50);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 35));
        cardTable.add(icon);

        lsearch = new JLabel("Cari Data");
        lsearch.setBounds(90, 25, 100, 25);
        cardTable.add(lsearch);

        txtSearch = new JTextField();
        txtSearch.setBounds(170, 20, 300, 38);
        cardTable.add(txtSearch);

        btnRefresh = new JButton("REFRESH");
        btnRefresh.setBounds(490, 20, 110, 38);
        btnRefresh.setBackground(new Color(52, 152, 219));
        btnRefresh.setForeground(Color.WHITE);
        cardTable.add(btnRefresh);

        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Tanggal");
        model.addColumn("Customer");
        model.addColumn("Barang");
        model.addColumn("Jumlah");
        model.addColumn("Total Bayar");

        table = new JTable(model);
        table.setRowHeight(32);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20, 80, 740, 360);
        cardTable.add(scroll);

        totalLabel = new JLabel("TOTAL TRANSAKSI : Rp 0");
        totalLabel.setBounds(20, 460, 400, 30);
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        totalLabel.setForeground(new Color(44, 62, 80));
        cardTable.add(totalLabel);


        // --- PANEL KANAN: PAPAN KASIR TOTAL SEKALIGUS ---
        cardKasir = new JPanel();
        cardKasir.setLayout(null);
        cardKasir.setBounds(840, 120, 400, 520);
        cardKasir.setBackground(Color.WHITE);
        cardKasir.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        panel.add(cardKasir);

        JLabel kasirTitle = new JLabel("PAPAN KASIR");
        kasirTitle.setBounds(25, 20, 200, 30);
        kasirTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        kasirTitle.setForeground(new Color(44, 62, 80));
        cardKasir.add(kasirTitle);

        JLabel lTotal = new JLabel("Total Semua Transaksi :");
        lTotal.setBounds(25, 75, 200, 20);
        cardKasir.add(lTotal);

        txtTampilTotal = new JLabel("Rp 0");
        txtTampilTotal.setBounds(25, 100, 350, 35);
        txtTampilTotal.setFont(new Font("Segoe UI", Font.BOLD, 28));
        txtTampilTotal.setForeground(new Color(46, 204, 113));
        cardKasir.add(txtTampilTotal);

        lUangBayar = new JLabel("Uang Tunai / Bayar (Rp)");
        lUangBayar.setBounds(25, 160, 200, 20);
        cardKasir.add(lUangBayar);

        txtUangBayar = new JTextField();
        txtUangBayar.setBounds(25, 185, 350, 45);
        txtUangBayar.setFont(new Font("Segoe UI", Font.BOLD, 20));
        cardKasir.add(txtUangBayar);

        lUangKembalian = new JLabel("Uang Kembalian :");
        lUangKembalian.setBounds(25, 255, 200, 20);
        cardKasir.add(lUangKembalian);

        txtTampilKembalian = new JLabel("Rp 0");
        txtTampilKembalian.setBounds(25, 280, 350, 35);
        txtTampilKembalian.setFont(new Font("Segoe UI", Font.BOLD, 26));
        txtTampilKembalian.setForeground(new Color(231, 76, 60)); 
        cardKasir.add(txtTampilKembalian);

        btnBayarCetak = new JButton("BAYAR & CETAK STRUK GABUNGAN");
        btnBayarCetak.setBounds(25, 440, 350, 55);
        btnBayarCetak.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBayarCetak.setBackground(new Color(44, 62, 80));
        btnBayarCetak.setForeground(Color.WHITE);
        cardKasir.add(btnBayarCetak);


        // --- FOOTER ---
        footer = new JLabel("© TOKO BERKAH JAYA 2026");
        footer.setBounds(540, 690, 250, 20);
        footer.setForeground(Color.GRAY);
        panel.add(footer);

        // Ambil data pertama kali saat form dibuka
        tampilData();

        // --- ACTION LISTENERS ---
        btnRefresh.addActionListener(e -> {
            tampilData();
            txtUangBayar.setText("");
            txtTampilKembalian.setText("Rp 0");
        });

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cariData();
            }
        });

        // Real-time hitung kembalian otomatis saat kasir mengetik uang tunai
        txtUangBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                hitungKembalianOtomatis();
            }
        });

        // Cetak struk gabungan estetik
        btnBayarCetak.addActionListener(e -> {
            prosesBayarDanCetakStrukGabungan();
        });
    }

    private String formatRupiah(int nilai) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("id", "ID"));
        return nf.format(nilai);
    }

    private String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }

    private String padLeft(String s, int n) {
        return String.format("%" + n + "s", s);
    }

    private void hitungKembalianOtomatis() {
        String bayarStr = txtUangBayar.getText().trim();
        if (bayarStr.isEmpty() || !bayarStr.matches("[0-9]+")) {
            txtTampilKembalian.setText("Rp 0");
            return;
        }
        int uangBayar = Integer.parseInt(bayarStr);
        int kembalian = uangBayar - currentTotalBelanja;
        
        if (kembalian < 0) {
            txtTampilKembalian.setText("Uang Kurang!");
        } else {
            txtTampilKembalian.setText("Rp " + formatRupiah(kembalian));
        }
    }

    private void prosesBayarDanCetakStrukGabungan() {
        if (table.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Tidak ada data transaksi untuk dibayar!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String bayarStr = txtUangBayar.getText().trim();
        if (bayarStr.isEmpty() || !bayarStr.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(this, "Masukkan nominal uang tunai pembayaran!", "Gagal", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int uangBayar = Integer.parseInt(bayarStr);
        if (uangBayar < currentTotalBelanja) {
            JOptionPane.showMessageDialog(this, "Transaksi gagal karena uang pembayaran kurang!", "Gagal Cetak", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int uangKembalian = uangBayar - currentTotalBelanja;
        String tglSekarang = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());

        // --- GENERATE DAFTAR BARANG DARI TABEL SECARA MASAL ---
        StringBuilder daftarBarangTeks = new StringBuilder();
        for (int i = 0; i < table.getRowCount(); i++) {
            String namaBarang = table.getValueAt(i, 3).toString();
            String qty = table.getValueAt(i, 4).toString();
            String subTotal = table.getValueAt(i, 5).toString().replace("Rp", "").trim();

            daftarBarangTeks.append(" ").append(namaBarang).append("\n")
                            .append("   ").append(qty).append("x").append(padLeft("", 15))
                            .append("Rp ").append(padLeft(subTotal, 12)).append("\n");
        }

        // --- STRUKTURAL NOTA PRINT GABUNGAN (MONOSPACED STYLE) ---
        String plainStrukText = 
              "========================================\n"
            + "            TOKO BERKAH JAYA            \n"
            + "       Jl. Raya Puspitek No. 100        \n"
            + "========================================\n"
            + " Tanggal : " + tglSekarang + "\n"
            + " Kasir   : Kasir Utama\n"
            + " Tipe    : Rekap Transaksi Gabungan\n"
            + "----------------------------------------\n"
            + daftarBarangTeks.toString()
            + "----------------------------------------\n"
            + " TOTAL TRANSAKSI :          Rp " + padLeft(formatRupiah(currentTotalBelanja), 12) + "\n"
            + " TUNAI/BAYAR     :          Rp " + padLeft(formatRupiah(uangBayar), 12) + "\n"
            + " KEMBALIAN       :          Rp " + padLeft(formatRupiah(uangKembalian), 12) + "\n"
            + "========================================\n"
            + "  Terima Kasih Atas Kunjungan Anda,     \n"
            + "     Barang Bersih, Harga Berkah!       \n"
            + "========================================\n";

        // Memakai JTextArea agar font thermal kasir lurus simetris ke bawah
        JTextArea textAreaStruk = new JTextArea(plainStrukText);
        textAreaStruk.setFont(new Font("Monospaced", Font.PLAIN, 13));
        textAreaStruk.setEditable(false);
        textAreaStruk.setBackground(new Color(255, 255, 245)); // Background krem kertas struk asli
        textAreaStruk.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        // Tampilkan Nota Pop-up
        JOptionPane.showMessageDialog(this, new JScrollPane(textAreaStruk), "STRUK REKAP KASIR LUNAS", JOptionPane.PLAIN_MESSAGE);
        
        txtUangBayar.setText("");
        txtTampilKembalian.setText("Rp 0");
    }

    public void tampilData() {
        try {
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();

            Connection conn = Koneksi.configDB();
            String sql = "SELECT " +
                    "tb_penjualan.id_jual, " +
                    "tb_penjualan.tgl_transaksi, " +
                    "tb_customer.nama_customer, " +
                    "tb_barang.nama_barang, " +
                    "tb_penjualan.jumlah_beli, " +
                    "tb_penjualan.total_bayar " +
                    "FROM tb_penjualan " +
                    "JOIN tb_customer ON tb_penjualan.id_customer = tb_customer.id_customer " +
                    "JOIN tb_barang ON tb_penjualan.id_barang = tb_barang.id_barang";

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            int totalSemua = 0;
            while (rs.next()) {
                int total = rs.getInt("total_bayar");
                totalSemua += total;

                model.addRow(new Object[]{
                    rs.getString("id_jual"),
                    rs.getString("tgl_transaksi"),
                    rs.getString("nama_customer"),
                    rs.getString("nama_barang"),
                    rs.getString("jumlah_beli"),
                    "Rp " + formatRupiah(total)
                });
            }
            
            // Mengatur total rekap gabungan langsung ke papan kasir kanan
            totalLabel.setText("TOTAL TRANSAKSI : Rp " + formatRupiah(totalSemua));
            currentTotalBelanja = totalSemua;
            txtTampilTotal.setText("Rp " + formatRupiah(currentTotalBelanja));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void cariData() {
        try {
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();

            Connection conn = Koneksi.configDB();
            String sql = "SELECT " +
                    "tb_penjualan.id_jual, " +
                    "tb_penjualan.tgl_transaksi, " +
                    "tb_customer.nama_customer, " +
                    "tb_barang.nama_barang, " +
                    "tb_penjualan.jumlah_beli, " +
                    "tb_penjualan.total_bayar " +
                    "FROM tb_penjualan " +
                    "JOIN tb_customer ON tb_penjualan.id_customer = tb_customer.id_customer " +
                    "JOIN tb_barang ON tb_penjualan.id_barang = tb_barang.id_barang " +
                    "WHERE tb_customer.nama_customer LIKE '%" + txtSearch.getText() + "%'";

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            int totalSemua = 0;
            while (rs.next()) {
                int total = rs.getInt("total_bayar");
                totalSemua += total;

                model.addRow(new Object[]{
                    rs.getString("id_jual"),
                    rs.getString("tgl_transaksi"),
                    rs.getString("nama_customer"),
                    rs.getString("nama_barang"),
                    rs.getString("jumlah_beli"),
                    "Rp " + formatRupiah(total)
                });
            }
            
            // Total papan kasir mengikuti hasil filter data pencarian
            totalLabel.setText("TOTAL TRANSAKSI : Rp " + formatRupiah(totalSemua));
            currentTotalBelanja = totalSemua;
            txtTampilTotal.setText("Rp " + formatRupiah(currentTotalBelanja));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}