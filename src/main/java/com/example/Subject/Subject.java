package com.example.Subject;


import com.example.Observer.Observer;

public interface Subject {
    public void addObserver(Observer o);
    public void deleteObserver(Observer o);
    public void notifyObservers();
}
