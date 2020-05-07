
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;


public class HapusData extends JFrame{
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/latress";
    static final String USER = "root";
    static final String PASS = "";

    Connection koneksi;
    Statement statement;

    JTable table;
    DefaultTableModel tableModel;
    JScrollPane scrollPane;
    Object namaKolom[] = {"nohp", "Nama"};

    JLabel ljudul = new JLabel("HAPUS NOMOR HP");
    JButton btnKembali = new JButton("Kembali");
    JButton btnHapus = new JButton("Hapus");

    public void HapusData(){
        try{
            Class.forName(JDBC_DRIVER);
            koneksi = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Koneksi Berhasil");
        }catch(ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            System.out.println("Koneksi Gagal");
        }


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(null);
        setSize(500, 500);


        tableModel = new DefaultTableModel(namaKolom,0);
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);

        add(scrollPane);
        scrollPane.setBounds(20, 180, 400, 250);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        add(ljudul);
        ljudul.setBounds(180, 10, 300, 60);

        add(btnKembali);
        add(btnHapus);

        btnKembali.setBounds(160, 90, 80, 30);
        btnHapus.setBounds(280, 90, 80, 30);

        btnKembali.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                setVisible (false);
                new Menu();
            }
        });

        if (this.getBanyakData() != 0) {
            String datanohp[][] = this.readnohp();
            table.setModel((new JTable(datanohp, namaKolom)).getModel());

        } else {
            JOptionPane.showMessageDialog(null, "Data Tidak Ada!");
        }

        if (this.getBanyakData() != 0) {
            String datanohp[][] = this.readnohp();
            table.setModel((new JTable(datanohp, namaKolom)).getModel());

        } else {
            JOptionPane.showMessageDialog(null, "Data Tidak Ada!");
        }

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                int baris = table.getSelectedRow();
                int kolom = table.getSelectedColumn();

                String dataterpilih = table.getValueAt(baris, 0).toString();
                btnHapus.addActionListener((ActionEvent f) -> {
                    hapusnohp(dataterpilih);
                    String datanohp[][] = readnohp();
                    table.setModel(new JTable(datanohp,namaKolom).getModel());
                });
            }
        });
    }

    String[][] readnohp() {
        try{
            int jmlData = 0;
            String data[][]=new String[getBanyakData()][2];
            String query = "Select * from `data`";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){

                data[jmlData][0] = resultSet.getString("positif");
                data[jmlData][1] = resultSet.getString("negara");
                jmlData++;
            }
            return data;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("SQL error");
            return null;
        }
    }

    int getBanyakData() {
        int jmlData = 0;
        try{
            statement = koneksi.createStatement();
            String query = "SELECT * from `data`";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                jmlData++;
            }
            return jmlData;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("SQL error");
            return 0;
        }
    }

    void hapusnohp(String nohp) {
        try{
            String query = "DELETE FROM `data` WHERE `nohp` = '"+nohp+"'";
            statement = koneksi.createStatement();
            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Berhasil menghapus "+nohp+"" );
        }catch(SQLException sql){
            System.out.println(sql.getMessage());
        }
    }





}
