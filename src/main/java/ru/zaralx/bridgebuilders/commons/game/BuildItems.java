package ru.zaralx.bridgebuilders.commons.game;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class BuildItems {
    private final List<BuildItem> buildItems = new ArrayList<>();
    private final HashMap<Location, Material> endLocations = new HashMap<>();
    public void add(BuildItem buildItem, Location location) {
        for (BuildItem item : buildItems) {
            if (item.equals(buildItem)) {
                if (endLocations.get(location) != null) {
                    if (!endLocations.get(location).equals(buildItem.material)) {
                        endLocations.remove(location);
                        endLocations.put(location, buildItem.material);
                    }
                }
                item.add(buildItem);
                return;
            }
        }
        buildItems.add(buildItem);
        endLocations.put(location, buildItem.material);
    }

    public void remove(BuildItem buildItem, Location location) {
        Iterator<BuildItem> iterator = buildItems.iterator();
        while (iterator.hasNext()) {
            BuildItem item = iterator.next();
            if (item.equals(buildItem)) {
                item.remove(buildItem);
                if (item.count <= 0) {
                    iterator.remove();
                }
                endLocations.remove(location);
                return;
            }
        }
    }

    public static class BuildItem {
        public Material material;
        public int count;

        public BuildItem(Material material, int count) {
            this.material = material;
            this.count = count;
        }

        public void add() {
            count++;
        }

        public void add(BuildItem item) {
            count += item.count;
        }

        public void remove() {
            count--;
        }

        public void remove(BuildItem item) {
            count -= item.count;
        }

        public boolean equals(BuildItem item) {
            return material.equals(item.material);
        }
    }

    public List<BuildItem> getItems() {
        return buildItems;
    }

    public HashMap<Location, Material> getEndLocations() {
        return endLocations;
    }
}