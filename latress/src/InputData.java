
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;



public class InputData extends JFrame{
    JLabel lnohp,lnama,ljudul;
    JTextField txnohp,txnama;
    JButton btnSimpan, btnKembali;
    Statement statement;

    public void InputData()
    {
        ljudul = new JLabel("INPUT covid");
        lnohp = new JLabel("positif");
        lnama = new JLabel("negara");
        txnohp = new JTextField("");
        txnama = new JTextField("");
        btnSimpan  = new JButton("Simpan");
        btnKembali  = new JButton("Kembali");

        setTitle("INPUT covid");
        setSize(400,380);
        ljudul.setHorizontalAlignment(SwingConstants.CENTER);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        setLayout(null);
        add(ljudul);
        add(lnohp);
        add(lnama);
        add(txnohp);
        add(txnama);
        add(btnSimpan);
        add(btnKembali);

        ljudul.setBounds(50,30,300,25);
        lnohp.setBounds(75, 75, 30, 20);
        lnama.setBounds(75, 100, 50, 20);
        txnohp.setBounds(150, 75, 150, 20);
        txnama.setBounds(150, 100, 150, 20);
        btnKembali.setBounds(200,250,100,25);
        btnSimpan.setBounds(100,250,100,25);

        btnKembali.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new Menu();
            }
        });

        btnSimpan.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSimpanactionListener();
            }

            private void btnSimpanactionListener() {
                KoneksiDB koneksi = new KoneksiDB();
                try{

                    statement = koneksi.getKoneksi().createStatement();
                    statement.executeUpdate("INSERT INTO data VALUES ('"
                            + txnohp.getText() + "','" + txnama.getText() + "')");

                    JOptionPane.showMessageDialog(null, "data berhasil di input!", "Hasil", JOptionPane.INFORMATION_MESSAGE);
                    statement.close();

                } catch (SQLException ex){
                    JOptionPane.showMessageDialog(null, "data gagal di input!", "Hasil", JOptionPane.INFORMATION_MESSAGE);
                } catch (ClassNotFoundException ex){
                    JOptionPane.showMessageDialog(null, "driver tidak ditemukan!", "Hasil", JOptionPane.INFORMATION_MESSAGE);
                }
            }

        });
    }


}
