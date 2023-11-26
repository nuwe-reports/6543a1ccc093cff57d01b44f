
package com.example.demo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import java.time.LocalDateTime;
import java.time.format.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.demo.controllers.*;
import com.example.demo.repositories.*;
import com.example.demo.entities.*;
import com.fasterxml.jackson.databind.ObjectMapper;



/** TODO
 * Implement all the unit test in its corresponding class.
 * Make sure to be as exhaustive as possible. Coverage is checked ;)
*/

@WebMvcTest(DoctorController.class)
class DoctorControllerUnitTest {

    @MockBean
    private DoctorRepository doctorRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetAllDoctors() throws Exception {
        List<Doctor> doctors = new ArrayList<>();
        Doctor doctor1 = new Doctor("Joana", "Gómez", 30, "joana.gomez@email.com");
        Doctor doctor2 = new Doctor("Carlos", "Pérez", 35, "carlos.perez@email.com");
        doctors.add(doctor1);
        doctors.add(doctor2);

        when(doctorRepository.findAll()).thenReturn(doctors);

        mockMvc.perform(get("/api/doctors"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetNoDoctors() throws Exception {
        when(doctorRepository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetDoctorById() throws Exception {
        List<Doctor> doctors = new ArrayList<>();
        Doctor doctor = new Doctor("Joana", "Gómez", 30, "joana.gomez@email.com");

        doctor.setId(1);

        Optional<Doctor> opt = Optional.of(doctor);

        assertThat(opt).isPresent();
        assertThat(opt.get().getId()).isEqualTo(doctor.getId());
        assertThat(doctor.getId()).isEqualTo(1);

        when(doctorRepository.findById(doctor.getId())).thenReturn(opt);
        mockMvc.perform(get("/api/doctors/" + doctor.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotGetAnyDoctorById() throws Exception {
        long id = 12;
        mockMvc.perform(get("/api/doctors/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateDoctor() throws Exception {
        Doctor doctor = new Doctor("Joana", "Gómez", 30, "joana.gomez@email.com");

        mockMvc.perform(post("/api/doctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doctor)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldDeleteDoctorById() throws Exception {
        Doctor doctor = new Doctor("John", "Doe", 35, "john.doe@example.com");

        doctor.setId(1);

        Optional<Doctor> opt = Optional.of(doctor);

        assertThat(opt).isPresent();
        assertThat(opt.get().getId()).isEqualTo(doctor.getId());
        assertThat(doctor.getId()).isEqualTo(1);

        when(doctorRepository.findById(doctor.getId())).thenReturn(opt);
        mockMvc.perform(delete("/api/doctors/" + doctor.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotDeleteDoctor() throws Exception{
        long id = 5;
        mockMvc.perform(delete("/api/doctors/" + id))
                .andExpect(status().isNotFound());

    }

    @Test
    void shouldDeleteAllDoctors() throws Exception {
        Doctor doctor1 = new Doctor("Joana", "Gómez", 30, "joana.gomez@email.com");
        Doctor doctor2 = new Doctor("Carlos", "Pérez", 35, "carlos.perez@email.com");

        List<Doctor> doctors = Arrays.asList(doctor1, doctor2);
        when(doctorRepository.findAll()).thenReturn(doctors);

        mockMvc.perform(delete("/api/doctors"))
                .andExpect(status().isOk());
    }

}

@WebMvcTest(PatientController.class)
class PatientControllerUnitTest{

    @MockBean
    private PatientRepository patientRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetAllPatients() throws Exception {
        List<Patient> patients = new ArrayList<>();
        Patient patient1 = new Patient("Marta", "Fernández", 28, "marta.fernandez@email.com");
        Patient patient2 = new Patient("Jordi", "Martínez", 40, "jordi.martinez@email.com");
        patients.add(patient1);
        patients.add(patient2);

        when(patientRepository.findAll()).thenReturn(patients);

        mockMvc.perform(get("/api/patients"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetNoPatients() throws Exception {
        when(patientRepository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getPatientById() throws Exception {
        Patient patient = new Patient("Marta", "Fernández", 28, "marta.fernandez@email.com");

        patient.setId(1);

        Optional<Patient> opt = Optional.of(patient);

        assertThat(opt).isPresent();
        assertThat(opt.get().getId()).isEqualTo(patient.getId());
        assertThat(patient.getId()).isEqualTo(1);

        when(patientRepository.findById(patient.getId())).thenReturn(opt);
        mockMvc.perform(get("/api/patients/" + patient.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotGetAnyPatientById() throws Exception {
        long id = 12;
        mockMvc.perform(get("/api/patients/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreatePatient() throws Exception {
        Patient patient = new Patient("Marta", "Fernández", 28, "marta.fernandez@email.com");

        mockMvc.perform(post("/api/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldDeletePatientById() throws Exception {
        Patient patient = new Patient("Marta", "Fernández", 28, "marta.fernandez@email.com");

        patient.setId(1);

        Optional<Patient> opt = Optional.of(patient);

        assertThat(opt).isPresent();
        assertThat(opt.get().getId()).isEqualTo(patient.getId());
        assertThat(patient.getId()).isEqualTo(1);

        when(patientRepository.findById(patient.getId())).thenReturn(opt);
        mockMvc.perform(delete("/api/patients/" + patient.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotDeletePatient() throws Exception{
        long id = 5;
        mockMvc.perform(delete("/api/patients/" + id))
                .andExpect(status().isNotFound());

    }

    @Test
    void shouldDeleteAllPatients() throws Exception {
        Patient patient1 = new Patient("Marta", "Fernández", 28, "marta.fernandez@email.com");
        Patient patient2 = new Patient("Jordi", "Martínez", 40, "jordi.martinez@email.com");

        List<Patient> patients = Arrays.asList(patient1, patient2);
        when(patientRepository.findAll()).thenReturn(patients);

        mockMvc.perform(delete("/api/patients"))
                .andExpect(status().isOk());
    }
}

@WebMvcTest(RoomController.class)
class  RoomControllerUnitTest{

    @MockBean
    private RoomRepository roomRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetAllRooms() throws Exception {
        List<Room> rooms = new ArrayList<>();
        Room room1 = new Room("101");
        Room room2 = new Room("202");
        rooms.add(room1);
        rooms.add(room2);

        when(roomRepository.findAll()).thenReturn(rooms);

        mockMvc.perform(get("/api/rooms"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetNoRooms() throws Exception {
        when(roomRepository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetRoomByRoomName() throws Exception {
        String roomName = "303";
        Room room = new Room(roomName);

        Optional<Room> opt = Optional.of(room);

        assertThat(opt).isPresent();
        assertThat(opt.get().getRoomName()).isEqualTo(room.getRoomName());

        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(opt);
        mockMvc.perform(get("/api/rooms/" + room.getRoomName()))
                .andExpect(status().isOk());
    }

    void shouldNotGetAnyRoomByName() throws Exception {
        String roomName = "345";
        mockMvc.perform(get("/api/rooms/" + roomName))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateRoom() throws Exception {
        Room room = new Room("404");

        mockMvc.perform(post("/api/room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(room)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldDeleteRoomByRoomName() throws Exception {
        String roomName = "505";
        Room room = new Room(roomName);

        Optional<Room> opt = Optional.of(room);

        assertThat(opt).isPresent();
        assertThat(opt.get().getRoomName()).isEqualTo(room.getRoomName());

        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(opt);
        mockMvc.perform(delete("/api/rooms/" + room.getRoomName()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotDeleteRoom() throws Exception{
        String roomName = "345";
        mockMvc.perform(delete("/api/patients/" + roomName))
                .andExpect(status().isNotFound());

    }

    @Test
    void shouldDeleteAllRooms() throws Exception {
        Room room1 = new Room("606");
        Room room2 = new Room("707");

        List<Room> rooms = Arrays.asList(room1, room2);
        when(roomRepository.findAll()).thenReturn(rooms);

        mockMvc.perform(delete("/api/rooms"))
                .andExpect(status().isOk());
    }

}
