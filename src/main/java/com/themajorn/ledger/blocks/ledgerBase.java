package com.themajorn.ledger.blocks;

import com.themajorn.ledger.tileEntity.ledgerTileEntity;
import com.themajorn.ledger.util.registryHandler;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.stream.Stream;

public class ledgerBase extends Block {

    //--------------------------------------
    //-------------Constructor--------------
    //--------------------------------------

    public ledgerBase() {
        super(AbstractBlock.Properties.create(Material.WOOD)
                .hardnessAndResistance(1.0f, 1.0f)
                .sound(SoundType.WOOD)
                .harvestLevel(0));
    }

    //--------------------------------------
    //--------Block Placement Data----------
    //--------------------------------------

    private static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

    public static final VoxelShape SHAPE_N = Stream.of(
            Block.makeCuboidShape(0, 14, 1, 16, 15, 15),
            Block.makeCuboidShape(2, 0, 2, 14, 1, 14),
            Block.makeCuboidShape(5, 1, 5, 11, 5, 11),
            Block.makeCuboidShape(6, 14, 8, 10, 15, 10),
            Block.makeCuboidShape(6, 5, 6, 10, 14, 10),
            Block.makeCuboidShape(2, 15.100000000000001, 4, 14, 16.1, 12),
            Block.makeCuboidShape(1, 15.1, 3, 15, 15.1, 13),
            Block.makeCuboidShape(0, 15, 1, 16, 16, 2)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    public static final VoxelShape SHAPE_E = Stream.of(
            Block.makeCuboidShape(1, 14, 0, 15, 15, 16),
            Block.makeCuboidShape(2, 0, 2, 14, 1, 14),
            Block.makeCuboidShape(5, 1, 5, 11, 5, 11),
            Block.makeCuboidShape(6, 14, 6, 8, 15, 10),
            Block.makeCuboidShape(6, 5, 6, 10, 14, 10),
            Block.makeCuboidShape(4, 15.100000000000001, 2, 12, 16.1, 14),
            Block.makeCuboidShape(3, 15.1, 1, 13, 15.1, 15),
            Block.makeCuboidShape(14, 15, 0, 15, 16, 16)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    public static final VoxelShape SHAPE_S = Stream.of(
            Block.makeCuboidShape(0, 14, 1, 16, 15, 15),
            Block.makeCuboidShape(2, 0, 2, 14, 1, 14),
            Block.makeCuboidShape(5, 1, 5, 11, 5, 11),
            Block.makeCuboidShape(6, 14, 6, 10, 15, 8),
            Block.makeCuboidShape(6, 5, 6, 10, 14, 10),
            Block.makeCuboidShape(2, 15.100000000000001, 4, 14, 16.1, 12),
            Block.makeCuboidShape(1, 15.1, 3, 15, 15.1, 13),
            Block.makeCuboidShape(0, 15, 14, 16, 16, 15)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    public static final VoxelShape SHAPE_W = Stream.of(
            Block.makeCuboidShape(1, 14, 0, 15, 15, 16),
            Block.makeCuboidShape(2, 0, 2, 14, 1, 14),
            Block.makeCuboidShape(5, 1, 5, 11, 5, 11),
            Block.makeCuboidShape(8, 14, 6, 10, 15, 10),
            Block.makeCuboidShape(6, 5, 6, 10, 14, 10),
            Block.makeCuboidShape(4, 15.100000000000001, 2, 12, 16.1, 14),
            Block.makeCuboidShape(3, 15.1, 1, 13, 15.1, 15),
            Block.makeCuboidShape(1, 15, 0, 2, 16, 16)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(FACING)) {
            case NORTH:
                return SHAPE_N;
            case EAST:
                return SHAPE_E;
            case SOUTH:
                return SHAPE_S;
            case WEST:
                return SHAPE_W;
            default:
                return SHAPE_N;
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 0.6f;
    }

    //--------------------------------------
    //--------Block Activation Data---------
    //--------------------------------------

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return registryHandler.LEDGER_TILE.get().create();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn != null && !worldIn.isRemote) {
            TileEntity tile = worldIn.getTileEntity(pos);
            if (tile instanceof ledgerTileEntity) {
                NetworkHooks.openGui((ServerPlayerEntity)player, (INamedContainerProvider)tile, pos);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.hasTileEntity() && state.getBlock() != newState.getBlock()) {
            worldIn.removeTileEntity(pos);
        }
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
        return Container.calcRedstone(worldIn.getTileEntity(pos));
    }
}
