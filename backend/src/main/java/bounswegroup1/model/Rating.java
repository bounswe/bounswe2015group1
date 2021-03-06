package bounswegroup1.model;

import java.util.Date;

import org.joda.time.DateTime;


public class Rating {
    private Long id;
    private Long userId;
    private String type;
    private Long parentId;
    private Float rating;
    private DateTime createdAt;
    
    public Rating () {

    }

    public Rating(Long id, Long userId, String type, Long parentId, Float rating, DateTime createdAt) {
        super();
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.parentId = parentId;
        this.rating = rating;
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

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    
}
