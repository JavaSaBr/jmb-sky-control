package com.ss.editor.sky.control;

import com.ss.editor.manager.FileIconManager;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;

/**
 * The class with all plugin icons.
 *
 * @author JavaSaBr
 */
public interface PluginIcons {

    @NotNull ClassLoader CLASS_LOADER = PluginIcons.class.getClassLoader();
    @NotNull FileIconManager ICON_MANAGER = FileIconManager.getInstance();

    @NotNull Image STARS_16 = ICON_MANAGER.getImage("com/ss/editor/sky/control/icons/stars.svg", CLASS_LOADER, 16);

}
