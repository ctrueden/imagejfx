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
package ijfx.ui.previewToolbar;

import ijfx.ui.context.ContextualWidget;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 *
 * @author Tuan anh TRINH
 */
public class ContextualPaneIconWrapper  implements ContextualWidget, ContextualPaneIcon {

    private String context;
    private final Node node;
    private final Pane parentPane;

    double order = 0.0;
    
    public ContextualPaneIconWrapper(Pane parentPane, Node node, String context) {
        this.node = node;
        this.parentPane = parentPane;
        this.context = context;
    }

    @Override
    public String getContext() {
        return context;
    }

 

    @Override
    public Event getOnAction() {
        //TODO
        return null;
    }

    @Override
    public String getName() {
        return node.getId();
    }

    @Override
    public boolean isHidden() {
        return !parentPane.getChildren().contains(node);
    }

    @Override
    public void enable() {
        node.setDisable(false);
    }

    @Override
    public boolean isEnabled() {
        return !node.isDisabled();
    }

    @Override
    public void disable() {
        node.setDisable(true);
    }

    @Override
    public void show() {

        parentPane.getChildren().add(node);
        node.setVisible(true);
    }

    @Override
    public void hide() {
        parentPane.getChildren().remove(node);
    }

    @Override
    public Node getObject() {        
        return node;
    }

    public String toString() {
        return node.toString();
    }

    @Override
    public double getPriority() {
        return order;
    }

    public void setOrder(double order) {
        this.order = order;
    }
    
    

}
