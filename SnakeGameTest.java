import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.event.KeyEvent;
import java.awt.*;

public class SnakeGameTest {

    private SnakeGame snakeGame;

    @BeforeEach
    public void setUp() {
        snakeGame = new SnakeGame();
    }

    @Test
    public void testInitialization() {
        Assertions.assertFalse(snakeGame.isRunning());
        Assertions.assertFalse(snakeGame.isGameOver());
        Assertions.assertEquals(1, snakeGame.getSnake().size());
    }

    @Test
    public void testFoodSpawn() {
        snakeGame.initializeGame();
        Component food = snakeGame.getFood();
        Assertions.assertTrue(food.getX() >= 0 && food.getX() < 20);
        Assertions.assertTrue(food.getY() >= 0 && food.getY() < 20);
    }

    @Test
    public void testValidMovement() {
        snakeGame.initializeGame();
        int initialSize = snakeGame.getSnake().size();

        snakeGame.setDirection(KeyEvent.VK_RIGHT);
        snakeGame.move();
        Assertions.assertEquals(initialSize, snakeGame.getSnake().size());

        snakeGame.setDirection(KeyEvent.VK_DOWN);
        snakeGame.move();
        Assertions.assertEquals(initialSize, snakeGame.getSnake().size());

        snakeGame.setDirection(KeyEvent.VK_LEFT);
        snakeGame.move();
        Assertions.assertEquals(initialSize, snakeGame.getSnake().size());

        snakeGame.setDirection(KeyEvent.VK_UP);
        snakeGame.move();
        Assertions.assertEquals(initialSize, snakeGame.getSnake().size());
    }

    @Test
    public void testCollisionWithWall() {
        snakeGame.initializeGame();
        snakeGame.setDirection(KeyEvent.VK_UP);
        for (int i = 0; i < 6; i++) {
            snakeGame.move();
        }
        Assertions.assertTrue(snakeGame.isGameOver());

        snakeGame.initializeGame();
        snakeGame.setDirection(KeyEvent.VK_LEFT);
        for (int i = 0; i < 6; i++) {
            snakeGame.move();
        }
        Assertions.assertTrue(snakeGame.isGameOver());

        snakeGame.initializeGame();
        snakeGame.setDirection(KeyEvent.VK_DOWN);
        for (int i = 0; i < 21; i++) {
            snakeGame.move();
        }
        Assertions.assertTrue(snakeGame.isGameOver());

        snakeGame.initializeGame();
        snakeGame.setDirection(KeyEvent.VK_RIGHT);
        for (int i = 0; i < 21; i++) {
            snakeGame.move();
        }
        Assertions.assertTrue(snakeGame.isGameOver());
    }
}
