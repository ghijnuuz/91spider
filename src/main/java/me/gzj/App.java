package me.gzj;

import me.gzj.configuration.AppConfiguration;
import me.gzj.service.i.ISpiderService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);

        ISpiderService spiderService = context.getBean(ISpiderService.class);

        if (args.length > 0) {
            String command = args[0];
            switch (command) {
                case "get":
                    spiderService.getAllVideoAndSaveToDatabase();
                    break;
                case "download":
                    break;
                default:
                    System.out.println("command error");
            }
        }
    }
}
