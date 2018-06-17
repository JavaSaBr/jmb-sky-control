package com.ss.editor.sky.control;

import com.ss.editor.annotation.BackgroundThread;
import com.ss.editor.annotation.FromAnyThread;
import com.ss.editor.plugin.EditorPlugin;
import com.ss.editor.sky.control.creator.SceneWithSkyControlFileCreator;
import com.ss.editor.sky.control.handler.PrePostSaveHandler;
import com.ss.editor.sky.control.property.SkyControlPropertyBuilder;
import com.ss.editor.sky.control.tree.SkyControlTreeNodeFactory;
import com.ss.editor.sky.control.tree.action.CreateSkyControlAction;
import com.ss.editor.ui.component.creator.FileCreatorRegistry;
import com.ss.editor.ui.component.editor.impl.scene.AbstractSceneFileEditor;
import com.ss.editor.ui.control.property.builder.PropertyBuilderRegistry;
import com.ss.editor.ui.control.tree.node.factory.TreeNodeFactoryRegistry;
import com.ss.editor.ui.control.tree.node.impl.spatial.NodeTreeNode;
import com.ss.editor.ui.control.tree.node.impl.spatial.SpatialTreeNode;
import com.ss.rlib.common.plugin.PluginContainer;
import com.ss.rlib.common.plugin.PluginSystem;
import com.ss.rlib.common.plugin.annotation.PluginDescription;
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
        version = "1.2.2",
        minAppVersion = "1.9.0",
        name = "SkyControl Support",
        description = "Provides integration with the library 'SkyControl'."
)
public class SkyControlEditorPlugin extends EditorPlugin {

    private static final String GRADLE_DEPENDENCIES;
    private static final String MAVEN_DEPENDENCIES;

    static {
        var loader = SkyControlEditorPlugin.class;
        GRADLE_DEPENDENCIES = FileUtils.read(loader.getResourceAsStream("/com/ss/editor/sky/control/dependency/gradle.html"));
        MAVEN_DEPENDENCIES = FileUtils.read(loader.getResourceAsStream("/com/ss/editor/sky/control/dependency/maven.html"));
    }

    public SkyControlEditorPlugin(@NotNull PluginContainer pluginContainer) {
        super(pluginContainer);
    }

    @Override
    @BackgroundThread
    public void register(@NotNull TreeNodeFactoryRegistry registry) {
        super.register(registry);
        registry.register(SkyControlTreeNodeFactory.getInstance());
    }

    @Override
    @BackgroundThread
    public void register(@NotNull PropertyBuilderRegistry registry) {
        super.register(registry);
        registry.register(SkyControlPropertyBuilder.getInstance());
    }

    @Override
    @BackgroundThread
    public void register(@NotNull FileCreatorRegistry registry) {
        super.register(registry);
        registry.register(SceneWithSkyControlFileCreator.DESCRIPTION);
    }

    @Override
    @BackgroundThread
    public void onAfterCreateJavaFxContext(@NotNull PluginSystem pluginSystem) {
        super.onAfterCreateJavaFxContext(pluginSystem);

        SpatialTreeNode.registerCreationControlAction((node, tree) -> {

            if (node instanceof NodeTreeNode) {
                return new CreateSkyControlAction(tree, node);
            }

            return null;
        });

        NodeTreeNode.registerNodeChildrenFilter((parent, spatial) -> {
            var control = parent.<SkyControl>getControl(SkyControl.class);
            return control != null && spatial == control.getSubtree();
        });

        var prePostSaveHandler = new PrePostSaveHandler();

        AbstractSceneFileEditor.registerPreSaveHandler(prePostSaveHandler::preSave);
        AbstractSceneFileEditor.registerPostSaveHandler(prePostSaveHandler::postSave);
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
