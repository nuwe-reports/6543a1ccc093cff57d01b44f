package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.example.demo.entities.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
class EntityUnitTest {

	@Autowired
	private TestEntityManager entityManager;

	private Doctor d1;

	private Patient p1;

    private Room r1;

    private Appointment a1;
    private Appointment a2;
    private Appointment a3;

    /** TODO
     * Implement tests for each Entity class: Doctor, Patient, Room and Appointment.
     * Make sure you are as exhaustive as possible. Coverage is checked ;)
     */

    /** Doctor Entity test
     *
    */
    @Test
    void createDoctorInstanceWithoutAttributes() {
        Doctor invalidDoctor = new Doctor();
        assertThat(invalidDoctor).isNotNull();
    }

    @Test
    void createDoctorInstance() {
        d1 = new Doctor("Shaun", "Murphy", 20, "shaun.murphy@thegooddoctor.com");
        assertThat(d1).isNotNull();
        assertThat(d1.getFirstName()).isEqualTo("Shaun");
        assertThat(d1.getLastName()).isEqualTo("Murphy");
        assertThat(d1.getAge()).isEqualTo(20);
        assertThat(d1.getEmail()).isEqualTo("shaun.murphy@thegooddoctor.com");
    }

    @Test
    void createDoctorInstanceWithNullFirstName() {
        Doctor invalidDoctor = new Doctor(null, "Murphy", 20, "shaun.murphy@thegooddoctor.com");
        assertThat(invalidDoctor).isNull();
    }

    @Test
    void createDoctorInstanceWithNullLastName() {
        Doctor invalidDoctor = new Doctor("Shaun", null, 20, "shaun.murphy@thegooddoctor.com");
        assertThat(invalidDoctor).isNull();
    }

    @Test
    void createDoctorInstanceWithEmptyFirstName() {
        Doctor invalidDoctor = new Doctor("", "Murphy", 25, "empty.first.name@example.com");
        assertThat(invalidDoctor).isNull();
    }

    @Test
    void createDoctorInstanceWithEmptyLastName() {
        Doctor invalidDoctor = new Doctor("Shaun", "", 20, "shaun.murphy@thegooddoctor.com");
        assertThat(invalidDoctor).isNull();
    }

    @Test
    void createDoctorInstanceWithNegativeAge() {
        Doctor invalidDoctor = new Doctor("John", "Doe", -5, "john.doe@example.com");
        assertThat(invalidDoctor).isNull();
    }

    @Test
    void createDoctorInstanceWithNullEmail() {
        Doctor invalidDoctor = new Doctor("Alice", "Johnson", 30, null);
        assertThat(invalidDoctor).isNull();
    }

    @Test
    void createDoctorInstanceWithInvalidEmailFormat() {
        Doctor invalidDoctor = new Doctor("Bob", "Smith", 40, "invalid-email-format");
        assertThat(invalidDoctor).isNull();
    }

    /** Patient
     *
     */
    @Test
    void createPatientInstanceWithoutAttributes() {
        Patient invalidPatient = new Patient();
        assertThat(invalidPatient).isNotNull();
    }

    @Test
    void createPatientInstance() {
        p1 = new Patient("Alice", "Johnson", 30, "alice.johnson@example.com");
        assertThat(p1).isNotNull();
        assertThat(p1.getFirstName()).isEqualTo("Alice");
        assertThat(p1.getLastName()).isEqualTo("Johnson");
        assertThat(p1.getAge()).isEqualTo(30);
        assertThat(p1.getEmail()).isEqualTo("alice.johnson@example.com");
    }

    @Test
    void createPatientInstanceWithNullFirstName() {
        Patient invalidPatient = new Patient(null, "Johnson", 25, "johnson@example.com");
        assertThat(invalidPatient).isNull();
    }

    @Test
    void createPatientInstanceWithNullLastName() {
        Patient invalidPatient = new Patient("Alice", null, 30, "alice.johnson@example.com");
        assertThat(invalidPatient).isNull();
    }

    @Test
    void createPatientInstanceWithEmptyFirstName() {
        Patient invalidPatient = new Patient("", "Johnson", 25, "empty.first.name@example.com");
        assertThat(invalidPatient).isNull();
    }

    @Test
    void createPatientInstanceWithEmptyLastName() {
        Patient invalidPatient = new Patient("Alice", "", 30, "alice.johnson@example.com");
        assertThat(invalidPatient).isNull();
    }

