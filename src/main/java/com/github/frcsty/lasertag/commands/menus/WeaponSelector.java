package com.github.frcsty.lasertag.commands.menus;

import com.github.frcsty.lasertag.LaserTag;
import com.github.frcsty.lasertag.game.GameManager;
import com.github.frcsty.lasertag.utility.Color;
import com.github.frcsty.lasertag.weapons.WeaponBuilder;
import com.github.frcsty.lasertag.weapons.Weapons;
import me.mattstudios.mfgui.gui.components.ItemBuilder;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import me.mattstudios.mfgui.gui.guis.PaginatedGui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class WeaponSelector
{

    private final static int    INVENTORY_ROWS      = 3;
    private final static int    INVENTORY_PAGE_SIZE = 7;
    private final static String INVENTORY_NAME      = "        &8Weapon Selector";

    private final LaserTag plugin;
    private final Player   player;

    public WeaponSelector(final LaserTag plugin, final Player player)
    {
        this.plugin = plugin;
        this.player = player;
    }

    public void getWeaponSelector()
    {
        final PaginatedGui gui = new PaginatedGui(plugin, INVENTORY_ROWS, INVENTORY_PAGE_SIZE, Color.colorize(INVENTORY_NAME));
        final GuiItem filler = new GuiItem(new ItemBuilder(new ItemStack(Material.GRAY_STAINED_GLASS_PANE)).setName(" ").build());
        final GameManager manager = plugin.getGameManager();
        final Weapons[] weapons = Weapons.values();

        gui.setDefaultClickAction(event -> event.setCancelled(true));
        gui.getFiller().fillBorder(filler);

        for (Weapons weapon : weapons)
        {
            WeaponBuilder gun = new WeaponBuilder(weapon, weapon.getMaterial(), weapon.getName(), weapon.getAmmo());

            final GuiItem item = new GuiItem(new ItemBuilder(new ItemStack(weapon.getMaterial())).setName(Color.colorize(weapon.getName())).build(), event ->
            {
                final ClickType action = event.getClick();

                if (action == ClickType.RIGHT)
                {
                    player.getInventory().addItem(gun.getWeapon());
                }
            });
            gui.addItem(item);
        }

        int arena = 0;

        for (Integer a : manager.getArenas().keySet())
        {
            if (manager.getArenaParticipants(a).contains(player.getUniqueId()))
            {
                arena = a;
            }
        }

        if (manager.getGameStatus(arena))
        {
            gui.open(player);
        }
        else
        {
            player.sendMessage(Color.colorize("&8[&bLaserTag&8] &7Game is not active!"));
        }
    }

}
