package ba.unsa.etf.rpr.projekat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Database {
    private Connection conn;
    private void regenerateDatabase() {
        Scanner input = null;
        try {
            input = new Scanner(new FileInputStream("projectDatabase.db.sql"));
            String sqlQuery = "";
            while (input.hasNext()) {

                sqlQuery += input.nextLine();
                if (sqlQuery.charAt(sqlQuery.length() - 1) == ';') {
                    try {

                        Statement stmnt = conn.createStatement();
                        stmnt.execute(sqlQuery);
                        sqlQuery = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        input.close();
    }
    public Database() {

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:projectDatabase.db");
        } catch (SQLException e) {

            e.printStackTrace();
        }
        try {

        } catch (Exception e) {
            regenerateDatabase();

        }

    }

}
