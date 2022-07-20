package edu.tntech.csc2310;

public class CourseNotFoundException extends Exception
{
    public CourseNotFoundException()
    {
        super();
    }

    public CourseNotFoundException(String message)
    {
        super(message);
    }

    @Override
    public String getMessage()
    {
        return "Course Search: " + super.getMessage();
    }

    @Override
    public String toString()
    {
        return this.getMessage();
    }
}
