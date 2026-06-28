import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class FormKategori extends JFrame {

    JLabel title,
            subtitle,
            lnama,
            lsearch,
            footer;

    JTextField txtNama,
            txtSearch;

    JButton btnSimpan,
            btnHapus;

    JTable table;

    DefaultTableModel model;

    int selectedId = 0;

    public FormKategori() {

        setTitle("TOKO BERKAH JAYA - KATEGORI");

        setSize(1100,650);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();

        panel.setLayout(null);

        panel.setBackground(
                new Color(245,247,250));

        add(panel);

        title = new JLabel(
                "DATA KATEGORI");

        title.setBounds(380,20,400,45);

        title.setFont(new Font(
                "Segoe UI",
                Font.BOLD,
                36));

        title.setForeground(
                new Color(44,62,80));

        panel.add(title);

        subtitle = new JLabel(
                "Kelola Data Kategori Barang");

        subtitle.setBounds(380,65,350,30);

        subtitle.setFont(new Font(
                "Segoe UI",
                Font.PLAIN,
                18));

        subtitle.setForeground(Color.GRAY);

        panel.add(subtitle);

        JPanel cardForm = new JPanel();

        cardForm.setLayout(null);

        cardForm.setBounds(40,120,360,400);

        cardForm.setBackground(Color.WHITE);

        cardForm.setBorder(
                BorderFactory.createLineBorder(
                        new Color(220,220,220)));

        panel.add(cardForm);

        JLabel icon = new JLabel("📦");

        icon.setBounds(130,25,100,70);

        icon.setFont(new Font(
                "Segoe UI Emoji",
                Font.PLAIN,
                50));

        cardForm.add(icon);

        lnama = new JLabel("Nama Kategori");

        lnama.setBounds(40,130,120,25);

        cardForm.add(lnama);

        txtNama = new JTextField();

        txtNama.setBounds(40,165,280,40);

        cardForm.add(txtNama);

        btnSimpan = new JButton("SIMPAN");

        btnSimpan.setBounds(40,260,120,45);

        btnSimpan.setBackground(
                new Color(46,204,113));

        btnSimpan.setForeground(Color.WHITE);

        cardForm.add(btnSimpan);

        btnHapus = new JButton("HAPUS");

        btnHapus.setBounds(190,260,120,45);

        btnHapus.setBackground(
                new Color(231,76,60));

        btnHapus.setForeground(Color.WHITE);

        cardForm.add(btnHapus);

        JPanel cardTable = new JPanel();

        cardTable.setLayout(null);

        cardTable.setBounds(450,120,600,400);

        cardTable.setBackground(Color.WHITE);

        cardTable.setBorder(
                BorderFactory.createLineBorder(
                        new Color(220,220,220)));

        panel.add(cardTable);

        lsearch = new JLabel("Cari Kategori");

        lsearch.setBounds(20,20,120,25);

        cardTable.add(lsearch);

        txtSearch = new JTextField();

        txtSearch.setBounds(140,20,420,38);

        cardTable.add(txtSearch);

        model = new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("Nama Kategori");

        table = new JTable(model);

        JScrollPane scroll =
                new JScrollPane(table);

        scroll.setBounds(20,80,550,280);

        cardTable.add(scroll);

        footer = new JLabel(
                "© TOKO BERKAH JAYA 2026");

        footer.setBounds(470,580,250,20);

        panel.add(footer);

        tampilData();

        btnSimpan.addActionListener(e -> {

            simpanData();

        });

        btnHapus.addActionListener(e -> {

            hapusData();

        });

        txtSearch.addKeyListener(
                new java.awt.event.KeyAdapter() {

            public void keyReleased(
                    java.awt.event.KeyEvent evt) {

                cariKategori();

            }

        });

        table.addMouseListener(
                new java.awt.event.MouseAdapter() {

            public void mouseClicked(
                    java.awt.event.MouseEvent evt) {

                ambilData();

            }

        });

    }

    private void tampilData() {

        try {

            model.getDataVector()
                    .removeAllElements();

            model.fireTableDataChanged();

            Connection conn =
                    Koneksi.configDB();

            Statement st =
                    conn.createStatement();

            ResultSet rs =
                    st.executeQuery(
                            "SELECT * FROM tb_kategori");

            while (rs.next()) {

                model.addRow(new Object[]{

                    rs.getString("id_kategori"),

                    rs.getString("nama_kategori")

                });

            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage());

        }

    }

    private void simpanData() {

        try {

            Connection conn =
                    Koneksi.configDB();

            String sql =
                    "INSERT INTO tb_kategori VALUES(NULL,?)";

            PreparedStatement pst =
                    conn.prepareStatement(sql);

            pst.setString(1,
                    txtNama.getText());

            pst.executeUpdate();

            JOptionPane.showMessageDialog(
                    null,
                    "Kategori Berhasil Disimpan");

            tampilData();

            clearForm();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage());

        }

    }

    private void ambilData() {

        int baris =
                table.getSelectedRow();

        selectedId =
                Integer.parseInt(
                        table.getValueAt(
                                baris,
                                0).toString());

        txtNama.setText(
                table.getValueAt(
                        baris,
                        1).toString());

    }

    private void hapusData() {

        try {

            Connection conn =
                    Koneksi.configDB();

            String sql =
                    "DELETE FROM tb_kategori WHERE id_kategori=?";

            PreparedStatement pst =
                    conn.prepareStatement(sql);

            pst.setInt(1,
                    selectedId);

            pst.executeUpdate();

            JOptionPane.showMessageDialog(
                    null,
                    "Kategori Berhasil Dihapus");

            tampilData();

            clearForm();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage());

        }

    }

    private void cariKategori() {

        try {

            model.getDataVector()
                    .removeAllElements();

            model.fireTableDataChanged();

            Connection conn =
                    Koneksi.configDB();

            Statement st =
                    conn.createStatement();

            ResultSet rs =
                    st.executeQuery(
                            "SELECT * FROM tb_kategori WHERE nama_kategori LIKE '%"
                            + txtSearch.getText()
                            + "%'");

            while (rs.next()) {

                model.addRow(new Object[]{

                    rs.getString("id_kategori"),

                    rs.getString("nama_kategori")

                });

            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage());

        }

    }

    private void clearForm() {

        txtNama.setText("");

    }

}