import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static javax.swing.JOptionPane.showMessageDialog;


/**
 * GUI CLass creating new panel for editing and adding points to already created trips
 */

public class addPoints {
    protected JPanel panelMain;
    private JComboBox comboBox1;
    private JLabel opis;
    private JButton But1;
    private JButton But2;
    private JTable table1;
    private JButton send;
    private Boolean flag_first = true;

    public addPoints(DatabaseConnector db, String start_name, JFrame frame) {

        refreshCombo(db, start_name);

        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBox1.getSelectedItem() == null) showMessageDialog(null, "Wybierz najpierw punkt!");
                else {
                    String finish = comboBox1.getSelectedItem().toString().trim();
                    if (flag_first) {
                        db.addPoint(start_name, finish);
                        flag_first = false;
                    } else {
                        if (db.addPoint(finish))
                            showMessageDialog(null, "Osiągnąłęś maksymalną liczbę punktów podróży!");
                    }
                    refreshCombo(db, finish);
                    String[] columnNames = {"From point", "To point", "Time"};
                    table1.setModel(new DefaultTableModel(db.seeTripPoints(db.lastTripName), columnNames));
                }
            }
        });
        But1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                db.deleteLastPoint();
                if (db.checkLastFinish().equals("")) {
                    frame.setContentPane(new setStart(db, frame).panelMain);
                    frame.pack();
                    frame.repaint();
                } else {
                    refreshCombo(db, db.checkLastFinish());
                    String[] columnNames = {"From point", "To point", "Time"};
                    table1.setModel(new DefaultTableModel(db.seeTripPoints(db.lastTripName), columnNames));
                }

            }
        });
        But2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMessageDialog(null, "Wycieczka zapisana!");
                frame.setContentPane(new MainWindow().panelMain);
                frame.pack();
                frame.repaint();
            }
        });
    }

    public addPoints(DatabaseConnector db, JFrame frame) {

        refreshCombo(db, db.checkLastFinish());
        String[] columnNames = {"From point", "To point", "Time"};
        table1.setModel(new DefaultTableModel(db.seeTripPoints(db.lastTripName), columnNames));


        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBox1.getSelectedItem() == null) showMessageDialog(null, "Wybierz najpierw punkt!");
                else {
                    String finish = comboBox1.getSelectedItem().toString().trim();
                    if (db.addPoint(finish))
                        showMessageDialog(null, "Osiągnąłeś maksymalną liczbę punktów w swojej wycieczce!");
                    refreshCombo(db, finish);
                    String[] columnNames = {"From point", "To point", "Time"};
                    table1.setModel(new DefaultTableModel(db.seeTripPoints(db.lastTripName), columnNames));
                }
            }
        });
        But1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                db.deleteLastPoint();
                if (db.checkLastFinish().equals("")) {
                    frame.setContentPane(new setStart(db, frame).panelMain);
                    frame.pack();
                    frame.repaint();
                } else {
                    refreshCombo(db, db.checkLastFinish());
                    String[] columnNames = {"From point", "To point", "Time"};
                    table1.setModel(new DefaultTableModel(db.seeTripPoints(db.lastTripName), columnNames));
                }
            }
        });
        But2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMessageDialog(null, "Wycieczka zapisana!");
                frame.setContentPane(new MainWindow().panelMain);
                frame.pack();
                frame.repaint();
            }
        });
    }

    protected void refreshCombo(DatabaseConnector db, String name) {
        comboBox1.removeAllItems();
        for (String el : db.availablePoints(name)) {
            comboBox1.addItem(el);
        }
        comboBox1.setSelectedIndex(-1);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panelMain = new JPanel();
        panelMain.setLayout(new GridLayoutManager(6, 3, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panel1, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panel2, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        But1 = new JButton();
        But1.setText("Usuń ostatni punkt!");
        panel2.add(But1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        But2 = new JButton();
        But2.setText("Zapisz wycieczkę!");
        panel2.add(But2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setBackground(new Color(-1380627));
        panelMain.add(scrollPane1, new GridConstraints(0, 2, 4, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        table1 = new JTable();
        table1.setUpdateSelectionOnSort(true);
        scrollPane1.setViewportView(table1);
        opis = new JLabel();
        opis.setText("Wybierz punkt wycieczki:");
        panelMain.add(opis, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox1 = new JComboBox();
        comboBox1.setBackground(new Color(-4341312));
        panelMain.add(comboBox1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        send = new JButton();
        send.setText("Dodaj punkt!");
        panelMain.add(send, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panel4, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelMain;
    }

}
