package org.BananasAmIRite.NaturalSelectionSimulation.apitest;

import org.BananasAmIRite.NaturalSelectionSimulation.Simulation;
import org.BananasAmIRite.NaturalSelectionSimulation.api.listenerapi.Listener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationController implements Listener {

    private JFrame frame;
    private Simulation sim;

    public SimulationController(Simulation sim) {

        this.sim = sim;

        initFrame();
    }

    private void initFrame() {
        frame = new JFrame();

        frame.setTitle("Simulation Controller");

        // pause all button
        JButton pauseBtn = new JButton("Pause All");
        pauseBtn.addActionListener(new ButtonPauseListener());
        frame.add(pauseBtn);

        // play all button
        JButton playBtn = new JButton("Play All");
        playBtn.addActionListener(new ButtonPlayListener());
        frame.add(playBtn);

        // play specific button
        JTextField playSpecField = new JTextField("Play Specific Creature");
        JButton playSpecBtn = new JButton("Play Specific");
        frame.add(playSpecField);
        frame.add(playSpecBtn);
        playSpecBtn.addActionListener(new ButtonPlaySpecificListener(playSpecField));

        // pause specific button
        JTextField pauseSpecField = new JTextField("Pause Specific Creature");
        JButton pauseSpecBtn = new JButton("Pause Specific");
        frame.add(pauseSpecField);
        frame.add(pauseSpecBtn);
        pauseSpecBtn.addActionListener(new ButtonPauseSpecificListener(pauseSpecField));

//        // kill creature btn
//        JTextField killSpecField = new JTextField();
//        killSpecField.setMinimumSize(new Dimension(100, 10));
//        JButton killSpecBtn = new JButton("Kill Specific");
//        frame.add(killSpecField);
//        frame.add(killSpecBtn);
//        pauseSpecBtn.addActionListener(new CreatureKillListener(killSpecField));


        frame.setSize(400, 200);
        frame.setVisible(true);
        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    private class ButtonPauseListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            sim.getCreaturesManager().pauseAll();
        }
    }

    private class ButtonPlayListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            sim.getCreaturesManager().playAll();
        }
    }

    private class ButtonPlaySpecificListener implements ActionListener {

        private JTextField field;

        public ButtonPlaySpecificListener(JTextField field) {
            this.field = field;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int test = Integer.parseInt(field.getText());
            } catch (NumberFormatException ignored) {
                return;
            }

            int index = Integer.parseInt(field.getText());

            if (sim.getCreaturesManager().getCreature(index) == null) return;

            sim.getCreaturesManager().getCreature(index).play();
        }
    }

    private class ButtonPauseSpecificListener implements ActionListener {

        private JTextField field;

        public ButtonPauseSpecificListener(JTextField field) {
            this.field = field;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int test = Integer.parseInt(field.getText());
            } catch (NumberFormatException ignored) {
                return;
            }

            int index = Integer.parseInt(field.getText());

            if (sim.getCreaturesManager().getCreature(index) == null) return;

            sim.getCreaturesManager().getCreature(index).pause();
        }
    }

    private class CreatureKillListener implements ActionListener {

        private JTextField field;

        public CreatureKillListener(JTextField field) {
            this.field = field;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int test = Integer.parseInt(field.getText());
            } catch (NumberFormatException ignored) {
                return;
            }

            int index = Integer.parseInt(field.getText());

            if (sim.getCreaturesManager().getCreature(index) == null) return;

            System.out.println("killing...");

            sim.getCreaturesManager().getCreature(index).setDead(true);
            sim.getCreaturesManager().getCreature(index).removeFromMap();
        }
    }

    public JFrame getFrame() {
        return frame;
    }
}
