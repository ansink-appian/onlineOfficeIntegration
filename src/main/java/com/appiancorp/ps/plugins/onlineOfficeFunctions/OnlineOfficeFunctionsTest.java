package com.appiancorp.ps.plugins.onlineOfficeFunctions;

public class OnlineOfficeFunctionsTest {

	public static void main(String[] args) {
		OnlineOfficeFunctions obj = new OnlineOfficeFunctions();

		String url = obj.getEmbeddedOfficeURL(

				null,
				null,
				"https://officeonline-test.appian.com",
				"appian",
				null,
				true,
				true
		);
		System.out.println(url);
	}
}
