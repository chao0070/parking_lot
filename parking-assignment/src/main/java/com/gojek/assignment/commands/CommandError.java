package com.gojek.assignment.commands;

/**
 * Various syntactical error in commands
 */
public enum CommandError {
    CE_UNKNOW("Command Unknown/Invalid"),
    CE_INVALID_ARGS("Invalid Arguments");

    private String error;

    CommandError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
