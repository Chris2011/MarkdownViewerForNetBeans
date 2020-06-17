package org.apache.netbeans.core.ui.markdownviewer;

import javax.swing.JComponent;
import javax.swing.text.JTextComponent;
import org.netbeans.spi.editor.SideBarFactory;

/**
 *
 * @author Chrizzly
 */
public class MarkdownViewerSideBarFactory implements SideBarFactory {
    @Override
    public JComponent createSideBar(JTextComponent target) {
        return new MarkdownViewerSideBarPanel(target);
    }
}




