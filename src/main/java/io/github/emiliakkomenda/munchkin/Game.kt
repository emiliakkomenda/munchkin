package io.github.emiliakkomenda.munchkin

data class Hero(
    val name: String,
    var strength: Int
)

fun randomMonsterStrength() = (1..10).random()
fun randomHeroStrength() = (1..5).random()
fun randomDoorEffect() = (1..2).random()

fun handleDoorEffect(hero: Hero, doorEffect: Int) = when (doorEffect) {
    1 -> {
        val monsterStrength = randomMonsterStrength()
        println("The Monster appears with strength $monsterStrength")
        fight(hero, monsterStrength)
    }

    2 -> println("Weapon is found: ${hero.name} upgrades from level ${hero.strength} to level ${++hero.strength}")

    else -> throw IllegalArgumentException("Unknown door effect $doorEffect")
}

fun knockToDoor(hero: Hero) {
    println("Knock knock")
    handleDoorEffect(hero, randomDoorEffect())
}

fun fight(hero: Hero, monsterStrength: Int) {
    when {
        hero.strength > monsterStrength ->
            println("${hero.name} wins! ${hero.name} levels up: from level ${hero.strength} to level ${++hero.strength}")

        else ->
            println("${hero.name} loses! ${hero.name} downgrades from level ${hero.strength} to level ${--hero.strength}")
    }
}

fun playGame(hero: Hero) {
    while (hero.strength in 1..9) {
        knockToDoor(hero)

        when (hero.strength) {
            0 -> println("${hero.name} dies. Game over")
            10 -> println("${hero.name} wins! Game over")
        }
    }
}

fun main() {
    val hero = Hero("Hero", randomHeroStrength())

    playGame(hero)
}
