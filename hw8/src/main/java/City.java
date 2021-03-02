import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class City implements Serializable {
    private String englishName;
    private String key;

    @JsonCreator
    public City(@JsonProperty("Key") String key, @JsonProperty("EnglishName") String englishName) {
        this.englishName = englishName;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String getEnglishName() {
        return englishName;
    }
}