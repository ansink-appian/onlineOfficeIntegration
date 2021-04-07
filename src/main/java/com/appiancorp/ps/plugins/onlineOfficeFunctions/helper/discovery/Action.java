package com.appiancorp.ps.plugins.onlineOfficeFunctions.helper.discovery;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * The action element and its attributes in the discovery XML provides some important information
 * about Office for the web.
 * <p>
 * It provides:
 *   <ul>
 *     <li>the name of the action, (view, edit) which permits to know from Silverpeas's point of
 *     view what kind of manipulations it will be possible with the document</li>
 *     <li>the handled extension</li>
 *     <li>the client URL that permits to edit into the browser file a document</li>
 *   </ul>
 * </p>
 * @author mark.ansink
 */

@XmlRootElement(name = "action")
@XmlAccessorType(XmlAccessType.FIELD)
public class Action implements Serializable {
    private static final long serialVersionUID = -24246963248719096L;

    @XmlAttribute
    private String ext;

    @XmlAttribute
    private String name;

    @XmlAttribute
    private String urlsrc;

    public String getExt() {
        return ext;
    }

    public String getName() {
        return name;
    }

    public String getUrlsrc() {
        return urlsrc;
    }
}