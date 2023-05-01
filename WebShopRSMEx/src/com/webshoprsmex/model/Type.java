package com.webshoprsmex.model;

/**
 * 商品类型实体类
 */
public class Type extends BaseModel{
	
    private Integer id;

    private String typename;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename == null ? null : typename.trim();
    }
}