package bounswegroup1.model;

import java.util.Date;

public class Comment {
    private Long id;
    private Long userId;
    private String type;
    private Long parentId;
    private String body;
    private String userFullName;
    private Date createdAt;
    
    private Comment(){
        
    }

    public Comment(Long id, Long userId, String type, Long parentId, String body,
            String userFullName, Date createdAt) {
        super();
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.parentId = parentId;
        this.body = body;
        this.userFullName = userFullName;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
