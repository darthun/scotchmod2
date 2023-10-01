package net.darthun.scotchmod.block.entity;

import net.darthun.scotchmod.item.ModItems;
import net.darthun.scotchmod.screen.BarleySteepMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BarleySteepBlockEntity extends BlockEntity implements MenuProvider {
    public BarleySteepBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.BARLEY_STEEP_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex){
                    case 0 -> BarleySteepBlockEntity.this.progress;
                    case 1 -> BarleySteepBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex){
                    case 0 -> BarleySteepBlockEntity.this.progress = pValue;
                    case 1 -> BarleySteepBlockEntity.this.maxProgress = pValue;
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
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot){
                case 0 -> stack.getItem() == ModItems.BARLEY_GROWN.get();
                case 1 -> true; //liquid
                case 2 -> false; //output
                default -> super.isItemValid(slot,stack);
            };
        }
    };

    private static final int INPUT_SLOT = 0;
    private static final int FLUID_INPUT_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private final ContainerData data;
    private int progress = 0;
    private int maxProgress = 100;

    @Override
    public Component getDisplayName() {
        return Component.literal("Barley Steep");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer)
    {
        return new BarleySteepMenu(pContainerId,pPlayerInventory,this,this.data);
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
        if(cap == ForgeCapabilities.ITEM_HANDLER){
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap,side);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {

        if (isOutputSlotEmptyOrReceivable() && hasRecipe()){
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

    private void craftItem() {
        this.itemHandler.extractItem(INPUT_SLOT,1,false);

        this.itemHandler.setStackInSlot(OUTPUT_SLOT,new ItemStack(Items.DIAMOND,
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount()+1));
    }


    private boolean hasRecipe() {
        return canInsertAmountIntoOutputSlot(1) && canInsertItemIntoOutputSlot(Items.DIAMOND)
                && hasRecipeItemInInputSlot();
    }

    private boolean hasRecipeItemInInputSlot() {
        return this.itemHandler.getStackInSlot(INPUT_SLOT).getItem() == ModItems.BARLEY_GROWN.get();
    }

    private boolean canInsertItemIntoOutputSlot(Item diamond) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(diamond)
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


}
