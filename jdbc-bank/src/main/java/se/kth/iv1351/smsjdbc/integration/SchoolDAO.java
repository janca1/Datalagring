package se.kth.iv1351.smsjdbc.integration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import se.kth.iv1351.smsjdbc.model.Instrument;
//import se.kth.iv1351.smsjdbc.model.InstrumentDTO;

public class SchoolDAO {

    private static final String INSTRUMENT_TABLE_NAME = "instrument";
    private static final String INSTRUMENT_ID_COLUMN_NAME = "id";
    private static final String INSTRUMENT_TYPE_COLUMN_NAME = "type";
    private static final String INSTRUMENT_BRAND_COLUMN_NAME = "brand";
    private static final String INSTRUMENT_RENTAL_FEE_COLUMN_NAME = "rental_fee";
    private static final String STUDENT_ID_COLUMN_NAME = "student_id";
    private static final String STUDENT_TABLE_NAME = "student";
    private static final String RENTAL_TABLE_NAME = "rental";
    private static final String RENTAL_STATUS_COLUMN_NAME = "rental_status";

    private Connection connection;

    private PreparedStatement findAvailableInstrumentsStmt;
    private PreparedStatement numberOfRentedInstrumentsStmt;
    private PreparedStatement studentExistsStmt;
    private PreparedStatement instrumentExistsStmt;
    private PreparedStatement instrumentRentedStmt;
    private PreparedStatement rentStmt;
    private PreparedStatement updateRentalHistoryStmt;
    private PreparedStatement updateRentalStatusStmt;

    public SchoolDAO() throws SchoolDBException {
        try {
            connectToSchoolDB();
            prepareStatements();
        } catch (ClassNotFoundException | SQLException exception) {
            throw new SchoolDBException("Could not connect to datasource.", exception);
        }
    }

