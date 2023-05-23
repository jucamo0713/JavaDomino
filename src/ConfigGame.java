import javax.swing.*;
import java.awt.event.*;

public class ConfigGame extends JFrame {

    private int numberOfPlayers = 2;
    private int tabsBlocked = 0;
    private int tabsByPlayer = 14;
    private int minNumbersOfTabsBlocked = 0;
    private int maxNumbersOfTabsBlocked = 26;
    private final int minNumbersOfPlayers = 2;
    private final int maxNumbersOfPlayers = 26;

    private static ConfigGame frame;
    private JPanel main;
    private JTextField PlayerNumbers;
    private JButton EMPEZARButton;
    private JLabel players;
    private JLabel blokedTabs;
    private JLabel TabsForPlayers;
    private JTextField TabsBlocked;

    public ConfigGame() {


        this.add(main);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(3);
        frame = this;
        PlayerNumbers.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                String value = PlayerNumbers.getText();
                int numberOfPlayers = frame.numberOfPlayers;
                if (value.isEmpty()) {
                    return;
                }
                try {
                    numberOfPlayers = Integer.parseInt(value);
                } catch (Exception error) {
                    PlayerNumbers.setText("" + numberOfPlayers);
                    return;
                }
                if (numberOfPlayers < minNumbersOfPlayers) {
                    numberOfPlayers = minNumbersOfPlayers;
                } else if (numberOfPlayers > maxNumbersOfPlayers) {
                    numberOfPlayers = maxNumbersOfPlayers;
                }
                setNumberOfPlayers(numberOfPlayers);
            }
        });
        TabsBlocked.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String value = TabsBlocked.getText();
                int tabsBlocked = frame.tabsBlocked;
                if (value.isEmpty()) {
                    return;
                }
                try {
                    tabsBlocked = Integer.parseInt(value);
                } catch (Exception error) {
                    TabsBlocked.setText("" + tabsBlocked);
                    return;
                }
                if (tabsBlocked < minNumbersOfTabsBlocked) {
                    tabsBlocked = minNumbersOfTabsBlocked;
                } else if (tabsBlocked > maxNumbersOfTabsBlocked) {
                    tabsBlocked = maxNumbersOfTabsBlocked;
                }
                if ((28 - tabsBlocked) % numberOfPlayers == 0) {
                    setTabsBlocked(tabsBlocked);
                }
            }
        });
        TabsBlocked.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String value = TabsBlocked.getText();
                int tabsBlocked = frame.tabsBlocked;
                if (value.isEmpty()) {
                    return;
                }
                try {
                    tabsBlocked = Integer.parseInt(value);
                } catch (Exception error) {
                    TabsBlocked.setText("" + tabsBlocked);
                    return;
                }
                if ((28 - tabsBlocked) % numberOfPlayers != 0) {
                    tabsBlocked = -(Math.round((float)(28 - tabsBlocked) / numberOfPlayers) * numberOfPlayers - 28);
                    setTabsBlocked(tabsBlocked);
                }
            }
        });
        EMPEZARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConfirmResume(tabsBlocked, numberOfPlayers, tabsByPlayer, frame);
            }
        });
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        PlayerNumbers.setText("" + numberOfPlayers);
        this.numberOfPlayers = numberOfPlayers;
        players.setText("" + numberOfPlayers);
        updateTabsData();
        updateTabsByPlayers();
    }

    public void updateTabsData() {
        minNumbersOfTabsBlocked = 28 % numberOfPlayers;
        maxNumbersOfTabsBlocked = 28 - numberOfPlayers;
        if (minNumbersOfTabsBlocked > tabsBlocked) {
            setTabsBlocked(minNumbersOfTabsBlocked);
        } else if (maxNumbersOfTabsBlocked < tabsBlocked) {
            setTabsBlocked(maxNumbersOfTabsBlocked);
        }
    }

    public void setTabsBlocked(int tabsBlocked) {
        TabsBlocked.setText("" + tabsBlocked);
        this.tabsBlocked = tabsBlocked;
        blokedTabs.setText("" + tabsBlocked);
        updateTabsByPlayers();
    }

    public void updateTabsByPlayers() {
        setTabsByPlayer((28 - tabsBlocked) / numberOfPlayers);
    }

    public void setTabsByPlayer(int tabsByPlayer) {
        TabsForPlayers.setText("" + tabsByPlayer);
        this.tabsByPlayer = tabsByPlayer;
    }
}
