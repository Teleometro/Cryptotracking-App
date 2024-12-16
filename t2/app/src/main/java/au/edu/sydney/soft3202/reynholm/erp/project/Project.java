package au.edu.sydney.soft3202.reynholm.erp.project;

public interface Project {
    public static Project makeProject(int id, String name, double standardRate, double overDifference) throws IllegalArgumentException
    {
        return null;
    }
    public int getId();
    public String getName();
    public double getOverDifference();
}
