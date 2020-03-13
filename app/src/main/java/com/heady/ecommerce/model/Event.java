package com.heady.ecommerce.model;

public class Event<T> {

    String event;
    T data;
    Integer position;

    public Event(String event, T data, Integer position) {
        this.event = event;
        this.data = data;
        this.position = position;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Event{" +
                "event='" + event + '\'' +
                ", data=" + data +
                ", position=" + position +
                '}';
    }
}
