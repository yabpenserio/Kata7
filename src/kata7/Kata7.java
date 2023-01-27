package kata7;

import static spark.Spark.*;
import java.net.MalformedURLException;
import kata7.FlightsDB;

public class Kata7 {

    public static void main(String[] args) throws MalformedURLException {
        FlightsDB flightDB = new FlightsDB();
        
        port(8080);
        
        get("/flights", (req, res) -> "hello world");
        
        get("/flights/all", (req, res) -> {
            return flightDB.selectAllFlights();
        });
        
        get("/flights/day/:day", (req, res) -> {
            return flightDB.selectFlightsFromDayOfWeek(req.params(":day"));
        });
        
        get("/flights/distance/bigger/:distance", (req, res) -> {
            return flightDB.selectFlightsWithBiggerDistance(req.params(":distance"));
        });
        
        get("/flights/distance/lower/:distance", (req, res) -> {
            return flightDB.selectFlightsWithLowerDistance(req.params(":distance"));
        });
        
        get("/flights/cancelled", (req, res) -> {
            return flightDB.selectCancelled();
        });
        
        get("/flights/diverted", (req, res) -> {
            return flightDB.selectDiverted();
        });
    }
}
