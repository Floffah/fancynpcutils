package dev.floffah.plugin.fancynpcutils.config;

import java.util.List;

public class Config {
    public List<NPCFollowBoundary> boundaryFollows = List.of(new NPCFollowBoundary());

    public static class NPCFollowBoundary {
        public String npcName = "exampleNPC";
        public double x1 = 10;
        public double z1 = 15;
        public double x2 = 25;
        public double z2 = 30;
        public boolean returnToDefaultPoint = false;
        public double defaultX = 20;
        public double defaultZ = 20;
    }
}
