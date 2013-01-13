package cz.cvut.fel.bupro;

import java.io.File;

import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.naming.resources.VirtualDirContext;
import org.apache.tomcat.util.scan.StandardJarScanner;

public class Bootstrap {

	public static void main(String[] args) throws Exception {
		String webappDirLocation = "src/main/webapp/";
		Tomcat tomcat = new Tomcat();

		String webPort = System.getenv("PORT");
		// if (webPort == null || webPort.isEmpty()) {
		webPort = "8080";
		// }
		tomcat.setPort(Integer.valueOf(webPort));

		StandardContext ctx = (StandardContext) tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());		
//		tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
		System.out.println("configuring app with basedir: " + new File(webappDirLocation).getAbsolutePath());
		
		//declare an alternate location for your "WEB-INF/classes" dir:
		((StandardJarScanner) ctx.getJarScanner()).setScanAllDirectories(true); //FIXME Scan all - devel only 

		tomcat.start();
		tomcat.getServer().await();
	}

}
