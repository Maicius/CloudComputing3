package nju.software.graph.entity;

public class Node {
    private String source;
    private String target;
    private Integer source_id;
    private Integer target_id;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Integer getSource_id() {
        return source_id;
    }

    public void setSource_id(Integer source_id) {
        this.source_id = source_id;
    }

    public Integer getTarget_id() {
        return target_id;
    }

    public void setTarget_id(Integer target_id) {
        this.target_id = target_id;
    }

    public Node(String source, Integer source_id, String target, Integer target_id) {
        this.source = source;
        this.source_id = source_id;
        this.target = target;
        this.target_id = target_id;
    }

    public String toString() {
        return "node [source=" + this.source + ", source_id=" +
                this.source_id + ", target=" + this.target + ", target_id=" + this.target_id + "]";
    }
}
