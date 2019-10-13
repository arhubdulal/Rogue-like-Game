package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Generation {
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private static Avatar character;
    private static Location avatarLocation;
    private TERenderer ter;
    private Long value;
    private TETile[][] world;
    private Random r;
    //private ArrayList<Location> floors;
    private HashMap<Integer, Location> floors;
    private Room roomie;

    public Generation(Long seed, TETile[][] world, Location l) {
        this.world = world;
        this.value = seed;
        r = new Random(value);
        this.drawBlank(world);
        floors = new HashMap<>();
        roomie = new Room();
        avatarLocation = l;
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] tiles = new TETile[WIDTH][HEIGHT];
        Long seed = 12312345L;
        Generation gen = new Generation(seed, tiles, null);
        gen.createWorld();
        ter.renderFrame(tiles);
    }

    public TETile[][] createWorld() {

        // randomly build hallways
        Location start = new Location(5, 5);


        Hallway h;
        int length = 4;
        int orientation = r.nextInt(2);
        int direction = 1;
        int forer = r.nextInt(8) + 13;


        for (int i = 0; i < forer; i++) {


            orientation = Math.abs(orientation - 1);

            if (direction == 0) {
                direction = -1;
            }

            h = new Hallway(length, orientation, start, direction);
            if (!verifyHall(h)) {
                h.changeDirection();
                direction = h.direction();
            }
            if (verifyHall(h)) {
                buildHallway(world, h);
            }
            //start = floors.get(r.nextInt(floors.size()));

            start = floors.get(r.nextInt(h.length()));

            roomie.createRoom(world, r, floors);


            length = r.nextInt(10) + 10;


        }

        //roomie.createRoom(world, r, floors);
        //roomie.createRoom(world, r, floors);
        //roomie.createRoom(world, r, floors);
        //roomie.createRoom(world, r, floors);
        //roomie.createRoom(world, r, floors);


        buildWalls(world);

        return world;


    }

    private boolean verifyHall(Hallway hall) {

        if (hall.start() == null) {
            return false;
        }
        if (hall.orientation() == 0) {
            for (int i = 0; i < hall.length(); i++) {
                if (!verifyFloor(new Location((hall.start().x +
                        (hall.direction() * i)), (hall.start().y)))) {
                    return false;
                }
            }
        } else {
            for (int i = 0; i < hall.length(); i++) {
                if (!verifyFloor(new Location(hall.start().x, hall.start().y
                        + (hall.direction() * i)))) {
                    return false;
                }

            }

        }
        return true;
    }

    private void buildHallway(TETile[][] world1, Hallway hall) {
        if (hall.orientation() == 0) {
            for (int i = 0; i < hall.length(); i++) {
                if (verifyFloor(new Location(hall.start().x
                        + (hall.direction() * i), hall.start().y))) {
                    Location spot = new Location(hall.start().x
                            + (hall.direction() * i), hall.start().y);
                    world1[spot.x][spot.y] = Tileset.FLOOR;
                    //floors.add(spot);
                    floors.put(i, spot);
                }
            }
        } else {
            for (int i = 0; i < hall.length(); i++) {
                if (verifyFloor(new Location(hall.start().x, hall.start().y
                        + (hall.direction() * i)))) {
                    Location spot = new Location(hall.start().x, (hall.start().y
                            + (hall.direction() * i)));
                    world1[spot.x][spot.y] = Tileset.FLOOR;
                    floors.put(i, spot);
                }

            }

        }
    }

    private void drawBlank(TETile[][] world1) {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                world1[x][y] = Tileset.NOTHING;
            }
        }
    }

    private boolean verifyLocation(Location l) {
        return l.x < WIDTH && l.x >= 0 && l.y < HEIGHT && l.y >= 0;

    }

    public boolean verifyFloor(Location l) {
        return l.x > 0 && l.y > 0 && l.x < WIDTH - 1 && l.y < HEIGHT - 1;
    }

    private boolean nextToFloor(Location l) {
        ArrayList<TETile> neighbors = new ArrayList<>();
        int x = l.x;
        int y = l.y;
        for (int i = x - 1; i <= x + 1; i++) {
            if (verifyLocation(new Location(i, y + 1))) {
                neighbors.add(world[i][y + 1]);
            }
            if (verifyLocation(new Location(i, y - 1))) {
                neighbors.add(world[i][y - 1]);
            }
        }
        if (verifyLocation(new Location(x - 1, y))) {
            neighbors.add(world[x - 1][y]);
        }
        if (verifyLocation(new Location(x + 1, y))) {
            neighbors.add(world[x + 1][y]);
        }
        for (TETile t : neighbors) {
            if (t.equals(Tileset.FLOOR)) {
                return true;
            }
        }
        return false;
    }

    private void buildWalls(TETile[][] world1) {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (world1[x][y] != Tileset.FLOOR) {
                    if (nextToFloor(new Location(x, y))) {
                        world1[x][y] = Tileset.WALL;
                    }
                }
            }
        }
    }
}


