package org.example;

import org.example.resultset.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseQueryService {
    public static List<MaxProjectCountClient> findMaxProjectsClient() throws SQLException, IOException {
        List<MaxProjectCountClient> listMaxProjectClients = new ArrayList<>();
        Connection connService = Database.getInstance().getConnection();
        List<String> sqlStatements = Database.parseSQLScript("src/main/SQL/find_max_projects_client.sql", false);
        String sqlText = sqlStatements.getFirst();
        try (Statement stm = connService.createStatement()) {
            ResultSet resultSet = stm.executeQuery(sqlText);
            while (resultSet.next()) {
                MaxProjectCountClient maxProjectCountClient = new MaxProjectCountClient(
                        resultSet.getString("name")
                        , resultSet.getInt("projectCount")
                );
                listMaxProjectClients.add(maxProjectCountClient);
                MyFormat.logger.info(maxProjectCountClient.toString());
            }
            MyFormat.logger.info("");
        } catch (SQLException e) {
            throw new IllegalArgumentException(String.format("findMaxProjectsClient: %s", sqlText));
        }
        return listMaxProjectClients;
    }

    public static List<MaxSalaryWorker> findMaxSalaryWorker() throws SQLException, IOException {
        List<MaxSalaryWorker> listMaxSalaryWorker = new ArrayList<>();
        Connection connService = Database.getInstance().getConnection();
        List<String> sqlStatements = Database.parseSQLScript("src/main/SQL/find_max_salary_worker.sql", false);
        String sqlText = sqlStatements.getFirst();
        try (Statement stm = connService.createStatement()) {
            ResultSet resultSet = stm.executeQuery(sqlText);
            while (resultSet.next()) {
                MaxSalaryWorker maxSalaryWorker = new MaxSalaryWorker(
                        resultSet.getString("name")
                        , resultSet.getInt("salary")
                );
                listMaxSalaryWorker.add(maxSalaryWorker);
                MyFormat.logger.info(maxSalaryWorker.toString());
            }
            MyFormat.logger.info("");
        } catch (SQLException e) {
            throw new IllegalArgumentException(String.format("findMaxSalaryWorker: %s", sqlText));
        }
        return listMaxSalaryWorker;
    }

    public static List<LongestProject> findLongestProject() throws IOException, SQLException {
        List<LongestProject> listLongestProject = new ArrayList<>();
        Connection connService = Database.getInstance().getConnection();
        List<String> sqlStatements = Database.parseSQLScript("src/main/SQL/find_longest_project.sql", false);
        String sqlText = sqlStatements.getFirst();
        try (Statement stm = connService.createStatement()) {
            ResultSet resultSet = stm.executeQuery(sqlText);
            while (resultSet.next()) {
                LongestProject longestProject = new LongestProject(
                        resultSet.getString("name")
                        , resultSet.getInt("monthCount")
                );
                listLongestProject.add(longestProject);
                MyFormat.logger.info(longestProject.toString());
            }
            MyFormat.logger.info("");
        } catch (SQLException e) {
            throw new IllegalArgumentException(String.format("findLongestProject: %s", sqlText));
        }
        return listLongestProject;
    }
    public static List<YoungestEldestWorkers> findYoungestEldestWorkers() throws SQLException, IOException {
        List<YoungestEldestWorkers> listYoungestEldestWorkers = new ArrayList<>();
        Connection connService = Database.getInstance().getConnection();
        List<String> sqlStatements = Database.parseSQLScript("src/main/SQL/find_youngest_eldest_workers.sql", false);
        String sqlText = sqlStatements.getFirst();
        try (Statement stm = connService.createStatement()) {
            ResultSet resultSet = stm.executeQuery(sqlText);
            while (resultSet.next()) {
                YoungestEldestWorkers youngestEldestWorkers = new YoungestEldestWorkers(
                        resultSet.getString("type")
                        , resultSet.getString("name")
                        , resultSet.getDate("birthday")
                );
                listYoungestEldestWorkers.add(youngestEldestWorkers);
                MyFormat.logger.info(youngestEldestWorkers.toString());
            }
            MyFormat.logger.info("");
        } catch (SQLException e) {
            throw new IllegalArgumentException(String.format("findYoungestEldestWorkers: %s", sqlText));
        }
        return listYoungestEldestWorkers;
    }
    public static List<PrintProjectPrices> printProjectPrices() throws SQLException, IOException {
        List<PrintProjectPrices> listPrintProjectPrices = new ArrayList<>();
        Connection connService = Database.getInstance().getConnection();
        List<String> sqlStatements = Database.parseSQLScript("src/main/SQL/print_project_prices.sql", false);
        String sqlText = sqlStatements.getFirst();
        try (Statement stm = connService.createStatement()) {
            ResultSet resultSet = stm.executeQuery(sqlText);
            while (resultSet.next()) {
                PrintProjectPrices printProjectPrices = new PrintProjectPrices(
                        resultSet.getString("name")
                        , resultSet.getDouble("price")
                );
                listPrintProjectPrices.add(printProjectPrices);
                MyFormat.logger.info(printProjectPrices.toString());
            }
            MyFormat.logger.info("");
        } catch (SQLException e) {
            throw new IllegalArgumentException(String.format("printProjectPrices: %s", sqlText));
        }
        return listPrintProjectPrices;
    }

    public static void main(String[] args) throws SQLException, IOException {
        DatabaseQueryService.findMaxProjectsClient();
        DatabaseQueryService.findMaxSalaryWorker();
        DatabaseQueryService.findLongestProject();
        DatabaseQueryService.findYoungestEldestWorkers();
        DatabaseQueryService.printProjectPrices();
    }
}
