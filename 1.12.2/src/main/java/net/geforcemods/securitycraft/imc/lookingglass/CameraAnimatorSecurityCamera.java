package net.geforcemods.securitycraft.imc.lookingglass;

import com.xcompwiz.lookingglass.api.animator.ICameraAnimator;
import com.xcompwiz.lookingglass.api.view.IViewCamera;

import net.geforcemods.securitycraft.SCContent;
import net.geforcemods.securitycraft.tileentity.TileEntitySecurityCamera;
import net.geforcemods.securitycraft.util.BlockUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;

/**
 * The IWorldView animator for the security cameras. <p>
 *
 * Sets the location of the camera, and rotates the view.
 *
 * @author Geforce
 */
public class CameraAnimatorSecurityCamera implements ICameraAnimator {

	private final double cameraYOffset = 2.425D;

	private IViewCamera camera;
	private int cameraMeta = 0;
	private BlockPos pos;

	public CameraAnimatorSecurityCamera(IViewCamera camera, BlockPos pos, int securityCameraMeta){
		this.camera = camera;
		this.pos = pos;
		cameraMeta = securityCameraMeta;

		if(securityCameraMeta == 1)
			this.camera.setLocation(camera.getPosition().getX() + 0.5D, camera.getPosition().getY() - cameraYOffset, camera.getPosition().getZ() + 0.5D);
		else if(securityCameraMeta == 2)
			this.camera.setLocation(camera.getPosition().getX() + 0.5D, camera.getPosition().getY() - cameraYOffset, camera.getPosition().getZ() + 0.5D);
		else if(securityCameraMeta == 3)
			this.camera.setLocation(camera.getPosition().getX() + 0.5D, camera.getPosition().getY() - cameraYOffset, camera.getPosition().getZ() + 0.5D);
		else if(securityCameraMeta == 4)
			this.camera.setLocation(camera.getPosition().getX() + 0.5D, camera.getPosition().getY() - cameraYOffset, camera.getPosition().getZ() + 0.5D);

		if(securityCameraMeta == 1)
			this.camera.setYaw(180F);
		else if(securityCameraMeta == 2)
			this.camera.setYaw(90F);
		else if(securityCameraMeta == 3)
			this.camera.setYaw(0F);
		else if(securityCameraMeta == 4)
			this.camera.setYaw(270F);
	}

	@Override
	public void setTarget(BlockPos arg0) {}

	@Override
	public void refresh(){}

	@Override
	public void update(long arg0) {
		if(camera == null || cameraMeta == 0)
			return;
		if(BlockUtils.getBlock(Minecraft.getMinecraft().world, pos) != SCContent.securityCamera)
			return;

		float cameraRotation = ((TileEntitySecurityCamera) Minecraft.getMinecraft().world.getTileEntity(pos)).cameraRotation * 60;

		if(cameraMeta == 4)
			camera.setYaw(180 + cameraRotation);
		else if(cameraMeta == 2)
			camera.setYaw(90 + cameraRotation);
		else if(cameraMeta == 3)
			camera.setYaw(0 + cameraRotation);
		else if(cameraMeta == 1)
			camera.setYaw(270 + cameraRotation);
	}
}
