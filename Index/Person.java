package Index;

public class Person {
    private String name;
    private boolean isWaiting;

    public Person(String name) {
        this.name = name;
        isWaiting = true;
    }

    public String getName() { return name; }
    public Boolean isWaiting() { return isWaiting; }

    public void toggleWaiting() { isWaiting = !isWaiting; }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Person) {
            return this.name.equals(((Person)o).name);
        } else {
            return false;
        }
    }
}
