package com.appiancorp.ps.plugins.onlineOfficeFunctions.helper.discovery;



import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.InputStream;
import java.io.Serializable;
import java.util.function.BiConsumer;

/**
 * WOPI discovery is the process by which a WOPI host identifies Office for the web capabilities
 * and how to initialize Office for the web applications within a site. WOPI hosts use the
 * discovery XML to determine how to interact with Office for the web.
 * <p>
 *   This class represents the root element of a discovery XML description file.
 * </p>
 * <a href="https://wopi.readthedocs.io/en/latest/discovery.html#">See the explanations of WOPI discovery from official doc.</a>
 * @author mark.ansink
 */
@XmlRootElement(name = "wopi-discovery")
@XmlAccessorType(XmlAccessType.FIELD)
public class WopiDiscovery implements Serializable {
    private static final long serialVersionUID = -4834847364477784378L;

    @XmlElement(name = "net-zone")
    private NetZone netZone;

    public NetZone getNetZone() {
        return netZone;
    }

    public void consumeBaseUrlMimeType(BiConsumer<String, Action> consumer) {
        netZone.getApps().forEach(ap ->
                ap.getActions().stream()
                        .filter(ac -> ac.getName().contains("edit"))
                        .forEach(ac -> consumer.accept(ap.getName(), ac)));
    }

    public static WopiDiscovery load(InputStream in) {
        try {
            final JAXBContext context = JAXBContext.newInstance(WopiDiscovery.class);
            final Unmarshaller unmarshaller = context.createUnmarshaller();
            return (WopiDiscovery) unmarshaller.unmarshal(in);
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
}
