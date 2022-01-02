package dev.floffah.plugin.fancynpcutils.config;

import dev.floffah.plugin.fancynpcutils.config.behaviour.ConfigBehaviour;
import dev.floffah.plugin.fancynpcutils.config.behaviour.FollowBoundaryConfigBehaviour;

import java.util.List;

public class Config {
    public List<ConfigBehaviour> behaviours = List.of(new FollowBoundaryConfigBehaviour());

}
