package demo;

public class BeanDemo {

    private String name;
    private String id;
    private String standing;

    public BeanDemo(String name, String id, String standing) {
        this.name = name;
        this.id = id;
        this.standing = standing;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getStanding() {
        return standing;
    }
}
