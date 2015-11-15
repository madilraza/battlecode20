package battlecode.world;

import battlecode.common.MapLocation;
import battlecode.common.RobotType;
import java.io.Serializable;
import battlecode.common.ZombieCount;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;

/**
 * The class represents the map in the game world on which
 * objects interact.
 *
 * This class is STATIC and does not change. It reflects the initial
 * condition of the map. All changes to the map are reflected in GameWorld.
 */
public class GameMap implements Serializable {

    private static final long serialVersionUID = -2068896916199851260L;
    /**
     * The default game seed.
     */
    public static final int GAME_DEFAULT_SEED = 6370;
    /**
     * The default game maxiumum number of rounds.
     */
    public static final int GAME_DEFAULT_ROUNDS = 2000;
    /**
     * The width and height of the map.
     */
    private final int mapWidth, mapHeight;
    /**
     * The initial rubble on the map.
     */
    private final int[][] mapInitialRubble;
    /**
     * The initial parts on the map.
     */
    private final int[][] mapInitialParts;
    /**
     * The coordinates of the origin.
     */
    private final int mapOriginX, mapOriginY;
    /**
     * The random seed contained in the map file.
     */
    private final int seed;
    /**
     * The maximum number of rounds in the game
     */
    private final int rounds;

    /**
     * The name of the map
     */
    private final String mapName;

    /**
     * The zombie spawn schedule for the map
     */
    private final ZombieSpawnSchedule zSchedule;

    /**
     * Represents the various integer properties a GameMap
     * can have.
     */
    public enum MapProperties {
        WIDTH, HEIGHT, SEED, ROUNDS
    }

    /**
     * Creates a deep copy of the input GameMap.
     *
     * @param gm the GameMap to copy.
     */
    public GameMap(GameMap gm) {
        this.mapWidth = gm.mapWidth;
        this.mapHeight = gm.mapHeight;
        this.mapInitialRubble = new int[this.mapWidth][this.mapHeight];
        this.mapInitialParts = new int[this.mapWidth][this.mapHeight];
        for (int i = 0; i < this.mapWidth; i++) {
            System.arraycopy(gm.mapInitialRubble[i], 0, this.mapInitialRubble[i], 0, this.mapHeight);
            System.arraycopy(gm.mapInitialParts[i], 0, this.mapInitialParts[i], 0, this.mapHeight);
        }

        this.mapOriginX = gm.mapOriginX;
        this.mapOriginY = gm.mapOriginY;
        this.seed = gm.seed;
        this.rounds = gm.rounds;
        this.mapName = gm.mapName;
        this.zSchedule = new ZombieSpawnSchedule(gm.zSchedule);
    }

    /**
     * Creates a GameMap with the given parameters. Note: the rubble and
     * parts arrays should be indexed such that rubble[x][y] gives you the
     * rubble at coordinate (x, y). This is weird if you're used to imagining
     * the first index as the row and the second index as the column, because
     * here it's the other way around.
     *
     * The map will be initialized with a random origin between (0, 0) and
     * (500, 500).
     *
     * @param mapProperties used to specify integer properties of the map
     *                      (width, height, seed, and number of rounds).
     * @param mapInitialRubble initial rubble array for the map.
     * @param mapInitialParts initial parts array for the map.
     * @param zSchedule zombie spawn schedule.
     * @param mapName name of the map.
     */
    public GameMap(Map<MapProperties, Integer> mapProperties, int[][]
            mapInitialRubble, int[][] mapInitialParts, ZombieSpawnSchedule
            zSchedule, String mapName) {
        if (mapProperties.containsKey(MapProperties.WIDTH)) {
            this.mapWidth = mapProperties.get(MapProperties.WIDTH);
        } else {
            this.mapWidth = mapInitialRubble.length;
        }

        if (mapProperties.containsKey(MapProperties.HEIGHT)) {
            this.mapHeight = mapProperties.get(MapProperties.HEIGHT);
        } else {
            this.mapHeight = mapInitialRubble[0].length;
        }

        if (mapProperties.containsKey(MapProperties.SEED)) {
            this.seed = mapProperties.get(MapProperties.SEED);
        } else {
            this.seed = GAME_DEFAULT_SEED;
        }

        if (mapProperties.containsKey(MapProperties.ROUNDS)) {
            this.rounds = mapProperties.get(MapProperties.ROUNDS);
        } else {
            this.rounds = GAME_DEFAULT_ROUNDS;
        }

        Random rand = new Random(this.seed);
        this.mapOriginX = rand.nextInt(500);
        this.mapOriginY = rand.nextInt(500);
        this.mapInitialRubble = mapInitialRubble;
        this.mapInitialParts = mapInitialParts;
        this.zSchedule = zSchedule;
        this.mapName = mapName;
    }

