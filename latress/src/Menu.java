
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class Menu extends JFrame{
    JLabel ljudul;
    JButton create, read, delete, update, exit;

    public void tesMenu (){

        setTitle("MENU");
        ljudul = new JLabel("MENU");
        create = new JButton("1. INPUT covid");
        read = new JButton("2. TAMPILKAN jumlah kasus");
        delete = new JButton("3. HAPUS data covid");
        update = new JButton("4. EDIT covid");
        exit = new JButton("0. Exit");

        setLayout(null);
        add(ljudul);
        add(create);
        add(read);
        add(delete);
        add(update);
        add(exit);

        ljudul.setBounds(160, 20, 100, 20);
        create.setBounds(75, 50, 200, 20);
        read.setBounds(75, 75, 200, 20);
        delete.setBounds(75, 100, 200, 20);
        update.setBounds(75, 125, 200, 20);
        exit.setBounds(75, 150, 200, 20);
        setSize(370,300); //untuk luas jendela

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        create.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                InputData create = new InputData();
                create.InputData();
            }

        });
        read.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LihatData lihat = new LihatData();
                lihat.LihatData();
            }

        });
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HapusData hapus = new HapusData();
                hapus.HapusData();
            }

        });
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditData edit = new EditData();
                edit.EditData();
            }

        });
        exit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }

        });
    }


}
