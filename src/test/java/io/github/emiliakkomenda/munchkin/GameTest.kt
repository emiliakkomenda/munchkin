package io.github.emiliakkomenda.munchkin

import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class GameTest {

    private val outContent = ByteArrayOutputStream()
    private val originalOut = System.out

    @Before
    fun setUp() {
        System.setOut(PrintStream(outContent))
    }

    @After
    fun tearDown() {
        System.setOut(originalOut)
    }

    @Test
    fun `should hero strength increment when hero strength is greater than monster strength`() {
        // given
        val hero = Hero("name", 5)
        val monsterStrength = 4

        // when
        fight(hero, monsterStrength)

        // then
        assertThat(hero.strength).isEqualTo(6)
    }

    @Test
    fun `should hero strength decrement when hero strength is lower than monster strength`() {
        // given
        val hero = Hero("name", 5)
        val monsterStrength = 6

        // when
        fight(hero, monsterStrength)

        // then
        assertThat(hero.strength).isEqualTo(4)
    }

    @Test
    fun `should not change hero strength when hero strength is equal to monster strength`() {
        // given
        val strength = 5
        val hero = Hero("name", strength)
        val monsterStrength = hero.strength

        // when
        fight(hero, monsterStrength)

        // then
        assertThat(hero.strength).isEqualTo(strength)
    }

    @Test
    fun `should print the draw when hero strength is equal to monster strength`() {
        // given
        val hero = Hero("name", 5)
        val monsterStrength = hero.strength

        // when
        fight(hero, monsterStrength)

        // then
        assertThat(outContent.toString()).isEqualTo("It's a draw: ${hero.name} strength is ${hero.strength} plus ${hero.monsterCurse} monster curses and monster strength is $monsterStrength\n")
    }

    @Test
    fun `should print when knocking to door`() {
        // when
        knockToDoor()

        // then
        assertThat(outContent.toString()).isEqualTo("Knock knock\n")
    }

    @Test
    fun `should print monster strength when the door effect is 'MONSTER'`() {
        // given
        val hero = Hero("name", 5)

        // when
        handleDoorEffect(hero, DoorEffect.MONSTER)

        // then
        assertThat(outContent.toString()).contains("The Monster appears with strength")
    }

    @Test
    fun `should increment hero strength when the door effect is 'WEAPON'`() {
        // given
        val hero = Hero("name", 5)

        // when
        handleDoorEffect(hero, DoorEffect.WEAPON)

        // then
        assertThat(hero.strength).isEqualTo(6)
    }

    @Test
    fun `should decrement hero strength when the curse is 'ON_HERO'`() {
        // given
        val hero = Hero("name", 5)

        // when
        getCurse(hero, Curse.ON_HERO)

        // then
        assertThat(hero.strength).isEqualTo(4)
    }

    @Test
    fun `should increment monsterCurse when the curse is 'ON_MONSTER'`() {
        // given
        val hero = Hero("name", 5, 1)

        // when
        getCurse(hero, Curse.ON_MONSTER)

        // then
        assertThat(hero.monsterCurse).isEqualTo(2)
    }

    @Test
    fun `should end game with hero win when hero strength equals to 10`() {
        // given
        val hero = Hero("name", 10)

        // when
        playGame(hero)

        // then
        assertThat(outContent.toString()).isEqualTo("${hero.name} wins! Game over\n")
    }

    @Test
    fun `should end game with hero lose when hero strength equals to 0`() {
        // given
        val hero = Hero("name", 0)

        // when
        playGame(hero)

        // then
        assertThat(outContent.toString()).isEqualTo("${hero.name} dies. Game over\n")
    }
}
