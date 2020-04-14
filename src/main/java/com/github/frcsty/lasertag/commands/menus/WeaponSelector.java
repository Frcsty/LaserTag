package com.github.frcsty.lasertag.commands.menus;

import com.github.frcsty.lasertag.LaserTag;
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

import java.util.Arrays;
import java.util.List;

public class WeaponSelector
{

    private final static int    INVENTORY_ROWS      = 4;
    private final static int    INVENTORY_PAGE_SIZE = 14;
    private final static String INVENTORY_NAME      = "          &8Weapon Selector";

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
        final Weapons[] weapons = Weapons.values();

        gui.setDefaultClickAction(event -> event.setCancelled(true));
        gui.getFiller().fillBorder(filler);

        for (Weapons weapon : weapons)
        {
            final List<String> lore = Arrays.asList("", " &8• &fAmmo&8: &7{ammo}".replace("{ammo}", String.valueOf(weapon.getAmmo())), "");
            final GuiItem item = new GuiItem(new WeaponBuilder(weapon, weapon.getMaterial(), weapon.getName(), weapon.getAmmo(), lore, weapon.getCustomData()).getWeapon(), event ->
            {
                final ClickType action = event.getClick();

                if (action == ClickType.RIGHT)
                {
                    player.getInventory().addItem(new WeaponBuilder(weapon, weapon.getMaterial(), weapon.getName(), weapon.getAmmo(), lore, weapon.getCustomData()).getWeapon());
                }
            });
            gui.addItem(item);
        }

        previousPage(gui);
        nextPage(gui);
        close(gui);

        gui.open(player);
    }

    private void previousPage(final PaginatedGui gui)
    {
        gui.setItem(30, new GuiItem(new ItemBuilder(new ItemStack(Material.SPECTRAL_ARROW)).setName(Color.colorize("&fPrevious Page &8«")).build(), event -> gui.prevPage()));
    }

    private void nextPage(final PaginatedGui gui)
    {
        gui.setItem(32, new GuiItem(new ItemBuilder(new ItemStack(Material.SPECTRAL_ARROW)).setName(Color.colorize("&fNext Page &8»")).build(), event -> gui.nextPage()));
    }

    private void close(final PaginatedGui gui)
    {
        gui.setItem(31, new GuiItem(new ItemBuilder(new ItemStack(Material.STRUCTURE_VOID)).setName(Color.colorize("&cClose")).build(), event -> gui.close(player)));
    }

}
