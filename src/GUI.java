import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Main GUI window, contains menu with options and actions for each
 * @return null
 */

public class GUI {
    private DatabaseConnector db;
    public JFrame frame;
    public JMenu menu1, menu2, menu3;
    public JMenuItem i1, i2, i3, i4, i5, i6, i7;


    public GUI(DatabaseConnector db){
        this.db = db;
        frame = new JFrame("Database project");

        JMenuBar bar = new JMenuBar();
        menu1 = new JMenu("Moje wycieczki");
        menu2 = new JMenu("Planowanie wycieczki");
        menu3 = new JMenu("Pomocy!");

        i1 = new JMenuItem(new AbstractAction("Zobacz zaplanowane wycieczki") {
            @Override
            public void actionPerformed(ActionEvent e) {
                seeMyTrips();
            }
        });
        i2 = new JMenuItem(new AbstractAction("Usuń zaplanowaną wycieczkę") {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteMyTrip();
            }
        });
        i3 = new JMenuItem(new AbstractAction("Usuń wszystkie wycieczki") {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteALLTrips();
            }
        });

        menu1.add(i1);
        menu1.add(i2);
        menu1.add(i3);

        i4 = new JMenuItem(new AbstractAction("Zaplanuj wycieczkę") {
            @Override
            public void actionPerformed(ActionEvent e) {
                planMyTrip();
            }
        });
        i5 = new JMenuItem(new AbstractAction("Edytuj wycieczkę") {
            @Override
            public void actionPerformed(ActionEvent e) {
                editMyTrip();
            }
        });

        menu2.add(i4);
        menu2.add(i5);

        i6 = new JMenuItem(new AbstractAction("Potrzebuję instrukcji - patrz na dokumentację") {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new File("./BDDokumentacja.pdf");
                try {
                    java.awt.Desktop.getDesktop().open(file);
                }catch (IOException exception){}
            }
        });
        i7 = new JMenuItem(new AbstractAction("Chcę zobaczyć jak wygląda baza") {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new File("./db.sql");
                try {
                    java.awt.Desktop.getDesktop().open(file);
                }catch (IOException exception){}
            }
        });
        menu3.add(i6);
        menu3.add(i7);

        bar.add(menu1);
        bar.add(menu2);
        bar.add(menu3);

        frame.setJMenuBar(bar);
        frame.setVisible(true);
        frame.setLayout(null);
        frame.setSize(600,200);
        frame.setContentPane(new MainWindow().panelMain);
        frame.pack();
        frame.repaint();

        }

    /**
     * function creating new panel for viewing planned trips
     */
    public void seeMyTrips(){
        frame.setContentPane(new showTrip(db).panelMain);
        frame.pack();
        frame.repaint();
    }

    /**
     * function creating new panel for choosing planned trip to delete
     */
    public void deleteMyTrip(){
        frame.setContentPane(new deleteTrip(db).panelMain);
        frame.pack();
        frame.repaint();
    }

    /**
     * action - DROP FROM trip_list, gives message after
     */
    public void deleteALLTrips(){
        db.deleteAll();
        showMessageDialog(null,"Wszystkie wycieczki zostały usunięte!");
    }

    /**
     * function creating new panel for planning the trip
     */
    public void planMyTrip(){
        frame.setContentPane(new planTrip(db, frame).panelMain);
        frame.pack();
        frame.repaint();
    }

    /**
     * function creating new panel for editing the trip
     */
    public void editMyTrip(){
        frame.setContentPane(new chooseTrip(db, frame).panelMain);
        frame.pack();
        frame.repaint();
    }

}

