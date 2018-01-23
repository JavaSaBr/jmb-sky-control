package com.ss.editor.sky.control.handler;

import static java.lang.ThreadLocal.withInitial;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.ss.editor.annotation.FxThread;
import com.ss.rlib.util.dictionary.DictionaryFactory;
import com.ss.rlib.util.dictionary.ObjectDictionary;
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

        private DataInfo(final Node node, final int index) {
            this.node = node;
            this.index = index;
        }
    }

    @NotNull
    private static final ThreadLocal<ObjectDictionary<Spatial, DataInfo>> LOCAL_DATA_INFOS = withInitial(DictionaryFactory::newObjectDictionary);

    /**
     * Handle the pre save processing of the spatial.
     *
     * @param spatial the spatial.
     */
    @FxThread
    public void preSave(@NotNull final Spatial spatial) {

        if (!(spatial instanceof Node)) {
            return;
        }

        final SkyControl control = spatial.getControl(SkyControl.class);
        if (control == null) {
            return;
        }

        final Node node = (Node) spatial;
        final int index = node.getChildIndex(control.getSubtree());

        if(index == -1) {
            return;
        }

        node.detachChildAt(index);

        final ObjectDictionary<Spatial, DataInfo> dataInfos = LOCAL_DATA_INFOS.get();
        dataInfos.put(spatial, new DataInfo(control.getSubtree(), index));
    }

    /**
     * Handle the post save processing of the spatial.
     *
     * @param spatial the spatial.
     */
    @FxThread
    public void postSave(@NotNull final Spatial spatial) {

        if (!(spatial instanceof Node)) {
            return;
        }

        final SkyControl control = spatial.getControl(SkyControl.class);
        if (control == null) {
            return;
        }

        final ObjectDictionary<Spatial, DataInfo> dataInfos = LOCAL_DATA_INFOS.get();
        final DataInfo dataInfo = dataInfos.remove(spatial);
        if (dataInfo == null) {
            return;
        }

        ((Node) spatial).attachChildAt(dataInfo.node, dataInfo.index);
    }
}
