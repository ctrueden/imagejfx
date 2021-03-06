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
package ijfx.plugins.flatfield;

import ijfx.plugins.convertype.TypeChangerIJFX;
import ijfx.service.ui.CommandRunner;
import net.imagej.Dataset;
import net.imagej.plugins.commands.typechange.TypeChanger;
import net.imagej.types.DataTypeService;
import net.imglib2.type.numeric.RealType;
import org.scijava.Context;
import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.command.ContextCommand;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 *
 * @author Cyril MONGIS, 2016
 */
@Plugin(type = Command.class)
public class ConvertTo32Bits extends ContextCommand {

    @Parameter(type = ItemIO.BOTH)
    Dataset dataset;
    
    @Parameter
    DataTypeService dataTypeService;
    
    @Parameter
    Context context;
    
    @Override
    public void run() {
       RealType<?> firstElement = dataset.firstElement();
        String typeFirstElement = dataTypeService.getTypeByAttributes(firstElement.getBitsPerPixel(), true, false, !dataset.isInteger(), dataset.isSigned()).longName();

        String type = dataTypeService.getTypeByAttributes(32, true, false, true, true).longName();

        if (typeFirstElement.equals(type)) return;
        
        dataset = new CommandRunner(context)
                .set("data", dataset)
                .set("typeName",type)
                .set("combineChannels",false)
                .runSync(TypeChangerIJFX.class)
                .getOutput("data");
        //return output;
    }
    
}
