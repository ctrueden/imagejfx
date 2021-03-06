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
package ijfx.ui.explorer.view.chartview;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import ijfx.service.cluster.ExplorableClustererService;
import ijfx.service.ui.HintService;
import ijfx.service.ui.LoadingScreenService;
import ijfx.ui.correction.WorkflowModel;
import ijfx.ui.explorer.Explorable;
import ijfx.ui.explorer.ExplorerService;
import ijfx.ui.explorer.ExplorerView;
import ijfx.ui.explorer.view.GridIconView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import mongis.utils.CallbackTask;
import mongis.utils.FXUtilities;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 *
 * @author Tuan anh TRINH
 */
@Plugin(type = ExplorerView.class, priority = 0.5, label = "XMean (exp)")
public class ChartView extends AbstractChartView implements ExplorerView {

    @Parameter
    WorkflowModel workflowModel;
    @Parameter
    HintService hintService;

    @Parameter
    ExplorableClustererService explorableClustererService;

    @Parameter
    ExplorerService explorerService;

    @Parameter
    LoadingScreenService loadingScreenService;

    String[] metadatas;

    @FXML
    ComboBox<String> xComboBox;

    @FXML
    ComboBox<String> yComboBox;

    List<ComboBox<String>> comboBoxList;

    public ChartView() {
        super();
        comboBoxList = new ArrayList<>();
        metadatas = new String[2];
        try {
            FXUtilities.injectFXML(this, "/ijfx/ui/explorer/view/chartview/ChartView.fxml");
        } catch (IOException ex) {
            Logger.getLogger(GridIconView.class.getName()).log(Level.SEVERE, null, ex);
        }
        comboBoxList.add(xComboBox);
        comboBoxList.add(yComboBox);

        scatterChart.setTitle("ChartView: XMeans Clustering");
        scatterChart.getXAxis().labelProperty().bind(xComboBox.getSelectionModel().selectedItemProperty());
        scatterChart.getYAxis().labelProperty().bind(yComboBox.getSelectionModel().selectedItemProperty());
        initComboBox();
        setGraphicSnapshot();
    }

    @Override
    public Node getNode() {
        return this;
    }

    @Override
    public Node getIcon() {
        return new FontAwesomeIconView(FontAwesomeIcon.TIMES);
    }

    @Override
    public void setItem(List<? extends Explorable> items) {
        currentItems = items;
        List<String> metadatas = explorerService.getMetaDataKey(currentItems);

        comboBoxList.stream().forEach(c -> {
            String s = c.getSelectionModel().getSelectedItem();
            c.getItems().clear();
            c.getItems().addAll(metadatas);
            if (metadatas.contains(s)) {
                c.getSelectionModel().select(s);
            }
        });
        computeItems();
    }

    @Override
    public List<? extends Explorable> getSelectedItems() {
        return currentItems
                .stream()
                .map(item -> (Explorable) item)
                .filter(item -> item.selectedProperty().getValue() == true)
                .collect(Collectors.toList());
    }

    @Override
    public void setSelectedItem(List<? extends Explorable> items) {
        currentItems
                .stream()
                .forEach(e -> e.selectedProperty().setValue(true));
    }

//   
    /**
     * Perform a clustering algorithm and load the series.
     */
    @Override
    public void computeItems() {

        if (metadatas.length > 1) {
            try {
                scatterChart.getData().clear();

                List<List<? extends Explorable>> clustersList = new CallbackTask<Void, List<List<? extends Explorable>>>()
                        .run((a, b) -> explorableClustererService.clusterExplorable(currentItems, Arrays.asList(metadatas)))
                        .submit(loadingScreenService)
                        .call();

//            List<List<? extends Explorable>> clustersList = explorableClustererService.clusterExplorable(currentItems, Arrays.asList(metadatas));
                clustersList
                        .stream()
                        .forEach(e -> addDataToChart(e, Arrays.asList(metadatas)));

                bindLegend();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void initComboBox() {
        xComboBox.getSelectionModel().selectedItemProperty().addListener((obs, old, n) -> {
            metadatas[0] = n;
            deselecItems();
            computeItems();
        });
        yComboBox.getSelectionModel().selectedItemProperty().addListener((obs, old, n) -> {
            metadatas[1] = n;
            deselecItems();
            computeItems();
        });
    }

    public void deselecItems() {
        currentItems.stream()
                .forEach(e -> e.selectedProperty().setValue(false));
    }

}
