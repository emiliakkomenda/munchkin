package io.github.emiliakkomenda.munchkin

data class Hero(
    val heroName: String,
    var heroStrength: Int
)

val randomMonsterStrength: Int get() = (1..10).random()
val randomHeroStrength: Int get() = (1..5).random()
val randomDoorEffect: Int get() = (1..2).random()

val hero = Hero("Hero", randomHeroStrength)

fun doorEffect(number: Int) {
    if (number == 1) {
        val monsterStrength = randomMonsterStrength
        println("Monster appeared with strength: $monsterStrength")
        fight(monsterStrength)
    } else {
        println("Weapon found: ${hero.heroName} update from level ${hero.heroStrength} to level ${++hero.heroStrength}")
    }
}

fun doorKnocking() {
    println("Knock knock")
    doorEffect(randomDoorEffect)
}

fun fight(monsterStrength: Int) {
    if (hero.heroStrength > monsterStrength) {
        println("${hero.heroName} win! ${hero.heroName} level up: from level ${hero.heroStrength} to level ${++hero.heroStrength}")
    } else {
        println("${hero.heroName} lose from level ${hero.heroStrength} to level ${--hero.heroStrength}")
    }
}

fun main() {
    while (hero.heroStrength <= 10) {
        doorKnocking()

        if (hero.heroStrength == 10) {
            println("You win")
            break
        }

        if (hero.heroStrength == 0) {
            println("${hero.heroName} died. Game over")
            break
        }
    }
}
