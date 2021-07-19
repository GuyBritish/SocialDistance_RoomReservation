package Index;

import java.util.ArrayList;

public class Room {

    private static ArrayList<String> names = new ArrayList<>();
    private String name;
    private int currOccupancy;
    private Person[] occupants;

    public Room(String name, int capacity) {
        //invalid capacity
        if (capacity <= 0) {
            throw new IllegalArgumentException("Room's capacity must be greater than 0.");
        }
        //check the room name's availability
        for (int i = 0; i <= names.size() - 1; ++i) {
            if (name.equals(names.get(i))) {
                throw new IllegalArgumentException("This room-name is already in use.");
            }
        }
        this.name = name;
        names.add(name);
        currOccupancy = 0;
        occupants = new Person[capacity];
    }

    public static String[] getNames() {
        String[] list = new String[names.size()];
        for (int i = 0; i <= names.size() - 1; ++i) {
            list[i] = names.get(i);
        }
        return list;
    }

    public String getName() { return name; }

    public int getOccupancy() { return currOccupancy; }

    public int getCOVIDCapacity() { return (occupants.length + 1) / 2; }

    public int getCapacity() {
        return occupants.length;
    }

    public boolean contains(Person p) {
        for (int i = 0; i <= occupants.length - 1; ++i) {
            if ((occupants[i] != null) && (p.equals(occupants[i]))) {
                return true;
            }
        }
        return false;
    }

    public boolean checkIn(Person p) throws IllegalArgumentException {
        // null parameter
        if (p == null) {
            throw new IllegalArgumentException("The person checking in cannot be null.");
        }
        // person is in the room
        if (contains(p)) {
            throw new IllegalArgumentException("The person is already in the room.");
        }
        if (currOccupancy >= getCOVIDCapacity()) {
            return false;
        }
        for (int i = 0; i <= occupants.length - 1; ++i) {
            if ((i % 2 == 0) && (occupants[i] == null)) {
                //check in the person and update occupancy and isWaiting status
                occupants[i] = p;
                ++currOccupancy;
                p.toggleWaiting();
                return true;
            }
        }
        return false;
    }

    public boolean checkOut(Person p) throws IllegalArgumentException {
        // null parameter
        if (p == null) {
            throw new IllegalArgumentException("The person checking out cannot be null.");
        }
        // person is not in the room
        if (!contains(p)) {
            return false;
        }
        for (int i = 0; i <= occupants.length - 1; ++i) {
            if ((occupants[i] != null) && (occupants[i].equals(p))) {
                //check out the person and update occupancy and isWaiting status
                occupants[i] = null;
                --currOccupancy;
                p.toggleWaiting();
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String str = name + "\n===\n";
        for (int i = 0; i <= occupants.length - 1; ++i) {
            if (occupants[i] == null) {
                str += "-\n";
            } else {
                str += occupants[i].getName() + "\n";
            }
        }
        return str;
    }

}
