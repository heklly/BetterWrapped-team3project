package interface_adapter.sharedsong;

import java.util.Map;

public class SharedSongState {

    private Map<String, String> usernameToShared;

    private String errorMessage;

    public void setUsernameToShared(Map<String, String> sharedSongOutputData) {
        this.usernameToShared = sharedSongOutputData;
    }
    public Map<String, String> getUsernameToShared() {return usernameToShared;}

    public String getErrorMessage() {return errorMessage; }

    public void setErrorMessage(String errorMessage) {this.errorMessage = errorMessage;}


}
