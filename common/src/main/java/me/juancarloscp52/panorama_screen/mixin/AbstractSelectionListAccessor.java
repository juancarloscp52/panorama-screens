package me.juancarloscp52.panorama_screen.mixin;

import net.minecraft.client.gui.components.AbstractSelectionList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractSelectionList.class)
public interface AbstractSelectionListAccessor {

    @Accessor("y0")
    int getY0();
    @Accessor("y1")
    int getY1();

}
