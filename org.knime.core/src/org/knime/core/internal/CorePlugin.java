/* 
 * -------------------------------------------------------------------
 * This source code, its documentation and all appendant files
 * are protected by copyright law. All rights reserved.
 * 
 * Copyright, 2003 - 2006
 * Universitaet Konstanz, Germany.
 * Lehrstuhl fuer Angewandte Informatik
 * Prof. Dr. Michael R. Berthold
 * 
 * You may not modify, publish, transmit, transfer or sell, reproduce,
 * create derivative works from, distribute, perform, display, or in
 * any way exploit any of the content, in whole or in part, except as
 * otherwise expressly permitted in writing by the copyright owner.
 * -------------------------------------------------------------------
 * 
 * History
 *   Oct 13, 2006 (wiswedel): created
 */
package org.knime.core.internal;

import java.io.File;
import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.knime.core.node.NodeLogger;
import org.osgi.framework.BundleContext;

/**
 * Plugin class that is initialized when the plugin project is started. It will
 * set the workspace path as KNIME home dir in the KNIMEConstants utility class.
 * @author wiswedel, University of Konstanz
 */
public class CorePlugin extends Plugin {

    /**
     * @see Plugin#start(BundleContext)
     */
    @Override
    public void start(final BundleContext context) throws Exception {
        super.start(context);
        try {
            URL workspaceURL = Platform.getInstanceLocation().getURL();
            File workspaceDir = new File(workspaceURL.toURI());
            File metaDataFile = new File(workspaceDir, ".metadata");
            if (!metaDataFile.exists()) {
                metaDataFile.mkdir();
            }
            File knimeHomeDir = new File(metaDataFile, "knime");
            if (!knimeHomeDir.exists()) {
                knimeHomeDir.mkdir();
            }
            KNIMEPath.setKNIMEHomeDir(knimeHomeDir.getAbsoluteFile());
        } catch (Exception e) {
            // the logger will use the "user.dir" as knime home, unfortunately. 
            NodeLogger.getLogger(getClass()).warn(
                    "Can't init knime home dir to workspace path.", e);
        }
    }
}
