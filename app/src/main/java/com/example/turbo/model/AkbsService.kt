package com.example.turbo.model

import java.util.*

typealias AkbsListener = (akbs:List<Akb>)->Unit

class AkbsService {

    private var akbs = mutableListOf<Akb>()
    private val listeners= mutableSetOf<AkbsListener>()

    init {
        akbs = listOf<Akb>(
            Akb(0,  IMAGES[(0..6).shuffled().last()], "VAIPER", "Min", 55, 3950),
            Akb(1, IMAGES[(0..6).shuffled().last()], "TUBOR", "Min", 60, 5800),
            Akb(2, IMAGES[(0..6).shuffled().last()], "TITAN ARCTIC", "Min", 65, 8300),
            Akb(3,IMAGES[(0..6).shuffled().last()], "VAIPER", "Min", 55, 3950),
            Akb(4, IMAGES[(0..6).shuffled().last()], "TUBOR", "Min", 60, 5800),
            Akb(5, IMAGES[(0..6).shuffled().last()], "TITAN ARCTIC", "Min", 65, 8300),
            Akb(6, IMAGES[(0..6).shuffled().last()], "VAIPER", "Min", 55, 3950),
            Akb(7,IMAGES[(0..6).shuffled().last()], "TUBOR", "Min", 60, 5800),
            Akb(8, IMAGES[(0..6).shuffled().last()], "TITAN ARCTIC", "Min", 65, 8300),
        ).toMutableList()
    }

    fun getAkbs():List<Akb>{
        return akbs
    }

    fun deleteAkb(akb:Akb){
       // akbs.remove(akb)    /*danger
        val indexToDelete = akbs.indexOfFirst { it.id == akb.id }
        if(indexToDelete!=-1){
            akbs.removeAt(indexToDelete)
            notifyChanges()
        }
    }

    fun moveAkb(akb:Akb, moveBy:Int){
        val oldIndex=akbs.indexOfFirst { it.id==akb.id }
        if (oldIndex==-1)return
        val newIndex=oldIndex+moveBy
        if(newIndex<0||newIndex>akbs.size)return
        Collections.swap(akbs,oldIndex,newIndex)
        notifyChanges()
    }

    fun addListener(listener: AkbsListener){
        listeners.add (listener)
        listener.invoke(akbs)
    }
    fun removeListener(listener: AkbsListener){
        listeners.remove(listener)
    }
    private fun notifyChanges(){
        listeners.forEach { it.invoke(akbs) }
    }


    companion object {
        private val IMAGES = mutableListOf<String>(
            "https://tubor.ru/upload/iblock/097/0979d436c81cb23a2378d5e7a7b5118a.png",
            "https://tubor.ru/upload/iblock/179/179373536ce79a8ee037b31edf595ed0.png",
            "https://tubor.ru/upload/iblock/65b/65b428e0d7c46024166f47975bd00aca.png",
            "https://tubor.ru/upload/iblock/c5e/c5edf7c7e90eafdd2e4723615004ad98.png",
            "https://tubor.ru/upload/iblock/035/035f2ee00416e1b3f3a85f5be56cd67e.png",
            "https://tubor.ru/upload/iblock/061/0617e4f87242faafc15dd7ae8944d0c7.png",
            "https://tubor.ru/upload/iblock/a6c/a6cfd41632c0c70d089ac828361a90c9.png"
        )
    }
}