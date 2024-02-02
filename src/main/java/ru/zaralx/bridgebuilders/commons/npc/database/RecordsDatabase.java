package ru.zaralx.bridgebuilders.commons.npc.database;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.world.entity.Pose;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import ru.zaralx.bridgebuilders.BridgeBuilders;
import ru.zaralx.bridgebuilders.commons.npc.CustomEquipment;
import ru.zaralx.bridgebuilders.commons.npc.Replay;
import ru.zaralx.bridgebuilders.commons.npc.database.models.*;
import ru.zaralx.bridgebuilders.commons.npc.record.*;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecordsDatabase {
    private Connection _connection;
    private final ReplayModel replayModel = new ReplayModel();
    private final TickModel tickModel = new TickModel();
    private final PositionModel positionModel = new PositionModel();
    private final EquipmentModel equipmentModel = new EquipmentModel();
    private final PoseModel poseModel = new PoseModel();
    private final BlockPlaceModel blockPlaceModel = new BlockPlaceModel();
    private final BlockDestroyModel blockDestroyModel = new BlockDestroyModel();
    public final Logger logger = BridgeBuilders.getInstance().getLogger();

    public Connection getConnection(){
        return _connection;
    }

    public boolean isConnected(){
        return _connection != null;
    }

    public void connect() {
        logger.info("Initializing database..");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            File dataFolder = new File(BridgeBuilders.getInstance().getDataFolder(), "records.db");
            if (!dataFolder.exists()){
                try {
                    dataFolder.createNewFile();
                } catch (IOException e) {
                    BridgeBuilders.getInstance().getLogger().log(Level.SEVERE, "File write error: records.db");
                }
            }
            _connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
            initAllModels();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void disconnect(){
        if(isConnected()){
            try {
                _connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Replay loadReplay(Player player, String name) {
        try {
            ResultSet replayRow = execute("SELECT * FROM Replays WHERE name = '"+name+"'");
            if (replayRow == null) {
                replayRow = execute("SELECT * FROM Replays WHERE id = "+name);
                if (replayRow == null) {
                    logger.severe("Failed to load replay '" + name + "' - Not found");
                    return null;
                }
            }

            List<TickRecord> records = new ArrayList<>();

            ResultSet replayTicks = executeNoNext("SELECT * FROM Replay_Tick WHERE replay = "+replayRow.getInt("id"));

            int id = 0;
            while (replayTicks.next()) {
                id++;

                PositionRecord positionRecord = null;
                EquipmentRecord equipmentRecord = null;
                PoseRecord poseRecord = null;
                BlockPlaceRecord blockPlaceRecord = null;
                BlockDestroyRecord blockDestroyRecord = null;

                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§d"+id+"/"+replayRow.getInt("totalTicks")));

                ResultSet positionRow = execute("SELECT * FROM Replay_Tick_Position WHERE id = "+replayTicks.getInt("position"));
                ResultSet equipmentRow = execute("SELECT * FROM Replay_Tick_Equipment WHERE id = "+replayTicks.getInt("equipment"));
                ResultSet poseRow = execute("SELECT * FROM Replay_Tick_Pose WHERE id = "+replayTicks.getInt("pose"));
                ResultSet placeBlockRow = execute("SELECT * FROM Replay_Tick_Block_Place WHERE id = "+replayTicks.getInt("place_block"));
                ResultSet destroyBlockRow = execute("SELECT * FROM Replay_Tick_Block_Destroy WHERE id = "+replayTicks.getInt("destroy_block"));

                if (positionRow != null) {
                    positionRecord = new PositionRecord(new Location(player.getWorld(), positionRow.getDouble("x"), positionRow.getDouble("y"), positionRow.getDouble("z"), positionRow.getFloat("yaw"), positionRow.getFloat("pitch")));
                }
                if (equipmentRow != null) {
                    equipmentRecord = new EquipmentRecord(new CustomEquipment(
                            equipmentRow.getString("HAND_material"),
                            equipmentRow.getString("OFF_HAND_material"),
                            equipmentRow.getString("HELMET_material"),
                            equipmentRow.getString("CHESTPLATE_material"),
                            equipmentRow.getString("LEGGINGS_material"),
                            equipmentRow.getString("BOOTS_material")
                    ));
                }
                if (poseRow != null) {
                    poseRecord = new PoseRecord(Pose.valueOf(poseRow.getString("pose")));
                }
                if (placeBlockRow != null) {
                    blockPlaceRecord = new BlockPlaceRecord(
                            new Location(player.getWorld(), placeBlockRow.getDouble("x"), placeBlockRow.getDouble("y"), placeBlockRow.getDouble("z")),
                            Bukkit.createBlockData(placeBlockRow.getString("block_data")),
                            placeBlockRow.getInt("hand"));
                }
                if (destroyBlockRow != null) {
                    blockDestroyRecord = new BlockDestroyRecord(new Location(player.getWorld(),
                            destroyBlockRow.getDouble("x"),
                            destroyBlockRow.getDouble("y"),
                            destroyBlockRow.getDouble("z")
                    ));
                }

                records.add(new TickRecord(
                        positionRecord,
                        equipmentRecord,
                        poseRecord,
                        blockPlaceRecord,
                        blockDestroyRecord
                ));
            }

            return new Replay(replayRow.getString("name"), records, false);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean saveNewReplay(Player player, String name, List<TickRecord> records) {
        if (records.isEmpty()) {
            player.sendMessage("Failed save replay '"+name+"' - RecordTicks size is 0");
            logger.severe("Failed save replay '"+name+"' - RecordTicks size is 0");
            return false;
        }

        try {
            ResultSet replayExists = execute("SELECT * FROM Replays WHERE name = '"+name+"'");
            if (replayExists != null) {
                player.sendMessage("Failed save replay '"+name+"' - Replay already exists");
                return false;
            }
            int replayId = executeUpdateWithGeneratedId("INSERT INTO Replays(id, name, totalTicks) VALUES (null, '"+name+"', "+records.size()+")");

            int tick = 0;
            for (TickRecord record : records) {
                tick++;
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("Saving record "+name+" "+tick+"/"+records.size()+" ticks"));
                logger.info("Saving record "+name+" "+tick+"/"+records.size()+" ticks");
                buildTickQuery(replayId, record);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    private void buildTickQuery(int replayId, TickRecord tickRecord) throws SQLException, ClassNotFoundException {
        PositionRecord positionRecord = tickRecord.getPositionRecord();
        Location positionRecordLocation = positionRecord.getLocation();
        EquipmentRecord equipmentRecord = tickRecord.getEquipmentRecord();
        PoseRecord poseRecord = tickRecord.getPoseRecord();
        BlockPlaceRecord blockPlaceRecord = tickRecord.getBlockPlaceRecord();
        Location blockPlaceLocation = null;
        if (blockPlaceRecord != null)
            blockPlaceLocation = blockPlaceRecord.getLocation();
        BlockDestroyRecord blockDestroyRecord = tickRecord.getBlockDestroyRecord();
        Location blockDestroyLocation = null;
        if (blockDestroyRecord != null)
            blockDestroyLocation = blockDestroyRecord.getLocation();

        int positionId = executeUpdateWithGeneratedId("INSERT INTO Replay_Tick_Position(id, replay, x, y, z, yaw, pitch) VALUES (null, "
                + replayId + ", "
                + positionRecordLocation.getX() + ", "
                + positionRecordLocation.getY() + ", "
                + positionRecordLocation.getZ() + ", "
                + positionRecordLocation.getYaw() + ", "
                + positionRecordLocation.getPitch() +
                ");");
        int equipmentId = executeUpdateWithGeneratedId("INSERT INTO Replay_Tick_Equipment(id, replay, HAND_material, OFF_HAND_material, HELMET_material, CHESTPLATE_material, LEGGINGS_material, BOOTS_material) VALUES (null, "
                + replayId + ", "
                + "'" + equipmentRecord.getHand().getType() + "', "
                + "'" + equipmentRecord.getOffHand().getType() + "', "
                + "'" + equipmentRecord.getHelmet().getType() + "', "
                + "'" + equipmentRecord.getChestplate().getType() + "', "
                + "'" + equipmentRecord.getLeggings().getType() + "', "
                + "'" + equipmentRecord.getBoots().getType() +
                "');");
        int poseId = executeUpdateWithGeneratedId("INSERT INTO Replay_Tick_Pose(id, replay, pose) VALUES (null, "
                + replayId + ", '"
                + poseRecord.getPose().toString() +
                "');");
        int placeBlockId = -1;
        if (blockPlaceLocation != null) {
            placeBlockId = executeUpdateWithGeneratedId("INSERT INTO Replay_Tick_Block_Place(id, replay, x, y, z, block_data, hand) VALUES (null, "
                    + replayId + ", "
                    + blockPlaceLocation.getX() + ", "
                    + blockPlaceLocation.getY() + ", "
                    + blockPlaceLocation.getZ() + ", "
                    + "'" + blockPlaceRecord.getBlockData().getAsString() + "', " +
                    blockPlaceRecord.getHand() +
                    ");");
        }
        int destroyBlockId = -1;
        if (blockDestroyLocation != null) {
            destroyBlockId = executeUpdateWithGeneratedId("INSERT INTO Replay_Tick_Block_Destroy(id, replay, x, y, z) VALUES (null, "
                    + replayId + ", "
                    + blockDestroyLocation.getX() + ", "
                    + blockDestroyLocation.getY() + ", "
                    + blockDestroyLocation.getZ() +
                    ");");
        }
        executeUpdate("INSERT INTO Replay_Tick(id, replay, position, equipment, pose, place_block, destroy_block) VALUES (null, "
                + replayId + ", "
                + positionId + ", "
                + equipmentId + ", "
                + poseId + ", "
                + placeBlockId + ", "
                + destroyBlockId +
                ");");
    }

    public void initAllModels() {
        replayModel.init();
        tickModel.init();
        positionModel.init();
        equipmentModel.init();
        poseModel.init();
        blockPlaceModel.init();
        blockDestroyModel.init();
    }

    public ResultSet executeNoNext(String query) throws SQLException, ClassNotFoundException {
        Statement statement = _connection.createStatement();
        return statement.executeQuery(query);
    }

    public ResultSet execute(String query) throws SQLException, ClassNotFoundException {
        if(!isConnected() || !_connection.isValid(10)) connect();
        Statement statement = _connection.createStatement();
        ResultSet result = statement.executeQuery(query);
        if(result.next()) return result;
        else return null;
    }

    public void executeUpdate(String query) throws SQLException, ClassNotFoundException {
        if(!isConnected() || !_connection.isValid(10)) connect();
        Statement statement = _connection.createStatement();
        statement.executeUpdate(query);
        statement.close();
    }

    public int executeUpdateWithGeneratedId(String query) throws SQLException, ClassNotFoundException {
        if (!isConnected() || !_connection.isValid(10)) connect();
        Statement statement = _connection.createStatement();
        statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

        ResultSet generatedKeys = statement.getGeneratedKeys();

        int generatedId = -1;
        if (generatedKeys.next()) {
            generatedId = generatedKeys.getInt(1);
        }

        statement.close();
        return generatedId;
    }
}