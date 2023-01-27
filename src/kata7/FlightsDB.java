package kata7;

import com.google.gson.Gson;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import kata7.Flight;

public class FlightsDB {

    private static String databaseURL = "jdbc:sqlite:flights.db";
    
    public String selectAllFlights() {
        String sql = "SELECT * FROM flights";
        return getDataFromSql(sql);
    }

    public String selectFlightsFromDayOfWeek(String day) {
        String sql = "SELECT * FROM flights WHERE DAY_OF_WEEK=" + getNumberOfDay(day);
        return getDataFromSql(sql);
    }

    public String selectFlightsWithBiggerDistance(String distance) {
        String sql = "SELECT * FROM flights WHERE DISTANCE>=" + distance;
        return getDataFromSql(sql);
    }
    
    public String selectFlightsWithLowerDistance(String distance) {
        String sql = "SELECT * FROM flights WHERE DISTANCE<=" + distance;
        return getDataFromSql(sql);
    }
    
    public String selectCancelled() {
        String sql = "SELECT * FROM flights WHERE CANCELLED=1";
        return getDataFromSql(sql);
    }
    public String selectDiverted() {
        String sql = "SELECT * FROM flights WHERE DIVERTED=1";
        return getDataFromSql(sql);
    }
    
    private List<Flight> resultSetToList(ResultSet rs) throws SQLException {
        List<Flight> flights = new ArrayList<>();
        
        while (rs.next()) {
            flights.add(new Flight(
                rs.getInt(1),
                rs.getInt(2),
                rs.getInt(3),
                rs.getInt(4),
                rs.getInt(5),
                rs.getInt(6),
                rs.getInt(7),
                rs.getBoolean(8),
                rs.getBoolean(9))
            );
        }
        
        return flights;
    }
    
    private String getNumberOfDay(String day) {
        switch(day) {
            case "monday": return "1";
            case "tuesday": return "2";
            case "wednesday": return "3";
            case "thusrday": return "4";
            case "friday": return "5";
            case "saturday": return "6";
            case "sunday": return "7";
        }
        return "-1";
    }
    
    private String serializeList(List<Flight> flights) {
        return new Gson().toJson(flights);
    }
    
    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(databaseURL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
        
    private String getDataFromSql(String sql) {
        List<Flight> flights = new ArrayList<>();
        
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            flights = resultSetToList(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return serializeList(flights);
    }
}
