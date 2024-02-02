package ru.zaralx.bridgebuilders.commons.npc;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Skin {
    private String signature = "";
    private String texture = "";

    public Skin(String texture, String signature) {
        this.signature = signature;
        this.texture = texture;
    }

    public Skin(String nickname) {
        getSkin(nickname);
    }

    private void getSkin(String nickname) {
        try {
            URL mojangUrl = new URL("https://api.mojang.com/users/profiles/minecraft/" + nickname);
            BufferedReader reader = new BufferedReader(new InputStreamReader(mojangUrl.openStream()));
            MojangProfile profile = new Gson().fromJson(reader, MojangProfile.class);
            reader.close();

            URL skinUrl = new URL("https://sessionserver.mojang.com/session/minecraft/profile/"+profile.getId()+"?unsigned=false");
            BufferedReader skinReader = new BufferedReader(new InputStreamReader(skinUrl.openStream()));
            JsonObject skinJson = new Gson().fromJson(skinReader, JsonObject.class);
            skinReader.close();

            JsonArray properties = skinJson.getAsJsonArray("properties");
            if (properties != null && !properties.isEmpty()) {
                JsonObject property = properties.get(0).getAsJsonObject();
                String value = property.get("value").getAsString();
                System.err.println(value);
                String signature = property.get("signature").getAsString();
                System.err.println(signature);
                this.texture = value;
                this.signature = signature;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getSignature() {
        return signature;
    }

    public String getTexture() {
        return texture;
    }

    private static class MojangProfile {
        private String id;
        private String name;

        public String getId() {
            return id;
        }
    }
}
