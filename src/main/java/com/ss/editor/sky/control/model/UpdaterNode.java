package com.ss.editor.sky.control.model;

import jme3utilities.sky.SkyControl;
import org.jetbrains.annotations.NotNull;

/**
 * The sky control's updater wrapper.
 *
 * @author JavaSaBr
 */
public class UpdaterNode {

    @NotNull
    private final SkyControl skyControl;

    public UpdaterNode(@NotNull SkyControl skyControl) {
        this.skyControl = skyControl;
    }
}
