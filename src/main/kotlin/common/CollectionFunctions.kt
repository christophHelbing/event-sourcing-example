package common

fun<T, R> Collection<T>.mapToSet(mappingFunction: (T) -> R): Set<R> {
    val set = mutableSetOf<R>()
    forEach { set.add(mappingFunction(it)) }
    return set
}