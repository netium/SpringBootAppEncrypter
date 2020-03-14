package io.netium.sprintbootappencrytor;

import io.xjar.XConstants;
import io.xjar.XKit;
import io.xjar.boot.XBoot;
import io.xjar.key.XKey;
public class Application {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage: SprintBootAppEncrypter source_jar_path target_jar_path");
            return;
        }

        String password = "io.xjar";
        XKey xKey = XKit.key(password);
        XBoot.encrypt(args[0], args[1], xKey, XConstants.MODE_DANGER);
    }
}
