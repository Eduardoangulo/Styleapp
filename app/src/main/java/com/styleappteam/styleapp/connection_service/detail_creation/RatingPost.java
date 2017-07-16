package com.styleappteam.styleapp.connection_service.detail_creation;

/**
 * Created by Luis on 16/07/2017.
 */

public class RatingPost {
    private Integer detail_id;
    private Integer value;

    public RatingPost(Integer detail_id, Integer value) {
        this.detail_id = detail_id;
        this.value = value;
    }

    public Integer getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(Integer detail_id) {
        this.detail_id = detail_id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
