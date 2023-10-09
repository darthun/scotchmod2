package net.darthun.scotchmod.block.entity;




import net.darthun.scotchmod.block.custom.MashTunBlock;
import net.darthun.scotchmod.fluid.ModFluids;
import net.darthun.scotchmod.recipe.MashTunRecipe;
import net.darthun.scotchmod.screen.MashTunMenu;
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


public class MashTunBlockEntity extends BlockEntity implements MenuProvider {
    public MashTunBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.MASH_TUN_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex){
                    case 0 -> MashTunBlockEntity.this.progress;
                    case 1 -> MashTunBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex){
                    case 0 -> MashTunBlockEntity.this.progress = pValue;
                    case 1 -> MashTunBlockEntity.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    private final ItemStackHandler itemHandler = new ItemStackHandler(2){
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
                case 1 -> stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent();
                //case 2 -> false; //output
                default -> super.isItemValid(slot,stack);
            };
        }
    };

    public ItemStack getRenderStack(){
        ItemStack stack = itemHandler.getStackInSlot(INPUT_SLOT);
        return stack;
    }

    private static final int INPUT_SLOT = 0;
    private static final int FLUID_INPUT_SLOT = 1;
   // private static final int FLUID_OUTPUT_SLOT = 2;

    private final FluidTank FLUID_TANK = createFluidTank();
    private final FluidTank OUTPUT_FLUID_TANK = createOutputFluidTank();

    private FluidTank createFluidTank() {
        return new FluidTank(1000){
            @Override
            protected void onContentsChanged() {
                setChanged();
                if(!level.isClientSide()){
                    level.sendBlockUpdated(getBlockPos(),getBlockState(),getBlockState(),3);
                }
            }

            @Override
            public boolean isFluidValid(FluidStack stack) {
                return stack.getFluid() == Fluids.WATER;
            }
        };
    }

    private FluidTank createOutputFluidTank() {
        return new FluidTank(1000){
            @Override
            protected void onContentsChanged() {
                setChanged();
                if(!level.isClientSide()){
                    level.sendBlockUpdated(getBlockPos(),getBlockState(),getBlockState(),3);
                }
            }

            @Override
            public boolean isFluidValid(FluidStack stack) {
                return stack.getFluid() == ModFluids.SOURCE_WORT.get();
            }
        };
    }


    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            new InventoryDirectionWrapper(itemHandler,
                    //new InventoryDirectionEntry(Direction.DOWN, OUTPUT_SLOT, false),
                    new InventoryDirectionEntry(Direction.NORTH, INPUT_SLOT, true),
                    //new InventoryDirectionEntry(Direction.SOUTH, OUTPUT_SLOT, false),
                    //new InventoryDirectionEntry(Direction.EAST, OUTPUT_SLOT, false),
                    new InventoryDirectionEntry(Direction.WEST, INPUT_SLOT, true),
                    new InventoryDirectionEntry(Direction.UP, INPUT_SLOT, true)).directionsMap;

    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyOutputFluidHandler = LazyOptional.empty();
    private final ContainerData data;
    private int progress = 0;
    private int maxProgress = 100;

    @Override
    public Component getDisplayName() {
        return Component.literal("Mash Tun");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer)
    {
        return new MashTunMenu(pContainerId,pPlayerInventory,this,this.data);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(()-> itemHandler);
        lazyFluidHandler = LazyOptional.of(()-> FLUID_TANK);
        lazyOutputFluidHandler = LazyOptional.of(()-> OUTPUT_FLUID_TANK);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyFluidHandler.invalidate();
        lazyOutputFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory",itemHandler.serializeNBT());
        pTag = FLUID_TANK.writeToNBT(pTag);
        pTag = OUTPUT_FLUID_TANK.writeToNBT(pTag);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        FLUID_TANK.readFromNBT(pTag);
        OUTPUT_FLUID_TANK.readFromNBT(pTag);
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

        if(cap == ForgeCapabilities.FLUID_HANDLER){
                return lazyFluidHandler.cast();
        }

        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            if(side == null) {
                return lazyItemHandler.cast();
            }



            if(directionWrappedHandlerMap.containsKey(side)) {
                Direction localDir = this.getBlockState().getValue(MashTunBlock.FACING);

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
        //Empties any bucket into the water tank
        fillUpOnFluid();

        //Do we have room in our wort tank and the smoked stuff
        if (isOutputTankReceivable() && hasRecipe()){
            this.progress++;
            setChanged(pLevel,pPos,pState);
            if(this.progress >= this.maxProgress){
                craftItem();
                craftFluid();
                this.progress = 0;

            }
        }else{
            this.progress = 0;
        }

    }

    private void craftFluid() {
        this.FLUID_TANK.drain(100, IFluidHandler.FluidAction.EXECUTE);
        this.OUTPUT_FLUID_TANK.fill(
                new FluidStack(ModFluids.SOURCE_WORT.get(),10), IFluidHandler.FluidAction.EXECUTE

        );
    }

    private void fillUpOnFluid() {
        if(hasFluidSourceInSlot(FLUID_INPUT_SLOT)){
            transferItemFluidToTank(FLUID_INPUT_SLOT);
        }
    }

    private void transferItemFluidToTank(int fluidInputSlot) {
        this.itemHandler.getStackInSlot(fluidInputSlot).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(iFluidHandlerItem -> {
            int drainAmount = Math.min(this.FLUID_TANK.getSpace(),1000);

            FluidStack stack = iFluidHandlerItem.drain(drainAmount, IFluidHandler.FluidAction.SIMULATE);
            if(stack.getFluid() == Fluids.WATER){
                stack = iFluidHandlerItem.drain(drainAmount, IFluidHandler.FluidAction.EXECUTE);
                fillTankWithWater(stack,iFluidHandlerItem.getContainer());
            }
        });

    }

    private void fillTankWithWater(FluidStack stack, ItemStack container) {
        this.FLUID_TANK.fill(new FluidStack(stack.getFluid(),stack.getAmount()),IFluidHandler.FluidAction.EXECUTE);
        this.itemHandler.extractItem(FLUID_INPUT_SLOT,1,false);
        this.itemHandler.insertItem(FLUID_INPUT_SLOT,container,false);
    }

    private boolean hasFluidSourceInSlot(int fluidInputSlot) {
        return this.itemHandler.getStackInSlot(fluidInputSlot).getCount() > 0 &&
                this.itemHandler.getStackInSlot(fluidInputSlot).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent();
    }

    private void craftItem() {
        //At this point, we're doing it !
        Optional<MashTunRecipe> recipe = getCurrentRecipe();
        ItemStack resultItem = recipe.get().getResultItem(getLevel().registryAccess());
        this.itemHandler.extractItem(INPUT_SLOT,1,false);

    }


    private boolean  hasRecipe() {
        Optional<MashTunRecipe> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) {
            return false;
        }
        ItemStack resultItem = recipe.get().getResultItem(getLevel().registryAccess());
        return hasEnoughFluidToCraft();
    }

    private boolean hasEnoughFluidToCraft() {
        return this.FLUID_TANK.getFluidAmount()>=100;
    }

    private Optional<MashTunRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for(int i=0;i<this.itemHandler.getSlots();i++){
            inventory.setItem(i,this.itemHandler.getStackInSlot(i));
        }
        return this.level.getRecipeManager().getRecipeFor(MashTunRecipe.Type.INSTANCE,inventory,level);
    }

    /*
    private boolean hasRecipeItemInInputSlot() {
        return this.itemHandler.getStackInSlot(INPUT_SLOT).getItem() == ModItems.yeast.get();
    }*/


    private boolean isOutputTankReceivable() {
        //we got 1000mb in that tank, we want something under 900.
        return this.OUTPUT_FLUID_TANK.getFluidAmount() <= (this.OUTPUT_FLUID_TANK.getCapacity()-100);
    }


    public FluidStack getFluid() {
        return FLUID_TANK.getFluid();
    }

    public FluidStack getOutputFluid() {
        return OUTPUT_FLUID_TANK.getFluid();
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
