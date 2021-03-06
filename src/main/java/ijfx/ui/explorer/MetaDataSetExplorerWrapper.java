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
package ijfx.ui.explorer;

import ijfx.core.metadata.MetaData;
import ijfx.core.metadata.MetaDataSet;
import ijfx.ui.main.ImageJFX;
import javafx.beans.property.BooleanProperty;
import javafx.scene.image.Image;
import net.imagej.Dataset;

/**
 *
 * @author Cyril MONGIS, 2016
 */
public class MetaDataSetExplorerWrapper extends AbstractExplorable implements Explorable{

    private static Image bieber = new Image("http://www.billboard.com/files/styles/article_main_image/public/media/justin-bieber-shirtless-presents-onstage-at-fashion-rocks-2014-billboard-650.jpg");
    
    private final MetaDataSet wrappedMetaDataSet;
    
    public MetaDataSetExplorerWrapper(MetaDataSet metaDataSet) {
        wrappedMetaDataSet = metaDataSet;
    }

    
    @Override
    public String getTitle() {
        return getMetaDataSet().get(MetaData.FILE_NAME).getStringValue();
    }

    @Override
    public String getSubtitle() {
        return "";
    }

    @Override
    public String getInformations() {
        return "";
    }

    @Override
    public Image getImage() {
        return bieber;
    }

    @Override
    public void open() throws Exception {
        ImageJFX.getLogger().severe("Not supported.");
    }

    

    @Override
    public Dataset getDataset() {
        return null;
    }
    
    @Override
    public MetaDataSet getMetaDataSet() {
        return wrappedMetaDataSet;
    }
    
    public void dispose() {
        
    }
    
}
