/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connector;

import java.util.HashMap;

/**
 *
 * @author edytka
 */
public class miniStream {
    
    private String id;
    private String server_modified_at;
    private Long contributor_count;
    private String server_created_at;
    private String name;
    private HashMap annotations = new HashMap();
    private String deleted_locally;
    private String modified_locally;
    
    public miniStream(String _id, String _server_modified_at, Long _contributor_count, String _server_created_at, String _name, HashMap _annotation){
        
        id = _id;
        server_created_at = _server_created_at;
        server_modified_at = _server_modified_at;
        annotations = _annotation;
        name = _name;
        contributor_count = _contributor_count;
    }

    public String getId() {
        return id;
    }

    public String getServer_modified_at() {
        return server_modified_at;
    }

    public Long getContributor_count() {
        return contributor_count;
    }

    public String getServer_created_at() {
        return server_created_at;
    }

    public String getName() {
        return name;
    }

    public HashMap getAnnotations() {
        return annotations;
    }
    
    public String getModified_locally() {
            return modified_locally;
    }

    public void setModified_locally(String modified_locally) {
            this.modified_locally = modified_locally;
    }

    public String getDeleted_locally() {
            return deleted_locally;
    }

    public void setDeleted_locally(String deleted_locally) {
            this.deleted_locally = deleted_locally;
    }
    
}
