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

import ijfx.core.project.Project;
import java.util.concurrent.Callable;
import javafx.concurrent.Task;
import javafx.util.Callback;

/**
 *
 * @author cyril
 */
public class ProjectUpdateTask extends Task<Boolean> {
    
    
    private final Project project;
    
    private final Callback<Project,Boolean> update;

    public ProjectUpdateTask(Project project, Callback<Project,Boolean> callable) {
        this.project = project;
        this.update = callable;
    }

    public Project getProject() {
        return project;
    }
    
    

    @Override
    protected Boolean call() throws Exception {
        System.out.println("Starting thread");
        return update.call(project);
    }
    
    
    
}