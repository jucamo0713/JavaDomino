import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JFrame{

    private static JFrame frame;
    private JLabel TITLE;
    private JButton SALIRButton;
    private JButton INICIARButton;
    private JPanel main;
public Home() {
    INICIARButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            new ConfigGame();
            frame.setVisible(false);
        }
    });
    SALIRButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    });
    this.add(main);
    this.pack();
    this.setLocationRelativeTo(null);
    this.setVisible(true);
    this.setDefaultCloseOperation(3);
    this.setTitle("El domino de julian moreno");
    frame = this;
}
}
