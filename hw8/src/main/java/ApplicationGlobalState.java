public class ApplicationGlobalState {
    private static ApplicationGlobalState INSTANCE;
    private String selectedCity;
    public static final String API_KEY = "d6cuOVK7EzADGTiBYI4wz7x1ZitXRFyq";

    private static final String BASE_HOST = "dataservice.accuweather.com";
    private static final String LOCATIONS = "locations";
    private static final String TOP_CITIES = "topcities";
    private static final String API_VERSION = "v1";
    private static final String SCHEME = "http";
    private static final String CITIES_NUMBER = "50";

    ApplicationGlobalState() {

    }

    public static ApplicationGlobalState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ApplicationGlobalState();
        }
        return INSTANCE;
    }

    public String getSelectedCity() {
        return selectedCity;
    }

    public String getBaseHost() {
        return BASE_HOST;
    }

    public String getLocations() {
        return LOCATIONS;
    }

    public String getTopCities() {
        return TOP_CITIES;
    }

    public String getApiVersion() {
        return API_VERSION;
    }

    public String getScheme() {
        return SCHEME;
    }

    public String getCitiesNumber() {
        return CITIES_NUMBER;
    }

    public void setSelectedCity(String selectedCity) {
        this.selectedCity = selectedCity;
    }

    public String getApiKey() {
        return this.API_KEY;
    }
}
