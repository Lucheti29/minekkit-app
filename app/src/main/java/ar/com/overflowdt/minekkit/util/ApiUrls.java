package ar.com.overflowdt.minekkit.util;

/**
 * Created by Fede on 26/12/2014.
 */
public class ApiUrls {
    private static final String API_URL = "http://minekkit.com/api/";
    private static ApiUrls _instance;

    public static ApiUrls getInstance() {
        if (_instance == null)
            _instance = new ApiUrls();
        return _instance;
    }

    public String getNewsURL() {
        return API_URL + "news.php";

    }

    public String getProfileURL() {
        return API_URL + "profile.php";
    }
}
