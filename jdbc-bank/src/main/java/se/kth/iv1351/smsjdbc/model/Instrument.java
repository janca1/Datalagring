package se.kth.iv1351.smsjdbc.model;

public class Instrument implements InstrumentDTO{
    /*Id
    type
    brand
    rental_fee
    student_id() [if null, can be rented]

    LIST
    RENT
    TERMINATE
    */

    private int instrumentId;
    private String type;
    private String brand;
    private int rentalFee;
    private int studentId;

    public Instrument(int instrumentId, String type, String brand, int rentalFee, int studentId) {
        this.instrumentId = instrumentId;
        this.type = type;
        this.brand = brand;
        this.rentalFee = rentalFee;
        this.studentId = studentId;
    }

    public String getType(){
        return type;
    }

    public String getBrand(){
        return brand;
    }

    public int getInstrumentId(){
        return instrumentId;
    }

    public int getStudentId(){
        return studentId;
    }

    public int getRentalFee(){
        return rentalFee;
    }

        /**
     * @return A string representation of all fields in this object.
     */
    @Override
    public String toString() {
        StringBuilder stringRepresentation = new StringBuilder();
        stringRepresentation.append("Instrument: [");
        stringRepresentation.append("id: ");
        stringRepresentation.append(instrumentId);
        stringRepresentation.append(", type: ");
        stringRepresentation.append(type);
        stringRepresentation.append(", brand: ");
        stringRepresentation.append(brand);
        stringRepresentation.append(", rental fee: ");
        stringRepresentation.append(rentalFee);
        stringRepresentation.append(",rented to student with id : ");
        stringRepresentation.append(studentId);
        stringRepresentation.append("]");
        return stringRepresentation.toString();
    }
}