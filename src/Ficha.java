import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.Path;

public class Ficha {
    private Ficha ficha;
    public JPanel main;
    public JLabel image;
    public boolean assigned = false;
    public boolean played = false;

    public int values[] = new int[2];
    public int assign = -1;
    public boolean marrana = false;
    public File file = null;
    public int priority = -1;
    public Domino game;
    public boolean selected;
    public boolean allowed = false;
    public int direction = -1;

    public Ficha(int first, int second) {
        values[0] = first;
        values[1] = second;
        //ImageIcon i = new ImageIcon(Path.of(System.getProperty("user.dir"), "src", "assets", "fichas", "" + values[0] + "" + values[1] + ".png").toString());
        ImageIcon i = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(Path.of(".","assets","fichas",  "" + values[0] + values[1]  + ".png").toString()));
        image.setIcon(i);
        image.setForeground(Color.red);
        this.ficha = this;
        image.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (game.turnPlayer.real) {
                    if (!played && allowed) {
                        if (selected) {
                            game.turnPlayer.selecteds.remove(ficha);
                            image.setText(null);
                        } else {
                            if (game.turnPlayer.selecteds.size() == 0 || (game.turnPlayer.selecteds.get(0).marrana && marrana)) {
                                game.turnPlayer.selecteds.add(ficha);
                                image.setText("SELECTED");
                            }
                        }
                    } else if (played && assign != -1) {
                        if (selected) {
                            game.turnPlayer.side = -1;
                            image.setText(null);
                        } else {
                            if (game.turnPlayer.side == -1) {
                                game.turnPlayer.side = Math.abs(assign - 1);
                                image.setText("selected");
                            }
                        }
                    }
                    selected = !selected;
                    game.turnPlayer.generate();
                }
            }
        });
    }

    public JLabel evaluate() {
        if (!marrana) {
            //image.setIcon(new ImageIcon(Path.of(System.getProperty("user.dir"), "src", "assets", "fichas", "" + values[0] + values[1] + direction + ".png").toString()));
            image.setIcon(new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(Path.of(".","assets","fichas",  "" + values[0] + values[1] + direction + ".png").toString())));
        }
        return image;
    }
}
