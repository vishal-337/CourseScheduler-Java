/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ACER
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

public class ScheduleQueries {
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addScheduleEntry;
    private static PreparedStatement getScheduleByStudent;
    private static PreparedStatement getScheduledStudentCount;
    private static PreparedStatement getScheduledStudentsByCourse;
    private static PreparedStatement getWaitlistedStudentsByCourse;
    private static PreparedStatement dropStudentScheduleByCourse;
    private static PreparedStatement dropScheduleByCourse;
    private static PreparedStatement updateScheduleEntry;
    private static ResultSet resultSet;
    
    
    public static void addScheduleEntry(ScheduleEntry entry) 
    {
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
        connection = DBConnection.getConnection();
        try
        {
            addScheduleEntry = connection.prepareStatement("insert into app.schedules(Semester,CourseCode,StudentID,Status,timestamp) values (?,?,?,?,?)");
            addScheduleEntry.setString(1,entry.getSemester());
            addScheduleEntry.setString(2,entry.getCourseCode());
            addScheduleEntry.setString(3,entry.getStudentID());
            addScheduleEntry.setString(4,entry.getStatus());
            addScheduleEntry.setTimestamp(5,currentTimestamp);
            addScheduleEntry.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID) 
    {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> Schedules = new ArrayList<ScheduleEntry>();
        try
        {
            getScheduleByStudent = connection.prepareStatement("select * from app.schedules where Semester=? and StudentID = ?");
            getScheduleByStudent.setString(1,semester);
            getScheduleByStudent.setString(2,studentID);
            resultSet = getScheduleByStudent.executeQuery();
            
            while(resultSet.next())
            {
                          Schedules.add(new ScheduleEntry(resultSet.getString("Semester"),resultSet.getString("CourseCode"),resultSet.getString("StudentID"),resultSet.getString("Status")) );
      
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return Schedules;
    }
    
   public static int getScheduledStudentCount(String currentSemester, String courseCode) {
       connection = DBConnection.getConnection();
       int count = 0;
       try
       {
           getScheduledStudentCount = connection.prepareStatement("select count(studentID) from app.schedules where semester = ? and courseCode = ? and status = ?");
           getScheduledStudentCount.setString(1,currentSemester);
           getScheduledStudentCount.setString(2,courseCode);
           getScheduledStudentCount.setString(3,"S");
           resultSet = getScheduledStudentCount.executeQuery();
           while(resultSet.next())
            {
                count = resultSet.getInt(1);
            }
       }
       catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
       return count;
   }
   
   public static ArrayList<ScheduleEntry> getScheduledStudentsByCourse(String semester, String courseCode) 
    {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> Schedules = new ArrayList<ScheduleEntry>();
        try
        {
            getScheduledStudentsByCourse = connection.prepareStatement("select * from app.schedules where Semester=? and coursecode = ? and status=?");
            getScheduledStudentsByCourse.setString(1,semester);
            getScheduledStudentsByCourse.setString(2,courseCode);
            getScheduledStudentsByCourse.setString(3,"S");
            resultSet = getScheduledStudentsByCourse.executeQuery();
            
            while(resultSet.next())
            {
                          Schedules.add(new ScheduleEntry(resultSet.getString("Semester"),resultSet.getString("CourseCode"),resultSet.getString("StudentID"),resultSet.getString("Status")) );
      
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return Schedules;
    }
   
   public static ArrayList<ScheduleEntry> getWaitlistedStudentsByCourse(String semester, String courseCode) 
    {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> Schedules = new ArrayList<ScheduleEntry>();
        try
        {
            getWaitlistedStudentsByCourse = connection.prepareStatement("select * from app.schedules where Semester=? and coursecode = ? and status=? ORDER BY TIMESTAMP ASC");
            getWaitlistedStudentsByCourse.setString(1,semester);
            getWaitlistedStudentsByCourse.setString(2,courseCode);
            getWaitlistedStudentsByCourse.setString(3,"W");
            resultSet = getWaitlistedStudentsByCourse.executeQuery();
            
            while(resultSet.next())
            {
                          Schedules.add(new ScheduleEntry(resultSet.getString("Semester"),resultSet.getString("CourseCode"),resultSet.getString("StudentID"),resultSet.getString("Status")) );
      
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return Schedules;
    }
   public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode)
    {
        connection = DBConnection.getConnection();
        try
        {
            dropStudentScheduleByCourse = connection.prepareStatement("delete from app.schedules where semester=? and studentid=? and CourseCode=?");
            dropStudentScheduleByCourse.setString(1,semester);
            dropStudentScheduleByCourse.setString(2,studentID);
            dropStudentScheduleByCourse.setString(3,courseCode);
            dropStudentScheduleByCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
   public static void dropScheduleByCourse(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        try
        {
            dropScheduleByCourse = connection.prepareStatement("delete from app.schedules where semester=? and CourseCode=?");
            dropScheduleByCourse.setString(1,semester);
            dropScheduleByCourse.setString(2,courseCode);
            dropScheduleByCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
   public static void updateScheduleEntry(String semester, ScheduleEntry entry)
   {
       java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
       connection = DBConnection.getConnection();
        try
        {
            updateScheduleEntry = connection.prepareStatement("update app.schedules set status=?,timestamp=?  where semester=? and studentid=? and CourseCode=?");
            updateScheduleEntry.setString(1,"S");
            updateScheduleEntry.setTimestamp(2,currentTimestamp);
            updateScheduleEntry.setString(3,semester);
            updateScheduleEntry.setString(4,entry.getStudentID());
            updateScheduleEntry.setString(5,entry.getCourseCode());
            updateScheduleEntry.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
       
   }
  // public static void dropScheduleByStudent(String studentID)
    //{
    //    connection = DBConnection.getConnection();
       // try
       // {
         //   dropScheduleByStudent = connection.prepareStatement("delete from app.schedules where studentid=?");
           // dropScheduleByStudent.setString(1,studentID);
            //dropScheduleByStudent.executeUpdate();
   //     }
     //   catch(SQLException sqlException)
       // {
       //     sqlException.printStackTrace();
        //}
        
    //}
    
}

