package logisticspipes.gui.hud;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;

import logisticspipes.interfaces.IHUDButton;
import logisticspipes.interfaces.IHeadUpDisplayRenderer;

public abstract class BasicHUDGui implements IHeadUpDisplayRenderer {
	
	protected final List<IHUDButton> buttons = new ArrayList<IHUDButton>();
	
	protected void addButton(IHUDButton button) {
		buttons.add(button);
	}
	
	@Override
	public void renderHeadUpDisplay(double d, boolean day, Minecraft mc) {
		for(IHUDButton button:buttons) {
			if(button.shouldRenderButton()) {
				button.renderButton(button.isFocused(), button.isblockFocused());
			}
		}
	}

	@Override
	public void handleCursor(int x, int y) {
		GL11.glPushMatrix();
		for(IHUDButton button:buttons) {
			if(!button.buttonEnabled()) continue;
			if((button.getX() - 1 < x && x < (button.getX() + button.sizeX() + 1)) && (button.getY() - 1 < y && y < (button.getY() + button.sizeY() + 1))) {
				if(!button.isFocused() && !button.isblockFocused()) {
					button.setFocused();
				} else if(button.focusedTime() > 400) {
					button.clicked();
					button.blockFocused();
				}
			} else if(button.isFocused() || button.isblockFocused()) {
				button.clearFocused();
			}
		}
		GL11.glPopMatrix();
	}
}
