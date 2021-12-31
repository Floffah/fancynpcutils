package dev.floffah.plugin.fancynpcutils;

import dev.floffah.plugin.fancynpcutils.boundaries.FollowBoundary;
import dev.floffah.plugin.fancynpcutils.config.Config;

import java.util.ArrayList;
import java.util.List;

public class Manager {
    public final FancyNPCUtils plugin;

    public List<FollowBoundary> runningBounaries = new ArrayList<>();

    public Manager(FancyNPCUtils plugin) {
        this.plugin = plugin;
    }

    public void loadAll() {
        for (Config.NPCFollowBoundary configBoundary : this.plugin.config.config.boundaryFollows) {
            this.load(configBoundary);
        }
    }

    public void load(Config.NPCFollowBoundary configBoundary) {
        this.runningBounaries.add(new FollowBoundary(this, configBoundary));
    }

    public void unloadAll() {
        for (FollowBoundary boundary: this.runningBounaries) {
            this.unload(boundary);
        }
    }

    public void unload(FollowBoundary boundary) {
        boundary.stop();
        this.runningBounaries.remove(boundary);
    }
}
