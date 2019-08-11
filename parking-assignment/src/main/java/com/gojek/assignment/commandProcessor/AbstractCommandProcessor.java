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

    // Should chage this to use buffered writter
    protected String processCommand(Command command) {
        CommandType commandType = command.getCommandType();
        List<String> cmdArgs = command.getArgs();
        if (parkingLot == null && !commandType.equals(CommandType.CREATE_PL)) {
            return "Parking lot not initialized";
        }
        StringBuilder cmdOut = new StringBuilder();

        try {
            switch (commandType) {
                case CREATE_PL:
                    if (parkingLot != null) {
                        cmdOut.append("Parking lot initialized already");
                        break;
                    }
                    String capacityArg = cmdArgs.get(0);
                    Integer capacity;
                    try {
                        capacity = Integer.parseInt(capacityArg);
                    } catch (NumberFormatException e) {
                        cmdOut.append("Invalid Arguments");
                        break;
                    }
                    this.parkingLot = new ParkingLot(capacity);
                    cmdOut.append("Created a parking lot with " + capacity + " slots");
                    break;
                case PARK:
                    String regNoArg = cmdArgs.get(0);
                    String colorArg = cmdArgs.get(1);
                    Car car = new Car(regNoArg, colorArg);
                    Slot parkedInSlot = this.parkingLot.parkCarInSlot(car);
                    cmdOut.append("Allocated slot number: " + parkedInSlot.getId());
                    break;
                case UNPARK:
                    String slotArg = cmdArgs.get(0);
                    Integer slotArgInt;
                    try {
                        slotArgInt = Integer.parseInt(slotArg);
                    } catch (NumberFormatException e) {
                        cmdOut.append("Invalid Arguments");
                        break;
                    }
                    Slot slotToUnpark = new Slot(slotArgInt);
                    this.parkingLot.unparkCarInSlot(slotToUnpark);
                    cmdOut.append("Slot number " + slotArgInt + " is free");
                    break;
                case GET_SLOTNO_FOR_REGNO:
                    regNoArg = cmdArgs.get(0);
                    Slot slotFromRegno = this.parkingLot.getSlotFromRegno(regNoArg);
                    cmdOut.append(slotFromRegno.getId());
                    break;
                case GET_REGNO_FOR_CAR_COLOR:
                    String carColorArg = cmdArgs.get(0);
                    List<String> regNosForColor = this.parkingLot.regNoForColor(carColorArg);
                    for (int i = 0; i < regNosForColor.size(); i++) {
                        if (i == 0) {
                            cmdOut.append(regNosForColor.get(i));
                        } else {
                            cmdOut.append(", " + regNosForColor.get(i));
                        }
                    }
                    break;
                case GET_SLOTNO_FOR_CAR_COLOR:
                    carColorArg = cmdArgs.get(0);
                    List<Integer> slotNosForColor = this.parkingLot.slotNoForColor(carColorArg);
                    for (int i = 0; i < slotNosForColor.size(); i++) {
                        if (i == 0) {
                            cmdOut.append(slotNosForColor.get(i));
                        } else {
                            cmdOut.append(", " + slotNosForColor.get(i));
                        }
                    }
                    break;
                case STATUS:
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
                    break;
            }
        } catch (ParkingException pe) {
            cmdOut.append(pe.getParkingError().getError());
        }

        return cmdOut.toString();
    }

    public abstract void process();
}
