/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connector;

/**
 *
 * @author edytka
 */
public class miniObject {
    
    private String id;
    private String server_modified_at;
    private String type;
    
    public String getId() {
        return id;
    }

    public String getServer_modified_at() {
        return server_modified_at;
    }

    public String getType() {
        return type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setServer_modified_at(String server_modified_at) {
        this.server_modified_at = server_modified_at;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public miniObject(String _id, String _server_modified_at, String _type){
        id = _id;
        server_modified_at = _server_modified_at;
        type = _type;
    }
    
    
}
