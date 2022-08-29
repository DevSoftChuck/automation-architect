package setup;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.force.api.ApiConfig;
import com.force.api.ApiSession;
import com.force.api.Auth;
import com.force.api.ForceApi;
import config.PropertiesManager;

public class SalesforceApi extends ForceApi{

    private final ObjectMapper jsonMapper;
    private ApiConfig config;
    private ApiSession session;

    public SalesforceApi(ApiConfig apiConfig) {
        super(apiConfig);
        this.config = apiConfig;
        this.jsonMapper = config.getObjectMapper();
        this.jsonMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        this.jsonMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        this.session = Auth.authenticate(apiConfig);
    }

    public static SalesforceApi makeConnection(String org){
        ApiConfig apiConfig = new ApiConfig();
        SalesforceApi session;
        apiConfig.setUsername(PropertiesManager.getConfig(org + "_USERNAME"));
        apiConfig.setPassword(PropertiesManager.getConfig(org + "_PASSWORD"));
        apiConfig.setLoginEndpoint(PropertiesManager.getConfig(org + "_ENDPOINT"));
        apiConfig.setApiVersionString(PropertiesManager.getConfig("SALESFORCE_API_VERSION"));

        try{
            session = new SalesforceApi(apiConfig);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        return session;
    }
}
