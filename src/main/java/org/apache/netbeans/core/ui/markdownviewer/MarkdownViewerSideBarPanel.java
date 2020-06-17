package org.apache.netbeans.core.ui.markdownviewer;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import org.netbeans.modules.editor.NbEditorUtilities;

/**
 *
 * @author Chrizzly
 */
public class MarkdownViewerSideBarPanel extends JPanel implements MouseListener, MouseMotionListener {
    private JTextComponent target;
    private final JScrollPane editorjScrollPane;
    private final JScrollPane sidebarjScrollPane;
    private final JScrollBar verticalScrollBar;
    private int startLine;
    private int endLine;

    int blockHeight;
    int margin = 1;
    double ratio;

    public MarkdownViewerSideBarPanel(JTextComponent target) {
        super(new BorderLayout());

        this.target = target;
        sidebarjScrollPane = (JScrollPane)target.getParent();
        editorjScrollPane = (JScrollPane) target.getParent().getParent().getParent();
        verticalScrollBar = editorjScrollPane.getVerticalScrollBar();
        
//        sidebarjScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
//        sidebarjScrollPane.setAutoscrolls(true);

        editorjScrollPane.getViewport().addChangeListener((ChangeEvent e) -> {

            if (target.getText().length() > 0) {
                JViewport viewport = (JViewport) e.getSource();
                Rectangle viewRect = viewport.getViewRect();

                Point p = viewRect.getLocation();
                int startIndex = target.viewToModel(p);

                int lineHeight = target.getFontMetrics(target.getFont()).getHeight();
                p.y += viewRect.height - lineHeight;
                int endIndex = target.viewToModel(p);

                Element root = target.getDocument().getDefaultRootElement();
                startLine = root.getElementIndex(startIndex);
                endLine = root.getElementIndex(endIndex);

                repaint();
            }
        });

        MarkdownViewerDataObject lookup = NbEditorUtilities.getDataObject(target.getDocument()).getLookup().lookup(MarkdownViewerDataObject.class);

        add(new MarkdownViewerVisualPanel(lookup), BorderLayout.CENTER);
    }

    public void init() {
        this.setAutoscrolls(true);
        
        if (target.getText().length() > 0) {
            JViewport viewport = editorjScrollPane.getViewport();
            Rectangle viewRect = viewport.getViewRect();

            Point p = viewRect.getLocation();
            int startIndex = target.viewToModel(p);

            int lineHeight = target.getFontMetrics(target.getFont()).getHeight();
            p.y += viewRect.height - lineHeight;
            int endIndex = target.viewToModel(p);

            Element root = target.getDocument().getDefaultRootElement();
            startLine = root.getElementIndex(startIndex);
            endLine = root.getElementIndex(endIndex);

            repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int rowNo = (int) (e.getY() / (blockHeight + margin) * ratio);
        int lineHeight = target.getFontMetrics(target.getFont()).getHeight();

        target.scrollRectToVisible(new Rectangle(0, (int) target.getSize().getHeight(), 100, 100));
        int temp = getHeight() / lineHeight;
        target.scrollRectToVisible(new Rectangle(0, (rowNo - (temp / 2)) * lineHeight, 100, 100));
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}
