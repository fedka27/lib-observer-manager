package github.fedka27.com.lib.observer_manager

interface ObserverListener<in T> {
    fun notifyObserver(obj: T)
}