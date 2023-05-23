import javax.swing.*;
import java.awt.event.*;

public class FinishMessage extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel message;

    public FinishMessage(String Message) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        message.setText(Message);
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
        this.pack();
        this.setLocationRelativeTo(null);
    }

    private void onOK() {
        System.exit(0);
    }
}
