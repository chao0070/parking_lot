package com.gojek.assignment.commands;

import java.util.List;

public class Command {
    private CommandType commandType;
    private List<String> args;

    public Command(CommandType commandType, List<String> args) throws CommandException {
        this.commandType = commandType;
        if (commandType.getNoOfArgs() != args.size()) {
            throw new CommandException(CommandError.CE_INVALID_ARGS.getError(), CommandError.CE_INVALID_ARGS);
        }
        this.args = args;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public List<String> getArgs() {
        return args;
    }

    public void setArgs(List<String> args) {
        this.args = args;
    }
}