    /**
     * Returns whether two GameMaps are equivalent. Two equivalent game maps
     * have the same information, but their zombie schedules might be in
     * different orders and their origins do not have to be the same.
     *
     * @param other the other map to compare to.
     * @return whether the two maps are equivalent.
     */
    public boolean equivalentTo(GameMap other) {
        if (this.rounds != other.rounds) return false;
        if (this.mapWidth != other.mapWidth) return false;
        if (this.mapHeight != other.mapHeight) return false;
        if (this.seed != other.seed) return false;
        if (!Arrays.deepEquals(this.mapInitialRubble, other.mapInitialRubble))
            return false;
        if (!Arrays.deepEquals(this.mapInitialParts, other.mapInitialParts))
            return false;
        if (!this.mapName.equals(other.mapName)) return false;
        if (!this.zSchedule.equivalentTo(other.zSchedule)) return false;

        return true;
    }

    /**
     * Returns the width of this map.
     *
     * @return the width of this map.
     */
    public int getWidth() {
        return mapWidth;
    }

    /**
     * Returns the height of this map.
     *
     * @return the height of this map.
     */
    public int getHeight() {
        return mapHeight;
    }

    /**
     * Returns the name of the map.
     *
     * @return the name o the map.
     */
    public String getMapName() {
        return mapName;
    }

    /**
     * Determines whether or not the location at the specified
     * coordinates is on the map. The coordinate should be a shifted one
     * (takes into account the origin).
     *
     * @param x the (shifted) x-coordinate of the location
     * @param y the (shifted) y-coordinate of the location
     * @return true if the given coordinates are on the map,
     *         false if they're not
     */
    private boolean onTheMap(int x, int y) {
        return (x >= mapOriginX && y >= mapOriginY && x < mapOriginX + mapWidth && y < mapOriginY + mapHeight);
    }

    /**
     * Determines whether or not the specified location is on the map.
     *
     * @param location the MapLocation to test
     * @return true if the given location is on the map,
     *         false if it's not
     */
    public boolean onTheMap(MapLocation location) {
        return onTheMap(location.x, location.y);
    }

    /**
     * Determines the amount of rubble on the map at
     * the given location.
     *
     * @param location the MapLocation to get rubble for.
     * @return the amount of rubble in the given location, or 0 if off map
     */
    public int getInitialRubble(MapLocation location) {
        if (!onTheMap(location))
            return 0;

        return mapInitialRubble[location.x - mapOriginX][location.y - mapOriginY];
    }

    /**
     * Returns a two-dimensional array of rubble data for this map.
     *
     * @return the map's rubble in a 2D array
     */
    public int[][] getInitialRubbleMatrix() {
        return mapInitialRubble;
    }
    
    /**
     * Determines the amount of ore on the map at the
     * given location.
     *
     * @param location the MapLocation to test
     * @return the amount of parts in the given location, or 0 if off the map
     */
    public int getInitialParts(MapLocation location) {
        if (!onTheMap(location))
            return 0;

        return mapInitialParts[location.x - mapOriginX][location.y - mapOriginY];
    }

    /**
     * Returns a two-dimensional array of ore data for this map.
     *
     * @return the map's ore in a 2D array
     */
    public int[][] getInitialPartsMatrix() {
        return mapInitialParts;
    }

    /**
     * Gets the maximum number of rounds for this game.
     *
     * @return the maximum number of rounds for this game
     */
    public int getRounds() {
        return rounds;
    }

    public int getStraightMaxRounds() {
        return rounds;
    }

    public int getSeed() {
        return seed;
    }

    public ZombieCount[] getZombieSpawnSchedule(int round) {
        return zSchedule.getScheduleForRound(round).toArray(new ZombieCount[0]);
    }

    /**
     * Gets the origin (i.e., upper left corner) of the map
     *
     * @return the origin of the map
     */
    public MapLocation getMapOrigin() {
        return new MapLocation(mapOriginX, mapOriginY);
    }

    // TODO: this shouldn't be named MapMemory
    public static class MapMemory {
        private final GameMap map;

        /** Represents how many units are currently able to sense a given location. */
        private final int[][] currentCount;

