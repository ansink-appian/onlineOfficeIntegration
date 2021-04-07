package com.appiancorp.ps.plugins.onlineOfficeFunctions.helper.discovery;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Defines an application that can takes in charge a kind of document.
 * @author mark.ansink
 */
@XmlRootElement(name = "app")
@XmlAccessorType(XmlAccessType.FIELD)
public class App implements Serializable {
    private static final long serialVersionUID = -2370009372124602067L;

    @XmlAttribute
    private String name;

    @XmlElement(name = "action")
    private List<Action> actions;

    protected String getName() {
        return name;
    }

    public List<Action> getActions() {
        return actions;
    }
}
