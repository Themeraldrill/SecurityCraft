package net.geforcemods.securitycraft.network.packets;

import io.netty.buffer.ByteBuf;
import net.geforcemods.securitycraft.SecurityCraft;
import net.geforcemods.securitycraft.tileentity.TileEntityInventoryScanner;
import net.geforcemods.securitycraft.util.BlockUtils;
import net.geforcemods.securitycraft.util.Utils;
import net.geforcemods.securitycraft.util.WorldUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSetISType implements IMessage{

	private int x, y, z;
	private String type;

	public PacketSetISType(){

	}

	public PacketSetISType(int x, int y, int z, String type){
		this.x = x;
		this.y = y;
		this.z = z;
		this.type = type;
	}

	@Override
	public void fromBytes(ByteBuf par1ByteBuf) {
		x = par1ByteBuf.readInt();
		y = par1ByteBuf.readInt();
		z = par1ByteBuf.readInt();
		type = ByteBufUtils.readUTF8String(par1ByteBuf);

	}

	@Override
	public void toBytes(ByteBuf par1ByteBuf) {
		par1ByteBuf.writeInt(x);
		par1ByteBuf.writeInt(y);
		par1ByteBuf.writeInt(z);
		ByteBufUtils.writeUTF8String(par1ByteBuf, type);

	}

	public static class Handler extends PacketHelper implements IMessageHandler<PacketSetISType, IMessage> {

		@Override
		public IMessage onMessage(PacketSetISType packet, MessageContext context) {
			WorldUtils.addScheduledTask(getWorld(context.getServerHandler().player), () -> {
				BlockPos pos = BlockUtils.toPos(packet.x, packet.y, packet.z);

				((TileEntityInventoryScanner) getWorld(context.getServerHandler().player).getTileEntity(pos)).setType(packet.type);

				SecurityCraft.log("Setting type to " + packet.type);
				getWorld(context.getServerHandler().player).scheduleUpdate(pos, BlockUtils.getBlock(getWorld(context.getServerHandler().player), pos), 1);

				Utils.setISinTEAppropriately(getWorld(context.getServerHandler().player), pos, ((TileEntityInventoryScanner) getWorld(context.getServerHandler().player).getTileEntity(pos)).getContents(), ((TileEntityInventoryScanner) getWorld(context.getServerHandler().player).getTileEntity(pos)).getType());
			});

			return null;
		}

	}

}
