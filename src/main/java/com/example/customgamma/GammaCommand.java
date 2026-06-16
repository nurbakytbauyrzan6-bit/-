package com.example.customgamma;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class GammaCommand implements CommandExecutor {

    private static final int INFINITE_DURATION = -1;
    private static final int EFFECT_LEVEL = 0;

    private final CustomGammaPlugin plugin;
    private final MiniMessage miniMessage;

    public GammaCommand(@NotNull CustomGammaPlugin plugin) {
        this.plugin = plugin;
        this.miniMessage = MiniMessage.miniMessage();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                              @NotNull Command command,
                              @NotNull String label,
                              @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(getMessage("messages.console-not-allowed"));
            return true;
        }

        if (!player.hasPermission("customgamma.use")) {
            player.sendMessage(getMessage("messages.no-permission"));
            return true;
        }

        togglesNightVision(player);
        return true;
    }

    private void togglesNightVision(@NotNull Player player) {
        if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
            player.sendMessage(getMessage("messages.disabled"));
        } else {
            PotionEffect nightVisionEffect = new PotionEffect(
                    PotionEffectType.NIGHT_VISION,
                    INFINITE_DURATION,
                    EFFECT_LEVEL,
                    false,
                    false,
                    false
            );
            player.addPotionEffect(nightVisionEffect);
            player.sendMessage(getMessage("messages.enabled"));
        }
    }

    @NotNull
    private Component getMessage(@NotNull String path) {
        String raw = plugin.getConfig().getString(path);
        if (raw == null) {
            raw = "<red>Сообщение не найдено в config.yml: " + path + "</red>";
        }
        return miniMessage.deserialize(raw);
    }
}
