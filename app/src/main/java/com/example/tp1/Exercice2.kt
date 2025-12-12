package com.example.tp1

fun main() {
    println("coucou")
    etudiants.filter {
        it.promo == "2024"
    }.forEach { println(it.name) }

    etudiants.filter {
        it.matieres.count() > 2
    }.forEach { println(it.name) }

}

class Etudiant(val name: String, val promo: String, val matieres: List<String>)

val etudiants = listOf(
    Etudiant("Paul", "2025", listOf("mobile", "Web", "BDD")),
    Etudiant("Yazid", "2024", listOf("mobile", "Android", "Réseau")),
    Etudiant("Caroline", "2025", listOf("SE", "Anglais")),
)