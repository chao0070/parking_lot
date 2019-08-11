package com.gojek.assignment.main;

import com.gojek.assignment.commandProcessor.AbstractCommandProcessor;
import com.gojek.assignment.commandProcessor.FileCommandProcessor;
import com.gojek.assignment.commandProcessor.InteractiveCommandProcessor;

public class Main {
    public static void main(String[] args) {
        AbstractCommandProcessor abstractCommandProcessor;
        if (args.length == 1) {
            abstractCommandProcessor = new FileCommandProcessor(args[1]);
        } else {
            abstractCommandProcessor = new InteractiveCommandProcessor();
        }
        abstractCommandProcessor.process();
    }
}
