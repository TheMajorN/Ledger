package com.themajorn.ledger.util;

import com.themajorn.ledger.blocks.ledgerBase;
import com.themajorn.ledger.ledger;
import com.themajorn.ledger.tileEntity.ledgerContainer;
import com.themajorn.ledger.tileEntity.ledgerTileEntity;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class registryHandler {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ledger.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ledger.MOD_ID);
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ledger.MOD_ID);
    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, ledger.MOD_ID);

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    // Blocks
    //----------

    public static final RegistryObject<Block> LEDGER = BLOCKS.register("ledger", ledgerBase::new);

    // Block Items
    //----------

    public static final RegistryObject<Item> LEDGER_ITEM = ITEMS.register("ledger_item", () -> new BlockItem(LEDGER.get(), new Item.Properties().group(ItemGroup.REDSTONE)));

    // Tile Entities
    //----------

    public static final RegistryObject<TileEntityType<ledgerTileEntity>> LEDGER_TILE = TILE_ENTITY_TYPES.register("ledger_tile",
                        () -> TileEntityType.Builder.create(ledgerTileEntity::new, LEDGER.get()).build(null));


    //Container Types
    //----------

    public static final RegistryObject<ContainerType<ledgerContainer>>LEDGER_CONTAINER = CONTAINER_TYPES.register("ledger1",
                        () -> IForgeContainerType.create(ledgerContainer::new));
}
