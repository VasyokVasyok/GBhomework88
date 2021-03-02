import enums.Functionality;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Controller {
    AccuWeatherProvider weatherProvider = new AccuWeatherProvider();
    SQLiteDB sqLiteDB = new SQLiteDB();
    Map<Integer, Functionality> variantResult = new HashMap();

    public Controller() {
        variantResult.put(1, Functionality.GET_WEATHER);
    }

    public void onUserInput(String input) throws IOException, SQLException {
        sqLiteDB.createConnection();
        int command = Integer.parseInt(input);
        if (!variantResult.containsKey(command)) {
            throw new IOException("Такой команды нет в системе! " + command);
        }
        switch (variantResult.get(command)) {
            case GET_WEATHER:
                weatherProvider.getWeather();
        }
    }
}
