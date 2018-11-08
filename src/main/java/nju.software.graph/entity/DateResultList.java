package nju.software.graph.entity;

import java.util.List;

public class DateResultList {
    private String date;
    private List<Node> friend_list;

    public DateResultList(String date, List<Node> node){
        this.date = date;
        this.friend_list = node;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Node> getFriend_list() {
        return friend_list;
    }

    public void setFriend_list(List<Node> friend_list) {
        this.friend_list = friend_list;
    }
}
