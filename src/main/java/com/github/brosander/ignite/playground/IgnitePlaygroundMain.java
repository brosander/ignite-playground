package com.github.brosander.ignite.playground;

import org.codehaus.groovy.tools.shell.Main;

public class IgnitePlaygroundMain {

    public static void main(String[] args) throws ClassNotFoundException {
        String[] newArgs = new String[args.length + 2];
        newArgs[0] = "-e";
        newArgs[1] = ":load " + System.getProperty("init.groovy");
        System.arraycopy(args, 0, newArgs, 2, args.length);
        Main.main(newArgs);
    }
}
