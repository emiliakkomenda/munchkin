package io.github.emiliakkomenda.munchkin

data class Hero(
    val name: String,
    var strength: Int,
    var monsterCurse: Int = 0
)

enum class DoorEffect {
    MONSTER, WEAPON, CURSE
}

enum class Curse {
    ON_HERO, ON_MONSTER
}

fun randomMonsterStrength() = (1..10).random()
fun randomHeroStrength() = (1..5).random()
fun randomDoorEffect() = DoorEffect.values().random()
fun randomCurseEffect() = Curse.values().random()

fun handleDoorEffect(hero: Hero, doorEffect: DoorEffect) = when (doorEffect) {
    DoorEffect.MONSTER -> {
        val monsterStrength = randomMonsterStrength()
        println("The Monster appears with strength $monsterStrength")
        fight(hero, monsterStrength)
    }

    DoorEffect.WEAPON -> println("Weapon is found: ${hero.name} upgrades from level ${hero.strength} to level ${++hero.strength}")

    DoorEffect.CURSE -> getCurse(hero, randomCurseEffect())
}

fun getCurse(hero: Hero, curseEffect: Curse) = when (curseEffect) {
    Curse.ON_HERO -> println("${hero.name} is cursed and loses a level: from level ${hero.strength} to level ${--hero.strength}")
    Curse.ON_MONSTER -> println("${hero.name} finds monster curse! An additional point of attack against another monster: from ${hero.monsterCurse} points to ${++hero.monsterCurse} points")
}

fun knockToDoor() {
    println("Knock knock")
}

fun fight(hero: Hero, monsterStrength: Int) = when {
    hero.strength > monsterStrength -> println("${hero.name} wins! ${hero.name} levels up: from level ${hero.strength} to level ${++hero.strength}")
    else -> heroTotalPointsFight(hero, monsterStrength)
}

fun heroTotalPointsFight(hero: Hero, monsterStrength: Int) {
    val totalHeroStrength = hero.strength + hero.monsterCurse

    when {
        totalHeroStrength > monsterStrength ->
            println("${hero.name} wins! ${hero.name} levels up: from level ${hero.strength} to level ${++hero.strength}")

        totalHeroStrength < monsterStrength ->
            println("${hero.name} loses! ${hero.name} downgrades from level ${hero.strength} to level ${--hero.strength}")

        totalHeroStrength == monsterStrength ->
            println("It's a draw: ${hero.name} strength is ${hero.strength} plus ${hero.monsterCurse} monster curses and monster strength is $monsterStrength")
    }

    hero.monsterCurse = 0
}

fun playGame(hero: Hero) {
    while (true) {
        when (hero.strength) {
            0 -> {
                println("${hero.name} dies. Game over")
                return
            }
            10 -> {
                println("${hero.name} wins! Game over")
                return
            }

            in 1..9 -> {
                knockToDoor()
                handleDoorEffect(hero, randomDoorEffect())
            }

            else -> throw IllegalStateException()
        }
    }
}

fun main() {
    val hero = Hero("Hero", randomHeroStrength())

    println("${hero.name} starts with the strength: ${hero.strength}")
    playGame(hero)
}
