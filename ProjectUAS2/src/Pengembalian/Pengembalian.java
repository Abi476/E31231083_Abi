package Pengembalian;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
import static java.lang.Thread.sleep;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import Dashboard.Dashboard;
import Sewa.Sewa;
import Transaksi.Transaksi;
import javax.swing.table.DefaultTableModel;
import KoneksiDB.KoneksiDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;

/**
 *
 * @author abiba
 */
public class Pengembalian extends javax.swing.JFrame {

    JComboBox<String> jComboTangga1 = new JComboBox<>();
    Dashboard Dashboard = new Dashboard();
    Sewa Sewa = new Sewa();
    Transaksi Transaksi = new Transaksi();
    DefaultTableModel dtm = new DefaultTableModel();
    KoneksiDB KoneksiDB = new KoneksiDB();
    DefaultTableModel dtm2 = new DefaultTableModel();

    /**
     * Creates new form Pengembalian
     */
    public Pengembalian() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Waktu();
        AturKolom();
        AturBaris();
        generateReturnNumber();
        getItem();
        jComboTanggal();
        AturBaris2();
        AturKolom2();
        gege();
    }

    public void Waktu() {

        Thread p = new Thread() {
            public void run() {
                for (;;) {

                    Calendar kal = new GregorianCalendar();
                    int hari = kal.get(Calendar.DAY_OF_MONTH);
                    int bulan = kal.get(Calendar.MONTH) + 1;
                    int tahun = kal.get(Calendar.YEAR);
                    String tanggal = hari + "/" + bulan + "/" + tahun;
                    jTanggal2.setText(tanggal);
                    try {
                        sleep(1000);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                }
            }
        };
        p.start();
    }

    public void AturKolom() {
        dtm.addColumn("ID Pengembalian");
        dtm.addColumn("ID Transaksi");
        dtm.addColumn("Tanggal");
        dtm.addColumn("Harga");
        dtm.addColumn("Status");
        jPengembalian.setModel(dtm);
    }

    public void AturBaris() {
        dtm.setRowCount(0);
        jPengembalian.setModel(dtm);
        ResultSet rs = KoneksiDB.ambilData("SELECT * FROM Pengembalian");
        try {
            while (rs.next()) {
                dtm.addRow(new Object[]{rs.getString("ID_Pengembalian"), rs.getString("ID_Transaksi"), rs.getString("Tanggal"), rs.getString("Harga"), rs.getString("Status")});
            }
            jPengembalian.setModel(dtm);
        } catch (Exception e) {
        }
    }

    public void generateReturnNumber() {
        ResultSet rs = KoneksiDB.ambilData("SELECT * FROM pengembalian ORDER BY ID_Pengembalian DESC");
        try {
            rs.next();
            String id = rs.getString("ID_Pengembalian");
            String[] arr = id.split("PNG");
            int idNum = Integer.parseInt(arr[1]);
            idNum++;
            String result = "";
            if (idNum < 9) {
                result = "000" + idNum;
            } else if (idNum < 99) {
                result = "00" + idNum;
            } else if (idNum < 999) {
                result = "" + idNum;
            }
            jIDpengembalian.setText("PNG" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getItem() {
        ResultSet rs = KoneksiDB.ambilData("SELECT * FROM Transaksi");
        jComboIDTransaksi.addItem("--Pilih Item--");
        try {
            while (rs.next()) {
                jComboIDTransaksi.addItem(rs.getString("ID_Transaksi"));
            }
        } catch (Exception e) {

        }
    }

    public void jComboTanggal() {
        LocalDate tanggalSekarang = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        for (int i = 0; i < 7; i++) {
            String tanggalTerformat = tanggalSekarang.plusDays(i).format(formatter);
            jComboTanggal1.addItem(tanggalTerformat);
        }
    }

    public void AturKolom2() {
        dtm2.addColumn("Tanggal");
        dtm2.addColumn("Total Pendapatan");
        //jPendapatan.setModel(dtm2);
    }

    public void AturBaris2() {
        dtm2.setRowCount(0);
        //jPendapatan.setModel(dtm2); // Set model sebelum memulai pengisian ulang

        ResultSet rs = KoneksiDB.ambilData("SELECT Tanggal, Harga FROM Pengembalian ORDER BY Tanggal");
        HashMap<String, Integer> totalPendapatan = new HashMap<>();

        try {
            while (rs.next()) {
                String tanggal = rs.getString("Tanggal");
                int harga = Integer.parseInt(rs.getString("Harga"));
                totalPendapatan.put(tanggal, totalPendapatan.getOrDefault(tanggal, 0) + harga);
            }

            for (Map.Entry<String, Integer> entry : totalPendapatan.entrySet()) {
                dtm2.addRow(new Object[]{entry.getKey(), entry.getValue()});
            }

            //jPendapatan.setModel(dtm2); // Set model setelah selesai pengisian
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void gege() {
        ResultSet rs = KoneksiDB.ambilData("SELECT * FROM Pengembalian GROUP BY Tanggal");
        try {
            while(rs.next()) {
                jComboBoxTgl.addItem(rs.getString("Tanggal"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jDashboard = new javax.swing.JButton();
        jSewa = new javax.swing.JButton();
        jTransaksi = new javax.swing.JButton();
        jLogout = new javax.swing.JButton();
        jTanggal2 = new javax.swing.JTextField();
        ID_Pengembalian = new javax.swing.JLabel();
        jIDpengembalian = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jComboIDTransaksi = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jComboStatus = new javax.swing.JComboBox<>();
        jComboTanggal1 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPengembalian = new javax.swing.JTable();
        jSimpan = new javax.swing.JButton();
        jEdit = new javax.swing.JButton();
        jHapus = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jHarga = new javax.swing.JTextField();
        jComboBoxTgl = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setMaximumSize(new java.awt.Dimension(1920, 1080));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Pengembalian Barang dan Laporan Keuangan");

        jPanel2.setBackground(new java.awt.Color(153, 255, 153));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ProjectUAS2/Group 296.png"))); // NOI18N

        jDashboard.setText("Dashboard");
        jDashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDashboardActionPerformed(evt);
            }
        });

        jSewa.setText("Katalog Sewa");
        jSewa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSewaActionPerformed(evt);
            }
        });

        jTransaksi.setText("Transaksi");
        jTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTransaksiActionPerformed(evt);
            }
        });

        jLogout.setText("Log Out");
        jLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLogoutMouseClicked(evt);
            }
        });
        jLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSewa, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(106, 106, 106)
                .addComponent(jDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSewa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );

        jTanggal2.setEditable(false);
        jTanggal2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTanggal2.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        ID_Pengembalian.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ID_Pengembalian.setText("ID_Pengembalian");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("ID_Transaksi");

        jComboIDTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboIDTransaksiActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Tanggal ");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Status");

        jComboStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Pilih Status--", "Sudah", "Belum", "Terlambat" }));

        jComboTanggal1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Pilih Tanggal--" }));
        jComboTanggal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboTanggal1ActionPerformed(evt);
            }
        });

        jPengembalian.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID Pengembalian", "ID Transaksi", "Tanggal", "Harga", "Status"
            }
        ));
        jPengembalian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPengembalianMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jPengembalian);

        jSimpan.setBackground(new java.awt.Color(51, 255, 51));
        jSimpan.setText("Simpan");
        jSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSimpanActionPerformed(evt);
            }
        });

        jEdit.setBackground(new java.awt.Color(51, 51, 255));
        jEdit.setText("Edit");

        jHapus.setBackground(new java.awt.Color(255, 0, 0));
        jHapus.setText("Hapus");
        jHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jHapusActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("Laporan Pendapatan");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Harga");

        jComboBoxTgl.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Pilih Tanggal--" }));
        jComboBoxTgl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTglActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Pilih Tanggal:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(416, 416, 416)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTanggal2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ID_Pengembalian, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(102, 102, 102)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jIDpengembalian)
                                    .addComponent(jComboIDTransaksi, 0, 182, Short.MAX_VALUE)
                                    .addComponent(jComboStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboTanggal1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jHarga))
                                .addGap(119, 119, 119)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 808, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(106, 106, 106)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jComboBoxTgl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jTextField1)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(143, 143, 143)
                                .addComponent(jLabel6)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jTanggal2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(90, 90, 90)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ID_Pengembalian)
                            .addComponent(jIDpengembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jComboIDTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jComboTanggal1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jComboStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(5, 5, 5)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxTgl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(203, 203, 203))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLogoutMouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jLogoutMouseClicked

    private void jComboIDTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboIDTransaksiActionPerformed
        // TODO add your handling code here:
        if (!jComboIDTransaksi.getSelectedItem().equals("--Pilih Item--")) {
            ResultSet rs = KoneksiDB.ambilData("SELECT * FROM Transaksi WHERE ID_Transaksi = '" + jComboIDTransaksi.getSelectedItem() + "'");
            try {
                rs.next();
                int harga = Integer.parseInt(rs.getString("Harga"));
                jHarga.setText(String.valueOf(harga));
                jHarga.setText(String.valueOf(Integer.parseInt(jHarga.getText())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }//GEN-LAST:event_jComboIDTransaksiActionPerformed

    private void jDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDashboardActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        Dashboard.setVisible(true);
    }//GEN-LAST:event_jDashboardActionPerformed

    private void jSewaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSewaActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        Sewa.setVisible(true);
    }//GEN-LAST:event_jSewaActionPerformed

    private void jTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTransaksiActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        Transaksi.setVisible(true);
    }//GEN-LAST:event_jTransaksiActionPerformed

    private void jPengembalianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPengembalianMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPengembalianMouseClicked

    private void jHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jHapusActionPerformed
        // TODO add your handling code here:
        int n = jPengembalian.getSelectedRow();
        String ID_Pengembalian = String.valueOf(jPengembalian.getValueAt(n, 0));
        KoneksiDB.aksi("Delete from Pengembalian WHERE ID_Pengembalian = '" + ID_Pengembalian + "'");
        AturBaris();
    }//GEN-LAST:event_jHapusActionPerformed

    private void jComboTanggal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboTanggal1ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jComboTanggal1ActionPerformed

    private void jSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSimpanActionPerformed
        // TODO add your handling code here
        String ID_Pengembalian = (String) jIDpengembalian.getText();
        String ID_Transaksi = (String) jComboIDTransaksi.getSelectedItem();
        String Tanggal = (String) jComboTanggal1.getSelectedItem();
        String Harga = (String) jHarga.getText();
        String Status = (String) jComboStatus.getSelectedItem();

        KoneksiDB.aksi("INSERT INTO Pengembalian Values('" + ID_Pengembalian + "','" + ID_Transaksi + "','" + Tanggal + "','" + Harga + "', '" + Status + "')");
        jIDpengembalian.setText("");
        jComboIDTransaksi.setSelectedItem("");
        jComboTanggal1.setSelectedItem("");
        jHarga.setText("");
        jComboStatus.setSelectedItem("");
        AturBaris();
        generateReturnNumber();
    }//GEN-LAST:event_jSimpanActionPerformed

    private void jLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLogoutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jLogoutActionPerformed

    private void jComboBoxTglActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxTglActionPerformed
        // TODO add your handling code here:
        if(jComboBoxTgl.getSelectedItem() != "--Pilih Tanggal--") {
            ResultSet rs = KoneksiDB.ambilData("SELECT COUNT(Tanggal) AS hehe, Tanggal, SUM(Harga) AS hoho FROM Pengembalian WHERE Tanggal = '" + jComboBoxTgl.getSelectedItem() + "' GROUP BY Tanggal");
            try {
                if(rs.next()) {
                    jTextField1.setText(rs.getString("hoho"));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_jComboBoxTglActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Pengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pengembalian().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ID_Pengembalian;
    private javax.swing.JComboBox<String> jComboBoxTgl;
    private javax.swing.JComboBox<String> jComboIDTransaksi;
    private javax.swing.JComboBox<String> jComboStatus;
    private javax.swing.JComboBox<String> jComboTanggal1;
    private javax.swing.JButton jDashboard;
    private javax.swing.JButton jEdit;
    private javax.swing.JButton jHapus;
    private javax.swing.JTextField jHarga;
    private javax.swing.JTextField jIDpengembalian;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JButton jLogout;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTable jPengembalian;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jSewa;
    private javax.swing.JButton jSimpan;
    private javax.swing.JTextField jTanggal2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton jTransaksi;
    // End of variables declaration//GEN-END:variables
}
