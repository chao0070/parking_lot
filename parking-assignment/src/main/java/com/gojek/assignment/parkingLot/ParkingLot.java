package com.gojek.assignment.parkingLot;

import com.gojek.assignment.vehicle.Car;

import java.util.*;

/**
 * Holds the info of the parking lot and the parked cars. In a real world case this would be divided into 2 layer. One
 * which is acting as the data store and other acting as a wrapper around the data store.
 */
public class ParkingLot {
    private final int capacity;

    private PriorityQueue<Slot> slotsInLot;

    private PriorityQueue<Slot> allocatedSlots;

    private Map<Slot, Car> slotCarMap;

    private Map<String, Slot> regNoSlotMap;

    private Map<String, Set<Slot>> colorSlotMap;

    /**
     * Constructor for parking lot. Takes in Capacity of the parkinglot and creates a parking lot initializing fields.
     * @param capacity
     * @throws ParkingException
     */
    public ParkingLot(int capacity) throws ParkingException {
        if (capacity <= 0) {
            throw new ParkingException(ParkingError.PL_INVALID_CAPACITY.getError(), ParkingError.PL_INVALID_CAPACITY);
        }
        this.capacity = capacity;

        slotsInLot = new PriorityQueue<>(capacity, new Comparator<Slot>() {
            @Override
            public int compare(Slot o1, Slot o2) {
                return o1.getId() - o2.getId();
            }
        });

        allocatedSlots = new PriorityQueue<>(capacity, new Comparator<Slot>() {
            @Override
            public int compare(Slot o1, Slot o2) {
                return o1.getId() - o2.getId();
            }
        });

        for (int i = 1; i <= capacity; i++) {
            Slot slot = new Slot((Integer) i);

            slotsInLot.add(slot);
        }

        slotCarMap = new HashMap<>();
        regNoSlotMap = new HashMap<>();
        colorSlotMap = new HashMap<>();
    }

    /**
     * Parks a car in parking lot. Before parking the car, validates if the slots are available and the car to be parked
     * is valid or not. Creates various mapping which would make it easy when retrieving info.
     * @param car
     * @return
     * @throws ParkingException
     */
    public Slot parkCarInSlot(Car car) throws ParkingException {
        if (slotsInLot.size() == 0) {
            throw new ParkingException(ParkingError.PL_FULL.getError(), ParkingError.PL_FULL);
        }

        if (regNoSlotMap.containsKey(car.getReg())) {
            throw new ParkingException(ParkingError.PL_CAR_PARKED_REG_DUB.getError(), ParkingError.PL_CAR_PARKED_REG_DUB);
        }

        Slot slot = slotsInLot.poll();

        slot.setParkedCar(car);

        allocatedSlots.add(slot);

        slotCarMap.put(slot, car);

        regNoSlotMap.put(car.getReg(), slot);

        String carColor = car.getColor();

        Set<Slot> slots = null;
        slots = colorSlotMap.get(carColor);

        if (slots == null) {
            slots = new HashSet<>();
            colorSlotMap.put(carColor, slots);
        }

        slots.add(slot);

        return slot;
    }

    /**
     * Unparks a car from slot. Validates few things before unparking the car like if the slot is valid or not and the
     * slot is not empty. Removes the mappings and then adds the slot back to the queue.
     * @param slot
     * @throws ParkingException
     */
    public Car unparkCarInSlot(Slot slot) throws ParkingException {

        if (slot.getId() <=0 || slot.getId() > capacity) {
            throw new ParkingException(ParkingError.PL_SLOT_INVALID.getError(), ParkingError.PL_SLOT_INVALID);
        }

        Car car = slotCarMap.get(slot);
        if (car == null) {
            throw new ParkingException(ParkingError.PL_SLOT_NOCAR.getError(), ParkingError.PL_SLOT_NOCAR);
        }
        slotCarMap.remove(slot);

        allocatedSlots.remove(slot);

        Slot origSlot = regNoSlotMap.remove(car.getReg());

        Set<Slot> slots = colorSlotMap.get(car.getColor());
        slots.remove(origSlot);

        if (slots.isEmpty()) {
            colorSlotMap.remove(car.getColor());
        }

        origSlot.setParkedCar(null);

        slotsInLot.add(origSlot);

        return car;
    }

    public PriorityQueue<Slot> status() {
        return allocatedSlots;
    }

    private List<Slot> slotsForColor(String color) throws ParkingException {
        Set<Slot> slots = colorSlotMap.get(color);
        if (slots == null || slots.isEmpty()) {
            throw new ParkingException(ParkingError.PL_CAR_NOT_FOUND.getError(), ParkingError.PL_CAR_NOT_FOUND);
        }

        List<Slot> slotList = new ArrayList<>(slots);

        Collections.sort(slotList, new Comparator<Slot>() {
            @Override
            public int compare(Slot o1, Slot o2) {
                return o1.getId() - o2.getId();
            }
        });

        return slotList;
    }

    public List<String> regNoForColor(String color) throws ParkingException {
        List<Slot> slotList = slotsForColor(color);

        List<String> regNoList = new ArrayList<>(slotList.size());
        for (Slot slot: slotList) {
            regNoList.add(slot.getParkedCar().getReg());
        }

        return regNoList;
    }

    public Slot getSlotFromRegno(String regNo) throws ParkingException {
        Slot slot = regNoSlotMap.get(regNo);
        if (slot == null) {
            throw new ParkingException(ParkingError.PL_CAR_NOT_FOUND.getError(), ParkingError.PL_CAR_NOT_FOUND);
        }
        return slot;
    }

    public List<Integer> slotNoForColor(String color) throws ParkingException {
        List<Slot> slotList = slotsForColor(color);

        List<Integer> slotNoList = new ArrayList<>(slotList.size());
        for (Slot slot: slotList) {
            slotNoList.add(slot.getId());
        }

        return slotNoList;
    }
}
