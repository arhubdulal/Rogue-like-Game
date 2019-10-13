package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Avatar {
    static Location start;
    Location pos;
    TETile[][] world;
    long seed;
    int health;
    String name;
    ArrayList<Flower> flowers;


    public Avatar(Location a, TETile[][] world, long value, String name) {
        this.pos = a;
        this.world = world;
        this.seed = value;
        this.health = 5;
        start = a;
        this.name = name;
        this.flowers = new ArrayList<>();
    }

    public Avatar(Location a, TETile[][] world, long value, String name, ArrayList<Flower> fs) {
        this.pos = a;
        this.world = world;
        this.seed = value;
        this.health = 5;
        start = a;
        this.name = name;
        this.flowers = fs;
    }

    public void show() {
        Location l;
        if (flowers.size() == 0) {
            outerloop:
            for (int x = 10; x < byow.Core.Engine.WIDTH; x++) {
                for (int y = 10; y < byow.Core.Engine.HEIGHT; y++) {
                    if (world[x][y].equals(Tileset.FLOOR)) {
                        flowers.add(new Flower(world, new Location(x, y)));
                        break outerloop;
                    }
                }
            }
            outerloop:
            for (int x = 20; x < byow.Core.Engine.WIDTH; x++) {
                for (int y = 20; y < byow.Core.Engine.HEIGHT; y++) {
                    if (world[x][y].equals(Tileset.FLOOR)) {
                        flowers.add(new Flower(world, new Location(x, y)));
                        break outerloop;
                    }
                }
            }
            outerloop:
            for (int x = 25; x < byow.Core.Engine.WIDTH; x++) {
                for (int y = 10; y < byow.Core.Engine.HEIGHT; y++) {
                    if (world[x][y].equals(Tileset.FLOOR)) {
                        flowers.add(new Flower(world, new Location(x, y)));
                        break outerloop;
                    }
                }
            }
            outerloop:
            for (int x = 10; x < byow.Core.Engine.WIDTH; x++) {
                for (int y = 20; y < byow.Core.Engine.HEIGHT; y++) {
                    if (world[x][y].equals(Tileset.FLOOR)) {
                        flowers.add(new Flower(world, new Location(x, y)));
                        break outerloop;
                    }
                }
            }
        }
        for (int i = 0; i < flowers.size(); i++) {
            world[flowers.get(i).getPos().x][flowers.get(i).getPos().y] = flowers.get(i).getTile();
        }
        while (health > 0) {
            StdDraw.enableDoubleBuffering();
            world[pos.x][pos.y] = Tileset.AVATAR;
            TERenderer ter = new TERenderer();
            ter.renderFrame(world);
            hud();
            save();
            if (StdDraw.isKeyPressed(87)) {
                if (check(pos.x, pos.y + 1)) {
                    world[pos.x][pos.y] = Tileset.FLOOR;
                    this.pos = new Location(pos.x, pos.y + 1);

                    for (int i = 0; i < flowers.size(); i++) {
                        flowers.get(i).move(this);
                    }
                }
            }
            if (StdDraw.isKeyPressed(65)) {
                if (check(pos.x - 1, pos.y)) {
                    world[pos.x][pos.y] = Tileset.FLOOR;
                    this.pos = new Location(pos.x - 1, pos.y);

                    for (int i = 0; i < flowers.size(); i++) {
                        flowers.get(i).move(this);
                    }
                }
            }
            if (StdDraw.isKeyPressed(83)) {
                if (check(pos.x, pos.y - 1)) {
                    world[pos.x][pos.y] = Tileset.FLOOR;
                    this.pos = new Location(pos.x, pos.y - 1);

                    for (int i = 0; i < flowers.size(); i++) {
                        flowers.get(i).move(this);
                    }
                }
            }
            if (StdDraw.isKeyPressed(68)) {
                if (check(pos.x + 1, pos.y)) {
                    world[pos.x][pos.y] = Tileset.FLOOR;
                    this.pos = new Location(pos.x + 1, pos.y);

                    for (int i = 0; i < flowers.size(); i++) {
                        flowers.get(i).move(this);
                    }
                }
            }

            if (StdDraw.isKeyPressed(186)) {
                checkquit();
            }

            if (world[pos.x][pos.y] == Tileset.FLOWER) {
                for (int i = 0; i < flowers.size(); i++) {

                    if ((Math.abs(flowers.get(i).getPos().x - pos.x) + Math.abs(flowers.get(i).getPos().y - pos.y) <= 1) && flowers.get(i).getTile().equals(Tileset.FLOWER)) {
                        encounter(flowers.get(i));
                    }

                }

            }
            StdDraw.show();
            StdDraw.pause(75);
        }
        StdDraw.enableDoubleBuffering();
        StdDraw.clear(StdDraw.BOOK_RED);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(Engine.WIDTH / 2, Engine.HEIGHT / 2, "YOU DIED");
        StdDraw.show();
        StdDraw.pause(10000);
    }

    public void hud() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenColor(StdDraw.WHITE);
        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();
        if (checkmouse(x, y)) {
            TETile a = world[x][y];
            if (a.description().equals("floor")) {
                StdDraw.text(75, 25, "Floor");
            } else if (a.description().equals("nothing")) {
                StdDraw.text(75, 25, "The abyss");
            } else if (a.description().equals("wall")) {
                StdDraw.text(75, 25, "A brick wall");
            } else if (a.description().equals("you")) {
                StdDraw.text(75, 25, name);
            } else if (a.description().equals("flower")) {
                StdDraw.text(75, 25, "Ruler of grass");
            }
        }
        StdDraw.text(75, 20, "HEALTH: " + this.health);
        StdDraw.text(75, 15 , this.name);

        StdDraw.show();
    }

    public boolean checkmouse(int x, int y) {
        return (x > 0) && x < Engine.WIDTH && y > 0 && y < Engine.HEIGHT;
    }

    public void nextKey() {
        if (StdDraw.hasNextKeyTyped()) {
            char move = Character.toLowerCase(StdDraw.nextKeyTyped());
            switch (move) {
                case 'w':
                    if (check(pos.x, pos.y + 1)) {
                        world[pos.x][pos.y] = Tileset.FLOOR;
                        this.pos = new Location(pos.x, pos.y + 1);
                    }

                case 'a':
                    if (check(pos.x - 1, pos.y)) {
                        world[pos.x][pos.y] = Tileset.FLOOR;
                        this.pos = new Location(pos.x - 1, pos.y);
                    }

                case 's':
                    if (check(pos.x, pos.y - 1)) {
                        world[pos.x][pos.y] = Tileset.FLOOR;
                        this.pos = new Location(pos.x, pos.y - 1);
                    }

                case 'd':
                    if (check(pos.x + 1, pos.y)) {
                        world[pos.x][pos.y] = Tileset.FLOOR;
                        this.pos = new Location(pos.x + 1, pos.y);
                    }

                case ':':
                    checkquit();

            }
        }
    }


    public void checkquit() {
        outerloop:
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = Character.toLowerCase(StdDraw.nextKeyTyped());
                if (c == 'q') {
                    save();
                    System.exit(0);
                } else {
                    break outerloop;
                }
            }
        }
        return;
    }

    public boolean check(int x, int y) {
        return !world[x][y].equals(Tileset.WALL) && !world[x][y].equals(Tileset.NOTHING);
    }

    public void save() {
        try {
            FileOutputStream saveFile = new FileOutputStream("savefile.txt");
            ObjectOutputStream save = new ObjectOutputStream(saveFile);
            save.writeObject(seed);
            save.writeObject(pos);
            save.writeObject(name);
            save.writeObject(flowers);
            save.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public Location getPos() {
        return pos;
    }

    public void encounter(Flower f) {


        StdDraw.clear(Color.GREEN);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(Engine.WIDTH / 2, Engine.HEIGHT / 2 + 10, "I am Flower, ruler of grass");
        StdDraw.text(Engine.WIDTH / 2, Engine.HEIGHT / 2 - 5, "T to attack, Y to defend, R to run");

        StdDraw.enableDoubleBuffering();
        String message = "";
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                String b = Character.toString(StdDraw.nextKeyTyped());
                StdDraw.text(Engine.WIDTH / 2, Engine.HEIGHT / 2 - 12, b);
                message = message + b;
                StdDraw.show();
                StdDraw.pause(10);
                if (message.charAt(message.length() - 1) == 'T' || message.charAt(message.length() - 1) == 't') {
                    f.remove();
                    this.health -= 1;
                    return;
                } else if (message.charAt(message.length() - 1) == 'Y' || message.charAt(message.length() - 1) == 'y') {
                    f.remove();
                    this.health -= 0.5;
                    return;
                } else if (message.charAt(message.length() - 1) == 'R' || message.charAt(message.length() - 1) == 'r') {
                    this.pos = start;
                    return;
                }
            }
        }
    }

    public void moveW() {
        if (check(pos.x, pos.y + 1)) {
            world[pos.x][pos.y] = Tileset.FLOOR;
            this.pos = new Location(pos.x, pos.y + 1);

            for (int i = 0; i < flowers.size(); i++) {
                flowers.get(i).move(this);
            }
        }
    }

    public void moveS() {
        if (check(pos.x, pos.y - 1)) {
            world[pos.x][pos.y] = Tileset.FLOOR;
            this.pos = new Location(pos.x, pos.y - 1);

            for (int i = 0; i < flowers.size(); i++) {
                flowers.get(i).move(this);
            }
        }
    }

    public void moveD() {
        if (check(pos.x + 1, pos.y)) {
            world[pos.x][pos.y] = Tileset.FLOOR;
            this.pos = new Location(pos.x + 1, pos.y);

            for (int i = 0; i < flowers.size(); i++) {
                flowers.get(i).move(this);
            }
        }
    }

    public void moveA() {
        if (check(pos.x - 1, pos.y)) {
            world[pos.x][pos.y] = Tileset.FLOOR;
            this.pos = new Location(pos.x - 1, pos.y);

            for (int i = 0; i < flowers.size(); i++) {
                flowers.get(i).move(this);
            }
        }
    }
}
