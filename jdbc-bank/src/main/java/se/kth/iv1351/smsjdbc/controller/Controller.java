package se.kth.iv1351.smsjdbc.controller;

//import java.util.ArrayList;
import java.util.List;

import se.kth.iv1351.smsjdbc.integration.SchoolDAO;
import se.kth.iv1351.smsjdbc.integration.SchoolDBException;
//import se.kth.iv1351.smsjdbc.model.Instrument;
import se.kth.iv1351.smsjdbc.model.InstrumentDTO;
import se.kth.iv1351.smsjdbc.model.InstrumentException;
//import se.kth.iv1351.smsjdbc.model.RejectedException;

public class Controller {
    private final SchoolDAO schoolDb;

    public Controller() throws SchoolDBException {
        schoolDb = new SchoolDAO();
    }

    public List<? extends InstrumentDTO> getAllAvailableInstruments() throws InstrumentException {
        try {
            return schoolDb.findAvailableInstruments();
        } catch (Exception e) {
            throw new InstrumentException("Unable to list instruments.", e);
        }
    }

    public void rentInstrument(int studentId, int instrumentId) throws SchoolDBException{
        try {
            schoolDb.rent(studentId, instrumentId);
        } catch (Exception e) {
            throw new SchoolDBException("Unable to rent instrument.", e);
        }
    }

    public void terminateInstrumentRental(int instrumentId) throws SchoolDBException{
        try {
            schoolDb.terminateRental(instrumentId);
        } catch (Exception e) {
            throw new SchoolDBException("Unable to terminate rental.", e);
        }
    }

    /*
    private void commitOngoingTransaction(String failureMsg) throws InstrumentException {
        try {
            schoolDb.commit();
        } catch (SchoolDBException bdbe) {
            throw new InstrumentException(failureMsg, bdbe);
        }
    }
    */
}
