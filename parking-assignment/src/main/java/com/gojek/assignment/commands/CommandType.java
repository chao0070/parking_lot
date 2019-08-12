package com.gojek.assignment.commands;

import java.util.HashMap;
import java.util.Map;

/**
 * Specification of various commands supported by our Application
 */
public enum CommandType {
    CREATE_PL("create_parking_lot", 1),
    PARK("park", 2),
    UNPARK("leave", 1),
    STATUS("status", 0),
    GET_REGNO_FOR_CAR_COLOR("registration_numbers_for_cars_with_colour", 1),
    GET_SLOTNO_FOR_CAR_COLOR("slot_numbers_for_cars_with_colour", 1),
    GET_SLOTNO_FOR_REGNO("slot_number_for_registration_number", 1),
    EXIT("exit", 0);


    private final String commandSyntax;
    private final Integer noOfArgs;
    private static final Map<String, CommandType> lookupMap = new HashMap<>();

    CommandType(String commandSyntax, Integer noOfArgs) {
        this.commandSyntax = commandSyntax;
        this.noOfArgs = noOfArgs;
    }

    static {
        for(CommandType commandType: CommandType.values()) {
            lookupMap.put(commandType.getCommandSyntax(), commandType);
        }
    }

    public String getCommandSyntax() {
        return commandSyntax;
    }

    public Integer getNoOfArgs() {
        return noOfArgs;
    }

    public static CommandType get(String commandSyntax) throws CommandException {
        CommandType commandType = lookupMap.get(commandSyntax);
        if (commandType == null) {
            throw new CommandException(CommandError.CE_UNKNOW.getError(), CommandError.CE_UNKNOW);
        }
        return commandType;
    }
}
