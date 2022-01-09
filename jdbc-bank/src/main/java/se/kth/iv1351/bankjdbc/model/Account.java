package se.kth.iv1351.bankjdbc.model;

/**
 * An account in the bank.
 */
public class Account implements AccountDTO {
    private int balance;
    private String holderName;
    private String acctNo;

    /**
     * Creates an account for the specified holder with the balance zero. The account
     * number is unspecified.
     *
     * @param holderName The account holder's holderName.
     * @param bankDB     The DAO used to store updates to the database.
     */
    public Account(String holderName) {
        this(null, holderName, 0);
    }

    /**
     * Creates an account for the specified holder with the specified balance. The
     * account number is unspecified.
     *
     * @param holderName The account holder's holderName.
     * @param balance    The initial balance.
     */
    public Account(String holderName, int balance) {
        this(null, holderName, balance);
    }

    /**
     * Creates an account for the specified holder with the specified balance and account
     * number.
     *
     * @param acctNo     The account number.
     * @param holderName The account holder's holderName.
     * @param balance    The initial balance.
     */
    public Account(String acctNo, String holderName, int balance) {
        this.acctNo = acctNo;
        this.holderName = holderName;
        this.balance = balance;
    }

    /**
     * @return The account number.
     */
    public String getAccountNo() {
        return acctNo;
    }

    /**
     * @return The balance.
     */
    public int getBalance() {
        return balance;
    }

    /**
     * @return The holder's name.
     */
    public String getHolderName() {
        return holderName;
    }

    /**
     * Deposits the specified amount.
     *
     * @param amount The amount to deposit.
     * @throws AccountException If the specified amount is negative, or if unable to
     *                          perform the update.
     */
    public void deposit(int amount) throws RejectedException {
        if (amount < 0) {
            throw new RejectedException("Tried to deposit negative value, illegal value: "
                                        + amount + ", account: " + this);
    }
        balance = balance + amount;
    }

    /**
     * Withdraws the specified amount.
     *
     * @param amount The amount to withdraw.
     * @throws AccountException If the specified amount is negative, if the amount
     *                          is larger than the balance, or if unable to perform
     *                          the update.
     */
    public void withdraw(int amount) throws RejectedException {
        if (amount < 0) {
            throw new RejectedException("Tried to withdraw negative value, illegal value: "
                                        + amount + ", account: " + this);
        }
        if (balance - amount < 0) {
            throw new RejectedException("Overdraft attempt, illegal value: " + amount
                                        + ", account: " + this);
        }
        balance = balance - amount;
    }

    /**
     * @return A string representation of all fields in this object.
     */
    @Override
    public String toString() {
        StringBuilder stringRepresentation = new StringBuilder();
        stringRepresentation.append("Account: [");
        stringRepresentation.append("account number: ");
        stringRepresentation.append(acctNo);
        stringRepresentation.append(", holder: ");
        stringRepresentation.append(holderName);
        stringRepresentation.append(", balance: ");
        stringRepresentation.append(balance);
        stringRepresentation.append("]");
        return stringRepresentation.toString();
    }
}
