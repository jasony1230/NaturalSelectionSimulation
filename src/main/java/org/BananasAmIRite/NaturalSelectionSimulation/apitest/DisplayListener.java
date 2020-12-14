package org.BananasAmIRite.NaturalSelectionSimulation.apitest;

import org.BananasAmIRite.NaturalSelectionSimulation.Simulation;
import org.BananasAmIRite.NaturalSelectionSimulation.api.listenerapi.Listener;
import org.BananasAmIRite.NaturalSelectionSimulation.api.listenerapi.annotations.EventHandler;
import org.BananasAmIRite.NaturalSelectionSimulation.api.listenerapi.events.CreatureMoveEvent;
import org.BananasAmIRite.NaturalSelectionSimulation.api.listenerapi.events.SimulationUpdateEvent;
import org.BananasAmIRite.NaturalSelectionSimulation.objects.GenericArrayList;
import org.BananasAmIRite.NaturalSelectionSimulation.objects.Pair;
import org.BananasAmIRite.NaturalSelectionSimulation.objects.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

public class DisplayListener implements Listener {

    private JFrame frame;
    private GenericArrayList<GenericArrayList<Pair<Component, Tile>>> simulationComponentMapping; // used for relating the Tile to its corresponding JComponent
    private int x;
    private int y;
    private Simulation sim;
    private SimulationController simulationController;

    public DisplayListener(int x, int y, Simulation sim) {

        this.x = x;
        this.y = y;
        this.sim = sim;

        initFrame(x, y);


        this.simulationController = new SimulationController(sim);
    }

    private void initFrame(int x, int y) {
        frame = new JFrame();
        frame.setLayout(new GridLayout(x, y));

        // initiate simulation component mapping
        simulationComponentMapping = new GenericArrayList<>(y);

        // populate arrays
        for (int i = 0; i < y; i++) {
            simulationComponentMapping.add(new GenericArrayList<>());
        }

        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {

                Button button = new Button();

                button.addKeyListener(new ControllerKeyListener());

                simulationComponentMapping.get(i).put(j, new Pair<>(button, sim.getMap().getMap().get(i).get(j)));
                frame.add(button);
            }
        }

        frame.addKeyListener(new ControllerKeyListener());

        frame.setSize(400, 500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public Component getComponentAt(int x, int y) {
        if (x >= this.x || y >= this.y || x < 0 || y < 0) return null; // guaranteed not on map
        return simulationComponentMapping.get(y).get(x).getKey();
    }

    public Tile getTileAt(int x, int y) {
        if (x >= this.x || y >= this.y || x < 0 || y < 0) return null; // guaranteed not on map
        return simulationComponentMapping.get(y).get(x).getValue();
    }

    @EventHandler
    public void onMapUpdate(SimulationUpdateEvent e) {
        if (e instanceof CreatureMoveEvent) return; // leave for separate handler
        for (int i = 0; i < e.getMap().size(); i++) {
            List<Tile> tiles = e.getMap().get(i);
            for (int j = 0; j < tiles.size(); j++) {
                Tile tile = tiles.get(j);

//                System.out.println(tile);
//
//                System.out.println("COMPONENT");
//                System.out.println(getComponentAt(j, i));
//
                ((Button) getComponentAt(j, i)).setLabel(Tile.translateTileToString(tile));
            }
        }
    }

    @EventHandler
    public void onCreatureMove(CreatureMoveEvent e) {
        ((Button) getComponentAt(e.getFrom().getY(), e.getFrom().getX())).setLabel(Tile.translateTileToString(e.getMap().get(e.getFrom().getY()).get(e.getFrom().getX())));

        ((Button) getComponentAt(e.getTo().getY(), e.getTo().getX())).setLabel(Tile.translateTileToString(e.getMap().get(e.getTo().getY()).get(e.getTo().getX())));

    }


    private class ControllerKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == 'o') simulationController.getFrame().setVisible(true);
        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    public SimulationController getSimulationController() {
        return simulationController;
    }
}
