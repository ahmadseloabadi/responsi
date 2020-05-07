
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;


public class EditData extends JFrame{
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/latress";
    static final String USER = "root";
    static final String PASS = "";

    Connection koneksi;
    Statement statement;

    JTable table;
    DefaultTableModel tableModel;
    JScrollPane scrollPane;
    Object namaKolom[] = {"positif", "negara"};

    JLabel ljudul = new JLabel("EDIT covid");
    JLabel lnohp = new JLabel("positif ");
    JTextField tnohp = new JTextField();
    JLabel lnama = new JLabel("negara ");
    JTextField tnama = new JTextField();
    JButton btnEdit = new JButton("Edit");
    JButton btnKembali = new JButton("Kembali");


    public void EditData(){
        try{
            Class.forName(JDBC_DRIVER);
            koneksi = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Koneksi Berhasil");
        }   catch(ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            System.out.println("Koneksi Gagal");
        }

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(null);
        setSize(550, 600);

        tableModel = new DefaultTableModel(namaKolom,0);
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);
        add(scrollPane);
        scrollPane.setBounds(20, 270, 480, 250);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(ljudul);
        ljudul.setBounds(230, 10, 300, 60);
        add(lnama);
        add(lnohp);
        add(tnama);
        add(tnohp);

        lnohp.setBounds(40, 80, 80, 20);
        tnohp.setBounds(140, 80, 220, 20);
        lnama.setBounds(40, 110, 80, 20);
        tnama.setBounds(140, 110, 220, 20);


        add(btnEdit);
        add(btnKembali);
        btnEdit.setBounds(150, 220, 80, 30);
        btnKembali.setBounds(350, 220, 80, 30);

        btnKembali.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                setVisible (false);
                new Menu();
            }
        });

        btnEdit.addActionListener((ActionEvent e) -> {
            if (tnohp.getText().equals("") ) {
                JOptionPane.showMessageDialog(null, "Field tidak boleh kosong!");
            } else {
                String nohp = tnohp.getText();
                String nama = tnama.getText();


                this.simpanMahasiswa(nohp, nama);

                String datanohp[][] = this.readnohp();
                table.setModel(new JTable(datanohp,namaKolom).getModel());
            }
        });

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
            }
        });

    }

    public void simpanMahasiswa(String nohp, String nama) {
        try{
            String query ="UPDATE data SET nama='" + tnama.getText() + "' WHERE nohp='" + tnohp.getText() + "'";
            statement = (Statement) koneksi.createStatement();
            statement.executeUpdate(query);
            System.out.println("Berhasil diedit");
            JOptionPane.showMessageDialog(null,"Berhasil diedit "+nohp+"");
        }catch(Exception sql){
            System.out.println(sql.getMessage());
            JOptionPane.showMessageDialog(null, sql.getMessage());
        }
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




}
