package net.guille_dlc.necronomicon.common.entity.custom;

import net.guille_dlc.necronomicon.api.item.NecronomiconItems;
import net.guille_dlc.necronomicon.common.item.NecronomiconBookItem;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class AngleEntity extends Vindicator {
    static final Predicate<ItemEntity> ALLOWED_ITEMS = (p_28528_) -> {
        return !p_28528_.hasPickUpDelay() && p_28528_.isAlive() && p_28528_.getItem().getItem() instanceof NecronomiconBookItem;
    };

    private static final String TAG_JOHNNY = "Johnny";
    static final Predicate<Difficulty> DOOR_BREAKING_PREDICATE = (p_34082_) -> {
        return p_34082_ == Difficulty.NORMAL || p_34082_ == Difficulty.HARD;
    };
    boolean isJohnny;

    public AngleEntity(EntityType<? extends Vindicator> entityType, Level pLevel) {
        super(entityType, pLevel);
        this.setCanPickUpLoot(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, (double)0.35F).add(Attributes.FOLLOW_RANGE, 12.0D).add(Attributes.MAX_HEALTH, 24.0D).add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new AngleBreakDoorGoal(this));
        this.goalSelector.addGoal(2, new RaiderOpenDoorGoal(this));
        this.goalSelector.addGoal(3, new HoldGroundAttackGoal(this, 10.0F));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
        //this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Monster.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Skeleton.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(4, new AngleJohnnyAttackGoal(this));
        this.targetSelector.addGoal(1, new AngleAvoidNecronomiconGoal<Player>(this, Player.class, 4.0F, 1.5F, 1.5F));
        /*this.goalSelector.addGoal(0, new UseItemGoal<>(this,
                new NecronomiconBookItem(new Item.Properties()).getDefaultInstance(),
                SoundEvents.BOOK_PAGE_TURN, (arg) -> {
            return true;
        }));*/
        this.goalSelector.addGoal(1, new AngleSearchForItemsGoal());
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6D));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 0.5F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F, 0.25F));
    }

    @Override
    public boolean canBeLeader() {
        return false;
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty) {
        //this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.NECRONOMICON_BOOK.get()));
        int p = Math.abs(this.random.nextInt(67));
        if(p > 65)
            this.setItemSlotAndDropWhenKilled(EquipmentSlot.MAINHAND, new ItemStack(NecronomiconItems.IRON_DAGGER.get()));
        else if(p > 64)
            this.setItemSlotAndDropWhenKilled(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_AXE));
        else if(p > 63)
            this.setItemSlotAndDropWhenKilled(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_HOE));
        else if(p > 62)
            this.setItemSlotAndDropWhenKilled(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
        else if(p > 61)
            this.setItemSlotAndDropWhenKilled(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_PICKAXE));
        else if(p > 60)
            this.setItemSlotAndDropWhenKilled(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
        else if(p > 50)
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(NecronomiconItems.IRON_DAGGER.get()));
        else if(p > 40)
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_AXE));
        else if(p > 30)
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_HOE));
        else if(p > 20)
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
        else if(p > 10)
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_PICKAXE));
        else if(p > 0)
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
    }

    /**Method stolen from the Fox class**/
    protected void pickUpItem(ItemEntity pEntity) {
        ItemStack itemstack = pEntity.getItem();
        if (this.canHoldItem(itemstack) && AngleEntity.ALLOWED_ITEMS.test(pEntity) && !hasItemInSlot(EquipmentSlot.MAINHAND)) {
            int i = itemstack.getCount();
            if (i > 1) {
                this.dropItemStack(itemstack.split(i - 1));
            }

            this.onItemPickup(pEntity);
            //this.setItemSlot(EquipmentSlot.MAINHAND, itemstack.split(1));
            //this.handDropChances[EquipmentSlot.MAINHAND.getIndex()] = 2.0F;
            this.take(pEntity, itemstack.getCount());
            //this.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
            this.setItemSlotAndDropWhenKilled(EquipmentSlot.MAINHAND, itemstack);
            pEntity.discard();
        }
    }

    /**Method stolen from the Fox class**/
    private void dropItemStack(ItemStack pStack) {
        ItemEntity itementity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), pStack);
        this.level().addFreshEntity(itementity);
    }

    /**From the Vindicator class**/
    static class AngleBreakDoorGoal extends BreakDoorGoal {
        public AngleBreakDoorGoal(Mob p_34112_) {
            super(p_34112_, 6, AngleEntity.DOOR_BREAKING_PREDICATE);
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            AngleEntity angle = (AngleEntity) this.mob;
            return angle.hasActiveRaid() && super.canContinueToUse();
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            AngleEntity angle = (AngleEntity) this.mob;
            return angle.hasActiveRaid() && angle.random.nextInt(reducedTickDelay(10)) == 0 && super.canUse();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            super.start();
            this.mob.setNoActionTime(0);
        }
    }

    /**From the Vindicator class**/
    static class AngleJohnnyAttackGoal extends NearestAttackableTargetGoal<LivingEntity> {
        public AngleJohnnyAttackGoal(AngleEntity p_34117_) {
            super(p_34117_, LivingEntity.class, 0, true, true, LivingEntity::attackable);
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return ((AngleEntity)this.mob).isJohnny && super.canUse();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            super.start();
            this.mob.setNoActionTime(0);
        }
    }

    /**Class stolen from the Fox class**/
    class AngleSearchForItemsGoal extends Goal {
        public AngleSearchForItemsGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            if (!AngleEntity.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
                return false;
            } else if (AngleEntity.this.getTarget() == null && AngleEntity.this.getLastHurtByMob() == null) {
                if (AngleEntity.this.getRandom().nextInt(reducedTickDelay(10)) != 0) {
                    return false;
                } else {
                    List<ItemEntity> list = AngleEntity.this.level().getEntitiesOfClass(ItemEntity.class, AngleEntity.this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), AngleEntity.ALLOWED_ITEMS);
                    return !list.isEmpty() && AngleEntity.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty();
                }
            } else {
                return false;
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            List<ItemEntity> list = AngleEntity.this.level().getEntitiesOfClass(ItemEntity.class, AngleEntity.this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), AngleEntity.ALLOWED_ITEMS);
            ItemStack itemstack = AngleEntity.this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (itemstack.isEmpty() && !list.isEmpty()) {
                AngleEntity.this.getNavigation().moveTo(list.get(0), (double)1.05F);
            }

        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            List<ItemEntity> list = AngleEntity.this.level().getEntitiesOfClass(ItemEntity.class, AngleEntity.this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), AngleEntity.ALLOWED_ITEMS);
            if (!list.isEmpty()) {
                AngleEntity.this.getNavigation().moveTo(list.get(0), (double)1.1F);
            }
        }
    }

    class AngleAvoidNecronomiconGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
        private final AngleEntity angle;

        public AngleAvoidNecronomiconGoal(AngleEntity angle, Class<T> pEntityClassToAvoid, float pMaxDistance, double pWalkSpeedModifier, double pSprintSpeedModifier) {
            super(angle, pEntityClassToAvoid, pMaxDistance, pWalkSpeedModifier, pSprintSpeedModifier);
            this.angle = angle;
        }

        public boolean canUse() {
            if (super.canUse() && this.toAvoid instanceof Player) {
                return this.avoidPlayer((Player)this.toAvoid);
            } else {
                return false;
            }
        }

        private boolean avoidPlayer(Player player) {
            return player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof NecronomiconBookItem
                    || player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof NecronomiconBookItem;
        }

        public void start() {
            AngleEntity.this.setTarget((LivingEntity)null);
            super.start();
        }

        public void tick() {
            AngleEntity.this.setTarget((LivingEntity)null);
            super.tick();
        }
    }
}
