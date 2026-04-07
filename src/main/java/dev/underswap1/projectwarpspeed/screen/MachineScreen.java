package dev.underswap1.projectwarpspeed.screen;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class MachineScreen extends HandledScreen<MachineScreenHandler> {

    private static final Identifier TEXTURE =
            new Identifier("project_warpspeed", "textures/gui/machine.png");

    public MachineScreen(MachineScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }


    @Override
    protected void init() {
        super.init();
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        addDrawableChild(ButtonWidget.builder(
                Text.literal("Assemble Boiler"),
                button -> handler.assembleSteamBoiler()
        ).dimensions(x + 40, y + 60, 100, 20).build());

    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
        // You can also draw the recipe text here using SteamBoilerRecipe.REQUIRED
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
