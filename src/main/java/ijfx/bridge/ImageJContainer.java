/*
 * /*
 *     This file is part of ImageJ FX.
 *
 *     ImageJ FX is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     ImageJ FX is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with ImageJ FX.  If not, see <http://www.gnu.org/licenses/>. 
 *
 * 	Copyright 2015,2016 Cyril MONGIS, Michael Knop
 *
 */
package ijfx.bridge;

import ijfx.ui.context.animated.AnimatedPaneContextualView;
import ijfx.ui.datadisplay.image.ImageWindowContainer;
import ijfx.service.uicontext.UiContextService;
import java.io.File;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.ToolBar;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import org.scijava.Context;
import org.scijava.command.CommandService;
import org.scijava.module.ModuleService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.plugin.PluginService;

import mongis.utils.FXUtilities;
import ijfx.service.uiplugin.UiPluginService;
import ijfx.ui.activity.Activity;
import javafx.concurrent.Task;
import org.scijava.plugins.commands.io.OpenFile;

/**
 * This class contains ImageWindows generated by ImageJ.
 *
 * The ImageJContainer uses a singleton from the ImageWindowContainer.
 *
 * @author Cyril MONGIS, 2015
 */
@Plugin(type = Activity.class,name="imagej",label="Visualization")
//@UiConfiguration(context = "imagej", id = "imagej-container", localization = "centerStackPane")
public class ImageJContainer extends BorderPane implements Activity {

    //MenuBar menuBar = new MenuBar();
    ToolBar toolbar = new ToolBar();

    @Parameter
    Context context;

    @Parameter
    UiContextService contextService;

    @Parameter
    UiPluginService loaderService;

    @Parameter
    PluginService pluginService;

    @Parameter
    ModuleService moduleService;

    @Parameter
    CommandService commandService;

    private static final String DRAG_OVER_CSS_STYLE = "dragover";

    @Override
    public Node getContent() {
        return this;
    }
    public static final String TOP = "imagej-container-top";

    AnimatedPaneContextualView hboxViewCtrl;

    
    public ImageJContainer() {

        // setting the singleton in the center
        setCenter(ImageWindowContainer.getInstance());
        ImageWindowContainer.getInstance().getChildren().addListener(this::onListChange);

        
        this.setOnDragDropped(this::onDragDropped);
        this.setOnDragOver(this::onDragOver);
        this.setOnDragEntered(this::onDragEntered);
        this.setOnDragExited(this::onDragExited);

        
    }

    /* method called when the number of windows is changed. The container 
    takes care of entering or leaving the "image-open" context.
    
    
    TODO: delegate the context calculation to an other class
     */
    public void onListChange(ListChangeListener.Change<? extends Node> change) {

        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> onListChange(change));
            return;
        }

        change.next();
        if (change.getList().size() > 0) {
            contextService.enter("image-open");
            contextService.update();
        } else {
            contextService.leave("image-open");
            contextService.update();
        }
    }

    private void onDragEntered(DragEvent event) {
        FXUtilities.toggleCssStyle(this, DRAG_OVER_CSS_STYLE, true);
    }

    private void onDragExited(DragEvent event) {
        FXUtilities.toggleCssStyle(this, DRAG_OVER_CSS_STYLE, false);
    }

    private void onDragOver(DragEvent event) {
        Dragboard db = event.getDragboard();
        if (db.hasFiles()) {
            FXUtilities.toggleCssStyle(this, DRAG_OVER_CSS_STYLE, true);
            event.acceptTransferModes(TransferMode.COPY);

        } else {
            event.consume();
        }
        
           //FXUtilities.toggleCssStyle(this, DRAG_OVER_CSS_STYLE, false);

    }

    private void onDragDropped(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            success = true;
            String filePath = null;
            for (File file : db.getFiles()) {
                openFile(file);
            }
        }
        event.setDropCompleted(success);
        event.consume();
        FXUtilities.toggleCssStyle(this, DRAG_OVER_CSS_STYLE, false);
    }

    @Override
    public Task updateOnShow() {

      
        return null;
    }

    private void openFile(File file) {
        commandService.run(OpenFile.class, true, "inputFile", file);
    }

}
