package github.fedka27.com.lib.observer_manager

import android.util.Log

class ObserverManager<T> {

    protected var listenerHashMap: HashMap<Any, MutableList<ObserverListener<T>>> = HashMap()
    protected var objHashMap: HashMap<Any, T> = HashMap()

    val isEmptyListeners: Boolean
        get() = listenerHashMap.isEmpty()

    /**
     * Getters and Setters
     */

    fun getObject(key: Any): T? {
        val lastValue = if (objHashMap.containsKey(key)) objHashMap[key] else null

        if (lastValue != null) {
            objHashMap[key] = lastValue
        }

        return lastValue
    }

    fun setObject(key: Any, obj: T) {

        objHashMap[key] = obj

        notifyListeners(key, obj)
    }

    /**
     * Listeners
     */

    fun addListener(key: Any, listener: ObserverListener<T>) {
        var listeners: MutableList<ObserverListener<T>>? = listenerHashMap[key]

        if (listeners == null) {
            listeners = ArrayList()
        }

        if (!listeners.contains(listener)) {
            listeners.add(listener)
        }

        listenerHashMap[key] = listeners
    }

    fun removeListener(listener: ObserverListener<T>) {
        for (countListeners in listenerHashMap.values) {
            if (countListeners.contains(listener)) {
                countListeners.remove(listener)
            }
        }
    }

    /**
     * Other methods
     */
    private fun notifyListeners(key: Any, obj: T?) {
        if (obj == null) {
            Log.e(ObserverManager::class.simpleName, "notify method stopped: Obj is null")
            return
        }
        val listeners = listenerHashMap[key] ?: return

        listeners.forEach { l -> l.notifyObserver(obj) }
    }

    fun notifyListeners(key: Any) {
        notifyListeners(key, getObject(key))
    }

    fun isNotEmpty(key: Any): Boolean {
        return getObject(key) != null
    }

    /**
     * Clear listeners and object
     * */
    fun clear() {
        listenerHashMap.clear()
        objHashMap.clear()
    }

}