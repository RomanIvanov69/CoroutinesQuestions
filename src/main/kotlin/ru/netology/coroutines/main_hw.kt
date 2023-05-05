package ru.netology.coroutines

import kotlinx.coroutines.*
import kotlin.coroutines.EmptyCoroutineContext

// Cancellation
// 1. Отработает ли в этом коде строка <--? Поясните, почему да или нет.
// Ответ: нет, т.к. значение delay равно 100, функция job.cancelAndJoin() отменит println("ok")

/* fun main() = runBlocking {
    val job = CoroutineScope(EmptyCoroutineContext).launch {
        launch {
            delay(500)
            println("ok") // <--
        }
        launch {
            delay(500)
            println("ok")
        }
    }
    delay(100)
    job.cancelAndJoin()
}
 */

// 2. Отработает ли в этом коде строка <--. Поясните, почему да или нет.
// Ответ: нет, т.к. функция child.cancel() отменит вывод первой "ок", второй "ок" отобразится в консоли.

/* fun main() = runBlocking {
    val job = CoroutineScope(EmptyCoroutineContext).launch {
        val child = launch {
            delay(500)
            println("ok") // <--
        }
        launch {
            delay(500)
            println("ok")
        }
        delay(100)
        child.cancel()
    }
    delay(100)
    job.join()
}
 */

// Exception Handling
// 1. Отработает ли в этом коде строка <--. Поясните, почему да или нет.
// Ответ: не отработает, исключение выброшенное в дочерней корутине, в родительской перехвачено не будет

/* fun main() {
    with(CoroutineScope(EmptyCoroutineContext)) {
        try {
            launch {
                throw Exception("something bad happened")
            }
        } catch (e: Exception) {
            e.printStackTrace() // <--
        }
    }
    Thread.sleep(1000)
}
 */

// 2. Отработает ли в этом коде строка <--. Поясните, почему да или нет.
// Ответ: Да. В консоль выведется исключение "something bad happened". Т.к. функция coroutineScope {}
// позволяет перехватывать ошибки во вложенных корутинах.

/* fun main() {
    CoroutineScope(EmptyCoroutineContext).launch {
        try {
            coroutineScope {
                throw Exception("something bad happened")
            }
        } catch (e: Exception) {
            e.printStackTrace() // <--
        }
    }
    Thread.sleep(1000)
}
 */

// 3. Отработает ли в этом коде строка <--. Поясните, почему да или нет.
// Ответ: Да. Т.к. функция supervisorScope {} пробрасывает исключения произошедшие в нем или в дочерних корутинах.


/* fun main() {
    CoroutineScope(EmptyCoroutineContext).launch {
        try {
            supervisorScope {
                throw Exception("something bad happened")
            }
        } catch (e: Exception) {
            e.printStackTrace() // <--
        }
    }
    Thread.sleep(1000)
}
 */

// 4. Отработает ли в этом коде строка <--. Поясните, почему да или нет.
// Ответ: Нет. Функция delay(500) отменить выполнение текущей корутины, отработает второй Exception.

/* fun main() {
    CoroutineScope(EmptyCoroutineContext).launch {
        try {
            coroutineScope {
                launch {
                    delay(500)
                    throw Exception("something bad happened") // <--
                }
                launch {
                    throw Exception("something bad happened")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    Thread.sleep(1000)
}
 */

// 5. Отработает ли в этом коде строка <--. Поясните, почему да или нет.
// Ответ: Да, т.к. корутины запускаются через supervisorScope, которая выполнится после остальных

/* fun main() {
    CoroutineScope(EmptyCoroutineContext).launch {
        try {
            supervisorScope {
                launch {
                    delay(500)
                    throw Exception("something bad happened") // <--
                }
                launch {
                    throw Exception("something bad happened")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace() // <--
        }
    }
    Thread.sleep(1000)
}
 */

// 6. Отработает ли в этом коде строка <--. Поясните, почему да или нет.
// Ответ: Нет, сначала выведется исключение Exception("something bad happened"). CoroutineScope отменит дочернюю корутину в функции delay (1000).

/* fun main() {
    CoroutineScope(EmptyCoroutineContext).launch {
        CoroutineScope(EmptyCoroutineContext).launch {
            launch {
                delay(1000)
                println("ok") // <--
            }
            launch {
                delay(500)
                println("ok")
            }
            throw Exception("something bad happened")
        }
    }
    Thread.sleep(1000)
}
 */

// 7. Отработает ли в этом коде строка <--. Поясните, почему да или нет.
// Ответ: Нет, т.к. вызван SupervisorJob(), исключение отменит его и дочерние корутины.

fun main() {
    CoroutineScope(EmptyCoroutineContext).launch {
        CoroutineScope(EmptyCoroutineContext + SupervisorJob()).launch {
            launch {
                delay(1000)
                println("ok") // <--
            }
            launch {
                delay(500)
                println("ok")
            }
            throw Exception("something bad happened")
        }
    }
    Thread.sleep(1000)
}