package com.ss.editor.sky.control.tree.node;

import com.ss.editor.annotation.FromAnyThread;
import com.ss.editor.annotation.FxThread;
import com.ss.editor.ui.Icons;
import com.ss.editor.ui.control.model.ModelNodeTree;
import com.ss.editor.ui.control.tree.NodeTree;
import com.ss.editor.ui.control.tree.node.TreeNode;
import com.ss.editor.ui.control.tree.node.impl.control.ControlTreeNode;
import com.ss.rlib.util.array.Array;
import com.ss.rlib.util.array.ArrayFactory;
import javafx.scene.image.Image;
import jme3utilities.sky.SkyControl;
import jme3utilities.sky.SunAndStars;
import jme3utilities.sky.Updater;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The node to present {@link SkyControl}
 *
 * @author JavaSaBr
 */
public class SkyControlTreeNode extends ControlTreeNode<SkyControl> {

    public SkyControlTreeNode(@NotNull final SkyControl element, final long objectId) {
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
        return "Sky";
    }

    @Override
    public boolean hasChildren(@NotNull final NodeTree<?> nodeTree) {
        return nodeTree instanceof ModelNodeTree;
    }

    @Override
    public @NotNull Array<TreeNode<?>> getChildren(@NotNull final NodeTree<?> nodeTree) {

        final SkyControl element = getElement();
        final Updater updater = element.getUpdater();
        final SunAndStars sunAndStars = element.getSunAndStars();

        final Array<TreeNode<?>> result = ArrayFactory.newArray(TreeNode.class);
        result.add(FACTORY_REGISTRY.createFor(updater));
        result.add(FACTORY_REGISTRY.createFor(sunAndStars));

        return result;
    }
}
