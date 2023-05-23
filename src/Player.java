import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Player {
    public ArrayList<Ficha> tabs = new ArrayList();
    public boolean turno = false;
    public int suma = 0;
    public Player nextPlayer;
    public boolean real = false;
    public Domino game;

    public ArrayList<Ficha> selecteds = new ArrayList<>();
    public int side = -1;
    public boolean canPlay = true;

    Player(Domino game) {
        this.game = game;
    }

    public void manageTurn(boolean first) {
        game.turnPlayer = this;
        if (first) {
            int index = -1;
            int maxPriority = -1;
            Ficha ficha = null;
            for (int i = 0; i < tabs.size(); i++) {
                if (maxPriority < tabs.get(i).priority) {
                    maxPriority = tabs.get(i).priority;
                    ficha = tabs.get(i);
                    index = i;
                }
            }
            ficha.played = true;
            ficha.assign = -2;
            ficha.direction = 1;
            game.board.add(ficha);
            game.gruping.remove(ficha.image);
            tabs.remove(index);
            game.repaint();
            game.allowedNumbers[0] = ficha.values[0];
            game.allowedNumbers[1] = ficha.values[1];
            suma -= (ficha.values[0] + ficha.values[1]);
            turno = false;
            nextPlayer.turno = true;
            finishGame();
            nextPlayer.manageTurn();
        } else if (real) {
            ArrayList<Ficha> disponibles = new ArrayList<>(tabs.stream().filter(x -> x.values[0] == game.allowedNumbers[0] || x.values[0] == game.allowedNumbers[1] || x.values[1] == game.allowedNumbers[0] || x.values[1] == game.allowedNumbers[1]).toList());
            if (disponibles.size() > 0) {
                disponibles.forEach(x -> {
                    x.image.setBorder(new BasicBorders.ButtonBorder(Color.green, Color.green, Color.green, Color.green));
                    x.allowed = true;
                });
                game.cleanCanPlay();
                new JOptionPane("ES TU TURNO").createDialog("MESSAGE").setVisible(true);
            } else {
                canPlay = false;
                turno = false;
                nextPlayer.turno = true;
                finishGame();
                nextPlayer.manageTurn();
            }
        } else {
            ArrayList<Ficha> disponibles = new ArrayList<>(tabs.stream().filter(x -> x.values[0] == game.allowedNumbers[0] || x.values[0] == game.allowedNumbers[1] || x.values[1] == game.allowedNumbers[0] || x.values[1] == game.allowedNumbers[1]).toList());
            if (disponibles.size() > 0) {
                ArrayList<Ficha> marranas = new ArrayList<>(disponibles.stream().filter(x -> x.marrana).toList());
                if (marranas.size() == 2) {
                    for (Ficha ficha : marranas) {
                        ficha.played = true;
                        if (game.allowedNumbers[1] == ficha.values[0] || game.allowedNumbers[1] == ficha.values[1]) {
                            int assigned = game.allowedNumbers[1] == ficha.values[0] ? 1 : 0;
                            if (game.board.get(game.board.size() - 1).assign == -2) {
                                game.board.get(game.board.size() - 1).assign = 1;
                            } else {
                                game.board.get(game.board.size() - 1).assign = -1;
                            }
                            game.board.get(game.board.size() - 1).image.setText(null);
                            game.board.get(game.board.size() - 1).image.setBorder(null);
                            game.board.get(game.board.size() - 1).selected = false;
                            ficha.assign = 0;
                            game.board.add(ficha);
                            game.gruping.remove(ficha.image);
                            tabs.remove(ficha);
                            game.repaint();
                        } else {
                            int assigned = game.allowedNumbers[0] == ficha.values[0] ? 1 : 0;
                            if (game.board.get(0).assign == -2) {
                                game.board.get(0).assign = 0;
                            } else {
                                game.board.get(0).assign = -1;
                            }
                            ficha.assign = 1;
                            game.board.add(0, ficha);
                            game.gruping.remove(ficha.image);
                            tabs.remove(ficha);
                            game.repaint();
                        }
                        suma -= (ficha.values[0] + ficha.values[0]);
                    }
                    turno = false;
                    nextPlayer.turno = true;
                    finishGame();
                    nextPlayer.manageTurn();
                } else {
                    Ficha ficha = disponibles.get((int) Math.floor(disponibles.size() * Math.random()));
                    ficha.played = true;
                    if (game.allowedNumbers[1] == ficha.values[0] || game.allowedNumbers[1] == ficha.values[1]) {
                        int assigned = game.allowedNumbers[1] == ficha.values[0] ? 1 : 0;
                        game.allowedNumbers[1] = ficha.values[assigned];
                        if (game.board.get(game.board.size() - 1).assign == -2) {
                            game.board.get(game.board.size() - 1).assign = 1;
                        } else {
                            game.board.get(game.board.size() - 1).assign = -1;
                        }
                        game.board.get(game.board.size() - 1).image.setText(null);
                        game.board.get(game.board.size() - 1).image.setBorder(null);
                        game.board.get(game.board.size() - 1).selected = false;
                        ficha.assign = 0;
                        ficha.direction = assigned;
                        game.board.add(ficha);
                        game.gruping.remove(ficha.image);
                        tabs.remove(ficha);
                        game.repaint();
                    } else {
                        int assigned = game.allowedNumbers[0] == ficha.values[0] ? 1 : 0;
                        game.allowedNumbers[0] = ficha.values[assigned];

                        if (game.board.get(0).assign == -2) {
                            game.board.get(0).assign = 0;
                        } else {
                            game.board.get(0).assign = -1;
                        }
                        ficha.assign = 1;
                        ficha.direction = Math.abs(assigned - 1);
                        game.board.add(0, ficha);
                        game.gruping.remove(ficha.image);
                        tabs.remove(ficha);
                        game.repaint();
                    }
                    suma -= (ficha.values[0] + ficha.values[0]);
                    game.cleanCanPlay();
                    turno = false;
                    nextPlayer.turno = true;
                    finishGame();
                    nextPlayer.manageTurn();
                }
            } else {
                canPlay = false;
                turno = false;
                nextPlayer.turno = true;
                finishGame();
                nextPlayer.manageTurn();
            }
        }
    }

    public void manageTurn() {
        manageTurn(false);
    }

    public void generate() {
        if (turno && (selecteds.size() == 2)) {
            for (Ficha ficha : selecteds) {
                ficha.played = true;
                if (game.allowedNumbers[1] == ficha.values[0] || game.allowedNumbers[1] == ficha.values[1]) {
                    if (game.board.get(game.board.size() - 1).assign == -2) {
                        game.board.get(game.board.size() - 1).assign = 1;
                    } else {
                        game.board.get(game.board.size() - 1).assign = -1;
                    }
                    game.board.get(game.board.size() - 1).image.setText(null);
                    game.board.get(game.board.size() - 1).image.setBorder(null);
                    game.board.get(game.board.size() - 1).selected = false;
                    ficha.assign = 0;
                    game.board.add(ficha);
                    game.gruping.remove(ficha.image);
                    tabs.remove(ficha);
                    game.repaint();
                } else {
                    if (game.board.get(0).assign == -2) {
                        game.board.get(0).assign = 0;
                    } else {
                        game.board.get(0).assign = -1;
                    }
                    game.board.get(0).image.setText(null);
                    game.board.get(0).image.setBorder(null);
                    game.board.get(0).selected = false;
                    ficha.assign = 1;
                    game.board.add(0, ficha);
                    game.gruping.remove(ficha.image);
                    tabs.remove(ficha);
                    game.repaint();
                }
                suma -= (ficha.values[0] + ficha.values[0]);
                ficha.selected = false;
                ficha.image.setText(null);
                ficha.image.setBorder(null);
            }
            tabs.forEach(x -> {
                x.allowed = false;
                x.image.setBorder(null);
            });
            turno = false;
            nextPlayer.turno = true;
            finishGame();
            nextPlayer.manageTurn();
            selecteds = new ArrayList<>();
            side = -1;
        } else if (turno && selecteds.size() > 0 && side != -1) {
            Ficha ficha = selecteds.get(0);
            ficha.played = true;
            if (side == 3) {
                if (game.allowedNumbers[1] == ficha.values[0] || game.allowedNumbers[1] == ficha.values[1]) {
                    int assigned = game.allowedNumbers[1] == ficha.values[0] ? 1 : 0;
                    game.allowedNumbers[1] = ficha.values[assigned];
                    if (game.board.get(game.board.size() - 1).assign == -2) {
                        game.board.get(game.board.size() - 1).assign = 1;
                    } else {
                        game.board.get(game.board.size() - 1).assign = -1;
                    }
                    game.board.get(game.board.size() - 1).image.setText(null);
                    game.board.get(game.board.size() - 1).image.setBorder(null);
                    game.board.get(game.board.size() - 1).selected = false;
                    ficha.assign = 0;
                    ficha.direction = assigned;
                    game.board.add(ficha);
                    game.gruping.remove(ficha.image);
                    tabs.remove(ficha);
                    game.repaint();
                } else {
                    int assigned = game.allowedNumbers[0] == ficha.values[0] ? 1 : 0;
                    game.allowedNumbers[0] = ficha.values[assigned];

                    if (game.board.get(0).assign == -2) {
                        game.board.get(0).assign = 0;
                    } else {
                        game.board.get(0).assign = -1;
                    }
                    ficha.assign = 1;
                    ficha.direction = Math.abs(assigned - 1);
                    game.board.add(0, ficha);
                    game.gruping.remove(ficha.image);
                    tabs.remove(ficha);
                    game.repaint();
                }
                suma -= (ficha.values[0] + ficha.values[0]);
                ficha.selected = false;
                ficha.image.setBorder(null);
                ficha.image.setText(null);
                selecteds = new ArrayList<>();
                side = -1;
                turno = false;
                nextPlayer.turno = true;
                finishGame();
                nextPlayer.manageTurn();
            } else {
                if (game.allowedNumbers[side] == ficha.values[0] || game.allowedNumbers[side] == ficha.values[1]) {
                    int assigned = game.allowedNumbers[side] == ficha.values[0] ? 1 : 0;
                    game.allowedNumbers[side] = ficha.values[assigned];
                    if (side == 1) {
                        ficha.assign = 0;
                        ficha.direction = assigned;
                        if (game.board.get(game.board.size() - 1).assign == -2) {
                            game.board.get(game.board.size() - 1).assign = 1;
                        } else {
                            game.board.get(game.board.size() - 1).assign = -1;
                        }
                        game.board.get(game.board.size() - 1).image.setText(null);
                        game.board.get(game.board.size() - 1).image.setBorder(null);
                        game.board.get(game.board.size() - 1).selected = false;
                        game.board.add(ficha);
                    } else {
                        ficha.assign = 1;
                        ficha.direction = Math.abs(assigned - 1);
                        if (game.board.get(0).assign == -2) {
                            game.board.get(0).assign = 0;
                        } else {
                            game.board.get(0).assign = -1;
                        }
                        game.board.get(0).image.setText(null);
                        game.board.get(0).image.setBorder(null);
                        game.board.get(0).selected = false;
                        game.board.add(0, ficha);
                    }
                    game.gruping.remove(ficha.image);
                    tabs.remove(ficha);
                    tabs.forEach(x -> {
                        x.allowed = false;
                        x.image.setBorder(null);
                    });
                    game.repaint();
                    turno = false;
                    ficha.selected = false;
                    ficha.image.setBorder(null);
                    ficha.image.setText(null);
                    selecteds = new ArrayList<>();
                    side = -1;
                    nextPlayer.turno = true;
                    finishGame();
                    nextPlayer.manageTurn();
                } else {
                    new JOptionPane("la ficha seleccionada no puede ir en el lado elegido").createDialog("MESSAGE").setVisible(true);
                }
            }
        }
    }

    void finishGame() {
        if (tabs.size() == 0) {
            new FinishMessage(resolveMessage(real)).setVisible(true);
        } else if (Arrays.stream(game.players).noneMatch(x -> x.canPlay)) {
            new FinishMessage(resolveMessage(Arrays.stream(game.players).sorted(Comparator.comparingInt(x -> x.suma)).toList().get(0).real)).setVisible(true);
        }
    }

    String resolveMessage(boolean realWinner) {
        return realWinner ? "FELICIDADES GANASTE" : "PERDISTE SUERTE PARA LA PROXIMA";
    }
}


