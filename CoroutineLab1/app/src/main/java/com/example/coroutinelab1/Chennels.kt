package com.example.coroutinelab1

import android.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


//fun main(): Unit = runBlocking {
//    var fruitArray = arrayOf("m","a","d","b","c")
//    fun produceFruits() = GlobalScope.produce<String>{
//        for(fruit in fruitArray){
//            send(fruit)
//
//            if(fruit=="b"){
//                close()
//            }
//        }
//    }
//    val fruits= produceFruits()
//    fruits.consumeEach {
//        println(it)
//    }
//}

fun main(): Unit = runBlocking {
    var fruitArray = arrayOf("m","a","d","b","c")
    val channel = Channel<String>(capacity = 1)
    GlobalScope.launch {
        for(fruit in fruitArray){
            channel.send(fruit)
            if(fruit == "b"){
                channel.close()
                break
            }
        }
    }
//    for (fruit in channel) {
//        println(fruit)
//    }
    while (!channel.isClosedForReceive){
        val fruit = channel.receive()
        println(fruit)
    }
}
//fun main():Unit = runBlocking {
//
//    val channel = Channel<Int>(capacity = 1)
//
//    launch {
//        repeat(4){i->
//            channel.send(i)
//        }
//    }
//
//    launch {
//        repeat(5){
//            val result = channel.receive()
//            println(result)
//            //delay(2000)
//        }
//    }
//
//    launch {
//        repeat(5){
//            val result = channel.receive()
//            println(result)
//            //delay(2000)
//        }
//    }
//}


