package ru.zaralx.bridgebuilders.commons.game;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BuildItems {
    private final List<BuildItem> buildItems = new ArrayList<>();

    public void add(BuildItem buildItem) {
        for (BuildItem item : buildItems) {
            if (item.equals(buildItem)) {
                item.add(buildItem);
                return;
            }
        }
        buildItems.add(buildItem);
    }

    public void remove(BuildItem buildItem) {
        Iterator<BuildItem> iterator = buildItems.iterator();
        while (iterator.hasNext()) {
            BuildItem item = iterator.next();
            if (item.equals(buildItem)) {
                item.remove(buildItem);
                if (item.count <= 0) {
                    iterator.remove();
                }
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
}