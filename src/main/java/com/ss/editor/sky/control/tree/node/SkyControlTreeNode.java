package com.ss.editor.sky.control.tree.node;

import com.ss.editor.annotation.FromAnyThread;
import com.ss.editor.annotation.FxThread;
import com.ss.editor.sky.control.PluginMessages;
import com.ss.editor.ui.Icons;
import com.ss.editor.ui.control.model.ModelNodeTree;
import com.ss.editor.ui.control.tree.NodeTree;
import com.ss.editor.ui.control.tree.node.TreeNode;
import com.ss.editor.ui.control.tree.node.impl.control.ControlTreeNode;
import com.ss.rlib.common.util.array.Array;
import com.ss.rlib.common.util.array.ArrayFactory;
import javafx.scene.image.Image;
import jme3utilities.sky.SkyControl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The node to present {@link SkyControl}
 *
 * @author JavaSaBr
 */
public class SkyControlTreeNode extends ControlTreeNode<SkyControl> {

    public SkyControlTreeNode(@NotNull SkyControl element, long objectId) {
        super(element, objectId);
    }

    @Override
    @FxThread
    public @Nullable Image getIcon() {
        return Icons.SKY_16;
    }

    @Override
    @FromAnyThread
    public @NotNull String getName() {
        return PluginMessages.MODEL_FILE_EDITOR_NODE_SKY_CONTROL;
    }

    @Override
    public boolean hasChildren(@NotNull NodeTree<?> nodeTree) {
        return nodeTree instanceof ModelNodeTree;
    }

    @Override
    public @NotNull Array<TreeNode<?>> getChildren(@NotNull NodeTree<?> nodeTree) {

        var skyControl = getElement();
        var updater = skyControl.getUpdater();
        var sunAndStars = skyControl.getSunAndStars();

        var result = Array.<TreeNode<?>>ofType(TreeNode.class);
        result.add(FACTORY_REGISTRY.createFor(updater));
        result.add(FACTORY_REGISTRY.createFor(sunAndStars));

        return result;
    }
}
