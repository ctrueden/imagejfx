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
package ijfx.ui.datadisplay.metadataset;

import ijfx.core.metadata.MetaDataSet;
import org.scijava.command.Command;
import org.scijava.command.ContextCommand;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 *
 * @author Cyril MONGIS, 2016
 */
@Plugin(type = Command.class,menuPath = "Plugins > Test > MetaDataSet Display")
public class MetaDataSetDisplayTest extends ContextCommand{

    @Parameter
    MetaDataSetDisplayService metaDataDisplayService;
    
    @Override
    public void run() {
        
        doIt();
        
    }
    
    
    public void doIt() {
        String displayName = "Test display 2";
        for(int i = 0; i!= 100;i++) {
            MetaDataSet set = new MetaDataSet();
            set.putGeneric("Name","line "+(i+1));
            set.putGeneric("int",i);
            set.putGeneric("double",new Double(i*1.5).doubleValue());
            metaDataDisplayService.addMetaDataSetToDisplay(set, displayName);
            
            
        }
        
        
        
    }
    
}
