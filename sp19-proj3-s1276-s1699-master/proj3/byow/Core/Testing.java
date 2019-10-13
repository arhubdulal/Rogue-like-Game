package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;
import org.junit.Test;

public class Testing {

    @Test
    public void test() {
        Engine.interactWithKeyboard();
    }

    @Test
    public void testEncounter() {
        TERenderer ter = new TERenderer();
        ter.initialize(byow.Core.Engine.WIDTH, byow.Core.Engine.HEIGHT);

        TETile[][] tiles = new TETile[byow.Core.Engine.WIDTH][byow.Core.Engine.HEIGHT];
        Long seed = 12312345L;
        Generation gen = new Generation(seed, tiles, null);
        gen.createWorld();
        ter.renderFrame(tiles);
        StdDraw.clear();
        StdDraw.picture(1, 10, "Core/flower.png");
        while (true) {
            for (int i = 0; i < 9; i++) {
                StdDraw.clear();
                StdDraw.picture(0.1 + (0.1 * i), 0.5, "Core/flower.png");

            }
        }


    }

    @Test
    public void testInput() {
        Engine.interactWithInputString("N2523453SWWWAASSD:Q");
    }


}
