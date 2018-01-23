package com.ss.editor.sky.control;

import com.ss.editor.annotation.FromAnyThread;
import com.ss.editor.annotation.FxThread;
import com.ss.editor.plugin.EditorPlugin;
import com.ss.editor.sky.control.handler.PrePostSaveHandler;
import com.ss.editor.sky.control.property.SkyControlPropertyBuilder;
import com.ss.editor.sky.control.tree.SkyControlTreeNodeFactory;
import com.ss.editor.sky.control.tree.action.CreateSkyControlAction;
import com.ss.editor.ui.component.editor.impl.scene.AbstractSceneFileEditor;
import com.ss.editor.ui.control.property.builder.PropertyBuilderRegistry;
import com.ss.editor.ui.control.tree.node.factory.TreeNodeFactoryRegistry;
import com.ss.editor.ui.control.tree.node.impl.spatial.NodeTreeNode;
import com.ss.editor.ui.control.tree.node.impl.spatial.SpatialTreeNode;
import com.ss.rlib.plugin.PluginContainer;
import com.ss.rlib.plugin.PluginSystem;
import com.ss.rlib.plugin.annotation.PluginDescription;
import jme3utilities.sky.SkyControl;
import org.jetbrains.annotations.NotNull;

/**
 * The implementation of an editor plugin.
 *
 * @author JavaSaBr
 */
@PluginDescription(
        id = "com.ss.editor.sky.control",
        version = "1.0.0",
        minAppVersion = "1.6.0",
        name = "SkyControl Support",
        description = "Provides integration with the library 'SkyControl'."
)
public class SkyControlEditorPlugin extends EditorPlugin {

    public SkyControlEditorPlugin(@NotNull final PluginContainer pluginContainer) {
        super(pluginContainer);
    }

    @Override
    @FromAnyThread
    public void register(@NotNull final TreeNodeFactoryRegistry registry) {
        super.register(registry);
        registry.register(SkyControlTreeNodeFactory.getInstance());
    }

    @Override
    @FromAnyThread
    public void register(@NotNull final PropertyBuilderRegistry registry) {
        super.register(registry);
        registry.register(SkyControlPropertyBuilder.getInstance());
    }

    @Override
    @FxThread
    public void onAfterCreateJavaFxContext(@NotNull final PluginSystem pluginSystem) {
        super.onAfterCreateJavaFxContext(pluginSystem);
        SpatialTreeNode.registerCreationControlAction((node, tree) -> {
            if (node instanceof NodeTreeNode) {
                return new CreateSkyControlAction(tree, node);
            }
            return null;
        });
        NodeTreeNode.registerNodeChildrenFilter((parent, spatial) -> {
            final SkyControl control = parent.getControl(SkyControl.class);
            return control != null && spatial == control.getSubtree();
        });

        final PrePostSaveHandler prePostSaveHandler = new PrePostSaveHandler();

        AbstractSceneFileEditor.registerPreSaveHandler(prePostSaveHandler::preSave);
        AbstractSceneFileEditor.registerPostSaveHandler(prePostSaveHandler::postSave);
    }
}
