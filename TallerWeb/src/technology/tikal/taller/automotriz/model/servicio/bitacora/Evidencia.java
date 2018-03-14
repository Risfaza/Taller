/**
 *   Copyright 2013-2015 Tikal-Technology
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package technology.tikal.taller.automotriz.model.servicio.bitacora;

/**
 * 
 * @author Nekorp
 *
 */
public class Evidencia {

    private String thumbnail;
    private String image;
    private boolean appended;

    public Evidencia() {
        this.thumbnail = "";
        this.image = "";
    }
    
    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
 
    public void setAppended(boolean appended){
    	this.appended=appended;
    }
    	
    public boolean isAppended(boolean appended){
    	return this.appended;
    }
}
