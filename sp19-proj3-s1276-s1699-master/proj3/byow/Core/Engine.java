package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Engine {
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    static TERenderer ter = new TERenderer();
    static Long value;

    private static Random randomGeneration(long hee) {
        return new Random(hee);
    }

    public static void interactWithKeyboard() {
        TETile[][] world;
        StdDraw.clear(Color.RED);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 20));
        StdDraw.text(0.5, 0.9, "CS61B: The Game");
        StdDraw.text(0.5, 0.6, "New Game (N)");
        StdDraw.text(0.5, 0.5, "Load Game (L)");
        StdDraw.text(0.5, 0.4, "Quit (Q)");
        char choice = getNextKey();
        String name;
        switch (choice) {
            case 'N':
                String seed = newSeed();
                name = newName();
                world = interactWithInputString(seed);
                TERenderer a = new TERenderer();
                a.initialize(WIDTH,HEIGHT);
                Location avatarstart = new Location(0, 0);
                outerloop:
                for (int x = 0; x < WIDTH; x++) {
                    for (int y = 0; y < HEIGHT; y++) {
                        if (world[x][y].equals(Tileset.FLOOR)) {
                            avatarstart = new Location(x, y);
                            break outerloop;
                        }
                    }
                }
                Avatar you = new Avatar(avatarstart, world, value, name);
                you.show();
                break;
            case 'L':
                List<Object> sol = loadgame();
                world = interactWithInputString((String) sol.get(0));
                Long g;
                String simple = (String) sol.get(0);
                simple =  simple.toLowerCase();
                if (simple.contains("n") && simple.contains("s")) {
                    try {
                        g = Long.parseLong(simple.substring(simple.indexOf("n")
                                + 1, simple.indexOf("s")));
                    } catch (IllegalArgumentException q) {
                        throw new IllegalArgumentException("Incorrect Seed value");
                    }
                } else {
                    throw new IllegalArgumentException("Cannot begin without 'N' and 'S'");
                }
                Avatar player = new Avatar((Location) sol.get(1), world, g,
                        (String) sol.get(2), (ArrayList<Flower>) sol.get(3));
                player.show();
                break;
            case 'Q':
                break;
            default:
                interactWithKeyboard();
        }
    }
    public static String newSeed() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 20));
        StdDraw.text(0.5, 0.6, "Enter a random seed then press S: ");
        String seed = "N";
        Double x = 0.0;
        StdDraw.enableDoubleBuffering();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                String a = Character.toString(StdDraw.nextKeyTyped());
                StdDraw.text(0.3 + x, 0.3, a);
                x = x + 0.03;
                seed = seed + a;
                StdDraw.show();
                if (seed.charAt(seed.length() - 1) == 'S' || seed.charAt(seed.length() - 1) == 's') {
                    return seed;
                }
            }
        }
    }

    public static String newName() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 20));
        StdDraw.text(0.5, 0.6, "Choose your player name then press Y: ");
        String seed = "";
        Double x = 0.0;
        StdDraw.enableDoubleBuffering();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                String a = Character.toString(StdDraw.nextKeyTyped());
                StdDraw.text(0.3 + x, 0.3, a);
                x = x + 0.03;
                seed = seed + a;
                StdDraw.show();
                if (seed.charAt(seed.length() - 1) == 'Y' ||
                        seed.charAt(seed.length() - 1) == 'y') {
                    return seed.substring(0, seed.length() - 1);
                }
            }
        }
    }

    public static List loadgame() {
        List<Object> sol = new ArrayList<>();
        try {
            FileInputStream saveFile = new FileInputStream("savefile.txt");
            ObjectInputStream save = new ObjectInputStream(saveFile);
            String seed = "N" + (Long) save.readObject() + "S";
            Location pos = (Location) save.readObject();
            String name = (String) save.readObject();
            List<Flower> fs = (List<Flower>) save.readObject();
            sol.add(seed);
            sol.add(pos);
            sol.add(name);
            sol.add(fs);
            save.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return sol;
    }

    public static char getNextKey() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = Character.toUpperCase(StdDraw.nextKeyTyped());
                return c;
            }
        }
    }

    public static TETile[][] interactWithInputString(String input) {
        Avatar you;
        String simple = input.toLowerCase();
        TETile[][] tiles;
        if (simple.charAt(0) == 'l') {
            List<Object> sol = loadgame();
            tiles = new TETile[WIDTH][HEIGHT];
            tiles = interactWithInputString((String) sol.get(0));
            Long value;
            String easy = (String) sol.get(0);
            easy = easy.toLowerCase();
            if (easy.contains("n") && easy.contains("s")) {
                try {
                    value = Long.parseLong(easy.substring(easy.indexOf("n")
                            + 1, easy.indexOf("s")));
                } catch (IllegalArgumentException q) {
                    throw new IllegalArgumentException("Incorrect Seed value");
                }
            } else {
                throw new IllegalArgumentException("Cannot begin without 'N' and 'S'");
            }


            you = new Avatar((Location) sol.get(1),
                    tiles, value, (String) sol.get(2), (ArrayList<Flower>) sol.get(3));
        } else {
            if (simple.contains("n") && simple.contains("s")) {
                try {
                    value = Long.parseLong(simple.substring(simple.indexOf("n")
                            + 1, simple.indexOf("s")));
                } catch (IllegalArgumentException q) {
                    throw new IllegalArgumentException("Incorrect Seed value");
                }
            } else {
                throw new IllegalArgumentException("Cannot begin without 'N' and 'S'");
            }

            tiles = new TETile[WIDTH][HEIGHT];
            Generation gen = new Generation(value, tiles, null);
            gen.createWorld();

            Location avatarstart = new Location(0, 0);

            outerloop:
            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < HEIGHT; y++) {
                    if (tiles[x][y].equals(Tileset.FLOOR)) {
                        avatarstart = new Location(x, y);
                        break outerloop;
                    }
                }
            }

            you = new Avatar(avatarstart, tiles, value, "Default");
        }

        int index = input.indexOf("s") + 1;
        while (index < input.length() && !(input.charAt(index) == ':')) {
            char c = Character.toLowerCase(input.charAt(index));
            if (c == 'w') {
                you.moveW();
                you.save();
            } else if (c == 'a') {
                you.moveA();
                you.save();
            } else if (c == 's') {
                you.moveS();
                you.save();
            } else if (c == 'd') {
                you.moveD();
                you.save();
            }
            index++;
        }
        if (index != input.length()) {
            if (input.charAt(index) == ':') {
                try {
                    if (input.charAt(index + 1) == 'q') {
                        you.save();
                    }
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            }
        }
        return tiles;

    }

    public static TETile[][] interactWithInputString(String input, Location a, String name, List<Flower> fs) {
        Long h;
        String simple = input.toLowerCase();
        if (simple.contains("n") && simple.contains("s")) {
            try {
                h = Long.parseLong(simple.substring(simple.indexOf("n")
                        + 1, simple.indexOf("s")));
            } catch (IllegalArgumentException q) {
                throw new IllegalArgumentException("Incorrect Seed value");
            }
        } else {
            throw new IllegalArgumentException("Cannot begin without 'N' and 'S'");
        }

        TETile[][] tiles = new TETile[WIDTH][HEIGHT];
        Generation gen = new Generation(h, tiles, a);
        return gen.createWorld();

    }
}
