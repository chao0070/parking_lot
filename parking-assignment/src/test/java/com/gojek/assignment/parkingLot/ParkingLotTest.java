package com.gojek.assignment.parkingLot;

import com.gojek.assignment.vehicle.Car;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class ParkingLotTest {

    /*
        park KA-01-HH-1234 White
        park KA-01-HH-9999 White
        park KA-01-BB-0001 Black
        park KA-01-HH-7777 Red
        park KA-01-HH-2701 Blue
        park KA-01-HH-3141 Black
        leave 4
        status
        park KA-01-P-333 White
        park DL-12-AA-9999 White
        registration_numbers_for_cars_with_colour White
        slot_numbers_for_cars_with_colour White
        slot_number_for_registration_number KA-01-HH-3141
        slot_number_for_registration_number MH-04-AY-1111
     */
    @Test
    public void testParkingLot() throws ParkingException {
        ParkingLot pl;
        try {
            pl = new ParkingLot(0);
        } catch (ParkingException e) {
            assertSame(e.getParkingError(), ParkingError.PL_INVALID_CAPACITY);
        }

        pl = new ParkingLot(6);

        Car car1 = new Car("KA-01-HH-1234", "White");
        Slot slot1 = pl.parkCarInSlot(car1);
        assertEquals((int)slot1.getId(), 1);

        Car car2 = new Car("KA-01-HH-9999", "White");
        Slot slot2 = pl.parkCarInSlot(car2);
        assertEquals((int)slot2.getId(), 2);

        Car car3 = new Car("KA-01-BB-0001", "Black");
        Slot slot3 = pl.parkCarInSlot(car3);
        assertEquals((int)slot3.getId(), 3);

        Car car4 = new Car("KA-01-HH-7777", "Red");
        Slot slot4 = pl.parkCarInSlot(car4);
        assertEquals((int)slot4.getId(), 4);

        Car car5 = new Car("KA-01-HH-2701", "Blue");
        Slot slot5 = pl.parkCarInSlot(car5);
        assertEquals((int)slot5.getId(), 5);

        Car car6 = new Car("KA-01-HH-3141", "Black");
        Slot slot6 = pl.parkCarInSlot(car6);
        assertEquals((int)slot6.getId(), 6);

        Slot unslot = new Slot(4);
        Car unslotCar = pl.unparkCarInSlot(unslot);

        assertEquals(unslotCar.getReg(), car4.getReg());
        assertEquals(unslotCar.getColor(), car4.getColor());

        List<Slot> expectedList = new ArrayList<>();
        expectedList.add(slot1);
        expectedList.add(slot2);
        expectedList.add(slot3);
        expectedList.add(slot5);
        expectedList.add(slot6);

        Iterator<Slot> expectedIterator = expectedList.iterator();

        PriorityQueue<Slot> actualQueue = pl.status();
        Slot[] actualArr = actualQueue.toArray(new Slot[actualQueue.size()]);
        Arrays.sort(actualArr, new Comparator<Slot>() {
            @Override
            public int compare(Slot o1, Slot o2) {
                return o1.getId() - o2.getId();
            }
        });

        for (Slot aSlot : actualArr) {
            Slot eSlot = expectedIterator.next();

            assertEquals(eSlot, aSlot);
            assertEquals(eSlot.getParkedCar(), aSlot.getParkedCar());
        }

        Car car7 = new Car("KA-01-P-333", "White");
        Slot slot7 = pl.parkCarInSlot(car7);
        assertEquals((int)slot7.getId(), 4);

        Car car8 = new Car("DL-12-AA-9999", "White");

        Slot slot8;
        try {
            slot8 = pl.parkCarInSlot(car8);
        } catch (ParkingException pe) {
            assertEquals(pe.getParkingError(), ParkingError.PL_FULL);
        }

        List<String> actualRegList = pl.regNoForColor("White");
        String[] expectedRegArr = {"KA-01-HH-1234", "KA-01-HH-9999", "KA-01-P-333"};
        assertArrayEquals("Registration No list is not equal", actualRegList.toArray(), expectedRegArr);

        List<Integer> actualSlotList = pl.slotNoForColor("White");
        Integer[] expectedSlotArr = {1, 2, 4};
        assertArrayEquals("Slot No list is not equeal", actualSlotList.toArray(), expectedSlotArr);

        Slot findSlot = pl.getSlotFromRegno("KA-01-HH-3141");
        assertEquals(findSlot.getId(), (Integer) 6);

        try {
            pl.getSlotFromRegno("MH-04-AY-1111");
        } catch (ParkingException pe) {
            assertEquals(pe.getParkingError(), ParkingError.PL_CAR_NOT_FOUND);
        }

        try {
            pl.regNoForColor("Yellow");
        } catch (ParkingException pe) {
            assertEquals(pe.getParkingError(), ParkingError.PL_CAR_NOT_FOUND);
        }

        try {
            pl.slotNoForColor("Yellow");
        } catch (ParkingException pe) {
            assertEquals(pe.getParkingError(), ParkingError.PL_CAR_NOT_FOUND);
        }

        Slot unslot2 = new Slot(3);
        Car unslotCar2 = pl.unparkCarInSlot(unslot2);
        assertEquals(unslotCar2.getReg(), car3.getReg());
        assertEquals(unslotCar2.getColor(), car3.getColor());

        try {
            pl.unparkCarInSlot(unslot2);
        } catch (ParkingException pe) {
            assertEquals(pe.getParkingError(), ParkingError.PL_SLOT_NOCAR);
        }

        Slot unslot3 = new Slot(8);
        try {
            pl.unparkCarInSlot(unslot3);
        } catch (ParkingException pe) {
            assertEquals(pe.getParkingError(), ParkingError.PL_SLOT_INVALID);
        }

        Car dummyCar = new Car("KA-01-HH-3141", "Green");
        try {
            pl.parkCarInSlot(dummyCar);
        } catch (ParkingException pe) {
            assertEquals(pe.getParkingError(), ParkingError.PL_CAR_PARKED_REG_DUB);
        }
    }
}