package net.geforcemods.securitycraft.network.packets;

import io.netty.buffer.ByteBuf;
import net.geforcemods.securitycraft.util.WorldUtils;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketGivePotionEffect implements IMessage{

	private int potionID, duration, amplifier;

	public PacketGivePotionEffect(){

	}

	public PacketGivePotionEffect(int potionID, int duration, int amplifier){
		this.potionID = potionID;
		this.duration = duration;
		this.amplifier = amplifier;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		potionID = buf.readInt();
		duration = buf.readInt();
		amplifier = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(potionID);
		buf.writeInt(duration);
		buf.writeInt(amplifier);
	}

	public static class Handler extends PacketHelper implements IMessageHandler<PacketGivePotionEffect, IMessage> {

		@Override
		public IMessage onMessage(PacketGivePotionEffect packet, MessageContext ctx) {
			WorldUtils.addScheduledTask(getWorld(ctx.getServerHandler().playerEntity), () -> ctx.getServerHandler().playerEntity.addPotionEffect(new PotionEffect(Potion.getPotionById(packet.potionID), packet.duration, packet.amplifier, false, true)));
			return null;
		}

	}

}
