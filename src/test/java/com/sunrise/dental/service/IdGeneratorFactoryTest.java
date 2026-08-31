package com.sunrise.dental.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdGeneratorFactoryTest {

    // ID-01
    @Test
    void generate_withBillType_returnsBillIdWithCorrectFormat() {

        String id = IdGeneratorFactory.generate(
                IdGeneratorFactory.IdType.BILL
        );

        assertNotNull(id);
        assertTrue(id.startsWith("B"));
        assertEquals(9, id.length());
    }

    // ID-02
    @Test
    void generate_withAppointmentType_returnsAppointmentIdWithCorrectFormat() {

        String id = IdGeneratorFactory.generate(
                IdGeneratorFactory.IdType.APPOINTMENT
        );

        assertNotNull(id);
        assertTrue(id.startsWith("A"));
        assertEquals(9, id.length());
    }

    // ID-03
    @Test
    void generate_billId_containsUppercaseCharactersAndNumbers() {

        String id = IdGeneratorFactory.generate(
                IdGeneratorFactory.IdType.BILL
        );

        assertTrue(id.matches("B[A-Z0-9]{8}"));
    }

    // ID-04
    @Test
    void generate_appointmentId_containsUppercaseCharactersAndNumbers() {

        String id = IdGeneratorFactory.generate(
                IdGeneratorFactory.IdType.APPOINTMENT
        );

        assertTrue(id.matches("A[A-Z0-9]{8}"));
    }

    // ID-05
    @Test
    void generate_multipleIds_producesUniqueIds() {

        String id1 = IdGeneratorFactory.generate(
                IdGeneratorFactory.IdType.BILL
        );

        String id2 = IdGeneratorFactory.generate(
                IdGeneratorFactory.IdType.BILL
        );

        assertNotEquals(id1, id2);
    }

    // ID-06
    @Test
    void generate_withNullType_throwsException() {

        assertThrows(
                IllegalArgumentException.class,
                () -> IdGeneratorFactory.generate(null)
        );
    }
}