import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AccuWeatherProvider {

    public void showResult() throws SQLException {
        System.out.println("В городе " + ApplicationGlobalState.getInstance().getSelectedCity() + " на дату " + SQLiteDB.getInstance().returnDate() +
                " ожидается " + SQLiteDB.getInstance().returnText() + ", температура - " + SQLiteDB.getInstance().returnTemperature() + ".");
        SQLiteDB.getInstance().closeCon();
    }

    public void getWeather() throws IOException, SQLException {

        OkHttpClient client = new OkHttpClient();
        HttpUrl urlForCity = new HttpUrl.Builder()
                .scheme(ApplicationGlobalState.getInstance().getScheme())
                .host(ApplicationGlobalState.getInstance().getBaseHost())
                .addPathSegment(ApplicationGlobalState.getInstance().getLocations())
                .addPathSegment(ApplicationGlobalState.getInstance().getApiVersion())
                .addPathSegment(ApplicationGlobalState.getInstance().getTopCities())
                .addPathSegment(ApplicationGlobalState.getInstance().getCitiesNumber())
                .addQueryParameter("apikey", ApplicationGlobalState.getInstance().getApiKey())
                .addQueryParameter("language", "ru-ru")
                .addQueryParameter("details", "false")
                .build();

        Request requestForCity = new Request.Builder()
                .addHeader("accept", "application/json")
                .url(urlForCity)
                .build();

        String jsonResponse = client.newCall(requestForCity).execute().body().string();
        ObjectMapper objectMapper = new ObjectMapper();
        List<City> cityList = objectMapper.readValue(jsonResponse, new TypeReference<List<City>>() {
        });
        String numberOfCity = "null";
        for (int i = 0; i < cityList.size(); i++) {
            if (cityList.get(i).getEnglishName().equals(ApplicationGlobalState.getInstance().getSelectedCity())) {
                numberOfCity = cityList.get(i).getKey();
            }
        }

        HttpUrl urlWeather = new HttpUrl.Builder()
                .scheme(ApplicationGlobalState.getInstance().getScheme())
                .host(ApplicationGlobalState.getInstance().getBaseHost())
                .addPathSegment("forecasts")
                .addPathSegment(ApplicationGlobalState.getInstance().getApiVersion())
                .addPathSegment("daily")
                .addPathSegment("5day")
                .addPathSegment(numberOfCity)
                .addQueryParameter("apikey", ApplicationGlobalState.getInstance().getApiKey())
                .addQueryParameter("language", "ru-ru")
                .addQueryParameter("details", "false")
                .addQueryParameter("metric", "false")
                .build();

        Request requestWeather = new Request.Builder()
                .addHeader("accept", "application/json")
                .url(urlWeather)
                .build();

        String jsonResponseW = client.newCall(requestWeather).execute().body().string();
        Weather weather = new Weather();
        JsonNode dates = objectMapper.readTree(jsonResponseW).at("/Headline/EffectiveDate");
        weather.setDate(dates.toString());

        JsonNode text = objectMapper.readTree(jsonResponseW).at("/Headline/Text");
        weather.setText(text.toString());

        JsonNode temperature = objectMapper.readTree(jsonResponseW).at("/Headline/Severity");
        weather.setTemperature(temperature.toString());

        SQLiteDB.getInstance().createNewWeather(weather);
        showResult();
    }
}
