package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.HashMap;
import java.util.Random;

public class Room {

    public Room() {
    }

    public int createRoom(TETile[][] world, Random r,
                          //ArrayList<Location> floors) {
                          HashMap<Integer, Location> floors) {
        int width = r.nextInt(5);
        int height = r.nextInt(5);
        int counter = 0;
        Location o = floors.get(r.nextInt(floors.size()));
        //if (isValid(world, o, width, height)) {
        for (int x = (o.x - width); x < o.x + width - 1; x++) {
            for (int y = (o.y - height); y < o.y + height - 1; y++) {
                if (x > 0 && y > 0 && x < world.length - 1 && y < world[1].length - 1) {
                    world[x][y] = Tileset.FLOOR;
                }
                //floors.put(counter, new Location(x, y));
            }
        }
        return counter;
        // }else{
        //    createRoom(world, r, floors);
        // }
    }

    private boolean isValid(TETile[][] world, Location o, int width, int height) {
        if (o.x + width > Engine.WIDTH || o.x + width < 0
                || o.y + height > Engine.HEIGHT || o.y + height < 0) {
            return false;
        } else {
            for (int x = Math.min(o.x, o.x + width); x < Math.max(o.x, o.x + width); x++) {
                for (int y = Math.min(o.y, o.y + height); y < Math.max(o.y, o.y + height); y++) {
                    if (!world[x][y].equals(Tileset.NOTHING)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }



}

