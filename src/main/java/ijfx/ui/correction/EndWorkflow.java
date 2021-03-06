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

import io.datafx.controller.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 *
 * @author Tuan anh TRINH
 */
@ViewController(value = "EndWorkflow.fxml", title = "Wizard: Finish")
public class EndWorkflow extends CorrectionFlow {
 
  
    
    @Inject  WorkflowModel workflowModel;

    @PostConstruct
    public void init() {
        nextButton.setDisable(true);
        finishButton.setDisable(true);
    }
}
