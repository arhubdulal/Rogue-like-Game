package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.Serializable;

public class Flower implements Serializable {

    public TETile tile;
    private Location pos;
    private Location lastPos;
    private TETile[][] world;

    public Flower(TETile[][] tiles, Location l) {

        world = tiles;
        outerloop:

        pos = l;
        lastPos = l;
        tile = Tileset.FLOWER;

    }

    public void move(Avatar player) {
        Location target = player.getPos();
        int xDistance = pos.x - target.x;
        int yDistance = pos.y - target.y;

        if (Math.abs(xDistance) > Math.abs(yDistance)) {
            if (xDistance > 0) {
                if (check(pos.x - 1, pos.y)) {
                    setPos(new Location(pos.x - 1, pos.y));
                }
            } else {
                if (check(pos.x + 1, pos.y)) {
                    setPos(new Location(pos.x + 1, pos.y));
                }
            }
        } else {
            if (yDistance > 0) {
                if (check(pos.x, pos.y - 1)) {
                    setPos(new Location(pos.x, pos.y - 1));
                }
            } else {
                if (check(pos.x, pos.y + 1)) {
                    setPos(new Location(pos.x, pos.y + 1));
                }
            }
        }
        if ((Math.abs(pos.x - player.getPos().x) + Math.abs(pos.y - player.getPos().y) <= 1) && tile == Tileset.FLOWER) {
            player.encounter(this);
        }

        world[this.lastPos.x][this.lastPos.y] = Tileset.FLOOR;
        world[this.pos.x][this.pos.y] = this.getTile();
    }

    public TETile getTile() {
        return tile;
    }


    public Location getPos() {
        return pos;
    }


    public void setPos(Location l) {
        lastPos = pos;
        pos = l;
    }


    public Location getLastPos() {
        return lastPos;
    }

    public boolean check(int x, int y) {
        return !world[x][y].equals(Tileset.WALL) && !world[x][y].equals(Tileset.NOTHING) && !world[x][y].equals(Tileset.FLOWER);
    }

    public void remove() {
        this.tile = Tileset.FLOOR;
    }
}
