package com.github.frcsty.lasertag;

import com.github.frcsty.lasertag.commands.GameCommand;
import com.github.frcsty.lasertag.commands.WeaponCommand;
import com.github.frcsty.lasertag.game.GameManager;
import com.github.frcsty.lasertag.listeners.PlayerLeaveListener;
import com.github.frcsty.lasertag.listeners.PlayerTagListener;
import com.github.frcsty.lasertag.listeners.WeaponShootListener;
import com.github.frcsty.lasertag.weapons.ProjectileTimer;
import com.github.frcsty.lasertag.weapons.Weapons;
import me.mattstudios.mf.base.CommandManager;
import me.mattstudios.mf.base.components.TypeResult;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class LaserTag extends JavaPlugin
{

    private final GameManager     gameManager     = new GameManager();
    private final ProjectileTimer projectileTimer = new ProjectileTimer(this);

    @Override
    public void onEnable()
    {
        saveDefaultConfig();

        final CommandManager manager = new CommandManager(this);

        getServer().getPluginManager().registerEvents(new WeaponShootListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerTagListener(this), this);

        registerWeaponsEnum(manager);
        manager.register(new WeaponCommand(), new GameCommand(this));

        IntStream.rangeClosed(1, 2).forEach(gameManager::createNewArena);
        projectileTimer.runTimer();
    }

    @Override
    public void onDisable()
    {
        reloadConfig();
    }

    public GameManager getGameManager()
    {
        return gameManager;
    }

    public ProjectileTimer getProjectileTimer()
    {
        return projectileTimer;
    }

    private void registerWeaponsEnum(final CommandManager manager)
    {
        manager.getCompletionHandler().register("#weapons", input -> Arrays.stream(Weapons.values()).map(Weapons::toString).collect(Collectors.toList()));
        manager.getParameterHandler().register(Weapons.class, argument -> new TypeResult(Arrays.stream(Weapons.values()).map(Weapons::toString).collect(Collectors.toList()), argument));
    }

}
