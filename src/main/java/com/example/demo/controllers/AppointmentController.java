package com.example.demo.controllers;

import com.example.demo.repositories.*;
import com.example.demo.entities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class AppointmentController {

    @Autowired
    AppointmentRepository appointmentRepository;

    @GetMapping("/appointments")
    public ResponseEntity<List<Appointment>> getAllAppointments(){
        List<Appointment> appointments = new ArrayList<>();

        appointmentRepository.findAll().forEach(appointments::add);

        if (appointments.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping("/appointments/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable("id") long id){
        Optional<Appointment> appointment = appointmentRepository.findById(id);

        if (appointment.isPresent()){
            return new ResponseEntity<>(appointment.get(),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/appointment")
    public ResponseEntity<List<Appointment>> createAppointment(@RequestBody Appointment appointment){
        /** TODO 
         * Implement this function, which acts as the POST /api/appointment endpoint.
         * Make sure to check out the whole project. Specially the Appointment.java class
         */

        List<Appointment> appointmentsList = new ArrayList<>();
        List<Appointment> newAppointments = new ArrayList<>();

        // Check if any required field in the appointment is null
        if (appointment == null || appointment.getPatient() == null || appointment.getDoctor() == null || appointment.getRoom() == null ||
                appointment.getStartsAt() == null || appointment.getFinishesAt() == null) {
            return new ResponseEntity<>(newAppointments, HttpStatus.BAD_REQUEST);
        }

        // Check if the appointment end time is before or equal to the start time
        if (appointment.getFinishesAt().isBefore(appointment.getStartsAt()) || appointment.getFinishesAt().isEqual(appointment.getStartsAt())) {
            return new ResponseEntity<>(newAppointments, HttpStatus.BAD_REQUEST);
        }

        Appointment a = new Appointment(appointment.getPatient(), appointment.getDoctor(), appointment.getRoom(),appointment.getStartsAt(),appointment.getFinishesAt());
        appointmentRepository.findAll().forEach(appointmentsList::add);

        // Check for overlaps
        for (Appointment registeredAppointment : appointmentsList) {
            if (a.overlaps(registeredAppointment)) {
                return new ResponseEntity<>(newAppointments, HttpStatus.NOT_ACCEPTABLE);
            }
        }

        // Create appointment
        try {
            appointmentRepository.save(a);
            newAppointments.add(a);
            return new ResponseEntity<>(newAppointments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(newAppointments, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @DeleteMapping("/appointments/{id}")
    public ResponseEntity<HttpStatus> deleteAppointment(@PathVariable("id") long id){

        Optional<Appointment> appointment = appointmentRepository.findById(id);

        if (!appointment.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        appointmentRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
        
    }

    @DeleteMapping("/appointments")
    public ResponseEntity<HttpStatus> deleteAllAppointments(){
        appointmentRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
