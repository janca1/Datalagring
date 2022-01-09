package se.kth.iv1351.smsjdbc.startup;

import se.kth.iv1351.smsjdbc.controller.Controller;
import se.kth.iv1351.smsjdbc.integration.SchoolDBException;
import se.kth.iv1351.smsjdbc.view.BlockingInterpreter;

/**
 * Starts the school client.
 */
public class Main {
    /**
     * @param args There are no command line arguments.
     */
    public static void main(String[] args) {
        try {
        new BlockingInterpreter(new Controller()).handleCmds();
        } catch(SchoolDBException bdbe) {
            System.out.println("Could not connect to School db.");
            bdbe.printStackTrace();
        }
    }
}
