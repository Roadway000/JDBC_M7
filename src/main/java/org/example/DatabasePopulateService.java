package org.example;

import org.example.resultset.Worker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabasePopulateService {
    public static void main(String[] args) throws Exception {
        executePreparedBatchedSQL();
    }
    public static void executePreparedBatchedSQL() throws Exception {
        final String Senior = "Senior";
        final String Middle = "Middle";
        final String Junior = "Junior";
        final String Trainee = "Trainee";
        String[][] workersStr = {
                {"Theodore Roosevelt","1965-01-01",Senior},{"Franklin D. Roosevelt","1979-04-29",Middle},
                {"Harry S. Truman","1969-05-30",Junior},{"John F. Kennedy","1963-07-31",Senior},
                {"Richard M. Nixon","1969-09-11",Trainee},{"Jimmy Carter","1974-11-08",Junior},
                {"Ronald Reagan","1977-12-07",Middle},{"George Bush","1981-01-06",Junior},
                {"Bill Clinton","1989-02-21",Trainee},{"George W. Bush","1993-03-22",Trainee},
                {"Barack Obama","1994-04-11",Senior},{"Donald J. Trump","1999-05-19",Junior},
                {"Joseph R. Biden","2001-06-30",Trainee}};

        String[][] workersSalary = {{"900",Trainee},{"1900",Junior},{"3500",Middle},{"4900",Senior}};

        String[] clientsStr = {"Boston Consulting Group", "ADOBE", "Instacart", "Uber", "WorkDay", "AT&T", "Informatica", "Trustwave", "IBM"};

        String[][] projectStr = {{"1","2016-08-02","2016-12-10"},{"2","2016-08-22","2017-03-30"},{"3","2016-09-21","2016-12-30"},
                                 {"4","2016-11-11","2017-05-19"},{"3","2017-01-10","2017-09-20"},{"4","2017-03-20","2017-11-28"},
                                 {"5","2017-06-08","2017-10-16"},{"3","2017-09-06","2017-12-15"},{"5","2017-12-15","2018-03-25"},
                                 {"2","2018-03-12","2018-08-20"},{"1","2018-05-25","2018-09-02"},{"3","2018-07-26","2018-09-03"},
                                 {"5","2018-09-13","2018-12-22"},{"6","2018-11-06","2019-06-14"},{"5","2019-01-04","2019-07-14"},
                                 {"3","2019-03-08","2019-06-16"},{"4","2019-05-16","2019-06-24"},{"7","2019-07-28","2019-11-05"},
                                 {"7","2019-07-28","2020-05-05"},{"8","2019-12-09","2020-03-18"},{"9","2020-02-07","2020-08-17"}};

        Integer[][] projectWorker = {{1,1},{1,2},{2,1},{3,2},{2,2},{3,3},{4,1},{4,4},{4,5},{5,6},{5,4},
                                     {5,1},{5,2},{6,3},{6,7},{6,5},{6,4},{7,6},{7,8},{7,1},{7,2},{8,3},
                                     {8,4},{9,5},{9,6},{8,9},{9,7},{9,8},{10,1},{10,2},{10,3},{11,4},
                                     {10,10},{11,5},{11,6},{12,7},{12,8},{12,9},{13,1},{13,3},{13,2},
                                     {13,11},{12,4},{13,10},{14,4},{14,5},{14,6},{14,12},{15,7},{15,8},
                                     {16,1},{16,2},{16,3},{17,4},{16,11},{17,9},{17,6},{18,5},{18,10},
                                     {18,9},{18,12},{18,13},{19,1},{19,4},{19,2},{19,3},{20,8},{20,7},
                                     {20,9},{21,5},{21,6},{21,11},{21,13}};

        List<Worker> workers = new ArrayList<>();
        final String WORKER_INSERT = "insert into worker (name, birthday, level) VALUES (?, ?, ?);";
        final String WORKER_UPDATE = "update worker set salary = ? where level = ?;";
        final String CLIENT_INSERT = "insert into client (name) values (?);";
        final String PROJECT_INSERT = "insert into project(client_id, start_date, finish_date) values (?,?,?);";
        final String PROJECT_WORKER_INSERT = "insert into project_worker(project_id, worker_id)  values (?,?);";

        PreparedStatement ps1=null, ps2=null, ps3=null, ps4=null, ps5=null;
        int id = 1;

        for (String[] s: workersStr) {
            workers.add(new Worker(id, s[0], java.sql.Date.valueOf(s[1]), s[2], 0));
            id++;
        }
        Connection connection = Database.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
                ps1 = connection.prepareStatement(WORKER_INSERT);
                ps2 = connection.prepareStatement(WORKER_UPDATE);
                ps3 = connection.prepareStatement(CLIENT_INSERT);
                ps4 = connection.prepareStatement(PROJECT_INSERT);
                ps5 = connection.prepareStatement(PROJECT_WORKER_INSERT);
            for (Worker w : workers) {
                ps1.setString(1, w.getName());
                ps1.setDate(2, java.sql.Date.valueOf(w.getBirthday().toString()));
                ps1.setString(3, w.getLevel());
                ps1.addBatch();
                MyFormat.logger.info(w.toString());
            }
            for (String[] s: workersSalary) {
                ps2.setInt(1, Integer.parseInt(s[0]));
                ps2.setString(2, s[1]);
                ps2.addBatch();
                MyFormat.logger.info(ps2.toString());
            }
            for (String s: clientsStr) {
                ps3.setString(1, s);
                ps3.addBatch();
                MyFormat.logger.info(ps3.toString());
            }
            for (String[] s: projectStr) {
                ps4.setInt(1, Integer.parseInt(s[0]));
                ps4.setDate(2, java.sql.Date.valueOf(s[1]));
                ps4.setDate(3, java.sql.Date.valueOf(s[2]));
                ps4.addBatch();
                MyFormat.logger.info(ps4.toString());
            }
            for (Integer[] i: projectWorker) {
                ps5.setInt(1, i[0]);
                ps5.setInt(2, i[1]);
                ps5.addBatch();
                MyFormat.logger.info(ps5.toString());
            }

            ps1.executeBatch();
            ps1.clearBatch();
            ps2.executeBatch();
            ps2.clearBatch();
            ps3.executeBatch();
            ps3.clearBatch();
            ps4.executeBatch();
            ps4.clearBatch();
            ps5.executeBatch();
            ps5.clearBatch();
            connection.commit();

        } catch(SQLException e) {
            System.out.println("Rollback transaction: " + e.getMessage());
            connection.rollback();
                throw e;
        } finally {
            connection.setAutoCommit(true);
            if (ps1 != null) { ps1.close(); }
            if (ps2 != null) { ps2.close(); }
            if (ps3 != null) { ps3.close(); }
            if (ps4 != null) { ps4.close(); }
            if (ps5 != null) { ps5.close(); }
            connection.close();
        }
    }
    public static void executeBatchedSQL(int batchSize) throws Exception {
        Connection databaseConnection = Database.getInstance().getConnection();
        List<String> sqlStatements = Database.parseSQLScript("src/main/SQL/populate_db.sql", true);
        Database.executeBatches(databaseConnection, sqlStatements, batchSize);
    }
}
