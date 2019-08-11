package com.gojek.assignment.commandProcessor;

import com.gojek.assignment.commands.Command;
import com.gojek.assignment.commands.CommandException;
import com.gojek.assignment.commands.CommandType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InteractiveCommandProcessor extends AbstractCommandProcessor {

    public InteractiveCommandProcessor() {
    }

    @Override
    public void process() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.print("$ ");
            try {
                String line = reader.readLine();
                Command cmd;
                try {
                    cmd = AbstractCommandProcessor.getCommand(line);
                } catch (CommandException ce) {
                    System.out.println(ce.getCommandError().getError());
                    continue;
                }
                if (cmd.getCommandType().equals(CommandType.EXIT)) {
                    break;
                }
                String output = processCommand(cmd);
                System.out.println(output + "\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
