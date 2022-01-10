# A JDBC application with an appropriately layered architecture

This is an example of how an integration layer can be used to organize an application containing database calls.

## How to execute

1. Clone this git repository
1. Change to the newly created directory `cd jdbc-bank`
1. Make sure there is a database which can be reached with the url on line 46-50 in `SchoolDAO.java`. There are two ways to do this.
   1. Create a database that can be reached with one of the existing urls. If MySQL is used, that is a database called
      sms, which can be reached on port 3306 at localhost, by the user
      'root' with the password 'javajava'.
   1. Change the url to match your database.
1. Create the tables described by `createDatabase.sql` if yo use mysql.
2. Insert data described by `insert_data.sql` if yo use mysql.
3. Build the project with the command `mvn install`
4. Run the program with the command `mvn exec:java`

## Commands for the school program

* `help` displays all commands.
* `list` lists all instruments that are available for rental.
* `rent <student_id> <instrument_id>` rents specified instrument to specified student.
* `terminate <instrument_id>` terminates rental of specified instrument.
* `quit` quits the application.
