package net.darthun.scotchmod.block.entity;


import net.darthun.scotchmod.block.ModBlocks;
import net.darthun.scotchmod.block.custom.MaltKilnBlock;
import net.darthun.scotchmod.item.ModItems;
import net.darthun.scotchmod.recipe.MaltKilnRecipe;
import net.darthun.scotchmod.screen.MaltKilnMenu;
import net.darthun.scotchmod.utils.InventoryDirectionEntry;
import net.darthun.scotchmod.utils.InventoryDirectionWrapper;
import net.darthun.scotchmod.utils.WrappedHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;


public class MaltKilnBlockEntity extends BlockEntity implements MenuProvider {
    public MaltKilnBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.MALT_KILN_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex){
                    case 0 -> MaltKilnBlockEntity.this.progress;
                    case 1 -> MaltKilnBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex){
                    case 0 -> MaltKilnBlockEntity.this.progress = pValue;
                    case 1 -> MaltKilnBlockEntity.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    private final ItemStackHandler itemHandler = new ItemStackHandler(3){
        @Override
        protected void onContentsChanged(int slot) {

            setChanged();
            if(!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(),getBlockState(),getBlockState(),3);
            }
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot){
                case 0 -> true;
                case 1 -> stack.getItem() == ModItems.PEAT.get();
                case 2 -> false; //output
                default -> super.isItemValid(slot,stack);
            };
        }
    };

    // TODO : This is used for custom rendering
//    public ItemStack getRenderStack(){
//        ItemStack stack = itemHandler.getStackInSlot(INPUT_SLOT);
//        if(stack.isEmpty()){
//            stack = itemHandler.getStackInSlot(OUTPUT_SLOT);
//
//        }
//        return stack;
//    }

    private static final int INPUT_SLOT = 0;
    private static final int FUEL_INPUT_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;


    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            new InventoryDirectionWrapper(itemHandler,
                    new InventoryDirectionEntry(Direction.DOWN, OUTPUT_SLOT, false),
                    new InventoryDirectionEntry(Direction.NORTH, INPUT_SLOT, true),
                    new InventoryDirectionEntry(Direction.SOUTH, OUTPUT_SLOT, false),
                    new InventoryDirectionEntry(Direction.EAST, OUTPUT_SLOT, false),
                    new InventoryDirectionEntry(Direction.WEST, INPUT_SLOT, true),
                    new InventoryDirectionEntry(Direction.UP, INPUT_SLOT, true)).directionsMap;

    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();
    private final ContainerData data;
    private int progress = 0;
    private int maxProgress = 100;

    @Override
    public Component getDisplayName() {
        return Component.literal("Malt Kiln");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer)
    {
        return new MaltKilnMenu(pContainerId,pPlayerInventory,this,this.data);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(()-> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory",itemHandler.serializeNBT());
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i =0; i< itemHandler.getSlots(); i++){
            inventory.setItem(i,itemHandler.getStackInSlot(i));

        }
        Containers.dropContents(this.level,this.worldPosition,inventory);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {


        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            if(side == null) {
                return lazyItemHandler.cast();
            }



            if(directionWrappedHandlerMap.containsKey(side)) {
                Direction localDir = this.getBlockState().getValue(MaltKilnBlock.FACING);

                if(side == Direction.DOWN ||side == Direction.UP) {
                    return directionWrappedHandlerMap.get(side).cast();
                }

                return switch (localDir) {
                    default -> directionWrappedHandlerMap.get(side.getOpposite()).cast();
                    case EAST -> directionWrappedHandlerMap.get(side.getClockWise()).cast();
                    case SOUTH -> directionWrappedHandlerMap.get(side).cast();
                    case WEST -> directionWrappedHandlerMap.get(side.getCounterClockWise()).cast();
                };
            }
        }
        return super.getCapability(cap, side);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {

        if (isOutputSlotEmptyOrReceivable() && hasRecipe()
                && hasPagodaRoof(pLevel,pPos)){
            this.progress++;
            setChanged(pLevel,pPos,pState);
            if(this.progress >= this.maxProgress){
                craftItem();
                this.progress = 0;

            }
        }else{
            this.progress = 0;
        }

    }

    private boolean hasPagodaRoof(Level pLevel, BlockPos pPos){
        return pLevel.getBlockState(pPos.above()).getBlock() == ModBlocks.PAGODA_BLOCK.get();
    }

    private void craftItem() {
        Optional<MaltKilnRecipe> recipe = getCurrentRecipe();
        ItemStack resultItem = recipe.get().getResultItem(getLevel().registryAccess());
        this.itemHandler.extractItem(INPUT_SLOT,1,false);
        this.itemHandler.extractItem(FUEL_INPUT_SLOT,1,false);

        this.itemHandler.setStackInSlot(OUTPUT_SLOT,new ItemStack(resultItem.getItem(),
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + resultItem.getCount()));
    }


    private boolean hasRecipe() {
        Optional<MaltKilnRecipe> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) {
            return false;
        }
        ItemStack resultItem = recipe.get().getResultItem(getLevel().registryAccess());
        return canInsertAmountIntoOutputSlot(resultItem.getCount())
                && canInsertItemIntoOutputSlot(resultItem.getItem());
    }


    private Optional<MaltKilnRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for(int i=0;i<this.itemHandler.getSlots();i++){
            inventory.setItem(i,this.itemHandler.getStackInSlot(i));
        }
        return this.level.getRecipeManager().getRecipeFor(MaltKilnRecipe.Type.INSTANCE,inventory,level);
    }

    /*
    private boolean hasRecipeItemInInputSlot() {
        return this.itemHandler.getStackInSlot(INPUT_SLOT).getItem() == ModItems.MALTED_BARLEY.get();
    }*/

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item)
                || this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty();
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize() >=
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count;
    }

    private boolean isOutputSlotEmptyOrReceivable() {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() ||
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() < this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
    }

}
