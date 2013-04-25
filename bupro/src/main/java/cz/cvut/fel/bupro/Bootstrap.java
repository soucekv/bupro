package cz.cvut.fel.bupro;

import java.io.File;

import org.apache.catalina.Service;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.scan.StandardJarScanner;

public class Bootstrap {

	private static int getenvInt(String propertyName, int defaultValue) {
		String value = System.getenv(propertyName);
		if (value == null || value.trim().isEmpty()) {
			return defaultValue;
		}
		return Integer.parseInt(value);
	}

	public static void main(String[] args) throws Exception {
		String webappDirLocation = "src/main/webapp/";
		Tomcat tomcat = new Tomcat();

		int httpPort = getenvInt("PORT", 8080);
		tomcat.setPort(httpPort);
		boolean ssl = false;
		if (ssl) {
			// TODO implement SSL
			int httpsPort = getenvInt("HTTPS_PORT", 8443);
			String keyAlias = "";
			String password = "";
			String keystorePath = "";
			Connector httpsConnector = new Connector();
			httpsConnector.setPort(httpsPort);
			httpsConnector.setSecure(true);
			httpsConnector.setScheme("https");
			httpsConnector.setAttribute("keyAlias", keyAlias);
			httpsConnector.setAttribute("keystorePass", password);
			httpsConnector.setAttribute("keystoreFile", keystorePath);
			httpsConnector.setAttribute("clientAuth", "false");
			httpsConnector.setAttribute("sslProtocol", "TLS");
			httpsConnector.setAttribute("SSLEnabled", true);
			Service service = tomcat.getService();
			service.addConnector(httpsConnector);
			Connector defaultConnector = tomcat.getConnector();
			defaultConnector.setRedirectPort(httpsPort);
		}
		StandardContext ctx = (StandardContext) tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
		// tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
		System.out.println("configuring app with basedir: " + new File(webappDirLocation).getAbsolutePath());

		// declare an alternate location for your "WEB-INF/classes" dir:
		// FIXME Scanall - devel only
		((StandardJarScanner) ctx.getJarScanner()).setScanAllDirectories(true);

		tomcat.start();
		tomcat.getServer().await();
	}

}
