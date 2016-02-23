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
package ijfx.ui.project_manager.projectdisplay.card;


import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import ijfx.core.project.Project;
import ijfx.service.ui.AppService;
import ijfx.service.ui.Apps;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.Property;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import mongis.utils.FXUtilities;
import org.scijava.Context;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 *
 * @author cyril
 */
@Plugin(type = ProjectCard.class, priority = 9.0)
public class HierarchyProjectCard extends BorderPane implements ProjectCard{

    
    private static final String NAME = "Plane Organization";
    
    
    
    @FXML
    ListView<String> listView;
    
    @Parameter
    Context context;
    
    @Parameter
    AppService appService;
    
    public HierarchyProjectCard() {
        try {
            FXUtilities.injectFXML(this);
        } catch (IOException ex) {
            Logger.getLogger(HierarchyProjectCard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    @Override
    public Node getContent() {
        return this;
    }

    @Override
    public Task<Boolean> update(Project project) {
        
        
        return new ProjectUpdateTask(project, this::updateListView);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public FontAwesomeIcon getIcon() {
        return FontAwesomeIcon.CUBES;
    }
    
    public Boolean updateListView(Project project) {
        listView.setItems(project.getHierarchy());
        return true;
    }
    
    @FXML
    public void changePlaneOrganization() {
        appService.showApp(Apps.HIERARCHY_EDITOR);
    }
    
    
    DismissableCardDecorator<Project> decorator = new DismissableCardDecorator<>(this);
    
    @Override
    public Property<Boolean> dismissable() {
        return decorator.dismissable();
        
    }

    @Override
    public Property<Boolean> dismissed() {
        return decorator.dismissed();
    }
    
}
