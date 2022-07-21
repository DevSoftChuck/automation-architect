package Utils;

import java.net.URI;

public class ScratchOrgUserData {

    private final String userName;
    private final String password;
    private final String scratchOrgId;
    private final String org62UserId;
    private final URI loginUrl;


    public ScratchOrgUserData(String userName, String password, String scratchOrgId, String org62UserId, URI loginUrl) {
        this.userName = userName;
        this.password = password;
        this.scratchOrgId = scratchOrgId;
        this.org62UserId = org62UserId;
        this.loginUrl = loginUrl;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getScratchOrgId() {
        return scratchOrgId;
    }

    public String getOrg62UserId() {
        return org62UserId;
    }

    public URI getLoginUrl() {
        return loginUrl;
    }
}
