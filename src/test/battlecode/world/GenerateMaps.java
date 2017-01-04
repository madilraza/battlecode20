package battlecode.world;

import battlecode.common.MapLocation;
import battlecode.common.RobotType;
import battlecode.common.Team;
import battlecode.server.TeamMapping;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author james
 *
 * so uh
 *
 * this exists
 */
@Ignore
public class GenerateMaps {
    @Test
    public void makeSimple() throws IOException {
        LiveMap map = new TestMapBuilder("simple", 0, 0, 100, 100,30,3000)
                .addRobot(
                        0,
                        Team.A,
                        RobotType.ARCHON,
                        new MapLocation(
                                RobotType.ARCHON.bodyRadius + 1,
                                RobotType.ARCHON.bodyRadius + 1
                        )
                )
                .addRobot(
                        1,
                        Team.B,
                        RobotType.ARCHON,
                        new MapLocation(
                                99-RobotType.ARCHON.bodyRadius,
                                99-RobotType.ARCHON.bodyRadius
                        )
                )
                .build();

        GameMapIO.writeMap(map,
                new File("src/main/battlecode/world/resources"),
                new TeamMapping("Banana", "org.banana", "Ocelot", "org.ocelot"));

    }

    @Test
    public void makeShrine() throws IOException {
        LiveMap map = new TestMapBuilder("shrine", -15, -15, 30, 30,30,3000)
                .addRobot(
                        0,
                        Team.A,
                        RobotType.ARCHON,
                        new MapLocation(
                                RobotType.ARCHON.bodyRadius-15,
                                15-RobotType.ARCHON.bodyRadius
                        )
                )
                .addRobot(
                        1,
                        Team.B,
                        RobotType.ARCHON,
                        new MapLocation(
                                15-RobotType.ARCHON.bodyRadius,
                                RobotType.ARCHON.bodyRadius-15
                        )
                )
                .addNeutralTree(3, new MapLocation(0,0), 1, 20, RobotType.TANK)
                .build();

        GameMapIO.writeMap(map,
                new File("src/main/battlecode/world/resources"),
                new TeamMapping("Banana", "org.banana", "Ocelot", "org.ocelot"));

    }
}
