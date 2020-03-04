package eu.droogers.smsmatrix.viewModel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.lifecycle.AndroidViewModel;

public class MainViewModel extends AndroidViewModel {
    private String syncTimeout;
    private String syncDelay;
    private String hsUrl;
    private String device;
    private String username;
    private String botPassword;
    private String botUsername;
    private final SharedPreferences sp;

    public MainViewModel(Application context) {
        super(context);
        sp = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        botUsername = sp.getString("botUsername", "");
        botPassword = sp.getString("botPassword", "");
        username = sp.getString("username", "");
        device = sp.getString("device", "");
        hsUrl = sp.getString("hsUrl", "");
        syncDelay = sp.getString("syncDelay", "12");
        syncTimeout = sp.getString("syncTimeout", "30");
    }

    public String getSyncTimeout() {
        return syncTimeout;
    }

    public void setSyncTimeout(String syncTimeout) {
        this.syncTimeout = syncTimeout;
    }

    public String getSyncDelay() {
        return syncDelay;
    }

    public void setSyncDelay(String syncDelay) {
        this.syncDelay = syncDelay;
    }

    public String getHsUrl() {
        return hsUrl;
    }

    public void setHsUrl(String hsUrl) {
        this.hsUrl = hsUrl;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBotPassword() {
        return botPassword;
    }

    public void setBotPassword(String botPassword) {
        this.botPassword = botPassword;
    }

    public String getBotUsername() {
        return botUsername;
    }

    public void setBotUsername(String botUsername) {
        this.botUsername = botUsername;
    }

    public void apply(){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("botUsername", botUsername);
        editor.putString("botPassword", botPassword);
        editor.putString("username", username);
        editor.putString("device", device);
        editor.putString("hsUrl", hsUrl);
        editor.putString("syncDelay", syncDelay);
        editor.putString("syncTimeout", syncTimeout);
        editor.apply();
    }
}
