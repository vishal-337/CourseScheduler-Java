/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ACER
 */
//CourseEntry
//CourseQueries
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourseQueries {
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addCourse;
    private static PreparedStatement getAllCourses;
    private static PreparedStatement getAllCourseCodes;
    private static PreparedStatement getCourseSeats;
    private static PreparedStatement dropCourse;

    private static ResultSet resultSet;
    
    public static void addCourse(CourseEntry course)
    {
        connection = DBConnection.getConnection();
        try
        {
            addCourse = connection.prepareStatement("insert into app.courses(semester,CourseCode,Description,Seats) values (?,?,?,?)");
            addCourse.setString(1,course.getSemester());
            addCourse.setString(2,course.getCourseCode());
            addCourse.setString(3,course.getDescription());
            addCourse.setInt(4,course.getSeats());
            addCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<CourseEntry> getAllCourses(String semester)
    {
        connection = DBConnection.getConnection();
        ArrayList<CourseEntry> courses = new ArrayList<CourseEntry>();
        try
        {
            getAllCourses = connection.prepareStatement("select * from app.courses where semester=?");
            getAllCourses.setString(1,semester);
            resultSet = getAllCourses.executeQuery();
            
            while(resultSet.next())
            {
                courses.add(new CourseEntry(resultSet.getString("Semester"),resultSet.getString("CourseCode"),resultSet.getString("Description"),resultSet.getInt("Seats")) );
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courses;
    }
    
    public static ArrayList<String> getAllCourseCodes(String semester)
    {
        connection = DBConnection.getConnection();
        ArrayList<String> codes = new ArrayList<String>();
        try
        {
            getAllCourseCodes = connection.prepareStatement("select CourseCode from app.courses where semester=?");
            getAllCourseCodes.setString(1,semester);
            resultSet = getAllCourseCodes.executeQuery();
            
            while(resultSet.next())
            {
                codes.add(resultSet.getString(1) );
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return codes;
    }
    
    public static int getCourseSeats(String semester, String courseCode) 
    {
        connection = DBConnection.getConnection();
        int seats =0;
        try
        {
            getCourseSeats = connection.prepareStatement("select seats from app.courses where semester=? and CourseCode=? ");
            getCourseSeats.setString(1,semester);
            getCourseSeats.setString(2,courseCode);
            resultSet = getCourseSeats.executeQuery();
            while(resultSet.next())
            {
                seats=resultSet.getInt(1);
            }
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return seats;
    }
    
    public static void dropCourse(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        try
        {
            dropCourse = connection.prepareStatement("delete from app.courses where semester=? and CourseCode=?");
            dropCourse.setString(1,semester);
            dropCourse.setString(2,courseCode);
            dropCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    

    
    
}
