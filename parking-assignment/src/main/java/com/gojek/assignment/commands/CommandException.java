package com.gojek.assignment.commands;

public class CommandException extends Exception {
    private CommandError commandError;

    public CommandException(String message, CommandError commandError) {
        super(message);
        this.commandError = commandError;
    }

    public CommandError getCommandError() {
        return commandError;
    }
}
