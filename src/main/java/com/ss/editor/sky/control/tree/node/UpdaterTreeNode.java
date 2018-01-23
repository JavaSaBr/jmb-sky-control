package com.ss.editor.sky.control.tree.node;

import com.ss.editor.annotation.FromAnyThread;
import com.ss.editor.annotation.FxThread;
import com.ss.editor.sky.control.PluginMessages;
import com.ss.editor.ui.Icons;
import com.ss.editor.ui.control.tree.node.TreeNode;
import javafx.scene.image.Image;
import jme3utilities.sky.Updater;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The tree node to present {@link Updater} in model tree.
 *
 * @author JavaSaBr
 */
public class UpdaterTreeNode extends TreeNode<Updater> {

    public UpdaterTreeNode(@NotNull final Updater element, final long objectId) {
        super(element, objectId);
    }

    @Override
    @FromAnyThread
    public @NotNull String getName() {
        return PluginMessages.MODEL_FILE_EDITOR_NODE_UPDATER;
    }

    @Override
    @FxThread
    public @Nullable Image getIcon() {
        return Icons.UPDATER_16;
    }
}
