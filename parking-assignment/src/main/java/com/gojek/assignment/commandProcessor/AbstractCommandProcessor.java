package com.gojek.assignment.commandProcessor;

import com.gojek.assignment.commands.Command;
import com.gojek.assignment.commands.CommandError;
import com.gojek.assignment.commands.CommandException;
import com.gojek.assignment.commands.CommandType;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public abstract class AbstractCommandProcessor {

    public static Command getCommand(String cmd) throws CommandException {
        String[] cmdArr = cmd.split(" ");
        if (cmdArr.length == 0) {
            throw new CommandException(CommandError.CE_UNKNOW.getError(), CommandError.CE_UNKNOW);
        }

        CommandType commandType = CommandType.get(cmdArr[0]);
        List<String> cmdArgs = new ArrayList<>();
        for (int i = 1; i < cmdArr.length; i++) {
            cmdArgs.add(cmdArr[i]);
        }

        Command command = new Command(commandType, cmdArgs);
        return command;
    }

    public abstract void process();
}
