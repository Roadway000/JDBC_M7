package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.example.MyFormat.logger;

public class Database {
    private static Database instance;
    public static Connection conn;

    private Database() {
        String url = ConnectReader.getConnectionPostgres();
        String user = ConnectReader.getUserPostgres();
        String psw = ConnectReader.getPswPostgres();
        try {
            conn = DriverManager.getConnection(url, user, psw);
        } catch (SQLException e) {
            MyFormat.logger.info("SQL exception. Can not create connect");
            throw new RuntimeException("Can not create connect");
        }
    }

    public static synchronized Database getInstance() throws SQLException {
        if ((instance == null) || (instance.getConnection().isClosed())) {
            instance = new Database();
        }
        return instance;
    }

    public static Connection getConnection() {
        return conn;
    }

    public static List<String> parseSQLScript(String scriptFilePath, boolean isLogging) throws IOException {
        List<String> sqlStatements = new ArrayList<>();
        final Pattern COMMENT_PATTERN = Pattern.compile("--.*|/\\*(.|[\\r\\n])*?\\*/");
        try (BufferedReader reader = new BufferedReader(new FileReader(scriptFilePath))) {
            StringBuilder currentStatement = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher commentMatcher = COMMENT_PATTERN.matcher(line);
                line = commentMatcher.replaceAll("");
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                currentStatement.append(line).append(" ");
                if (line.endsWith(";")) {
                    sqlStatements.add(currentStatement.toString());
                    if (isLogging) MyFormat.logger.info(currentStatement.toString());
                    currentStatement.setLength(0);
                }
            }
        } catch (IOException e) {
            throw e;
        }
        return sqlStatements;
    }

    static void executeBatches(Connection connection, List<String> sqlList, int batchSize) throws SQLException {
        int count = 0;
        Statement statement = null;
        connection.setAutoCommit(false);
        try {
            statement = connection.createStatement();
            for (String sql : sqlList) {
                statement.addBatch(sql);
                count++;
                if (count % batchSize == 0) {
                    logger.info("Executing batch");
                    statement.executeBatch();
                    statement.clearBatch();
                }
            }
            if (count % batchSize != 0) {
                statement.executeBatch();
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw new RuntimeException(e.getMessage());
        }
        finally {
            connection.setAutoCommit(true);
            if (statement != null) {
                statement.close();
            }
        }
    }
}
