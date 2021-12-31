package dev.floffah.plugin.fancynpcutils.boundaries;

import dev.floffah.plugin.fancynpcutils.Manager;
import dev.floffah.plugin.fancynpcutils.config.Config;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class FollowBoundary implements Listener {
    public final Manager manager;
    public final Config.NPCFollowBoundary configBoundary;

    public NPC relatedNPC = null;
    public Player currentlyFollowing = null;
    public List<Player> canFollow = new ArrayList<>();

    public FollowBoundary(Manager manager, Config.NPCFollowBoundary configBoundary) {
        this.manager = manager;
        this.configBoundary = configBoundary;
        this.manager.plugin.getServer().getPluginManager().registerEvents(this, this.manager.plugin);

        for (NPC npc : CitizensAPI.getNPCRegistry()) {
            if (npc.getName().equals(this.configBoundary.npcName)) {
                this.relatedNPC = npc;
                break;
            }
        }
        if (this.relatedNPC == null) {
            this.manager.plugin.getLogger().log(Level.SEVERE, "No such NPC " + this.configBoundary.npcName);
            this.manager.unload(this);
            return;
        }
    }

    public void stop() {
        PlayerMoveEvent.getHandlerList().unregister(this);
        PlayerQuitEvent.getHandlerList().unregister(this);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        Location loc = e.getTo();
        boolean withinBounds = this.withinBoundary(loc);
        if (this.relatedNPC.getEntity().getWorld().equals(loc.getWorld()) && withinBounds) {
            if (!this.canFollow.contains(player)) {
                this.canFollow.add(player);
            }
            if (this.currentlyFollowing == null) {
                this.currentlyFollowing = player;
            }
        }
        if (this.canFollow.contains(player) && !withinBounds) {
            this.canFollow.remove(player);
        }
        if (this.currentlyFollowing != null && this.currentlyFollowing.equals(player) && !withinBounds) {
            if (this.canFollow.size() > 0) {
                this.currentlyFollowing = this.canFollow.remove(0);
            } else {
                this.currentlyFollowing = null;
                this.relatedNPC.getNavigator().setTarget(
                        new Location(this.relatedNPC.getEntity().getWorld(),
                                this.configBoundary.defaultX,
                                this.relatedNPC.getEntity().getWorld().getHighestBlockYAt(
                                        Double.valueOf(this.configBoundary.defaultX).intValue(),
                                        Double.valueOf(this.configBoundary.defaultZ).intValue()
                                ),
                                this.configBoundary.defaultZ
                        )
                );
            }
        }
        if (this.currentlyFollowing != null && this.currentlyFollowing.equals(player)) {
            this.relatedNPC.getNavigator().setTarget(player, false);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        this.canFollow.remove(e.getPlayer());
        if (this.currentlyFollowing != null && this.currentlyFollowing.equals(e.getPlayer())) {
            if (this.canFollow.size() > 0) {
                this.currentlyFollowing = this.canFollow.remove(0);
            } else {
                this.currentlyFollowing = null;
                this.relatedNPC.getNavigator().setTarget(
                        new Location(this.relatedNPC.getEntity().getWorld(),
                                this.configBoundary.defaultX,
                                this.relatedNPC.getEntity().getWorld().getHighestBlockYAt(
                                        Double.valueOf(this.configBoundary.defaultX).intValue(),
                                        Double.valueOf(this.configBoundary.defaultZ).intValue()
                                ),
                                this.configBoundary.defaultZ
                        )
                );
            }
        }
    }

    public boolean withinBoundary(Location location) {
        double x1 = Math.min(this.configBoundary.x1, this.configBoundary.x2);
        double x2 = Math.max(this.configBoundary.x1, this.configBoundary.x2);
        double z1 = Math.min(this.configBoundary.z1, this.configBoundary.z2);
        double z2 = Math.max(this.configBoundary.z1, this.configBoundary.z2);
        return location.getX() >= x1
                && location.getX() <= x2
                && location.getZ() >= z1
                && location.getZ() <= z2;
    }
}
