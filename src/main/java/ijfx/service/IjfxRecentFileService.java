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
package ijfx.service;

import com.google.common.collect.Lists;
import ijfx.service.ui.JsonPreferenceService;
import ijfx.ui.main.ImageJFX;
import java.util.List;
import org.scijava.Priority;
import org.scijava.event.EventService;
import org.scijava.io.RecentFileService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.service.AbstractService;
import org.scijava.service.Service;

/**
 *
 * @author cyril
 */
@Plugin(type = Service.class,priority=Priority.NORMAL_PRIORITY+1)
public class IjfxRecentFileService extends AbstractService implements RecentFileService {

    @Parameter
    JsonPreferenceService jsonPreferenceService;
   
    @Parameter
    EventService eventService;
    
    private static final String RECENT_FILE_JSON_FILE = "recentFile.json";
    
    List<String> listPath;
    
    
    @Override
    public void initialize() {
        listPath = jsonPreferenceService.loadListFromJson(RECENT_FILE_JSON_FILE, String.class);
    }
    
    @Override
    public void add(String path) {
        if(listPath.contains(path) == false) {
            listPath.add(path);
            
        }
        else {
            remove(path);
            listPath.add(path);
            eventService.publish(new RecentFileAddedEvent().setObject(path));
        };
        
        ImageJFX.getThreadQueue().execute(this::save);
        
    }

    @Override
    public boolean remove(String path) {
        listPath.remove(path);
        eventService.publish(new RecentFileRemovedEvent().setObject(path));
        return true;
    }

    @Override
    public void clear() {
        listPath.clear();
        eventService.publish(new RecentFileListClearedEvent());
    }

    @Override
    public List<String> getRecentFiles() {
        return Lists.reverse(listPath);
    }
    
    public void save() {
        jsonPreferenceService.savePreference(listPath, RECENT_FILE_JSON_FILE);
    }
    
}
