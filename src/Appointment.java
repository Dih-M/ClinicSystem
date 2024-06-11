import java.sql.Date;
import java.sql.Time;

public class Appointment {
    private int id;
    private int patientId;
    private int psychologistId;
    private Date appointmentDate;
    private Time appointmentTime;
    private String location;

    public Appointment(int id, int patientId, int psychologistId, Date appointmentDate, Time appointmentTime, String location) {
        this.id = id;
        this.patientId = patientId;
        this.psychologistId = psychologistId;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public int getPatientId() {
        return patientId;
    }

    public int getPsychologistId() {
        return psychologistId;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public Time getAppointmentTime() {
        return appointmentTime;
    }

    public String getLocation() {
        return location;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public void setAppointmentTime(Time appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
