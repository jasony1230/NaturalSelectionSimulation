package org.BananasAmIRite.NaturalSelectionSimulation.api;

import org.BananasAmIRite.NaturalSelectionSimulation.Simulation;
import org.BananasAmIRite.NaturalSelectionSimulation.exceptions.ClassUnassignableException;
import org.BananasAmIRite.NaturalSelectionSimulation.exceptions.ConfigExceptions.ConfigNotFoundException;
import org.BananasAmIRite.NaturalSelectionSimulation.exceptions.ConfigExceptions.NoConfigEntryException;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Reads from config.yml and initializes main api class
 */
public class APIManager {

    private Map<String, Object> config;
    private String API_MAIN;
    private Simulation simulation;

    public APIManager(Simulation sim) {
        try {
            config = getConfigFile();
            API_MAIN = getEntry("MainClass", false).toString();
            this.simulation = sim;
        } catch (/*a LOT of exception*/Exception e) {
            e.printStackTrace();
        }
    }

    private Map<String, Object> getConfigFile() throws ConfigNotFoundException {
        InputStream str = getClass().getClassLoader().getResourceAsStream("config.yml");
        System.out.println(str);
        Yaml yaml = new Yaml();
        Map<String, Object> e = yaml.load(str);
        if (e == null) throw new ConfigNotFoundException("No config found. Please create a config.yml file compiled in the jar. ");
        return e;
    }

    private Object getEntry(String key, boolean isThrow) throws NoConfigEntryException, ConfigNotFoundException {
        if (config == null) throw new ConfigNotFoundException();
        if (config.get(key) == null && isThrow) throw new NoConfigEntryException("No config entry provided for entry, " + key + ", when entry is required. ");
        return config.get(key);
    }

    public void initMainClass() throws ClassNotFoundException, ClassUnassignableException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> c = Class.forName(API_MAIN);

        if (!NaturalSelection.class.isAssignableFrom(c))
            throw new ClassUnassignableException("The provided main class, " + c.toString() + ", must be a subclass of the class, " + NaturalSelection.class.toString() + "!");

//        c.getSuperclass().getConstructor(Simulation.class);

        Constructor<?> constructor = c.getConstructor(Simulation.class); // guaranteed existence

        constructor.newInstance(this.simulation);
    }
}