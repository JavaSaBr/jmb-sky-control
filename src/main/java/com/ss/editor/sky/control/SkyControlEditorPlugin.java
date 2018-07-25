package com.ss.editor.sky.control;

import com.ss.editor.annotation.FromAnyThread;
import com.ss.editor.plugin.EditorPlugin;
import com.ss.editor.sky.control.creator.SceneWithSkyControlFileCreator;
import com.ss.editor.sky.control.handler.PrePostSaveHandler;
import com.ss.editor.sky.control.property.SkyControlPropertyBuilder;
import com.ss.editor.sky.control.tree.SkyControlTreeNodeFactory;
import com.ss.editor.sky.control.tree.action.CreateSkyControlAction;
import com.ss.editor.ui.component.creator.FileCreatorRegistry;
import com.ss.editor.ui.component.editor.impl.scene.AbstractSceneFileEditor;
import com.ss.editor.ui.component.editor.impl.scene.AbstractSceneFileEditor.SaveHandler;
import com.ss.editor.ui.control.property.builder.PropertyBuilderRegistry;
import com.ss.editor.ui.control.tree.node.factory.TreeNodeFactoryRegistry;
import com.ss.editor.ui.control.tree.node.impl.spatial.NodeTreeNode;
import com.ss.editor.ui.control.tree.node.impl.spatial.NodeTreeNode.ChildrenFilter;
import com.ss.editor.ui.control.tree.node.impl.spatial.SpatialTreeNode;
import com.ss.editor.ui.control.tree.node.impl.spatial.SpatialTreeNode.ActionFactory;
import com.ss.rlib.common.plugin.PluginContainer;
import com.ss.rlib.common.plugin.annotation.PluginDescription;
import com.ss.rlib.common.plugin.extension.ExtensionPointManager;
import com.ss.rlib.common.util.FileUtils;
import com.ss.rlib.common.util.Utils;
import jme3utilities.sky.SkyControl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;

/**
 * The implementation of an editor plugin.
 *
 * @author JavaSaBr
 */
@PluginDescription(
        id = "com.ss.editor.sky.control",
        version = "1.2.3",
        minAppVersion = "1.9.0",
        name = "SkyControl Support",
        description = "Provides integration with the library 'SkyControl'."
)
public class SkyControlEditorPlugin extends EditorPlugin {

    private static final Class<SkyControlEditorPlugin> OWNER = SkyControlEditorPlugin.class;

    private static final String GRADLE_DEPENDENCIES =
            FileUtils.readFromClasspath(OWNER, "/com/ss/editor/sky/control/dependency/gradle.html");

    private static final String MAVEN_DEPENDENCIES =
            FileUtils.readFromClasspath(OWNER, "/com/ss/editor/sky/control/dependency/maven.html");

    public SkyControlEditorPlugin(@NotNull PluginContainer pluginContainer) {
        super(pluginContainer);
    }

    @Override
    @FromAnyThread
    public void register(@NotNull ExtensionPointManager manager) {
        super.register(manager);

        ExtensionPointManager.getInstance()
                .addExtension(TreeNodeFactoryRegistry.EP_FACTORIES, SkyControlTreeNodeFactory.getInstance())
                .addExtension(PropertyBuilderRegistry.EP_BUILDERS, SkyControlPropertyBuilder.getInstance())
                .addExtension(FileCreatorRegistry.EP_DESCRIPTORS, SceneWithSkyControlFileCreator.DESCRIPTOR)
                .addExtension(SpatialTreeNode.EP_CREATION_ACTION_FACTORIES, makeCreateAction())
                .addExtension(NodeTreeNode.EP_CHILDREN_FILTERS, makeChildFilter());

        var prePostSaveHandler = new PrePostSaveHandler();

        ExtensionPointManager.getInstance()
                .<SaveHandler>getExtensionPoint(AbstractSceneFileEditor.EP_PRE_SAVE_HANDLERS)
                .register(prePostSaveHandler::preSave);
        ExtensionPointManager.getInstance()
                .<SaveHandler>getExtensionPoint(AbstractSceneFileEditor.EP_POST_SAVE_HANDLERS)
                .register(prePostSaveHandler::postSave);
    }

    @FromAnyThread
    private @NotNull ActionFactory makeCreateAction() {
        return (node, tree) -> node instanceof NodeTreeNode ? new CreateSkyControlAction(tree, node) : null;
    }

    @FromAnyThread
    private @NotNull ChildrenFilter makeChildFilter() {
        return (parent, spatial) -> {
            var control = parent.getControl(SkyControl.class);
            return control != null && spatial == control.getSubtree();
        };
    }

    @Override
    @FromAnyThread
    public @Nullable String getUsedGradleDependencies() {
        return GRADLE_DEPENDENCIES;
    }

    @Override
    @FromAnyThread
    public @Nullable String getUsedMavenDependencies() {
        return MAVEN_DEPENDENCIES;
    }

    @Override
    @FromAnyThread
    public @Nullable URL getHomePageUrl() {
        return Utils.get("https://github.com/JavaSaBr/jmb-sky-control", URL::new);
    }
}
