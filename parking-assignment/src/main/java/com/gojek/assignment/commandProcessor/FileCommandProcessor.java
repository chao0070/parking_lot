package com.gojek.assignment.commandProcessor;

import com.gojek.assignment.commands.Command;
import com.gojek.assignment.commands.CommandException;
import com.gojek.assignment.commands.CommandType;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * When a file is provided with a set of commands, then FileCommandProcessor is used
 */
public class FileCommandProcessor extends AbstractCommandProcessor {
    private String fileName;
    public FileCommandProcessor(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void process() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.fileName));
            String line;
            while ((line = reader.readLine()) != null) {
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
                System.out.println(output);
            }
            reader.close();
        } catch (Exception e) {
            System.err.format("Exception occurred trying to read '%s'.", this.fileName);
            e.printStackTrace();
        }
    }
}
