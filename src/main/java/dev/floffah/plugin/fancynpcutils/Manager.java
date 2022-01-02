package dev.floffah.plugin.fancynpcutils;

import dev.floffah.plugin.fancynpcutils.behaviours.FollowBoundaryBehaviour;
import dev.floffah.plugin.fancynpcutils.behaviours.RunningBehaviour;
import dev.floffah.plugin.fancynpcutils.config.behaviour.ConfigBehaviour;
import dev.floffah.plugin.fancynpcutils.config.behaviour.FollowBoundaryConfigBehaviour;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Manager {
    public final FancyNPCUtils plugin;

    public List<RunningBehaviour> runningBoundaries = new ArrayList<>();

    public Manager(FancyNPCUtils plugin) {
        this.plugin = plugin;
    }

    public void reloadAll() throws IOException {
        this.unloadAll();
        this.plugin.config.readConfig();
        this.loadAll();
    }

    public void loadAll() {
        for (ConfigBehaviour configBehaviour : this.plugin.config.config.behaviours) {
            this.load(configBehaviour);
        }
    }

    public void load(ConfigBehaviour configBehaviour) {
        RunningBehaviour runningBehaviour = this.associateRunnableBehaviour(configBehaviour);

        runningBehaviour.start();
        runningBehaviour.startChildren();

        this.runningBoundaries.add(runningBehaviour);
    }

    public RunningBehaviour associateRunnableBehaviour(ConfigBehaviour configBehaviour) {
        RunningBehaviour runningBehaviour = null;
        if (configBehaviour instanceof FollowBoundaryConfigBehaviour followBoundaryConfigBehaviour) {
            runningBehaviour = new FollowBoundaryBehaviour(this, followBoundaryConfigBehaviour);
        }
        if (runningBehaviour != null && configBehaviour.addons.size() > 0) {
            for (ConfigBehaviour childBehaviour : configBehaviour.addons) {
                runningBehaviour.children.add(this.associateRunnableBehaviour(childBehaviour));
            }
        }
        return runningBehaviour;
    }

    public void unloadAll() {
        for (RunningBehaviour boundary : this.runningBoundaries) {
            this.unload(boundary);
        }
    }

    public void unload(RunningBehaviour boundary) {
        boundary.stopChildren();
        boundary.stop();
        for (RunningBehaviour child : boundary.children) {
            this.unload(child);
        }
        this.runningBoundaries.remove(boundary);
    }
}
