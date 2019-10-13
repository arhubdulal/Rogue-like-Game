package byow.Core;

import java.util.Random;

public class Hallway {
    private int length;
    private int orientation; // 0 means horizontal, 1 means vertical
    private int direction;
    private Location start;


    public Hallway(int length, int orientation, Location start, int direction) {
        this.length = length;
        this.orientation = orientation;
        this.start = start;
        this.direction = direction;
    }

    public Location getPoint(Random r) {
        if (orientation == 0) {
            return new Location(start.x + r.nextInt(this.length), start.y);
        } else {
            return new Location(start.x, start.y + r.nextInt(this.length));
        }
    }

    public int length() {
        return length;
    }

    public int orientation() {
        return orientation;
    }

    public int direction() {
        return direction;
    }

    public Location start() {
        return start;
    }

    public void changeDirection() {
        direction *= -1;
    }


}
