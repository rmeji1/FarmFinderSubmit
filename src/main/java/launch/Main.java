package launch;

import java.io.File;
import org.apache.catalina.Engine;
import org.apache.catalina.Realm;
import org.apache.catalina.startup.Tomcat;

import com.farmfinder.config.MongoRealm;

public class Main {

    public static void main(String[] args) throws Exception {
        String webappDirLocation = "WebContent/";
        Realm mongorealm = new MongoRealm() ;
        Tomcat tomcat = new Tomcat();

        //The port that we should run on can be set into an environment variable
        //Look for that variable and default to 8080 if it isn't there.
        String webPort = System.getenv("PORT");
        if(webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }

        tomcat.setPort(Integer.valueOf(webPort));

        tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
        System.out.println("configuring app with basedir: " + new File("./" + webappDirLocation).getAbsolutePath());
        Engine eng = tomcat.getEngine() ;
        eng.setRealm( mongorealm );
        tomcat.start();
        tomcat.getServer().await();
    }
}