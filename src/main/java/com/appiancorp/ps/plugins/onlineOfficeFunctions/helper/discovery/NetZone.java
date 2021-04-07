package com.appiancorp.ps.plugins.onlineOfficeFunctions.helper.discovery;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

/**
 * Defines a net zone into which the client is provided.
 * <p>
 *   In most of cases, to not say in all cases, an "external-http" zone is defined. That means
 *   that the editors are provided from an external infrastructure.
 * </p>
 * @author mark.ansink
 */
@XmlRootElement(name = "net-zone")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "net-zone")
public class NetZone implements Serializable {
    private static final long serialVersionUID = 5086651622500970875L;

    @XmlAttribute
    private String name;

    @XmlElement(name = "app")
    private List<App> apps;

    protected String getName() {
        return name;
    }

    public List<App> getApps() {
        return apps;
    }
}

