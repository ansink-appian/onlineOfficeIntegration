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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class OnlineOfficeTest {

	public static String getWOPIDiscovery(String wopiClient, String Extension) {
		WopiDiscovery wopiDiscovery = null;
		final Map<String, String> baseUrlByMimeTypes = new ConcurrentHashMap<>();
		final Map<String, String> baseUrlByExtension = new ConcurrentHashMap<>();
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet request = new HttpGet(wopiClient + "/hosting/discovery");
		Map<String, Object> result = new HashMap<>();
		HttpEntity entity = null;
		InputStream xml = null;
		String url = null;
		try (

				CloseableHttpResponse response = httpClient.execute(request)) {
			entity = response.getEntity();

			if (entity != null) {
				// return it as a String

				xml = entity.getContent();
				WopiDiscovery discovery = WopiDiscovery.load(xml);
				 NetZone netZone = discovery.getNetZone();
				 List<App> apps = netZone.getApps();
				for (int i = 0; i <apps.size() ; i++) {
					App app = apps.get(i);
					List<Action> actions = app.getActions();
					for (int a = 0; a < actions.size(); a++) {
						Action action = actions.get(a);
						if (
								action.getExt().equals(Extension)
						){
							url = action.getUrlsrc();

						}

					}

				}

//				wopiDiscovery = WopiDiscovery.load(xml);
//			wopiDiscovery.consumeBaseUrlMimeType((n, a) -> {
//				baseUrlByMimeTypes.put(n, a.getUrlsrc());
//
//					baseUrlByExtension.put(a.getExt(), a.getUrlsrc());
//
//			});

			}
		} catch (IOException e) {
			e.printStackTrace();

		}

		return url;
	}
	public static void main(String[] args) throws IOException {

//		WopiDiscovery discovery;
String wopi = getWOPIDiscovery("https://officeappian.northeurope.cloudapp.azure.com/","docx");

//		discovery = WopiDiscovery.load(getWOPIDiscovery("https://officeappian.northeurope.cloudapp.azure.com/"));
//		final NetZone netZone = discovery.getNetZone();
//		final List<App> apps = netZone.getApps();
//		final App app = apps.get(0);
//		final List<Action> actions = app.getActions();
//		Action action = actions.get(0);


System.out.println(wopi);



	}



}
