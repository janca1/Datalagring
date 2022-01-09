
package se.kth.iv1351.smsjdbc.view;

import java.util.List;
import java.util.Scanner;

import se.kth.iv1351.smsjdbc.controller.Controller;
import se.kth.iv1351.smsjdbc.model.InstrumentDTO;

public class BlockingInterpreter {
    private static final String PROMPT = "> ";
    private final Scanner console = new Scanner(System.in);
    private Controller ctrl;
    private boolean keepReceivingCmds = false;

    public BlockingInterpreter(Controller ctrl) {
        this.ctrl = ctrl;
    }

    public void stop() {
        keepReceivingCmds = false;
    }

    public void handleCmds() {
        keepReceivingCmds = true;
        while (keepReceivingCmds) {
            try {
                CmdLine cmdLine = new CmdLine(readNextLine());
                switch (cmdLine.getCmd()) {

                    case LIST:
                    List<? extends InstrumentDTO> instruments = null;
                    instruments = ctrl.getAllAvailableInstruments();

                    for (InstrumentDTO instrument : instruments) {
                        System.out.println("instrument id: " + instrument.getInstrumentId() + ", "
                                         + "type: " + instrument.getType() + ", "
                                         + "brand: " + instrument.getBrand() + ", "
                                         + "rental fee: " + instrument.getRentalFee());
                    }
                    break;

                    case RENT:
                    ctrl.rentInstrument(Integer.parseInt(cmdLine.getParameter(0)), 
                    Integer.parseInt(cmdLine.getParameter(1)));
                    break;

                    case TERMINATE:
                    ctrl.terminateInstrumentRental(Integer.parseInt(cmdLine.getParameter(0))); 
                    break;

                    case QUIT:
                    keepReceivingCmds = false;
                    break;

                    case HELP:
                    for (Command command : Command.values()) {
                        if (command == Command.ILLEGAL_COMMAND) {
                            continue;
                        }
                        System.out.println(command.toString().toLowerCase());
                    }
                    break;

                    default:
                        System.out.println("illegal command");
                }
            } catch (Exception e) {
                System.out.println("Operation failed");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private String readNextLine() {
        System.out.print(PROMPT);
        return console.nextLine();
    }
}
