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
package ijfx.service.batch.input;

import ijfx.service.batch.BatchSingleInput;
import io.scif.services.DatasetIOService;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;
import org.scijava.Context;
import org.scijava.plugin.Parameter;

/**
 *
 * @author cyril
 */
public class SaveToFileWrapper extends AbstractSaverWrapper {

    private File saveTo;
            
    @Parameter
    private DatasetIOService datasetIoService;

    private String suffix = null;
    
    
    private SaveToFileWrapper(Context context, BatchSingleInput input) {
        super(input);
        context.inject(this);
    }
    
    public SaveToFileWrapper(Context context,BatchSingleInput input, File saveFile) {
        
       this(context,input);
        
        saveTo = saveFile;
        
    }
    
    public SaveToFileWrapper(Context context, BatchSingleInput input, File saveFile, String suffix) {
        
       this(context,input);
        
        String baseName = FilenameUtils.getBaseName(saveFile.getName());
        String ext = FilenameUtils.getExtension(saveFile.getName());
        String finalName = new StringBuilder().append(baseName).append(suffix).append(".").append(ext).toString();
        System.out.println(finalName);
        saveTo = new File(saveFile.getParentFile(),finalName);
    }
            
    
    @Override
    public void save() {
        try {
            System.out.println("Saving to ..."+saveTo.getAbsolutePath());
            datasetIoService.save(getWrappedObject().getDataset(), saveTo.getAbsolutePath());
        } catch (IOException ex) {
            Logger.getLogger(SaveToFileWrapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        getWrappedObject().save();
    }
    
}