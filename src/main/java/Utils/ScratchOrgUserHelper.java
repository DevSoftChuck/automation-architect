package Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

public class ScratchOrgUserHelper {

    private static final String CREATE_USER = "sfdx for5ce:org:create adminEmail=%s -f %s -d 30 -s -a %s";
    private static final String GENERATE_PASSWORD = "sfdx force:user:password:generate -u %s";
    private static final String DISPLAY_USER = "sfdx force:user:display -u %s";

    private final ProcessBuilder processBuilder = new ProcessBuilder();

    private Optional<String> createUser(String userEmail, String userAlias, Path jsonConfigurationPath){
        Process process = executeSfdxCommand(String.format(GENERATE_PASSWORD, userEmail, jsonConfigurationPath, userAlias));
        return RegExpHelper.extractData(processInputStream(process.getInputStream()),"(?s).*username:\\s*([a-zA-Z.@0-9-]*)");
    }

    private void setPassword(String userName){
        executeSfdxCommand(String.format(GENERATE_PASSWORD, userName));
    }

    private ScratchOrgUserData displayUserData(String userName){
        Process process = executeSfdxCommand(String.format(DISPLAY_USER, userName));

        String regex = "(?s).*Username\\s+(?<username>[a-zA-Z.@0-9-]*).*?Id\\s*(?<id>\\w*).*Org Id\\s*(?<orgId>\\w*).*Access Token\\s*(?<accessToken>\\S*).*Instance Url\\s*(?<instanceUrl>\\S*).*Login Url\\s*(?<loginUrl>\\S*).*Password\\s*(?<password>\\S*)";
        Matcher matcher = RegExpHelper.extractDataMatcher(processInputStream(process.getInputStream()), regex).orElseThrow(IllegalArgumentException::new);
        return new ScratchOrgUserData(matcher.group("userName"), matcher.group("password"),
                matcher.group("id"), matcher.group("orgId"), URI.create(matcher.group("loginUrl")));
    }

    public ScratchOrgUserData createUserAndSetPassword(String userEmail, String userAlias){
        try{
            Path configPath = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("sratchOrg/project-scratch-def.json")).toURI());
            String userName = createUser(userEmail, userAlias, configPath).orElseThrow(IllegalArgumentException::new);
            setPassword(userName);
            return displayUserData(userName);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private String processInputStream(InputStream stream){
        BufferedReader stdOut = new BufferedReader(new InputStreamReader(stream));
        return stdOut.lines().collect(Collectors.joining(System.lineSeparator()));
    }

    private Process executeSfdxCommand(String command){
        try{
            Process process = this.processBuilder.command(command.split(" "))
                            .start();
            process.waitFor();
            String errorText = processInputStream(process.getErrorStream());
            if(errorText.contains("ERROR")){
                throw new RuntimeException(errorText);
            }else {
                return process;
            }
        }catch (IOException | InterruptedException e){
            throw new RuntimeException(e);
        }
    }


}
