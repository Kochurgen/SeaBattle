package com.example.vladimir.seabattle;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.vladimir.seabattle.controllers.ShootCallback;
import com.example.vladimir.seabattle.players.AIPlayer;
import com.example.vladimir.seabattle.players.HPlayer;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private final ShootCallback shootCallback;

    private final Random random = new Random();

    public ExampleInstrumentedTest(ShootCallback shootCallback) {
        this.shootCallback = shootCallback;
    }

    @Test
    public void useAppContext() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.vladimir.seabattle", appContext.getPackageName());
    }

    @Test
    public void testAIPlayer() throws Exception {
        AIPlayer aiplayer = new AIPlayer(shootCallback);
        assertEquals(aiplayer, aiplayer);
    }

    @Test
    public void testHPlayer() throws Exception {
        HPlayer hplayer = new HPlayer(shootCallback, userName);
        assertEquals(hplayer, hplayer);
    }

    @Test
    public void testHumanBoard() throws Exception {
        HPlayer hplayer = new HPlayer(shootCallback, userName);
        assertNotNull(hplayer.board);
    }

    @Test
    public void testAIBoard() throws Exception {
        AIPlayer aiplayer = new AIPlayer(shootCallback);
        assertNotNull(aiplayer.board);
    }

    @Test
    public void testCreateShips() throws Exception {
        HPlayer hplayer = new HPlayer(shootCallback, userName);
        AIPlayer aiplayer = new AIPlayer(shootCallback);
        assertTrue(aiplayer.board.getKilledShips() == hplayer.board.getKilledShips());
    }

    @Test
    public void testShotAIPlayer() throws Exception {
        int[] randomXY = getRandomXY();
        AIPlayer aiplayer = new AIPlayer(shootCallback);
        assertNotNull(aiplayer.shoot(randomXY[0], randomXY[1]));
    }

    @Test
    public void testShotHPlayer() throws Exception {
        int[] randomXY = getRandomXY();
        HPlayer hplayer = new HPlayer(shootCallback, userName);
        assertNotNull(hplayer.shoot(randomXY[0], randomXY[1]));
    }

    @Test
    public void testDoubleShot() throws Exception {
        int[] randomXY = getRandomXY();
        HPlayer hplayer = new HPlayer(shootCallback, userName);
        hplayer.shoot(randomXY[0], randomXY[1]);
        assertNull(hplayer.shoot(randomXY[0], randomXY[1]));
    }

    private int[] getRandomXY(){
        int[] randomXY = new int[2];
        randomXY[0] = random.nextInt(10);
        randomXY[1] = random.nextInt(10);
        return randomXY;
    }



}
