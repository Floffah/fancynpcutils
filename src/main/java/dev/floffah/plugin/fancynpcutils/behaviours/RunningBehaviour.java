package dev.floffah.plugin.fancynpcutils.behaviours;

import dev.floffah.plugin.fancynpcutils.Manager;
import dev.floffah.plugin.fancynpcutils.flags.BehaviourFlagStore;
import dev.floffah.plugin.fancynpcutils.flags.FlagStore;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RunningBehaviour {
    public final Manager manager;

    @Nullable
    public RunningBehaviour parent;

    public List<RunningBehaviour> children = new ArrayList<>();

    public boolean stopped = true;

    public FlagStore<String> flags = new BehaviourFlagStore<>();

    public RunningBehaviour(Manager manager) {
        this.manager = manager;
    }

    public void start() {
        this.stopped = false;
    }

    public void startChildren() {
        for (RunningBehaviour child : this.children) {
            child.start();
            child.startChildren();
            child.stopped = false;
        }
    }

    public void stop() {
        this.stopped = true;
    }

    public void stopChildren() {
        for (RunningBehaviour child : this.children) {
            child.stopChildren();
            child.stop();
            child.stopped = true;
        }
    }
}
