package com.maverick.medklick.others;

/**
 * Created by sudha on 24-Mar-18.
 */

public class DoctorAppointments {

    public String hospitalName, departmentName, doctorName, doctorFee, dateOfAppointment, appointmentStatus;

    public DoctorAppointments() {
    }

    public DoctorAppointments(String hospitalName, String departmentName, String dateOfAppointment) {
        this.hospitalName = hospitalName;
        this.departmentName = departmentName;
        this.dateOfAppointment = dateOfAppointment;
    }

    public DoctorAppointments(String doctorName, String doctorFee) {
        this.doctorName = doctorName;
        this.doctorFee = doctorFee;
    }

    public DoctorAppointments(String hospitalName, String departmentName, String doctorName, String dateOfAppointment, String appointmentStatus) {
        this.hospitalName = hospitalName;
        this.departmentName = departmentName;
        this.doctorName = doctorName;
        this.dateOfAppointment = dateOfAppointment;
        this.appointmentStatus = appointmentStatus;
    }
}
