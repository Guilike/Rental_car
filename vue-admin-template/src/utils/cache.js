export function setCache(key, value) {
    window.localStorage.setItem(key, JSON.stringify(value))
}

export function getCache(key) {

    const value = window.localStorage.getItem(key)
    if (value) {
        return JSON.parse(value)
    }
}

export function deleteCache(key) {
    window.localStorage.removeItem(key)
}

export function clearCache() {
    window.localStorage.clear()
}