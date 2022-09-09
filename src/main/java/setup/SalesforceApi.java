package setup;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.force.api.ApiConfig;
import com.force.api.ApiSession;
import com.force.api.Auth;
import com.force.api.ForceApi;
import config.PropertiesManager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class SalesforceApi extends ForceApi{

    private static volatile SalesforceApi TZ_CONNECTION;
    private static volatile SalesforceApi ORG62_CONNECTION;

    public SalesforceApi(ApiConfig apiConfig) {
        super(apiConfig);
        ObjectMapper jsonMapper = apiConfig.getObjectMapper();
        jsonMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        jsonMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    public static SalesforceApi makeConnection(String org){
        ApiConfig apiConfig = new ApiConfig();
        apiConfig.setUsername(PropertiesManager.getConfig(org + "_USERNAME"));
        apiConfig.setPassword(PropertiesManager.getConfig(org + "_PASSWORD"));
        apiConfig.setLoginEndpoint(PropertiesManager.getConfig(org + "_ENDPOINT"));
        apiConfig.setApiVersionString(PropertiesManager.getConfig("SALESFORCE_API_VERSION"));
        return new SalesforceApi(apiConfig);
    }

    public static SalesforceApi getOrCreateInstance(String environment){
        if(environment.toLowerCase().contains("tz")){
            if(TZ_CONNECTION == null){
                synchronized (SalesforceApi.class){
                    if (TZ_CONNECTION == null){
                        TZ_CONNECTION = makeConnection(environment);
                    }
                }
            }
            return TZ_CONNECTION;
        }else{
            if(ORG62_CONNECTION == null){
                synchronized (SalesforceApi.class){
                    if (ORG62_CONNECTION == null){
                        ORG62_CONNECTION = makeConnection(environment);
                    }
                }
            }
            return ORG62_CONNECTION;
        }
    }

    public void executeApex(String apex){
        get("/tooling/executeAnonymous/?anonymousBody=" + URLEncoder.encode(apex, StandardCharsets.UTF_8));
    }
}
