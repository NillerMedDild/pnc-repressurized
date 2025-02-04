package me.desht.pneumaticcraft.api.item;

import me.desht.pneumaticcraft.api.PneumaticRegistry;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Get an instance of this with {@link PneumaticRegistry.IPneumaticCraftInterface#getItemRegistry()}
 */
public interface IItemRegistry {

    /**
     * Register a third-party class that can contain items.  This is intended for classes from other mods - if it's
     * your class, just make it implement {@link IInventoryItem} directly.
     *
     * @param handler instance of any class that implements {@link IInventoryItem}
     */
    void registerInventoryItem(IInventoryItem handler);

    /**
     * Register an item or block as being able to accept PneumaticCraft upgrades.
     *
     * @param upgradeAcceptor the upgrade acceptor
     */
    void registerUpgradeAcceptor(IUpgradeAcceptor upgradeAcceptor);

    /**
     * Can be used for custom upgrade items to handle tooltips. This will work for implementors registered via
     * {@link IItemRegistry#registerUpgradeAcceptor(IUpgradeAcceptor)}. You would generally call this from your
     * {@link Item#addInformation(ItemStack, World, List, ITooltipFlag)} method to display
     * which machines and/or items accept it.
     *
     * @param upgrade the upgrade item
     * @param tooltip the tooltip string list to append to
     */
    void addTooltip(EnumUpgrade upgrade, List<ITextComponent> tooltip);

    /**
     * Register a magnet suppressor; an object which can prevent the Magnet Upgrade from pulling in (usually item)
     * entities.
     *
     * @param suppressor a suppressor object
     */
    void registerMagnetSuppressor(IMagnetSuppressor suppressor);

    /**
     * Convenience method to check if an item matches a given filter item. Note that the filtering item (the first
     * parameter) could be a Tag Filter or other instance of {@link ITagFilteringItem}, so parameter order is important;
     * provide the filtering item first, and the item to check second.
     *
     * @param filterStack the item to check against
     * @param stack the item being checked
     * @param checkDurability true if item durability should be taken into account
     * @param checkNBT true if item NBT should be taken into account
     * @param checkModSimilarity true to just match by the two items' mod IDs
     * @return true if the item passes the filter test, false otherwise
     */
    boolean doesItemMatchFilter(@Nonnull ItemStack filterStack, @Nonnull ItemStack stack, boolean checkDurability, boolean checkNBT, boolean checkModSimilarity);

    /**
     * Register a handler to calculate the effective volume of a pneumatic item (i.e. one that hold air/pressure).
     *
     * @param modifierFunc a volume modifier function
     */
    void registerPneumaticVolumeModifier(ItemVolumeModifier modifierFunc);

    /**
     * Get some information for the given Spawner Core item
     * @param stack an ItemStack, which must be a Spawner Core
     * @return a spawner core stats object, to query and manipulate the item
     * @throws IllegalArgumentException if the passed ItemStack is not a Spawner Core
     */
    ISpawnerCoreStats getSpawnerCoreStats(ItemStack stack);
}
