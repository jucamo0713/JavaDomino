import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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
    private static Ficha[] allTabs = new Ficha[28];
    public ArrayList<Ficha> board = new ArrayList<Ficha>(){
        @Override
        public boolean add(Ficha element) {
            BoardPanel.add(element.evaluate());
            BoardPanel.repaint();
            return super.add(element);
        }

        @Override
        public void add(int index, Ficha element) {;
            BoardPanel.add(element.evaluate(), index);
            BoardPanel.repaint();
            super.add(index, element);
        }
    };

    public int[] allowedNumbers = new int[2];
    static {
        int count = 0;
        for (int i = 0; i <= 6; i++) {
            for (int j = i; j <= 6; j++) {
                allTabs[count] = new Ficha(i, j);
                if (i == j) {
                    allTabs[count].marrana = true;
                    allTabs[count].priority = 10000 + i;
                } else {
                    allTabs[count].priority = i + j;
                }
                count++;
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
            Ficha ficha;
            do {
                ficha = allTabs[(int) (Math.random() * 28)];
            } while (ficha.assigned);
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
