package com.ss.editor.sky.control.tree.action;

import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import com.ss.editor.annotation.FxThread;
import com.ss.editor.ui.Icons;
import com.ss.editor.ui.control.tree.NodeTree;
import com.ss.editor.ui.control.tree.action.impl.control.AbstractCreateControlAction;
import com.ss.editor.ui.control.tree.node.TreeNode;
import com.ss.editor.util.EditorUtil;
import javafx.scene.image.Image;
import jme3utilities.sky.SkyControl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The action to create {@link jme3utilities.sky.SkyControl}
 *
 * @author JavaSaBr
 */
public class CreateSkyControlAction extends AbstractCreateControlAction {

    public CreateSkyControlAction(@NotNull final NodeTree<?> nodeTree, @NotNull final TreeNode<?> node) {
        super(nodeTree, node);
    }

    @Override
    protected @NotNull String getName() {
        return "SkyControl";
    }

    @Override
    protected @Nullable Image getIcon() {
        return Icons.SKY_16;
    }

    @Override
    @FxThread
    protected @NotNull Control createControl(@NotNull final Spatial parent) {
        return new SkyControl(EditorUtil.getAssetManager(), EditorUtil.getGlobalCamera(),
                0.9f, true, true);
    }
}
