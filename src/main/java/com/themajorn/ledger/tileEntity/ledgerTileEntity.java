package com.themajorn.ledger.tileEntity;

import com.themajorn.ledger.util.registryHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;

public class ledgerTileEntity extends TileEntity implements INamedContainerProvider {

    public ledgerTileEntity(final TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public ledgerTileEntity() {
        this(registryHandler.LEDGER_TILE.get());
    }



    @Override
    public ITextComponent getDisplayName() {
        return null;
    }

    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return null;
    }
}