        /** Represents whether a certain location was ever in sensor range. */
        private final boolean[][] seen;

        /** Represents the amount of rubble on location when the location was last in sensor range. */
        private final int[][] rubbleOnSquare;
        
        /** Represents the amount of parts on location when the location was last in sensor range. */
        private final int[][] partsOnSquare;

        /** It's important to keep track of OFF_MAP squares so we have this buffer around the map. */
        private final int OFFSET = 50;

        public MapMemory(GameMap map) {
            this.map = map;
            this.currentCount = new int[map.getWidth() + 2 * OFFSET][map.getHeight() + 2 * OFFSET];
            this.seen = new boolean[map.getWidth() + 2 * OFFSET][map.getHeight() + 2 * OFFSET];
            this.rubbleOnSquare = new int[map.getWidth() + 2 * OFFSET][map.getHeight() + 2 * OFFSET];
            this.partsOnSquare = new int[map.getWidth() + 2 * OFFSET][map.getHeight() + 2 * OFFSET];
        }

        // x and y are locations relative to the origin
        private boolean validLoc(int x, int y) {
            return x >= -OFFSET && x < map.getWidth() + OFFSET && y >= -OFFSET && y < map.getHeight() + OFFSET;
        }

        public void removeLocation(MapLocation loc, int radiusSquared) {
            MapLocation[] locs = MapLocation.getAllMapLocationsWithinRadiusSq(loc, radiusSquared);

            for (int i = 0; i < locs.length; i++) {
                int x = locs[i].x - map.mapOriginX;
                int y = locs[i].y - map.mapOriginY;
                if (validLoc(x, y)) {
                    currentCount[x + OFFSET][y + OFFSET]--;
                }
            }
        }

        public void rememberLocation(MapLocation loc, int radiusSquared, Map<MapLocation, Integer> partsMap,  Map<MapLocation, Integer> rubbleMap) {
            MapLocation[] locs = MapLocation.getAllMapLocationsWithinRadiusSq(loc, radiusSquared);

            for (int i = 0; i < locs.length; i++) {
                int x = locs[i].x - map.mapOriginX;
                int y = locs[i].y - map.mapOriginY;
                if (validLoc(x, y)) {
                    seen[x + OFFSET][y + OFFSET] = true;
                    currentCount[x + OFFSET][y + OFFSET]++;
                    if (currentCount[x + OFFSET][y + OFFSET] == 1 && partsMap.containsKey(locs[i])) {
                        partsOnSquare[x + OFFSET][y + OFFSET] = partsMap.get(locs[i]);
                    }
                    if (currentCount[x + OFFSET][y + OFFSET] == 1 && rubbleMap.containsKey(locs[i])) {
                        rubbleOnSquare[x + OFFSET][y + OFFSET] = rubbleMap.get(locs[i]);
                    }
                }
            }
        }

        /** When a location gets rubble cleared, we'll update map memory if it's currently in sight. */
        public void updateLocation(MapLocation loc, int rubbleNew) {
            if (canSense(loc)) {
                int x = loc.x - map.mapOriginX;
                int y = loc.y - map.mapOriginY;
                if (validLoc(x, y)) {
                    rubbleOnSquare[x + OFFSET][y + OFFSET] = rubbleNew;
                }
            }
        }

        public boolean seenBefore(MapLocation loc) {
            int X = loc.x - map.mapOriginX;
            int Y = loc.y - map.mapOriginY;

            if (validLoc(X, Y)) {
                return seen[X + OFFSET][Y + OFFSET];
            } else {
                return false;
            }
        }

        public boolean canSense(MapLocation loc) {
            int X = loc.x - map.mapOriginX;
            int Y = loc.y - map.mapOriginY;

            if (validLoc(X, Y)) {
                return currentCount[X + OFFSET][Y + OFFSET] > 0;
            } else {
                return false;
            }
        }

        public int recallRubble(MapLocation loc) {
            int X = loc.x - map.mapOriginX;
            int Y = loc.y - map.mapOriginY;

            if (seenBefore(loc)) {
                return rubbleOnSquare[X + OFFSET][Y + OFFSET];
            } else {
                return -1;
            }
        }
        
        public int recallParts(MapLocation loc) {
            int X = loc.x - map.mapOriginX;
            int Y = loc.y - map.mapOriginY;

            if (seenBefore(loc)) {
                return partsOnSquare[X + OFFSET][Y + OFFSET];
            } else {
                return -1;
            } 
        }
    }
}
