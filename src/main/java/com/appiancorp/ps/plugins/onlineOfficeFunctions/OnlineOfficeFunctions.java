package com.appiancorp.ps.plugins.onlineOfficeFunctions;


import com.appiancorp.exceptions.InsufficientPrivilegesException;
import com.appiancorp.exceptions.ObjectNotFoundException;
import com.appiancorp.ps.plugins.onlineOfficeFunctions.helper.getDiscoveryURL;
import com.appiancorp.suiteapi.cfg.ConfigurationLoader;
import com.appiancorp.suiteapi.content.ContentService;
import com.appiancorp.suiteapi.expression.annotations.Function;
import com.appiancorp.suiteapi.expression.annotations.Parameter;
import com.appiancorp.suiteapi.knowledge.DocumentDataType;
import com.appiancorp.suiteapi.security.external.SecureCredentialsStore;
import org.apache.log4j.Logger;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;



/**
 * Class that provides list of functions for online office integration
 *
 * @author Mark Ansink
 */

@OnlineOfficeCategory
public class OnlineOfficeFunctions {

    private static final Logger LOG = Logger.getLogger(OnlineOfficeFunctions.class);

    @Function
    public String getEmbeddedOfficeURL(


            SecureCredentialsStore scs,
            ContentService
                    cs,
            @Parameter(required = true) String wopiClient,
            @Parameter(required = true) String scsKey,
            @DocumentDataType @Parameter(required = true) Long document,
            @Parameter boolean readOnly

    ) {

        Map<String, Object> result = new HashMap<>();

        String appianURL = ConfigurationLoader.getConfiguration().getScheme() + "://" +
                ConfigurationLoader.getConfiguration().getServerAndPort();
        Map<String, String> credentials = null;
        String apiKey = null;
        try {
            credentials = scs.getUserSecuredValues(scsKey);
            apiKey = scs.getSystemSecuredValues(scsKey).get("password");
        } catch (InsufficientPrivilegesException e) {
            e.printStackTrace();
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
        }


        String discoveryURL = getDiscoveryURL.getDiscoveryURL(cs, document, wopiClient);
        String embeddedURL = null;
        String username = credentials.get("username");

        String access_header = "Authorization: Basic " + username + ":" + credentials.get("password");
        String readOnlyString = "";
        if (readOnly){
            readOnlyString = "/readOnly/";
        }
            if (username == null) {

            embeddedURL = discoveryURL + "WOPISrc=" + appianURL + "/suite/webapi/wopi/files/" + document +readOnlyString+ "&access_header=" +
                    Base64.getEncoder().encodeToString(access_header.getBytes());
        } else {
            embeddedURL = discoveryURL + "WOPISrc=" + appianURL + "/suite/webapi/wopi/files/" + document +readOnlyString+ "&access_token=" +
                    apiKey;
        }
        return embeddedURL;
    }

}

