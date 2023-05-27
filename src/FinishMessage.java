import javax.swing.*;
import java.awt.event.*;

public class FinishMessage extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel message;
    private JLabel suma;
    private JLabel tabsSize;
    private JPanel fichas;

    public FinishMessage(Player ganador) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        message.setText(ganador.real ? "FELICIDADES GANASTE" : "PERDISTE SUERTE PARA LA PROXIMA");
        tabsSize.setText(""+ganador.tabs.size());
        suma.setText(""+ganador.suma);
        ganador.tabs.forEach(x->fichas.add(x.image));
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

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
