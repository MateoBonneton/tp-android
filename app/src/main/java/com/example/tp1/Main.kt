package com.example.tp1

fun main() {
    println("coucou")
    val cuisine = Cuisine(5f, 3f, "cuisine")
    val salon = Salon(8f, 4f, "salon")
    val pieces = listOf(cuisine, salon)
    pieces.forEach{
        println(it.nom)
        println("suface  "+it.nom + "= " + it.surface())
    }



}

abstract class Piece(val largeur: Float, val longueur: Float, val nom: String) {
    open fun surface(): Float {
        val surface = largeur * longueur
        return surface
    }
}

class Cuisine(largeur: Float, longueur: Float, nom: String): Piece(largeur, longueur, nom) {
    override fun surface(): Float {
        val surface = largeur * longueur
        return surface
    }
}

class Salon(largeur: Float, longueur: Float, nom: String): Piece(largeur, longueur, nom) {
    override fun surface(): Float {
        val surface = largeur * longueur
        return surface
    }
}