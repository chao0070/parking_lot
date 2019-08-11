package com.gojek.assignment.commandProcessor;

import com.gojek.assignment.commands.Command;
import com.gojek.assignment.commands.CommandError;
import com.gojek.assignment.commands.CommandException;
import com.gojek.assignment.commands.CommandType;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AbstractCommandProcessorTest {


    @Test
    public void test_create_pl_cmd() throws CommandException {
        Command command = null;
        try {
            command = AbstractCommandProcessor.getCommand("create_parking_lot");
        } catch (CommandException ce) {
            assertEquals(ce.getCommandError(), CommandError.CE_INVALID_ARGS);
        }

        try {
            command = AbstractCommandProcessor.getCommand("create_par");
        } catch (CommandException ce) {
            assertEquals(ce.getCommandError(), CommandError.CE_UNKNOW);
        }

        try {
            command = AbstractCommandProcessor.getCommand("create_parking_lot 3 4");
        } catch (CommandException ce) {
            assertEquals(ce.getCommandError(), CommandError.CE_INVALID_ARGS);
        }
        command = AbstractCommandProcessor.getCommand("create_parking_lot 6");
        assertEquals(command.getCommandType(), CommandType.CREATE_PL);
        assertEquals(command.getArgs().size(), 1);
        List<String> actualArgs = command.getArgs();
        String[] args = {"6"};
        assertArrayEquals(actualArgs.toArray(), args);
    }

    @Test
    public void test_park_cmd() throws CommandException {
        Command command = null;
        try {
            command = AbstractCommandProcessor.getCommand("park");
        } catch (CommandException ce) {
            assertEquals(ce.getCommandError(), CommandError.CE_INVALID_ARGS);
        }

        try {
            command = AbstractCommandProcessor.getCommand("par");
        } catch (CommandException ce) {
            assertEquals(ce.getCommandError(), CommandError.CE_UNKNOW);
        }

        try {
            command = AbstractCommandProcessor.getCommand("park 3 4 6");
        } catch (CommandException ce) {
            assertEquals(ce.getCommandError(), CommandError.CE_INVALID_ARGS);
        }
        command = AbstractCommandProcessor.getCommand("park KA-01-HH-1234 White");
        assertEquals(command.getCommandType(), CommandType.PARK);
        assertEquals(command.getArgs().size(), 2);
        List<String> actualArgs = command.getArgs();
        String[] args = {"KA-01-HH-1234", "White"};
        assertArrayEquals(actualArgs.toArray(), args);
    }

    /*
registration_numbers_for_cars_with_colour White
slot_numbers_for_cars_with_colour White
slot_number_for_registration_number KA-01-HH-3141
slot_number_for_registration_number MH-04-AY-1111
     */
    @Test
    public void test_leave_cmd() throws CommandException {
        Command command = null;
        try {
            command = AbstractCommandProcessor.getCommand("leave");
        } catch (CommandException ce) {
            assertEquals(ce.getCommandError(), CommandError.CE_INVALID_ARGS);
        }

        try {
            command = AbstractCommandProcessor.getCommand("lea");
        } catch (CommandException ce) {
            assertEquals(ce.getCommandError(), CommandError.CE_UNKNOW);
        }

        try {
            command = AbstractCommandProcessor.getCommand("leave 3 4");
        } catch (CommandException ce) {
            assertEquals(ce.getCommandError(), CommandError.CE_INVALID_ARGS);
        }
        command = AbstractCommandProcessor.getCommand("leave 4");
        assertEquals(command.getCommandType(), CommandType.UNPARK);
        assertEquals(command.getArgs().size(), 1);
        List<String> actualArgs = command.getArgs();
        String[] args = {"4"};
        assertArrayEquals(actualArgs.toArray(), args);
    }

    @Test
    public void test_status_cmd() throws CommandException {
        Command command = null;
        try {
            command = AbstractCommandProcessor.getCommand("status 30");
        } catch (CommandException ce) {
            assertEquals(ce.getCommandError(), CommandError.CE_INVALID_ARGS);
        }

        try {
            command = AbstractCommandProcessor.getCommand("sta");
        } catch (CommandException ce) {
            assertEquals(ce.getCommandError(), CommandError.CE_UNKNOW);
        }

        command = AbstractCommandProcessor.getCommand("status");
        assertEquals(command.getCommandType(), CommandType.STATUS);
        assertEquals(command.getArgs().size(), 0);
        List<String> actualArgs = command.getArgs();
        String[] args = {};
        assertArrayEquals(actualArgs.toArray(), args);
    }

    /*
slot_numbers_for_cars_with_colour White
slot_number_for_registration_number KA-01-HH-3141
slot_number_for_registration_number MH-04-AY-1111
 */
    @Test
    public void test_reg_no_car_color_cmd() throws CommandException {
        Command command = null;
        try {
            command = AbstractCommandProcessor.getCommand("registration_numbers_for_cars_with_colour");
        } catch (CommandException ce) {
            assertEquals(ce.getCommandError(), CommandError.CE_INVALID_ARGS);
        }

        try {
            command = AbstractCommandProcessor.getCommand("registration_numbers_for_ca");
        } catch (CommandException ce) {
            assertEquals(ce.getCommandError(), CommandError.CE_UNKNOW);
        }

        try {
            command = AbstractCommandProcessor.getCommand("registration_numbers_for_cars_with_colour 3 4");
        } catch (CommandException ce) {
            assertEquals(ce.getCommandError(), CommandError.CE_INVALID_ARGS);
        }
        command = AbstractCommandProcessor.getCommand("registration_numbers_for_cars_with_colour White");
        assertEquals(command.getCommandType(), CommandType.GET_REGNO_FOR_CAR_COLOR);
        assertEquals(command.getArgs().size(), 1);
        List<String> actualArgs = command.getArgs();
        String[] args = {"White"};
        assertArrayEquals(actualArgs.toArray(), args);
    }

    /*
slot_numbers_for_cars_with_colour White
slot_number_for_registration_number KA-01-HH-3141
slot_number_for_registration_number MH-04-AY-1111
*/
    @Test
    public void test_slot_no_car_color_cmd() throws CommandException {
        Command command = null;
        try {
            command = AbstractCommandProcessor.getCommand("slot_numbers_for_cars_with_colour");
        } catch (CommandException ce) {
            assertEquals(ce.getCommandError(), CommandError.CE_INVALID_ARGS);
        }

        try {
            command = AbstractCommandProcessor.getCommand("slot_numbers_for_cars_with");
        } catch (CommandException ce) {
            assertEquals(ce.getCommandError(), CommandError.CE_UNKNOW);
        }

        try {
            command = AbstractCommandProcessor.getCommand("slot_numbers_for_cars_with_colour White 3 4");
        } catch (CommandException ce) {
            assertEquals(ce.getCommandError(), CommandError.CE_INVALID_ARGS);
        }
        command = AbstractCommandProcessor.getCommand("slot_numbers_for_cars_with_colour White");
        assertEquals(command.getCommandType(), CommandType.GET_SLOTNO_FOR_CAR_COLOR);
        assertEquals(command.getArgs().size(), 1);
        List<String> actualArgs = command.getArgs();
        String[] args = {"White"};
        assertArrayEquals(actualArgs.toArray(), args);
    }

    /*
slot_number_for_registration_number KA-01-HH-3141
*/
    @Test
    public void test_slot_no_reg_no_cmd() throws CommandException {
        Command command = null;
        try {
            command = AbstractCommandProcessor.getCommand("slot_number_for_registration_number");
        } catch (CommandException ce) {
            assertEquals(ce.getCommandError(), CommandError.CE_INVALID_ARGS);
        }

        try {
            command = AbstractCommandProcessor.getCommand("slot_number_for_registration_n");
        } catch (CommandException ce) {
            assertEquals(ce.getCommandError(), CommandError.CE_UNKNOW);
        }

        try {
            command = AbstractCommandProcessor.getCommand("slot_number_for_registration_number KA-01-HH-3141 3 4");
        } catch (CommandException ce) {
            assertEquals(ce.getCommandError(), CommandError.CE_INVALID_ARGS);
        }
        command = AbstractCommandProcessor.getCommand("slot_number_for_registration_number KA-01-HH-3141");
        assertEquals(command.getCommandType(), CommandType.GET_SLOTNO_FOR_REGNO);
        assertEquals(command.getArgs().size(), 1);
        List<String> actualArgs = command.getArgs();
        String[] args = {"KA-01-HH-3141"};
        assertArrayEquals(actualArgs.toArray(), args);
    }

    @Test
    public void test_extra_cmd() throws CommandException {
        Command command = null;
        try {
            command = AbstractCommandProcessor.getCommand("");
        } catch (CommandException ce) {
            assertEquals(ce.getCommandError(), CommandError.CE_UNKNOW);
        }

        try {
            command = AbstractCommandProcessor.getCommand("djkfnd djskjf jdksfj");
        } catch (CommandException ce) {
            assertEquals(ce.getCommandError(), CommandError.CE_UNKNOW);
        }

        try {
            command = AbstractCommandProcessor.getCommand("park  White");
        } catch (CommandException ce) {
            assertEquals(ce.getCommandError(), CommandError.CE_INVALID_ARGS);
        }

        System.out.println("Command");
    }




}