package dev.floffah.plugin.fancynpcutils.command;

import dev.floffah.plugin.fancynpcutils.FancyNPCUtils;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;

public class FNPCCommand implements CommandExecutor, TabCompleter {
    final FancyNPCUtils plugin;

    public FNPCCommand(FancyNPCUtils plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
//        if (sender instanceof ConsoleCommandSender) {
//            sender.sendMessage(Component.text("Only players can use this command").color(NamedTextColor.RED));
//            return true;
//        }

//        NPC selectedNPC = CitizensAPI.getDefaultNPCSelector().getSelected(sender);
//        if (selectedNPC == null) {
//            sender.sendMessage(Component.text("You must have an npc selected").color(NamedTextColor.RED));
//            return true;
//        }

        if (args.length == 1) {
            if (args[0].equals("reload")) {
                sender.sendMessage(Component.text("Reloading...").color(NamedTextColor.BLUE));
                try {
                    this.plugin.manager.reloadAll();
                } catch (IOException e) {
                    e.printStackTrace();
                    sender.sendMessage(Component.text("There was an error while reloading FancyNPCUtils. Admins check console").color(NamedTextColor.RED));
                    return true;
                }
                sender.sendMessage(Component.text("Reloaded FancyNPCUtils!").color(NamedTextColor.GREEN));
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) return List.of("reload");

        return null;
    }
}
