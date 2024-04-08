package com.appiancorp.ps.plugins.onlineOfficeFunctions.helper;

import com.appiancorp.ps.plugins.onlineOfficeFunctions.helper.discovery.Action;
import com.appiancorp.ps.plugins.onlineOfficeFunctions.helper.discovery.App;
import com.appiancorp.ps.plugins.onlineOfficeFunctions.helper.discovery.NetZone;
import com.appiancorp.ps.plugins.onlineOfficeFunctions.helper.discovery.WopiDiscovery;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class getWOPIDiscovery {
    public static String getWOPIDiscovery(String wopiClient, String Extension, boolean readOnly) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(wopiClient + "/hosting/discovery");
        HttpEntity entity = null;
        InputStream xml = null;
        String url = null;
        String actionName = "view";
        if (
                readOnly
        ) {
            actionName = "view";
        } else {
            actionName = "edit";
        }
        try (
                CloseableHttpResponse response = httpClient.execute(request)) {
            entity = response.getEntity();

            if (entity != null) {
                // return it as a String

                xml = entity.getContent();
                WopiDiscovery discovery = WopiDiscovery.load(xml);
                NetZone netZone = discovery.getNetZone();
                List<App> apps = netZone.getApps();
                for (int i = 0; i < apps.size(); i++) {
                    App app = apps.get(i);
                    List<Action> actions = app.getActions();
                    for (int a = 0; a < actions.size(); a++) {
                        Action action = actions.get(a);
                        if (
                                action.getExt().equals(Extension) && action.getName().equals(actionName)
                        ) {
                            url = action.getUrlsrc().replaceAll("<.*?>", "") + "IsLicensedUser=1&";
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return url;
    }
}