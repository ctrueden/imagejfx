/*
    This file is part of ImageJ FX.

    ImageJ FX is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    ImageJ FX is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with ImageJ FX.  If not, see <http://www.gnu.org/licenses/>. 
    
     Copyright 2015,2016 Cyril MONGIS, Michael Knop
	
 */
package ijfx.ui.correction;

import ijfx.core.imagedb.ImageLoaderService;
import ijfx.service.ImagePlaneService;
import ijfx.service.batch.SilentImageDisplay;
import ijfx.service.dataset.DatasetUtillsService;
import ijfx.ui.datadisplay.image.ImageDisplayPane;
import io.datafx.controller.ViewController;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import net.imagej.Dataset;
import net.imagej.display.ImageDisplay;
import net.imagej.display.ImageDisplayService;
import org.scijava.Context;
import org.scijava.command.CommandService;
import org.scijava.display.DisplayService;
import org.scijava.plugin.Parameter;

/**
 *
 * @author Tuan anh TRINH
 *
 * Allow the users to choose the different flatfield
 */
@ViewController(value = "FlatfieldWorkflow.fxml")
public class FlatfieldWorkflow extends CorrectionFlow {

    @Inject
    WorkflowModel workflowModel;

    @FXML
    Button flatFieldLeftButton;

    @FXML
    Button flatFieldRightButton;

    @FXML
    GridPane gridPane;

    @FXML
    BorderPane borderPane;
    
    @Parameter
    ImageLoaderService imageLoaderService;

    @Parameter
    CommandService commandService;

    @Parameter
    Context context;

    @Parameter
    ImageDisplayService imageDisplayService;

    @Parameter
    ImagePlaneService imagePlaneService;

    protected ObjectProperty<ImageDisplayPane> flatFieldProperty1 = new SimpleObjectProperty<>();
    protected ObjectProperty<ImageDisplayPane> flatFieldProperty2 = new SimpleObjectProperty<>();

    @Parameter
    DatasetUtillsService datasetUtillsService;

    @Parameter
    DisplayService displayService;

    public FlatfieldWorkflow() throws IOException {
        CorrectionActivity.getStaticContext().inject(this);
    }

    @PostConstruct
    public void init() {
        workflowModel.bindFlatfield(this);
        gridPane.add(flatFieldProperty1.get(), 0, 1);
        gridPane.add(flatFieldProperty2.get(), 1, 1);

        ImageDisplayPane[] imageDisplayPanes = new ImageDisplayPane[]{flatFieldProperty1.get(), flatFieldProperty2.get()};
        bindPaneProperty(Arrays.asList(imageDisplayPanes));
        flatFieldLeftButton.setOnAction(e -> openImage(flatFieldProperty1.get()));
        flatFieldRightButton.setOnAction(e -> openImage(flatFieldProperty2.get()));
        if (flatFieldProperty1.get().getImageDisplay() != null && flatFieldProperty2.get().getImageDisplay() != null) {
            nextButton.setDisable(false);
        }
        
        Platform.runLater(this::initWebView);
        
    }

    protected void initWebView() {
        WebView webView = new WebView();
        borderPane.setTop(webView);
        initWebView(webView,"FlatfieldWorkflow.md");
    }
    
    protected void openImage(ImageDisplayPane imageDisplayPane) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        workflowModel.openImage(imageDisplayPane, file).thenRunnable(() -> {
            if (flatFieldProperty1.get().getImageDisplay() != null && flatFieldProperty2.get().getImageDisplay() != null) {
                nextButton.setDisable(false);
            }

        }).start();
    }

    protected ImageDisplay displayDataset(Dataset flatFieldDataset) {
        ImageDisplay imageDisplay = new SilentImageDisplay(context, flatFieldDataset);
        imageDisplay.display(flatFieldDataset);
        return imageDisplay;
    }

}
