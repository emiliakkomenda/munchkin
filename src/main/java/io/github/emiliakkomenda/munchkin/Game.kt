package io.github.emiliakkomenda.munchkin

data class Hero(
    val name: String,
    var strength: Int,
    var monsterCurse: Int = 0
)

fun randomMonsterStrength() = (1..10).random()
fun randomHeroStrength() = (1..5).random()
fun randomDoorEffect() = (1..3).random()
fun randomCurseEffect() = (1..2).random()

fun handleDoorEffect(hero: Hero, doorEffect: Int) = when (doorEffect) {
    1 -> {
        val monsterStrength = randomMonsterStrength()
        println("The Monster appears with strength $monsterStrength")
        fight(hero, monsterStrength)
    }

    2 -> println("Weapon is found: ${hero.name} upgrades from level ${hero.strength} to level ${++hero.strength}")
    3 -> getCurse(hero, randomCurseEffect())

    else -> throw IllegalArgumentException("Unknown door effect $doorEffect")
}

fun getCurse(hero: Hero, curseEffect: Int) = when (curseEffect) {
    1 -> println("${hero.name} is cursed and loses a level: from level ${hero.strength} to level ${--hero.strength}")
    2 -> println("${hero.name} finds monster curse! An additional point of attack against another monster: from ${hero.monsterCurse} points to ${++hero.monsterCurse} points")

    else -> throw IllegalArgumentException("Unknown curse effect $curseEffect")
}

fun knockToDoor(hero: Hero) {
    println("Knock knock")
    handleDoorEffect(hero, randomDoorEffect())
}

fun fight(hero: Hero, monsterStrength: Int) {
    when {
        hero.strength > monsterStrength -> println("${hero.name} wins! ${hero.name} levels up: from level ${hero.strength} to level ${++hero.strength}")
        hero.strength < monsterStrength -> heroTotalPointsFight(hero, monsterStrength)
        hero.strength == monsterStrength -> heroTotalPointsFight(hero, monsterStrength)

        else -> throw IllegalArgumentException("Unknown value: ${hero.strength} or $monsterStrength is unsuited")
    }
}

fun heroTotalPointsFight(hero: Hero, monsterStrength: Int) {
    val totalHeroStrength = hero.strength + hero.monsterCurse

    when {
        totalHeroStrength > monsterStrength -> {
            println("${hero.name} wins! ${hero.name} levels up: from level ${hero.strength} to level ${++hero.strength}")
            hero.monsterCurse = 0
        }
        totalHeroStrength < monsterStrength -> {
            println("${hero.name} loses! ${hero.name} downgrades from level ${hero.strength} to level ${--hero.strength}")
            hero.monsterCurse = 0
        }
        totalHeroStrength == monsterStrength -> {
            println("It's a draw: ${hero.name} strength is ${hero.strength} plus ${hero.monsterCurse} monster curses and monster strength is $monsterStrength")
            hero.monsterCurse = 0
        }

        else -> throw IllegalArgumentException("Unknown value: ${hero.strength}, $monsterStrength or ${hero.monsterCurse} is unsuited")
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
