package me.desht.pneumaticcraft.client.util;

import me.desht.pneumaticcraft.PneumaticCraftRepressurized;
import me.desht.pneumaticcraft.client.render.pneumatic_armor.HUDHandler;
import me.desht.pneumaticcraft.client.render.pneumatic_armor.upgrade_handler.EntityTrackUpgradeHandler;
import me.desht.pneumaticcraft.common.entity.living.EntityDrone;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.client.util.InputMappings;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.IParticleData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * Miscellaneous client-side utilities
 */
public class ClientUtils {
    /**
     * Emit particles from just above the given blockpos, which is generally a machine or similar.
     * Only call this clientside.
     *
     * @param world the world
     * @param pos the block pos
     * @param particle the particle type
     */
    public static void emitParticles(World world, BlockPos pos, IParticleData particle) {
        float xOff = world.rand.nextFloat() * 0.6F + 0.2F;
        float zOff = world.rand.nextFloat() * 0.6F + 0.2F;
        PneumaticCraftRepressurized.proxy.getClientWorld().addParticle(particle,
                pos.getX() + xOff, pos.getY() + 1.2, pos.getZ() + zOff,
                0, 0, 0);
    }

    @Nonnull
    public static ItemStack getWornArmor(EquipmentSlotType slot) {
        return Minecraft.getInstance().player.getItemStackFromSlot(slot);
    }

    public static void addDroneToHudHandler(EntityDrone drone, BlockPos pos) {
        HUDHandler.instance().getSpecificRenderer(EntityTrackUpgradeHandler.class).getTargetsStream()
                .filter(target -> target.entity == drone)
                .forEach(target -> target.getDroneAIRenderer().addBlackListEntry(drone.world, pos));
    }

    public static boolean isKeyDown(int keyCode) {
        return InputMappings.isKeyDown(Minecraft.getInstance().mainWindow.getHandle(), keyCode);
    }

    public static void openContainerGui(ContainerType<? extends Container> type, ITextComponent displayString) {
        // This windowId = -1 hack is ugly but appears to work...
        ScreenManager.openScreen(type, Minecraft.getInstance(), -1, displayString);
    }

    /**
     * For use where we can't reference Minecraft directly, e.g. packet handling code.
     * @return the client world
     */
    public static World getClientWorld() {
        return Minecraft.getInstance().world;
    }

    /**
     * Get a TE client-side.  Convenience method for packet handling code, primarily.
     * @return a tile entity or null
     */
    public static TileEntity getClientTE(BlockPos pos) {
        return Minecraft.getInstance().world.getTileEntity(pos);
    }

    /**
     * See AWT Rectangle's intersects() method
     *
     * @param rect
     * @param x
     * @param y
     * @param w
     * @param h
     * @return
     */
    public static boolean intersects(Rectangle2d rect, double x, double y, double w, double h) {
        if (rect.getWidth() <= 0 || rect.getHeight() <= 0 || w <= 0 || h <= 0) {
            return false;
        }
        double x0 = rect.getX();
        double y0 = rect.getY();
        return (x + w > x0 &&
                y + h > y0 &&
                x < x0 + rect.getWidth() &&
                y < y0 + rect.getHeight());
    }
}