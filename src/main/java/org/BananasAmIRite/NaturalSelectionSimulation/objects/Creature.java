package org.BananasAmIRite.NaturalSelectionSimulation.objects;

import org.BananasAmIRite.NaturalSelectionSimulation.Simulation;
import org.BananasAmIRite.NaturalSelectionSimulation.api.TraitsAPI.Traits;

public class Creature extends Entity {
    private final int id;
    private final Simulation simulation;
    private Coordinate home;
    private Coordinate location;
    private Traits traits;

    public Creature(Simulation sim, int id) throws IllegalAccessException, InstantiationException {
        this.id = id;
        this.simulation = sim;
        this.traits = new Traits();

        simulation.getCreaturesManager().registerCreature(this);
        simulation.getTraitManager().addToCreature(this);
        setHome(this.simulation.getCreaturesManager().generateHome());
    }

    public Coordinate getHome() {
        return home;
    }

    public void setHome(Coordinate home) {
        this.home = home;
    }

    public int getId() {
        return id;
    }

    public Coordinate getLocation() {
        return location;
    }

    public boolean setLocation(Coordinate location) {
        if (!simulation.getMap().changeCreatureLocation(this, this.location, location)) return false;
        this.location = location;

        return true;
    }

    public Traits getTraits() {
        return traits;
    }

    @Override
    public String toString() {
        return "Creature{" +
                "id=" + id +
                ", simulation=" + simulation +
                ", home=" + home +
                ", location=" + location +
                ", traits=" + traits +
                '}';
    }
}