package main.model;

import java.util.Date;

// Model class必须包含无参构造器，字段名称必须与解析的Json中名称一致
public class MyResponse {

    private String id;
    private String object;
    private Date created;
    private String result;
    private boolean is_truncated;
    private boolean need_clear_history;
    private Usage usage;

    public MyResponse() {
    }

    public MyResponse(String id, String object, Date created, String result, boolean is_truncated, boolean need_clear_history, Usage usage) {
        this.id = id;
        this.object = object;
        this.created = created;
        this.result = result;
        this.is_truncated = is_truncated;
        this.need_clear_history = need_clear_history;
        this.usage = usage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public boolean isIs_truncated() {
        return is_truncated;
    }

    public void setIs_truncated(boolean is_truncated) {
        this.is_truncated = is_truncated;
    }

    public boolean isNeed_clear_history() {
        return need_clear_history;
    }

    public void setNeed_clear_history(boolean need_clear_history) {
        this.need_clear_history = need_clear_history;
    }

    public Usage getUsage() {
        return usage;
    }

    public void setUsage(Usage usage) {
        this.usage = usage;
    }
}
