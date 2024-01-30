package ru.zaralx.bridgebuilders.commons.npc.database.models;

import ru.zaralx.bridgebuilders.BridgeBuilders;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class BaseModel {
    public String name;
    public List<ModelColumn> columns = new ArrayList<>();
    public final Logger logger = BridgeBuilders.getInstance().getLogger();

    public boolean init() {
        StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS "+name+" (");
        for (ModelColumn column : columns) {
            query.append("`").append(column.getName()).append("`");
            query.append(" ").append(column.getType());
            if (column.getNotNull()) {
                query.append(" ");
                query.append("NOT NULL");
            }
            if (column.getPrimary()) {
                query.append(" ");
                query.append("PRIMARY KEY");
            }
            if (column.getAutoIncrement()) {
                query.append(" ");
                query.append("AUTOINCREMENT");
            }
            if (!columns.get(columns.size()-1).equals(column)) {
                query.append(",");
            }
        }
        query.append(");");
        try {
            logger.info("QUERY: "+query);
            BridgeBuilders.getInstance().getRecordsDatabase().executeUpdate(query.toString());
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e);
            return false;
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public List<ModelColumn> getColumns() {
        return columns;
    }
}
