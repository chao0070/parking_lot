package com.gojek.assignment.commandProcessor;

import com.gojek.assignment.commands.Command;
import com.gojek.assignment.commands.CommandError;
import com.gojek.assignment.commands.CommandException;
import com.gojek.assignment.commands.CommandType;
import com.gojek.assignment.parkingLot.ParkingException;
import com.gojek.assignment.parkingLot.ParkingLot;
import com.gojek.assignment.parkingLot.Slot;
import com.gojek.assignment.vehicle.Car;

import java.util.*;
import java.util.concurrent.ConcurrentMap;

/**
 * Abstract class which handles the processing of command units
 * TODO: Separate the handling of specific command into functions
 */
public abstract class AbstractCommandProcessor {

    protected ParkingLot parkingLot = null;

    public static Command getCommand(String cmd) throws CommandException {
        String[] cmdArr = cmd.split("[ ]+");
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

    private String processCreatePL(Command command) throws ParkingException {
        List<String> cmdArgs = command.getArgs();
        if (parkingLot != null) {
            return "Parking lot initialized already";
        }
        String capacityArg = cmdArgs.get(0);
        Integer capacity;
        try {
            capacity = Integer.parseInt(capacityArg);
        } catch (NumberFormatException e) {
            return "Invalid Arguments";
        }
        StringBuilder cmdOut = new StringBuilder();
        this.parkingLot = new ParkingLot(capacity);
        cmdOut.append("Created a parking lot with " + capacity + " slots");
        return cmdOut.toString();
    }

    private String processPark(Command command) throws ParkingException {
        List<String> cmdArgs = command.getArgs();

        String regNoArg = cmdArgs.get(0);
        String colorArg = cmdArgs.get(1);
        Car car = new Car(regNoArg, colorArg);
        Slot parkedInSlot = this.parkingLot.parkCarInSlot(car);
        StringBuilder cmdOut = new StringBuilder();
        cmdOut.append("Allocated slot number: " + parkedInSlot.getId());
        return cmdOut.toString();
    }

    private String processUnpark(Command command) throws ParkingException {
        List<String> cmdArgs = command.getArgs();
        String slotArg = cmdArgs.get(0);
        Integer slotArgInt;
        try {
            slotArgInt = Integer.parseInt(slotArg);
        } catch (NumberFormatException e) {
            return "Invalid Arguments";
        }
        Slot slotToUnpark = new Slot(slotArgInt);
        this.parkingLot.unparkCarInSlot(slotToUnpark);
        StringBuilder cmdOut = new StringBuilder();
        cmdOut.append("Slot number " + slotArgInt + " is free");
        return cmdOut.toString();
    }

    private String processGetSlotNoForRegNo(Command command) throws ParkingException {
        List<String> cmdArgs = command.getArgs();
        String regNoArg = cmdArgs.get(0);
        Slot slotFromRegno = this.parkingLot.getSlotFromRegno(regNoArg);
        return slotFromRegno.getId().toString();
    }

    private String processGetRegNoForCarColor(Command command) throws ParkingException {
        List<String> cmdArgs = command.getArgs();
        String carColorArg = cmdArgs.get(0);
        List<String> regNosForColor = this.parkingLot.regNoForColor(carColorArg);
        StringBuilder cmdOut = new StringBuilder();
        for (int i = 0; i < regNosForColor.size(); i++) {
            if (i == 0) {
                cmdOut.append(regNosForColor.get(i));
            } else {
                cmdOut.append(", " + regNosForColor.get(i));
            }
        }
        return cmdOut.toString();
    }

    private String processGetSlotNoForCarColor(Command command) throws ParkingException {
        List<String> cmdArgs = command.getArgs();
        String carColorArg = cmdArgs.get(0);
        List<Integer> slotNosForColor = this.parkingLot.slotNoForColor(carColorArg);
        StringBuilder cmdOut = new StringBuilder();
        for (int i = 0; i < slotNosForColor.size(); i++) {
            if (i == 0) {
                cmdOut.append(slotNosForColor.get(i));
            } else {
                cmdOut.append(", " + slotNosForColor.get(i));
            }
        }
        return cmdOut.toString();
    }

    private String processStatus(Command command) {
        StringBuilder cmdOut = new StringBuilder();
        cmdOut.append("Slot No.    Registration No    Colour");
        PriorityQueue<Slot> pq = this.parkingLot.status();
        Slot[] slotArr = pq.toArray(new Slot[pq.size()]);
        Arrays.sort(slotArr, new Comparator<Slot>() {
            @Override
            public int compare(Slot o1, Slot o2) {
                return o1.getId() - o2.getId();
            }
        });
        for (int i = 0; i < slotArr.length; i++) {
            cmdOut.append("\n");
            Slot currSlot = slotArr[i];
            String slotId = currSlot.getId().toString();
            cmdOut.append(slotId);
            int spacesLeft = 12 - slotId.length();
            for(int j =0; j < spacesLeft; j++) {
                cmdOut.append(" ");
            }
            String regNo = currSlot.getParkedCar().getReg();
            cmdOut.append(regNo);
            spacesLeft = 19 - regNo.length();
            for(int j =0; j < spacesLeft; j++) {
                cmdOut.append(" ");
            }

            String carColor = currSlot.getParkedCar().getColor();
            cmdOut.append(carColor);
        }
        return cmdOut.toString();
    }

    // Should chage this to use buffered writter
    protected String processCommand(Command command) {
        CommandType commandType = command.getCommandType();
        List<String> cmdArgs = command.getArgs();
        if (parkingLot == null && !commandType.equals(CommandType.CREATE_PL)) {
            return "Parking lot not initialized";
        }
        String output = "";


        try {
            switch (commandType) {
                case CREATE_PL:
                    output = processCreatePL(command);
                    break;
                case PARK:
                    output = processPark(command);
                    break;
                case UNPARK:
                    output = processUnpark(command);
                    break;
                case GET_SLOTNO_FOR_REGNO:
                    output = processGetSlotNoForRegNo(command);
                    break;
                case GET_REGNO_FOR_CAR_COLOR:
                    output = processGetRegNoForCarColor(command);
                    break;
                case GET_SLOTNO_FOR_CAR_COLOR:
                    output = processGetSlotNoForCarColor(command);
                    break;
                case STATUS:
                    output = processStatus(command);
                    break;
            }
        } catch (ParkingException pe) {
            StringBuilder cmdOut = new StringBuilder();
            cmdOut.append(pe.getParkingError().getError());
            output = cmdOut.toString();

        }
        return output;
    }

    public abstract void process();
}
