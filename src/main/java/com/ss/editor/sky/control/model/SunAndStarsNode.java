package com.ss.editor.sky.control.model;

import jme3utilities.sky.SkyControl;
import org.jetbrains.annotations.NotNull;

/**
 * The sky control's sun and stars wrapper.
 *
 * @author JavaSaBr
 */
public class SunAndStarsNode {

    @NotNull
    private final SkyControl skyControl;

    public SunAndStarsNode(@NotNull SkyControl skyControl) {
        this.skyControl = skyControl;
    }
}
