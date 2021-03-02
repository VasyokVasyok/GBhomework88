import java.sql.*;

public class SQLiteDB {
    private static SQLiteDB INSTANCE;
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement preparedStatement;
    private static ResultSet rs;

    public void createConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:C://sqlite/lesson8.db");
            statement = connection.createStatement();
            dropTable();
            createTable();
            System.out.println("База Подключена!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static SQLiteDB getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SQLiteDB();
        }
        return INSTANCE;
    }

    private void createTable() throws SQLException {
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS weather (id INTEGER NOT NULL UNIQUE PRIMARY KEY AUTOINCREMENT, City STRING NOT NULL, WeatherText STRING NOT NULL, LocalDate STRING NOT NULL, Temperature DOUBLE NOT NULL)");
    }

    private void dropTable() throws SQLException {
        statement.executeUpdate("DROP TABLE IF EXISTS weather");
    }

    public void createNewWeather(Weather weather) throws SQLException {
        statement.executeUpdate("INSERT INTO weather (City, WeatherText, LocalDate, Temperature) VALUES ('" + ApplicationGlobalState.getInstance().getSelectedCity() + "' , '" + weather.getText() + "','" + weather.getDate() + "'," + weather.getTemperature() + ");");
    }

    public String returnTemperature() throws SQLException {
        rs = statement.executeQuery("SELECT Temperature FROM weather WHERE City = '" + ApplicationGlobalState.getInstance().getSelectedCity() + "'");
        return rs.getString(1);
    }

    public String returnText() throws SQLException {
        rs = statement.executeQuery("SELECT WeatherText FROM weather WHERE City = '" + ApplicationGlobalState.getInstance().getSelectedCity() + "'");
        return rs.getString(1);
    }

    public String returnDate() throws SQLException {
        rs = statement.executeQuery("SELECT LocalDate FROM weather WHERE City = '" + ApplicationGlobalState.getInstance().getSelectedCity() + "'");
        return rs.getString(1);
    }

    public void closeCon() throws SQLException {
        rs.close();
        statement.close();
        connection.close();
    }
}