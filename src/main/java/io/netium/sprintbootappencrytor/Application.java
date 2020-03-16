package io.netium.sprintbootappencrytor;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import io.xjar.XConstants;
import io.xjar.XKit;
import io.xjar.boot.XBoot;
import io.xjar.jar.XJar;
import io.xjar.key.XKey;
public class Application {

    private static String SOURCE_OPT = "s";
    private static String DESTINATION_OPT = "t";
    private static String PASSWORD_OPT = "p";

    public static void main(String[] args) throws Exception {

        Options options = new Options();

        Option sourcePathOption = Option.builder("s").longOpt("source").argName("sourceJar").hasArg().desc("The source jar file path").build();
        options.addOption(sourcePathOption);

        Option destinationPathOption = Option.builder("d").longOpt("destination").argName("destinationJar").hasArg().desc("The target jar file path").build();
        options.addOption(destinationPathOption);

        Option passwordOption = Option.builder("p").longOpt("password").argName("password").hasArg().desc("The encryption password").build();
        options.addOption(passwordOption);

        options.addOption("D", "danger", false, "Use the danger mode (the password will be in the jar file");
        options.addOption("b", "sprintboot", false, "Encrypt the SpringBoot jar package");
        options.addOption("h", "help", false, "Display the help information");

        CommandLineParser parser = new DefaultParser();

        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption("s") && cmd.hasOption("d") && cmd.hasOption("p")) {
            String sourceJarPath = cmd.getOptionValue("s");

            File file = new File(sourceJarPath);
            if (!file.exists()) {
                System.out.println(String.format("The source JAR file [%s] cannot be found", sourceJarPath));
                System.exit(1);
            }

            String destinationJarPath = cmd.getOptionValue("d");
            String password = cmd.getOptionValue("p");

            int mode = cmd.hasOption("D") ? XConstants.MODE_DANGER : XConstants.MODE_NORMAL;
            boolean isSpringBootJar = cmd.hasOption("b");

            XKey xKey = XKit.key(password);

            if (isSpringBootJar) {
                System.out.println("Encrypt the JAR using the SpringBoot application mode");
                XBoot.encrypt(sourceJarPath, destinationJarPath, xKey, mode);
            }
            else {
                System.out.println("Encrypt the JAR using the normal JAR mode");
                XJar.encrypt(sourceJarPath, destinationJarPath, xKey, mode);
            }
            System.out.println("Encrytion complete, the output file is: " + destinationJarPath);
        }
    }
}
