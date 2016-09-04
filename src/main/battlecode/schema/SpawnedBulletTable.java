// automatically generated, do not modify

package battlecode.schema;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
/**
 * A list of new bullets to be placed on the map.
 */
public final class SpawnedBulletTable extends Table {
  public static SpawnedBulletTable getRootAsSpawnedBulletTable(ByteBuffer _bb) { return getRootAsSpawnedBulletTable(_bb, new SpawnedBulletTable()); }
  public static SpawnedBulletTable getRootAsSpawnedBulletTable(ByteBuffer _bb, SpawnedBulletTable obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public SpawnedBulletTable __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  /**
   * The numeric ID of the new bullets.
   * Will never be negative.
   * There will only be one body/bullet with a particular ID at a time.
   * So, there will never be two robots with the same ID, or a robot and
   * a bullet with the same ID.
   */
  public int robotIDs(int j) { int o = __offset(4); return o != 0 ? bb.getInt(__vector(o) + j * 4) : 0; }
  public int robotIDsLength() { int o = __offset(4); return o != 0 ? __vector_len(o) : 0; }
  public ByteBuffer robotIDsAsByteBuffer() { return __vector_as_bytebuffer(4, 4); }
  /**
   * The locations of the bodies.
   */
  public VecTable locs() { return locs(new VecTable()); }
  public VecTable locs(VecTable obj) { int o = __offset(6); return o != 0 ? obj.__init(__indirect(o + bb_pos), bb) : null; }
  /**
   * The velocities of the bodies.
   */
  public VecTable vels() { return vels(new VecTable()); }
  public VecTable vels(VecTable obj) { int o = __offset(8); return o != 0 ? obj.__init(__indirect(o + bb_pos), bb) : null; }
  /**
   * The damage levels of the bodies.
   */
  public float damages(int j) { int o = __offset(10); return o != 0 ? bb.getFloat(__vector(o) + j * 4) : 0; }
  public int damagesLength() { int o = __offset(10); return o != 0 ? __vector_len(o) : 0; }
  public ByteBuffer damagesAsByteBuffer() { return __vector_as_bytebuffer(10, 4); }

  public static int createSpawnedBulletTable(FlatBufferBuilder builder,
      int robotIDsOffset,
      int locsOffset,
      int velsOffset,
      int damagesOffset) {
    builder.startObject(4);
    SpawnedBulletTable.addDamages(builder, damagesOffset);
    SpawnedBulletTable.addVels(builder, velsOffset);
    SpawnedBulletTable.addLocs(builder, locsOffset);
    SpawnedBulletTable.addRobotIDs(builder, robotIDsOffset);
    return SpawnedBulletTable.endSpawnedBulletTable(builder);
  }

  public static void startSpawnedBulletTable(FlatBufferBuilder builder) { builder.startObject(4); }
  public static void addRobotIDs(FlatBufferBuilder builder, int robotIDsOffset) { builder.addOffset(0, robotIDsOffset, 0); }
  public static int createRobotIDsVector(FlatBufferBuilder builder, int[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addInt(data[i]); return builder.endVector(); }
  public static void startRobotIDsVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static void addLocs(FlatBufferBuilder builder, int locsOffset) { builder.addOffset(1, locsOffset, 0); }
  public static void addVels(FlatBufferBuilder builder, int velsOffset) { builder.addOffset(2, velsOffset, 0); }
  public static void addDamages(FlatBufferBuilder builder, int damagesOffset) { builder.addOffset(3, damagesOffset, 0); }
  public static int createDamagesVector(FlatBufferBuilder builder, float[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addFloat(data[i]); return builder.endVector(); }
  public static void startDamagesVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static int endSpawnedBulletTable(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
};

