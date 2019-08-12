package com.gojek.assignment.commands;

/**
 * CommandException is a custom exception to handle various command syntax exceptions
 */
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
