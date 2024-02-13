package ru.zaralx.bridgebuilders.commons.npc;

import org.bukkit.Axis;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Orientable;
import org.bukkit.block.data.Rotatable;
import org.bukkit.block.data.type.Fence;

public enum ReplayReflect {
    ROTATE_0() {
        @Override
        public Location reflect(Location center, Location location) {
            return location;
        }

        @Override
        public Location reflectBlock(Location center, Location location) {
            return location;
        }

        @Override
        public BlockData reflectBlockData(BlockData blockData) {
            return blockData;
        }
    },
    ROTATE_90() {
        private Location reflectMiss(Location center, Location location, double offsetX, double offsetZ) {
            double dx = location.getX() - center.getX();
            double dz = location.getZ() - center.getZ();
            double reflectedX = center.getX() - dz + offsetX;
            double reflectedZ = center.getZ() + dx + offsetZ;
            return new Location(location.getWorld(), reflectedX, location.getY(), reflectedZ, location.getYaw()+90, location.getPitch());
        }

        @Override
        public Location reflect(Location center, Location location) {
            return reflectMiss(center, location, 0, 0);
        }

        @Override
        public Location reflectBlock(Location center, Location location) {
            return reflectMiss(center, location, -1, 0);
        }

        @Override
        public BlockData reflectBlockData(BlockData blockData) {
            return rotateBlockData(blockData, 90);
        }
    },
    ROTATE_180() {
        private Location reflectMiss(Location center, Location location, double offsetX, double offsetZ) {
            double dx = location.getX() - center.getX();
            double dz = location.getZ() - center.getZ();
            double reflectedX = center.getX() - dx + offsetX;
            double reflectedZ = center.getZ() - dz + offsetZ;
            return new Location(location.getWorld(), reflectedX, location.getY(), reflectedZ, location.getYaw()+180, location.getPitch());
        }

        @Override
        public Location reflect(Location center, Location location) {
            return reflectMiss(center, location, 0, 0);
        }

        @Override
        public Location reflectBlock(Location center, Location location) {
            return reflectMiss(center, location, -1, -1);
        }

        @Override
        public BlockData reflectBlockData(BlockData blockData) {
            return rotateBlockData(blockData, 180);
        }
    },
    ROTATE_270() {
        private Location reflectMiss(Location center, Location location, double offsetX, double offsetZ) {
            double dx = location.getX() - center.getX();
            double dz = location.getZ() - center.getZ();
            double reflectedX = center.getX() + dz + offsetX;
            double reflectedZ = center.getZ() - dx + offsetZ;
            return new Location(location.getWorld(), reflectedX, location.getY(), reflectedZ, location.getYaw()-90, location.getPitch());
        }

        @Override
        public Location reflect(Location center, Location location) {
           return reflectMiss(center, location, 0, 0);
        }

        @Override
        public Location reflectBlock(Location center, Location location) {
            return reflectMiss(center, location, 0, -1);
        }

        @Override
        public BlockData reflectBlockData(BlockData blockData) {
            return rotateBlockData(blockData, -90);
        }
    };

    public abstract Location reflect(Location center, Location location);
    public abstract Location reflectBlock(Location center, Location location);
    public abstract BlockData reflectBlockData(BlockData blockData);

    public static BlockData rotateBlockData(BlockData blockData, int degrees) {
        if (blockData instanceof Directional) {
            BlockFace currentFacing = ((Directional) blockData).getFacing();
            BlockFace newFacing = rotateBlockFace(currentFacing, degrees);
            ((Directional) blockData).setFacing(newFacing);
        } if (blockData instanceof Orientable) {
            Axis currentAxis = ((Orientable) blockData).getAxis();
            Axis newAxis = rotateAxis(currentAxis, degrees);
            ((Orientable) blockData).setAxis(newAxis);
        } if (blockData instanceof Rotatable) {
            BlockFace currentRotation = ((Rotatable) blockData).getRotation();
            BlockFace newRotation = rotateBlockFace(currentRotation, degrees);
            ((Rotatable) blockData).setRotation(newRotation);
        }
        return blockData;
    }

    private static BlockFace rotateBlockFace(BlockFace facing, int degrees) {
        if (facing == BlockFace.NORTH && degrees == 90) {
            facing = BlockFace.EAST;
        } else if (facing == BlockFace.EAST && degrees == 90) {
            facing = BlockFace.SOUTH;
        } else if (facing == BlockFace.SOUTH && degrees == 90) {
            facing = BlockFace.WEST;
        } else if (facing == BlockFace.WEST && degrees == 90) {
            facing = BlockFace.NORTH;

        } else if (facing == BlockFace.NORTH && degrees == -90) {
            facing = BlockFace.WEST;
        } else if (facing == BlockFace.WEST && degrees == -90) {
            facing = BlockFace.SOUTH;
        } else if (facing == BlockFace.SOUTH && degrees == -90) {
            facing = BlockFace.EAST;
        } else if (facing == BlockFace.EAST && degrees == -90) {
            facing = BlockFace.NORTH;

        } else if (facing == BlockFace.EAST && Math.abs(degrees) == 180) {
            facing = BlockFace.WEST;
        } else if (facing == BlockFace.WEST && Math.abs(degrees) == 180) {
            facing = BlockFace.EAST;
        } else if (facing == BlockFace.SOUTH && Math.abs(degrees) == 180) {
            facing = BlockFace.NORTH;
        } else if (facing == BlockFace.NORTH && Math.abs(degrees) == 180) {
            facing = BlockFace.EAST;
        }
        return facing;
    }

    private static Axis rotateAxis(Axis axis, int degrees) {
        if (axis == Axis.X && Math.abs(degrees) == 90) {
            axis = Axis.Z;
        } else if (axis == Axis.Z && Math.abs(degrees) == 90) {
            axis = Axis.X;
        }
        return axis;
    }

    public static ReplayReflect fromDegrees(String degrees) {
        return switch (degrees) {
            case "90" -> ReplayReflect.ROTATE_90;
            case "180" -> ReplayReflect.ROTATE_180;
            case "270" -> ReplayReflect.ROTATE_270;
            default -> ReplayReflect.ROTATE_0;
        };
    }
}
