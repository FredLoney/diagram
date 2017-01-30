package org.reactome.web.diagram.controls.top;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.FlowPanel;
import org.reactome.web.diagram.common.PwpButton;
import org.reactome.web.diagram.controls.top.illustrations.DiagramIllustrations;
import org.reactome.web.diagram.controls.top.key.DiagramKey;
import org.reactome.web.diagram.events.CanvasExportRequestedEvent;
import org.reactome.web.diagram.events.ContentLoadedEvent;
import org.reactome.web.diagram.handlers.ContentLoadedHandler;

import static org.reactome.web.diagram.events.CanvasExportRequestedEvent.Option.IMAGE;
import static org.reactome.web.diagram.events.CanvasExportRequestedEvent.Option.PPTX;


/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class RightTopLauncherPanel extends FlowPanel implements ClickHandler, ContentLoadedHandler {

    private EventBus eventBus;

    private DiagramIllustrations diagramIllustrations;
    private DiagramKey diagramKey;

    private PwpButton illustrationsBtn;
    private PwpButton captureBtn;
    private PwpButton exportBtn;
    private PwpButton diagramKeyBtn;

    public RightTopLauncherPanel(EventBus eventBus) {
        this.setStyleName(RESOURCES.getCSS().launcherPanel());

        this.eventBus = eventBus;
        this.diagramIllustrations = new DiagramIllustrations(eventBus);
        this.diagramKey = new DiagramKey(eventBus);

        this.illustrationsBtn = new PwpButton("Show illustrations", RESOURCES.getCSS().illustrations(), this);
        this.add(illustrationsBtn);

        this.captureBtn = new PwpButton("Diagram export", RESOURCES.getCSS().camera(), this);
        this.add(this.captureBtn);

        this.exportBtn = new PwpButton("Export to pptx", RESOURCES.getCSS().export(), this);
        this.add(this.exportBtn);

        this.diagramKeyBtn = new PwpButton("Diagram key", RESOURCES.getCSS().key(), this);
        this.add(this.diagramKeyBtn);

        eventBus.addHandler(ContentLoadedEvent.TYPE, this);
        this.setVisible(true);
    }

    @Override
    public void onClick(ClickEvent event) {
        PwpButton btn = (PwpButton) event.getSource();
        if (btn.equals(this.captureBtn)) {
            this.eventBus.fireEventFromSource(new CanvasExportRequestedEvent(IMAGE), this);
        } else if (btn.equals(this.diagramKeyBtn)) {
            if (this.diagramKey.isShowing()) {
                this.diagramKey.hide();
            } else {
                this.diagramKey.showRelativeTo(this.diagramKeyBtn);
            }
        } else if (btn.equals(this.illustrationsBtn)) {
            if (this.diagramIllustrations.isShowing()) {
                this.diagramIllustrations.hide();
            } else {
                this.diagramIllustrations.showRelativeTo(btn);
            }
        }  else if (btn.equals(this.exportBtn)) {
            this.eventBus.fireEventFromSource(new CanvasExportRequestedEvent(PPTX), this);
        }

    }

    @Override
    public void onContentLoaded(ContentLoadedEvent event) {
        switch (event.getContext().getContent().getType()) {
            case DIAGRAM:
                diagramKeyBtn.setEnabled(true);
                exportBtn.setEnabled(true);
                break;
            case SVG:
                diagramKeyBtn.setEnabled(false);
                exportBtn.setEnabled(false);
                break;
        }
    }


    public static Resources RESOURCES;
    static {
        RESOURCES = GWT.create(Resources.class);
        RESOURCES.getCSS().ensureInjected();
    }

    public interface Resources extends ClientBundle {
        @Source(ResourceCSS.CSS)
        ResourceCSS getCSS();


        @Source("images/camera_clicked.png")
        ImageResource cameraClicked();

        @Source("images/camera_disabled.png")
        ImageResource cameraDisabled();

        @Source("images/camera_hovered.png")
        ImageResource cameraHovered();

        @Source("images/camera_normal.png")
        ImageResource cameraNormal();

        @Source("images/illustrations_clicked.png")
        ImageResource illustrationsClicked();

        @Source("images/illustrations_disabled.png")
        ImageResource illustrationsDisabled();

        @Source("images/illustrations_hovered.png")
        ImageResource illustrationsHovered();

        @Source("images/illustrations_normal.png")
        ImageResource illustrationsNormal();

        @Source("images/key_clicked.png")
        ImageResource keyClicked();

        @Source("images/key_disabled.png")
        ImageResource keyDisabled();

        @Source("images/key_hovered.png")
        ImageResource keyHovered();

        @Source("images/key_normal.png")
        ImageResource keyNormal();

        @Source("images/export2ppt_clicked.png")
        ImageResource export2pptClicked();

        @Source("images/export2ppt_disabled.png")
        ImageResource export2pptDisabled();

        @Source("images/export2ppt_hovered.png")
        ImageResource export2pptHovered();

        @Source("images/export2ppt_normal.png")
        ImageResource export2pptNormal();

        @Source("images/settings_clicked.png")
        ImageResource settingsClicked();

        @Source("images/settings_disabled.png")
        ImageResource settingsDisabled();

        @Source("images/settings_hovered.png")
        ImageResource settingsHovered();

        @Source("images/settings_normal.png")
        ImageResource settingsNormal();
    }

    @CssResource.ImportedWithPrefix("diagram-LeftTopLauncher")
    public interface ResourceCSS extends CssResource {
        String CSS = "org/reactome/web/diagram/controls/top/RightTopLauncherPanel.css";

        String launcherPanel();

        String camera();

        String export();

        String illustrations();

        String key();
    }
}
