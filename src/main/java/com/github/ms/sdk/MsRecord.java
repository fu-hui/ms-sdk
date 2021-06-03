package com.github.ms.sdk;

import java.util.List;
import java.util.Map;

public class MsRecord {
    private String id;
    private long timestamp;
    private String from;
    private List<String> toList;
    private Map<String, String> extHeader;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public List<String> getToList() {
        return toList;
    }

    public void setToList(List<String> toList) {
        this.toList = toList;
    }

    public Map<String, String> getExtHeader() {
        return extHeader;
    }

    public void setExtHeader(Map<String, String> extHeader) {
        this.extHeader = extHeader;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
