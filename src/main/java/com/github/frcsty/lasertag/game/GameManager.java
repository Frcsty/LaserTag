package com.github.frcsty.lasertag.game;

import com.github.frcsty.lasertag.utility.Color;
import com.github.frcsty.lasertag.weapons.Weapons;
import me.mattstudios.mfgui.gui.components.ItemNBT;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class GameManager
{
    private int countdown = 15;
    private int game = 90;
    private int timer;

    private final Map<Integer, List<UUID>> arenas = new HashMap<>();
    private final Map<Integer, Boolean> arenaStatus = new HashMap<>();
    private final List<UUID> participants = new ArrayList<>();
    private final Timer gameTimer = new Timer();
    private final TimerTask gameTask = new TimerTask()
    {
        @Override
        public void run()
        {
            for (Integer arena : arenas.keySet())
            {
                for (UUID uuid : arenas.get(arena))
                {
                    final Player player = Bukkit.getPlayer(uuid);

                    if (player == null)
                    {
                        return;
                    }

                    if (timer <= countdown)
                    {
                        player.sendActionBar(Color.colorize("&bGame Starting In&8: &f{countdown}s".replace("{countdown}", String.valueOf(getCountdown()))));
                    }

                    if (timer <= game && timer > countdown)
                    {
                        final ItemStack item = player.getInventory().getItemInMainHand();

                        if (item.getType().equals(Material.AIR))
                        {
                            return;
                        }

                        final String object = ItemNBT.getNBTTag(item, "weapon");
                        final int ammo = Integer.valueOf(ItemNBT.getNBTTag(item, "ammo"));

                        if (object != null)
                        {
                            final Weapons weapon = Weapons.valueOf(object.toUpperCase());

                            if (ammo > 0)
                            {
                                player.sendActionBar(Color.colorize("&8| &fAmmo&8: &7{ammo}&8/&7{max-ammo} &8|".replace("{ammo}", String.valueOf(ammo)).replace("{max-ammo}", String.valueOf(weapon.getAmmo()))));
                            }
                            else
                            {
                                player.sendActionBar(Color.colorize("&8| &fLeft-Click To Reload &8|"));
                            }
                        }
                    }
                }
            }
            // Needs to be adjusted for multiple arenas
            if (timer == game)
            {
                for (Integer arena : arenas.keySet())
                {
                    for (UUID uuid : arenas.get(arena))
                    {
                        final Player player = Bukkit.getPlayer(uuid);

                        if (player == null)
                        {
                            return;
                        }

                        player.getInventory().clear();
                        player.sendMessage(Color.colorize("&8[&bLaserTag&8] &7The Game Has Ended!"));
                    }

                    removeArenaParticipants(arena);
                    setArenaStatus(arena, false);
                }
            }
            timer++;
        }
    };

    public Map<Integer, List<UUID>> getArenas()
    {
        return arenas;
    }

    public void startGameTimer()
    {
        gameTimer.schedule(gameTask, 0, 1000);
    }

    private int getCountdown()
    {
        return countdown - timer;
    }

    public int getGameTimer()
    {
        return game - timer;
    }

    public void createNewArena(final int id)
    {
        if (!arenas.containsKey(id))
        {
            arenas.put(id, participants);
        }
    }

    public void setArenaStatus(final int id, final boolean status)
    {
        if (arenas.containsKey(id))
        {
            arenaStatus.put(id, status);
        }
    }

    public boolean getGameStatus(final int id)
    {
        return arenaStatus.get(id);
    }

    public List<UUID> getArenaParticipants(final int id)
    {
        if (arenas.containsKey(id))
        {
            return arenas.get(id);
        }
        return null;
    }

    public void setArenaParticipants(final int id, final List<UUID> participants)
    {
        if (arenas.containsKey(id))
        {
            arenas.replace(id, participants);
        }
    }

    public void addArenaParticipant(final int id, final UUID uuid)
    {
        if (arenas.containsKey(id))
        {
            final List<UUID> participants = arenas.get(id);
            participants.add(uuid);

            arenas.replace(id, participants);
        }
    }

    public void removeArenaParticipant(final int id, final UUID uuid)
    {
        if (arenas.containsKey(id))
        {
            final List<UUID> participants = arenas.get(id);

            participants.remove(uuid);
            arenas.replace(id, participants);
        }
    }

    public void removeArenaParticipants(final int id)
    {
        if (arenas.containsKey(id))
        {
            arenas.put(id, null);
        }
    }

    public boolean isArenaParticipant(final int id, final UUID uuid)
    {
        if (arenas.containsKey(id))
        {
            return arenas.get(id).contains(uuid);
        }
        return false;
    }

}
