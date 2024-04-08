package com.appiancorp.ps.plugins.onlineOfficeFunctions.helper;

import com.appiancorp.suiteapi.content.ContentService;
import com.appiancorp.suiteapi.content.exceptions.InvalidContentException;
import org.apache.commons.io.FilenameUtils;

public class getDiscoveryURL {


    public static String getDiscoveryURL(ContentService cs, Long document, String wopiClient, boolean readOnly) {
        String discoveryURL = null;
        String extension = null;
        try {
            extension = FilenameUtils.getExtension(cs.getExternalFilename(document));
        } catch (InvalidContentException e) {
            e.printStackTrace();
        }
        discoveryURL = getWOPIDiscovery.getWOPIDiscovery(wopiClient, extension, readOnly);


        return discoveryURL;
    }
}