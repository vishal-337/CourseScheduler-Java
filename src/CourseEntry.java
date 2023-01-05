/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ACER
 */
public class CourseEntry {
    private String Semester;
    private String CourseCode;
    private String Description;
    private int Seats;
    private int Seatsleft;
    
    public CourseEntry(String Semester,String CourseCode,String Description,int Seats){
        setSemester(Semester);
        setCourseCode(CourseCode);
        setDescription(Description);
        setSeats(Seats);
    }

    public void setSemester(String Semester) {
        this.Semester = Semester;
    }

    public void setCourseCode(String CourseCode) {
        this.CourseCode = CourseCode;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public void setSeats(int Seats) {
       
        this.Seats = Seats;
    }

    public String getSemester() {
        return Semester;
    }

    public String getCourseCode() {
        return CourseCode;
    }

    public String getDescription() {
        return Description;
    }

    public int getSeats() {
        return Seats;
    }
    
}
