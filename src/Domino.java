import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class Domino extends JFrame {
    static Domino frame;
    private JScrollPane fichas;
    private JPanel main;
    public JPanel gruping;
    public JPanel BoardPanel;
    private int numberOfPlayers;
    public Player[] players;

    public Player turnPlayer;

    public Player realPlayer;
    private static ArrayList<Ficha> tabsNotAssigned = new ArrayList<>();
    public LinkedList<Ficha> board = new LinkedList<Ficha>(){
        @Override
        public void addLast(Ficha element) {
            BoardPanel.add(element.evaluate());
            BoardPanel.updateUI();
            BoardPanel.repaint();
            super.addLast(element);
        }

        @Override
        public void addFirst(Ficha element) {;
            BoardPanel.add(element.evaluate(),0);
            BoardPanel.updateUI();
            BoardPanel.repaint();
            super.addFirst(element);
        }
    };

    public int[] allowedNumbers = new int[2];
    static {
        for (int i = 0; i <= 6; i++) {
            for (int j = i; j <= 6; j++) {
                Ficha f = new Ficha(i, j);
                tabsNotAssigned.add(f);
                if (i == j) {
                    f.marrana = true;
                    f.priority = 10000 + i;
                } else {
                    f.priority = i + j;
                }
            }
        }
    }

    public Domino(int players, int tabsBlocked, int tabsByPlayers) {
        this.players = new Player[players];
        int realIndex = (int) Math.floor(Math.random() * players);
        for (int i = 0; i < players; i++) {
            this.players[i] = new Player(this);
            if (i > 0) this.players[i].nextPlayer = this.players[i - 1];
        }
        this.players[0].nextPlayer = this.players[players-1];
        this.players[realIndex].real = true;
        realPlayer = this.players[realIndex];
        int maxPriotiy = -1;
        for (int i = 0; i < tabsByPlayers * players; i++) {
            Ficha ficha = tabsNotAssigned.remove((int) (Math.random() *tabsNotAssigned.size()));
            ficha.assigned = true;
            ficha.game = this;
            this.players[i % players].tabs.add(ficha);
            this.players[i % players].suma += ficha.values[0] + ficha.values[1];
            if (turnPlayer == null || maxPriotiy < ficha.priority) {
                if (turnPlayer != null) turnPlayer.turno = false;
                turnPlayer = this.players[i % players];
                turnPlayer.turno = true;
                maxPriotiy = ficha.priority;
            }
        }
        for (Ficha tab : realPlayer.tabs) {
            JLabel ficha = tab.image;
            gruping.add(ficha);
        }
        gruping.setVisible(true);
        gruping.repaint();
        this.add(main);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(3);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame = this;
        gruping.repaint();
        turnPlayer.manageTurn(true);
    }

    private void createUIComponents() {
        gruping = new JPanel(new FlowLayout());
    }
    void cleanCanPlay(){
      for(Player p : players){
          p.canPlay = true;
      }
    };
}
