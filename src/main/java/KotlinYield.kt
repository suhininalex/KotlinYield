import com.offbynull.coroutines.user.Continuation
import com.offbynull.coroutines.user.CoroutineRunner

fun <T> yieldable(body: Continuation<T>.()->Unit) = object : Iterable<T> {
    val runner = CoroutineRunner({ (it as Continuation<T>).body() })

    var currentIterator = iterator<T>()

    override fun iterator() = iterate {
        if (currentIterator.hasNext())
            return@iterate currentIterator.next()
        else {
            while (runner.execute()) {
                currentIterator = (runner.context as Iterator<T>)
                if (currentIterator.hasNext()) return@iterate currentIterator.next()
            }
            return@iterate null
        }
    }
}

fun <T> Continuation<T>.yeild(result: T){
    context = iterator(result)
    suspend()
}

fun <T> Continuation<T>.yeild(result: Iterable<T>){
    context = result.iterator()
    suspend()
}

fun <T> Continuation<T>.yeild(result: Iterator<T>){
    context = result
    suspend()
}

fun <T> iterator(vararg objects: T) = objects.iterator()