package com.appiancorp.ps.plugins.onlineOfficeFunctions;

import java.util.Map;

public class OnlineOfficeFunctionsTest {

	public static void main(String[] args) {
		com.appiancorp.ps.plugins.onlineOfficeFunctions.OnlineOfficeFunctions obj = new OnlineOfficeFunctions();

			String url = obj.getEmbeddedOfficeURL(
					null,
					null,
					"https://officeappian.northeurope.cloudapp.azure.com",
//					"mark.ansink",
					"appian",
					null,
					false);
			
			System.out.println(url);

	}

}
