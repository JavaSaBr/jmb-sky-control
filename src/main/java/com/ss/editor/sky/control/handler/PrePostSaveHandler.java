package com.ss.editor.sky.control.handler;

import static java.lang.ThreadLocal.withInitial;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.ss.editor.annotation.FxThread;
import com.ss.rlib.common.util.dictionary.DictionaryFactory;
import com.ss.rlib.common.util.dictionary.ObjectDictionary;
import jme3utilities.sky.SkyControl;
import org.jetbrains.annotations.NotNull;

/**
 * The class to handle pre/post saves of {@link com.jme3.scene.Spatial} with {@link jme3utilities.sky.SkyControl}.
 *
 * @author JavaSaBr
 */
public class PrePostSaveHandler {

    private static class DataInfo {

        private final Node node;
        private final int index;

        private DataInfo(Node node, int index) {
            this.node = node;
            this.index = index;
        }
    }

    private static final ThreadLocal<ObjectDictionary<Spatial, DataInfo>> LOCAL_DATA_INFOS =
            withInitial(DictionaryFactory::newObjectDictionary);

    /**
     * Handle the pre save processing of the spatial.
     *
     * @param spatial the spatial.
     */
    @FxThread
    public void preSave(@NotNull Spatial spatial) {

        if (!(spatial instanceof Node)) {
            return;
        }

        var skyControl = spatial.getControl(SkyControl.class);
        if (skyControl == null) {
            return;
        }

        var node = (Node) spatial;
        var index = node.getChildIndex(skyControl.getSubtree());

        if (index == -1) {
            return;
        }

        node.detachChildAt(index);

        LOCAL_DATA_INFOS.get()
                .put(spatial, new DataInfo(skyControl.getSubtree(), index));
    }

    /**
     * Handle the post save processing of the spatial.
     *
     * @param spatial the spatial.
     */
    @FxThread
    public void postSave(@NotNull Spatial spatial) {

        if (!(spatial instanceof Node)) {
            return;
        }

        var skyControl = spatial.getControl(SkyControl.class);
        if (skyControl == null) {
            return;
        }

        var dataInfos = LOCAL_DATA_INFOS.get();
        var dataInfo = dataInfos.remove(spatial);
        if (dataInfo == null) {
            return;
        }

        ((Node) spatial).attachChildAt(dataInfo.node, dataInfo.index);
    }
}