    private void connectToSchoolDB() throws ClassNotFoundException, SQLException {
          connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms",
                                                 "root", "Sifra123!");
         connection.setAutoCommit(false);
     }

    public List<Instrument> findAvailableInstruments() throws SchoolDBException {
        String failureMsg = "Could not list available instruments.";
        List<Instrument> instruments = new ArrayList<>();
        try (ResultSet result = findAvailableInstrumentsStmt.executeQuery()) {
            while (result.next()) {
                instruments.add(new Instrument(result.getInt(INSTRUMENT_ID_COLUMN_NAME),
                                             result.getString(INSTRUMENT_TYPE_COLUMN_NAME),
                                             result.getString(INSTRUMENT_BRAND_COLUMN_NAME),
                                             result.getInt(INSTRUMENT_RENTAL_FEE_COLUMN_NAME),
                                             result.getInt(STUDENT_ID_COLUMN_NAME)));
            }
            connection.commit();
        } catch (SQLException sqle) {
            handleException(failureMsg, sqle);
        }
        return instruments;
    }

    public void rent(int studentId, int instrumentId) throws SchoolDBException{
        String failureMsg = "Could not rent the instrument.";
        int updatedRows = 0;
        try{
            if(numberOfRentedInstuments(studentId)==2)
                handleException("Student has reach the rental limit of 2 instruments!", null);
            
            if(!studentIdExists(studentId))
                 handleException("Non existing student id", null);

            if(!instrumentIdExists(instrumentId))
                 handleException("Non existing instrument id", null);

            if(instrumentRented(instrumentId))
                handleException("Instrument is already rented", null);

            rentStmt.setInt(1, studentId);
            rentStmt.setInt(2, instrumentId);

            updatedRows = rentStmt.executeUpdate();
            if (updatedRows != 1) {
                handleException(failureMsg, null);
            }

           updateRentalHistoryStmt.setString(1, "ongoing");
           updateRentalHistoryStmt.setInt(2, studentId);
           updateRentalHistoryStmt.setInt(3, instrumentId);

           updatedRows = updateRentalHistoryStmt.executeUpdate();
           if (updatedRows != 1) {
               handleException(failureMsg, null);
           }

            connection.commit();
            System.out.println("You have successfully rented the instrument!");
        }

        catch(SQLException sqle){
            handleException(failureMsg, sqle);
        }
    }

    public void terminateRental(int instrumentId) throws SchoolDBException{
        String failureMsg = "Could not terminate rental.";
        int updatedRows = 0;

        try{
            // if not rented
            if(!instrumentRented(instrumentId))
            handleException("Instrument is not rented", null);

            //update instrument table
            rentStmt.setNull(1, java.sql.Types.INTEGER);
            rentStmt.setInt(2, instrumentId);

            updatedRows = rentStmt.executeUpdate();
            if (updatedRows != 1) {
                handleException(failureMsg, null);
            }

            //update rental table
            updateRentalStatusStmt.setInt(1, instrumentId);

            updatedRows = updateRentalStatusStmt.executeUpdate();
            if (updatedRows != 1) {
               //failureMsg=String.valueOf(updatedRows);
                handleException(failureMsg, null);
            }

            connection.commit();
            System.out.println("You have successfully terminated the rental!");
        }

        catch(SQLException sqle){
            handleException(failureMsg, sqle);
        }
    }

    private int numberOfRentedInstuments(int studentId) throws SQLException{
        numberOfRentedInstrumentsStmt.setInt(1, studentId);
        ResultSet result = numberOfRentedInstrumentsStmt.executeQuery();
        result.next();
        return result.getInt(1);
    }

    private boolean studentIdExists(int studentId) throws SQLException{
        studentExistsStmt.setInt(1, studentId);
        ResultSet result = studentExistsStmt.executeQuery();
        result.next();
        return result.getInt(1)==1;
    }

    private boolean instrumentIdExists(int instrumentId) throws SQLException{
        instrumentExistsStmt.setInt(1, instrumentId);
        ResultSet result = instrumentExistsStmt.executeQuery();
        result.next();
        return result.getInt(1)==1;
    }

    private boolean instrumentRented(int instrumentId) throws SQLException{
        instrumentRentedStmt.setInt(1, instrumentId);
        ResultSet result = instrumentRentedStmt.executeQuery();
        result.next();
        return result.getInt(1)==1;
    }

     private void prepareStatements() throws SQLException {
        findAvailableInstrumentsStmt = connection.prepareStatement("SELECT * FROM " + INSTRUMENT_TABLE_NAME + " WHERE " + STUDENT_ID_COLUMN_NAME +" IS NULL;");

        numberOfRentedInstrumentsStmt = connection.prepareStatement("SELECT COUNT(id) FROM " +  INSTRUMENT_TABLE_NAME + " WHERE " + STUDENT_ID_COLUMN_NAME + " = ?");

        studentExistsStmt = connection.prepareStatement("SELECT COUNT(*) FROM " + STUDENT_TABLE_NAME + " WHERE id" + " = ?");
        
        instrumentExistsStmt = connection.prepareStatement("SELECT COUNT(*) FROM " + INSTRUMENT_TABLE_NAME + " WHERE " + INSTRUMENT_ID_COLUMN_NAME + " = ?");
        
        instrumentRentedStmt = connection.prepareStatement("SELECT COUNT(*) FROM " + INSTRUMENT_TABLE_NAME + " WHERE " + INSTRUMENT_ID_COLUMN_NAME + " = ?" + " AND " + STUDENT_ID_COLUMN_NAME + " IS NOT NULL");
        
        rentStmt = connection.prepareStatement("UPDATE " + INSTRUMENT_TABLE_NAME + " SET " + STUDENT_ID_COLUMN_NAME + " = ? WHERE " + INSTRUMENT_ID_COLUMN_NAME + " = ?");

        updateRentalHistoryStmt = connection.prepareStatement("INSERT INTO " + RENTAL_TABLE_NAME + " (rental_status, student_id, instrument_id) VALUES (?, ?, ?)");

        updateRentalStatusStmt = connection.prepareStatement("UPDATE " + RENTAL_TABLE_NAME + " SET " + RENTAL_STATUS_COLUMN_NAME + "='terminated' WHERE instrument_id" + " = ? AND rental_status='ongoing'");
    }

    private void handleException(String failureMsg, Exception cause) throws SchoolDBException {
        String completeFailureMsg = failureMsg;
        try {
            connection.rollback();
        } catch (SQLException rollbackExc) {
            completeFailureMsg = completeFailureMsg + 
            ". Also failed to rollback transaction because of: " + rollbackExc.getMessage();
        }

        if (cause != null) {
            throw new SchoolDBException(failureMsg, cause);
        } else {
            throw new SchoolDBException(failureMsg);
        }
    }

    public void commit() throws SchoolDBException {
        try {
            connection.commit();
        } catch (SQLException e) {
            handleException("Failed to commit", e);
        }
    }
}
