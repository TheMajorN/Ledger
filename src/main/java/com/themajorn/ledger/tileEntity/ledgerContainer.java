package com.themajorn.ledger.tileEntity;

import com.themajorn.ledger.util.registryHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;

import java.util.Objects;

public class ledgerContainer extends Container {

    //X = 12, Y = 15

    private ledgerTileEntity tileEntity;
    private IWorldPosCallable canInteractWithCallable;

    // Server Constructor
    public ledgerContainer(final int windowID, final PlayerInventory playerInv, final ledgerTileEntity tile) {
        super(registryHandler.LEDGER_CONTAINER.get(), windowID);

        this.tileEntity = tile;
        this.canInteractWithCallable = IWorldPosCallable.of(tile.getWorld(), tile.getPos());
    }

    // Client Constructor
    public ledgerContainer(final int windowID, final PlayerInventory playerInv, final PacketBuffer data) {
        this(windowID, playerInv, getTileEntity(playerInv, data));
    }

    private static ledgerTileEntity getTileEntity(final PlayerInventory playerInv, final PacketBuffer data) {
        Objects.requireNonNull(playerInv, "playerInv cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final TileEntity tileAtPos = playerInv.player.world.getTileEntity(data.readBlockPos());
        if (tileAtPos instanceof ledgerTileEntity) {
            return (ledgerTileEntity)tileAtPos;
        }
        throw new IllegalStateException("TileEntity is not correct " + tileAtPos);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(canInteractWithCallable, playerIn, registryHandler.LEDGER.get());
    }
}
