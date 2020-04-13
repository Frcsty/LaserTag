package com.github.frcsty.lasertag.commands.menus;

import com.github.frcsty.lasertag.LaserTag;
import com.github.frcsty.lasertag.game.GameManager;
import com.github.frcsty.lasertag.utility.Color;
import me.mattstudios.mfgui.gui.components.ItemBuilder;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import me.mattstudios.mfgui.gui.guis.PaginatedGui;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ParticipantsMenu
{

    private final static int    INVENTORY_ROWS      = 4;
    private final static int    INVENTORY_PAGE_SIZE = 14;
    private final static String INVENTORY_NAME      = "          &8Game Participants";
    private final static String USER_DISPLAY_NAME   = "&b{user}";

    private final LaserTag plugin;
    private final Player   player;
    private final int      arena;

    public ParticipantsMenu(final LaserTag plugin, final Player player, final int arena)
    {
        this.plugin = plugin;
        this.player = player;
        this.arena = arena;
    }

    public void getParticipantsMenu()
    {
        final PaginatedGui gui = new PaginatedGui(plugin, INVENTORY_ROWS, INVENTORY_PAGE_SIZE, Color.colorize(INVENTORY_NAME));
        final GuiItem filler = new GuiItem(new ItemBuilder(new ItemStack(Material.GRAY_STAINED_GLASS_PANE)).setName(" ").build());
        final GameManager manager = plugin.getGameManager();
        gui.getFiller().fillBorder(filler);
        gui.setDefaultClickAction(event -> event.setCancelled(true));

        for (int participant = 0; participant < manager.getArenaParticipants(arena).size(); participant++)
        {
            final Player plr = Bukkit.getPlayer(manager.getArenaParticipants(arena).get(participant));

            if (plr != null)
            {
                final GuiItem plrItem = new GuiItem(new ItemBuilder(new ItemStack(Material.PLAYER_HEAD)).setSkullOwner(plr).setName(Color.colorize(USER_DISPLAY_NAME.replace("{user}", plr.getName()))).build());
                gui.addItem(plrItem);
            }
        }

        player.sendMessage(manager.getArenaParticipants(arena).toString());
        gui.open(player);
    }

}
