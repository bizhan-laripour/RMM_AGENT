package com.submodule.dto;

public class ConsumerDto {

    private String topicName;

    private Object object;

    public ConsumerDto(String topicName , Object object){
        this.topicName = topicName;
        this.object = object;
    }

    public ConsumerDto() {
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
