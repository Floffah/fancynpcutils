package dev.floffah.plugin.fancynpcutils.config.behaviour;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.ArrayList;
import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = FollowBoundaryConfigBehaviour.class, name = "followBoundary")
})
public class ConfigBehaviour {
    public String id = "example_config_behaviour";

    public List<ConfigBehaviour> addons = new ArrayList<>();
}
