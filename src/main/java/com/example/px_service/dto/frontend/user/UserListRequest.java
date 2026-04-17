package com.example.px_service.dto.frontend.user;

public class UserListRequest {
    //接收参数 page 和 size
    private Integer page;
    private Integer size;
    private String username;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //默认第一页，每页10条信息
    public UserListRequest() {
        this.page = 1;
        this.size = 10;
        this.username = "";
    }
}
