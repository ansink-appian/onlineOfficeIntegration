package com.appiancorp.ps.plugins.onlineOfficeFunctions;

import com.appiancorp.exceptions.InsufficientPrivilegesException;
import com.appiancorp.exceptions.ObjectNotFoundException;
import com.appiancorp.ps.plugins.onlineOfficeFunctions.helper.getDiscoveryURL;
import com.appiancorp.suiteapi.cfg.ConfigurationLoader;
import com.appiancorp.suiteapi.content.ContentService;
import com.appiancorp.suiteapi.content.exceptions.InvalidContentException;
import com.appiancorp.suiteapi.expression.annotations.Function;
import com.appiancorp.suiteapi.expression.annotations.Parameter;
import com.appiancorp.suiteapi.knowledge.DocumentDataType;
import com.appiancorp.suiteapi.security.external.SecureCredentialsStore;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.Logger;
import static org.apache.logging.log4j.LogManager.getLogger;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Map;
import static java.net.URLEncoder.encode;

/**
 * Class that provides list of functions for online office integration
 *
 * @author Mark Ansink
 */

@OnlineOfficeCategory
public class OnlineOfficeFunctions {

    private static final Logger LOG = getLogger(OnlineOfficeFunctions.class.getName());

    @Function
    public String getEmbeddedOfficeURL(

            SecureCredentialsStore scs,

            ContentService cs,
            @Parameter(required = true) String wopiClient,
            @Parameter(required = true) String scsKey,
            @DocumentDataType @Parameter(required = true) Long document,
            @Parameter boolean readOnly,
            @Parameter boolean microsoft

    ) {


        String appianURL = ConfigurationLoader.getConfiguration().getScheme() + "://" +
                ConfigurationLoader.getConfiguration().getServerAndPort();
        Map<String, String> credentials = null;
        String apiKey = null;

        String username = null;
        try {
            credentials = scs.getUserSecuredValues(scsKey);
            apiKey = scs.getSystemSecuredValues(scsKey).get("password");
            username = credentials.get("username");
        } catch (InsufficientPrivilegesException e) {

            LOG.error(e.getMessage());
        } catch (ObjectNotFoundException e) {

            LOG.error(e.getMessage());
        }
        String title = null;
        try {
            title = FilenameUtils.getBaseName(cs.getExternalFilename(document));
        } catch (InvalidContentException e) {
            LOG.error(e.getMessage());
        }
        String discoveryURL = getDiscoveryURL.getDiscoveryURL(cs, document, wopiClient, readOnly) + "title=" + title+ "&";
        String embeddedURL = null;
        String access_header =
                username + ":" + credentials.get("password");
        String readOnlyString = "";

        if (readOnly) {
            readOnlyString = "/readOnly/";
        }
        if (username == null) {
            embeddedURL = discoveryURL
                    + "WOPISrc="
                    + appianURL + "/suite/webapi/wopi/files/"
                    + document
                    + readOnlyString
                    + "&access_token="
                    + apiKey;
        } else {
            try {

                String accessHeader = null;
                if (microsoft) {
                    accessHeader = "&access_token=";
                } else {
                    accessHeader = "&access_header=";
                }
                embeddedURL = discoveryURL
                        + "WOPISrc="
                        + appianURL
                        + "/suite/webapi/wopi/files/"
                        + document
                        + readOnlyString
                        + accessHeader
                        + encode("Authorization: Basic ", "UTF-8")
                        + Base64.getEncoder().encodeToString(access_header.getBytes());
            } catch (UnsupportedEncodingException e) {
                LOG.error(e.getMessage());
            }
        }
        return embeddedURL;
    }

}