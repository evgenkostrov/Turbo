package com.example.turbo.model

import android.content.Context
import android.content.res.Resources
import android.provider.Settings.Global.getString

import com.example.turbo.R
import java.util.*

typealias AkbsListener = (akbs:List<Akb>)->Unit

class AkbsService {

    private var akbs = mutableListOf<Akb>()
    private val listeners= mutableSetOf<AkbsListener>()

    init {
        akbs = (0..100).map { Akb(it,
            IMAGES[(0..6).shuffled().last()],
            BAT[(0..9).shuffled().last()],
            CAT[(0..3).shuffled().last()],
            60,
            (5000..15000 step 100).shuffled().last()) }

        .toMutableList()
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
            "https://tubor.ru/upload/iblock/097/0979d436c81cb23a2378d5e7a7b5118a.png",
            "https://tubor.ru/upload/iblock/65b/65b428e0d7c46024166f47975bd00aca.png",
            "https://tubor.ru/upload/iblock/c5e/c5edf7c7e90eafdd2e4723615004ad98.png",
            "https://tubor.ru/upload/iblock/097/0979d436c81cb23a2378d5e7a7b5118a.png",
            "https://tubor.ru/upload/iblock/061/0617e4f87242faafc15dd7ae8944d0c7.png",
            "https://tubor.ru/upload/iblock/a6c/a6cfd41632c0c70d089ac828361a90c9.png"
        )
        private val CAT = mutableListOf<String>(
            "для интенсивной эксплуатации в жестких погодных условиях, плохих дорог и мест хранения техники транспорта",
            "для автомобилей с минимальным набором электрооборудования",
            "для автомобилей, оборудованных системой старт-стоп, имеющих большое количество энергопотребителей, работающих в такси или с длительными простоями, а также систем ИПБ",
            "для эксплуатации на водном транспорте, (река, море), а также в системах ИПБ"
        )

        private val BAT = mutableListOf<String>(
            "TUBOR AQUATECH",
            "TUBOR SYNERGY",
            "TUBOR STANDART",
            "TUBOR TRUCK",
            "TUBOR EFB",
            "TUBOR ASIA",
            "TUBOR GEL",
            "TITAN ARCTIC",
            "ARCTIC ASIA",
            "VAIPER",


        )

    }
}