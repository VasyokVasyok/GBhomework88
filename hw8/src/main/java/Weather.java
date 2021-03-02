import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather implements Serializable {
    private String date;
    private String text;
    private String temperature;

    public Weather() {

    }

    public Weather setDate(String date) {
        this.date = date;
        return this;
    }

    public Weather setText(String text) {
        this.text = text;
        return this;
    }

    public Weather setTemperature(String temperature) {
        this.temperature = temperature;
        return this;
    }

    public String getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public String getTemperature() {
        return temperature;
    }

    public void deleteInfo() {
        date = null;
        text = null;
        temperature = null;
    }
}
