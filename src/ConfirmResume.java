import javax.swing.*;
import java.awt.event.*;

public class ConfirmResume extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel players;
    private JLabel blokedTabs;
    private JLabel TabsForPlayers;
    private ConfigGame caller;
    private int tabsBlocked;
    private int numberOfPlayers;
    private int tabsByPlayer;

    public ConfirmResume(int tabsBlocked, int numberOfPlayers, int tabsByPlayer, ConfigGame frame) {
        players.setText(" " + numberOfPlayers);
        blokedTabs.setText(" " + tabsBlocked);
        TabsForPlayers.setText(" " + tabsByPlayer);
        caller = frame;
        this.numberOfPlayers = numberOfPlayers;
        this.tabsByPlayer = tabsByPlayer;
        this.tabsBlocked = tabsBlocked;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void onOK() {
        caller.setVisible(false);
        dispose();
        new Domino(numberOfPlayers, tabsBlocked, tabsByPlayer);
    }

    private void onCancel() {
        dispose();
    }

}
