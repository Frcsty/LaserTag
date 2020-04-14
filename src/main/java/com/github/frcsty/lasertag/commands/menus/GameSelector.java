package com.github.frcsty.lasertag.commands.menus;

import com.github.frcsty.lasertag.LaserTag;
import com.github.frcsty.lasertag.game.GameManager;
import com.github.frcsty.lasertag.utility.Color;
import me.mattstudios.mfgui.gui.components.ItemBuilder;
import me.mattstudios.mfgui.gui.guis.Gui;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameSelector
{

    private final static int    INVENTORY_ROWS = 3;
    private final static String INVENTORY_NAME = "            &8Game Selector";

    private final static String ARENA_NAME = "&fArena &8(&f{id}&8)";

    private final LaserTag plugin;
    private final Player   player;

    public GameSelector(final LaserTag plugin, final Player player)
    {
        this.plugin = plugin;
        this.player = player;
    }

    public void getGameSelector()
    {
        final Gui gui = new Gui(plugin, INVENTORY_ROWS, Color.colorize(INVENTORY_NAME));
        final GuiItem filler = new GuiItem(new ItemBuilder(new ItemStack(Material.GRAY_STAINED_GLASS_PANE)).setName(" ").build());
        final GameManager manager = plugin.getGameManager();

        gui.setDefaultClickAction(event -> event.setCancelled(true));
        gui.getFiller().fillBorder(filler);

        final List<Integer> emptySlots = new ArrayList<>();
        for (int i = 0; i < gui.getRows() * 9; i++)
        {
            if (gui.getGuiItem(i) == null)
            {
                emptySlots.add(i);
            }
        }

        for (Integer arena : manager.getArenas().keySet())
        {
            int slot = emptySlots.get(arena - 1);
            int players = manager.getArenaParticipants(arena).size();
            GuiItem joinGame = new GuiItem(
                    getArenaItem(arena, Material.LIGHT_BLUE_CONCRETE_POWDER, players, "&7Click To Join").build(), event ->
            {
                final ClickType action = event.getClick();
                int participants = manager.getArenaParticipants(arena).size();

                if (action == ClickType.LEFT)
                {
                    if (manager.getArenaParticipants(arena).contains(player.getUniqueId()))
                    {
                        updateArenaItem(gui, arena, slot, Material.RED_CONCRETE_POWDER, participants, "&cAlready Joined!");
                        new BukkitRunnable()
                        {
                            @Override
                            public void run()
                            {
                                updateArenaItem(gui, arena, slot, Material.LIGHT_BLUE_CONCRETE_POWDER, participants, "&aJoined");
                            }
                        }.runTaskLaterAsynchronously(plugin, 20);
                    }
                    else
                    {
                        updateArenaItem(gui, arena, slot, Material.LIME_CONCRETE_POWDER, participants, "&aSuccessfully Joined");
                        new BukkitRunnable()
                        {
                            @Override
                            public void run()
                            {
                                updateArenaItem(gui, arena, slot, Material.LIGHT_BLUE_CONCRETE_POWDER, participants, "&aJoined");
                            }
                        }.runTaskLaterAsynchronously(plugin, 20);
                        manager.addArenaParticipant(arena, player.getUniqueId());
                        player.sendMessage(Color.colorize("&8[&bLaserTag&8] &7You have queued into the game!"));
                    }
                }
                else if (action == ClickType.RIGHT)
                {
                    player.sendMessage(manager.getArenaParticipants(arena).toString());
                    if (!manager.getArenaParticipants(arena).contains(player.getUniqueId()))
                    {
                        updateArenaItem(gui, arena, slot, Material.RED_CONCRETE_POWDER, participants, "&cLeft-Click To Join!");
                        new BukkitRunnable()
                        {
                            @Override
                            public void run()
                            {
                                updateArenaItem(gui, arena, slot, Material.LIGHT_BLUE_CONCRETE_POWDER, participants, "&7Click To Join");
                            }
                        }.runTaskLaterAsynchronously(plugin, 20);
                    }
                    else
                    {
                        updateArenaItem(gui, arena, slot, Material.RED_CONCRETE_POWDER, participants, "&aSuccessfully Left Queue");
                        new BukkitRunnable()
                        {
                            @Override
                            public void run()
                            {
                                updateArenaItem(gui, arena, slot, Material.LIGHT_BLUE_CONCRETE_POWDER, participants, "&7Click To Join");
                            }
                        }.runTaskLaterAsynchronously(plugin, 20);
                        manager.removeArenaParticipant(arena, player.getUniqueId());
                        player.sendMessage(Color.colorize("&8[&bLaserTag&8] &7You have left the game queue!"));
                    }
                }
            });
            gui.setItem(slot, joinGame);
        }

        final GuiItem close = new GuiItem(
                new ItemBuilder(new ItemStack(Material.STRUCTURE_VOID))
                        .setName(Color.colorize("&cClose"))
                        .build(), event ->
                {
                    final ClickType action = event.getClick();

                    if (action == ClickType.LEFT)
                    {
                        player.sendMessage(Color.colorize("&8[&bLaserTag&8] &7You have closed the game selector!"));
                        gui.close(player);
                    }
                });
        gui.setItem(22, close);

        gui.open(player);
    }

    private void updateArenaItem(final Gui gui, final int arena, final int slot, final Material material, final int participants, final String status)
    {
        gui.updateItem(slot, getArenaItem(arena, material, participants, status).build());
    }

    private ItemBuilder getArenaItem(final int arena, final Material material, final int participants, final String status)
    {
        final List<String> ARENA_LORE = Arrays.asList("", " &8• &fParticipants&8: &7{participants}".replace("{participants}", String.valueOf(participants)), " &8• &fUser Status&8: &7{user-status}".replace("{user-status}", status), "");

        return new ItemBuilder(material)
                .setName(Color.colorize(ARENA_NAME.replace("{id}", String.valueOf(arena))))
                .setLore(Color.colorize(ARENA_LORE));
    }

}
