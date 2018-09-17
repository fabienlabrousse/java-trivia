package com.adaptionsoft.games.trivia;

import com.adaptionsoft.games.uglytrivia.Game;
import com.adaptionsoft.games.uglytrivia.GameV2;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class GameV2Should {

    public static final int RANDOM_SEED = 123456789;

    @Test
	public void run_as_same_as_game_legacy() {
        Game aGame = new Game();

        aGame.add("Chet");
        aGame.add("Pat");
        aGame.add("Sue");

        Random rand = new Random(RANDOM_SEED);

        boolean notAWinner;

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (PrintStream printStream = new PrintStream(byteArrayOutputStream, true, "UTF-8")) {
            System.setOut(printStream);
            do {
                aGame.roll(rand.nextInt(5) + 1);

                if (rand.nextInt(9) == 7) {
                    notAWinner = aGame.wrongAnswer();
                } else {
                    notAWinner = aGame.wasCorrectlyAnswered();
                }

            } while (notAWinner);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        List<String> oldData = Arrays.asList(new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8).split("\r\n"));


        GameV2 gameV2 = new GameV2();

        gameV2.add("Chet");
        gameV2.add("Pat");
        gameV2.add("Sue");

        rand = new Random(RANDOM_SEED);

        byteArrayOutputStream = new ByteArrayOutputStream();
        try (PrintStream printStream = new PrintStream(byteArrayOutputStream, true, "UTF-8")) {
            System.setOut(printStream);
            do {
                gameV2.roll(rand.nextInt(5) + 1);

                if (rand.nextInt(9) == 7) {
                    notAWinner = gameV2.wrongAnswer();
                } else {
                    notAWinner = gameV2.wasCorrectlyAnswered();
                }

            } while (notAWinner);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        List<String> newData = Arrays.asList(new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8).split("\r\n"));

        assertThat(oldData).isEqualTo(newData);
    }
}
