package com.ss.editor.sky.control.tree;

import static com.ss.rlib.util.ClassUtils.unsafeCast;
import com.ss.editor.annotation.FromAnyThread;
import com.ss.editor.annotation.FxThread;
import com.ss.editor.sky.control.tree.node.SkyControlTreeNode;
import com.ss.editor.sky.control.tree.node.SunAndStarsTreeNode;
import com.ss.editor.sky.control.tree.node.UpdaterTreeNode;
import com.ss.editor.ui.control.tree.node.TreeNode;
import com.ss.editor.ui.control.tree.node.factory.TreeNodeFactory;
import jme3utilities.sky.SkyControl;
import jme3utilities.sky.SunAndStars;
import jme3utilities.sky.Updater;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The node factory to create node's of sky control things.
 *
 * @author JavaSaBr
 */
public class SkyControlTreeNodeFactory implements TreeNodeFactory {

    @NotNull
    private static final SkyControlTreeNodeFactory INSTANCE = new SkyControlTreeNodeFactory();

    @FromAnyThread
    public static @NotNull SkyControlTreeNodeFactory getInstance() {
        return INSTANCE;
    }

    @Override
    @FxThread
    public <T, V extends TreeNode<T>> @Nullable V createFor(@Nullable final T element, final long objectId) {

        if (element instanceof SkyControl) {
            return unsafeCast(new SkyControlTreeNode((SkyControl) element, objectId));
        } else if (element instanceof SunAndStars) {
            return unsafeCast(new SunAndStarsTreeNode((SunAndStars) element, objectId));
        } else if (element instanceof Updater) {
            return unsafeCast(new UpdaterTreeNode((Updater) element, objectId));
        }

        return null;
    }

    @Override
    public int getPriority() {
        return 10;
    }
}