    @Test
    void createPatientInstanceWithNegativeAge() {
        Patient invalidPatient = new Patient("John", "Doe", -5, "john.doe@example.com");
        assertThat(invalidPatient).isNull();
    }

    @Test
    void createPatientInstanceWithNullEmail() {
        Patient invalidPatient = new Patient("Alice", "Johnson", 30, null);
        assertThat(invalidPatient).isNull();
    }

    @Test
    void createPatientInstanceWithInvalidEmailFormat() {
        Patient invalidPatient = new Patient("Bob", "Smith", 40, "invalid-email-format");
        assertThat(invalidPatient).isNull();
    }

    /** Room
     *
     */
    @Test
    void createRoomInstanceWithoutAttributes() {
        Room invalidRoom = new Room();
        assertThat(invalidRoom).isNotNull();
    }

    @Test
    void createRoomInstance() {
        r1 = new Room("101");
        assertThat(r1).isNotNull();
        assertThat(r1.getRoomName()).isEqualTo("101");
    }

    @Test
    void createRoomInstanceWithEmptyRoomName() {
        Room invalidRoom = new Room("");
        assertThat(invalidRoom).isNull();
    }


    /** Appointment
     *
     */
    @Test
    void createAppointmentInstance() {
        p1 = new Patient("Alice", "Johnson", 30, "alice.johnson@example.com");
        d1 = new Doctor("Shaun", "Murphy", 40, "shaun.murphy@thegooddoctor.com");
        r1 = new Room("101");

        LocalDateTime startsAt = LocalDateTime.now().plusHours(1);
        LocalDateTime finishesAt = startsAt.plusHours(2);

        a1 = new Appointment(p1, d1, r1, startsAt, finishesAt);
        assertThat(a1).isNotNull();
        assertThat(a1.getPatient()).isEqualTo(p1);
        assertThat(a1.getDoctor()).isEqualTo(d1);
        assertThat(a1.getRoom()).isEqualTo(r1);
        assertThat(a1.getStartsAt()).isEqualTo(startsAt);
        assertThat(a1.getFinishesAt()).isEqualTo(finishesAt);
    }

    @Test
    void createAppointmentInstanceWithoutAttributes() {
        Appointment invalidAppointment = new Appointment();
        assertThat(invalidAppointment).isNotNull();
    }

    @Test
    void createAppointmentInstanceWithNullPatient() {
        Appointment invalidAppointment = new Appointment(null, d1, r1, LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        assertThat(invalidAppointment).isNull();
    }

    @Test
    void createAppointmentInstanceWithNullDoctor() {
        Appointment invalidAppointment = new Appointment(p1, null, r1, LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        assertThat(invalidAppointment).isNull();
    }

    @Test
    void createAppointmentInstanceWithNullRoom() {
        Appointment invalidAppointment = new Appointment(p1, d1, null, LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        assertThat(invalidAppointment).isNull();
    }

    @Test
    void createAppointmentInstanceWithNullStartsAt() {
        Appointment invalidAppointment = new Appointment(p1, d1, r1, null, LocalDateTime.now().plusHours(1));
        assertThat(invalidAppointment).isNull();
    }

    @Test
    void createAppointmentInstanceWithNullFinishesAt() {
        Appointment invalidAppointment = new Appointment(p1, d1, r1, LocalDateTime.now(), null);
        assertThat(invalidAppointment).isNull();
    }

    @Test
    void createAppointmentInstanceWithStartsAtAfterFinishesAt() {
        LocalDateTime startsAt = LocalDateTime.now().plusHours(2);
        LocalDateTime finishesAt = LocalDateTime.now().plusHours(1);

        Appointment invalidAppointment = new Appointment(p1, d1, r1, startsAt, finishesAt);
        assertThat(invalidAppointment).isNull();
    }

    @Test
    void testAppointmentOverlap() {
        LocalDateTime startsAt = LocalDateTime.now().plusHours(1);
        LocalDateTime finishesAt = startsAt.plusHours(2);

        a1 = new Appointment(p1, d1, r1, startsAt, finishesAt);

        // Overlapping appointment
        Appointment overlappingAppointment = new Appointment(p1, d1, r1, startsAt.minusHours(1), finishesAt.plusHours(1));
        assertThat(a1.overlaps(overlappingAppointment)).isTrue();

        // Non-overlapping appointment
        Appointment nonOverlappingAppointment = new Appointment(p1, d1, r1, finishesAt.plusHours(1), finishesAt.plusHours(3));
        assertThat(a1.overlaps(nonOverlappingAppointment)).isFalse();
    }
}
