import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class SnakeGame extends JPanel implements KeyListener, ActionListener {
    private ArrayList<Point> snake;
    private int direction;
    private boolean isRunning;
    private boolean isGameOver;
    private Point food;
    private Random random;

    /**
     * The SnakeGame function initializes the game by creating a new snake, setting the direction to right,
     * and setting isRunning and isGameOver to false. It also creates a new Point for food at (0, 0) and
     * sets up a random number generator. The function then sets the preferred size of the window to 400x400 pixels
     * with black background color. It makes sure that it can be focused on by adding key listeners so that we can use
     * arrow keys later on in our code. Finally it starts up a timer which will call an actionPerformed method every 100 milliseconds (10 times per second
     *
     *
     * @return A snakegame object
     *
     */
    public SnakeGame() {
        snake = new ArrayList<>();
        direction = KeyEvent.VK_RIGHT;
        isRunning = false;
        isGameOver = false;
        food = new Point(0, 0);
        random = new Random();

        setPreferredSize(new Dimension(400, 400));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(this);

        Timer timer = new Timer(100, this);
        timer.start();

        initializeGame();
    }


    /**
     * The initializeGame function initializes the game by clearing the snake, adding a new point to it, setting isGameOver to false and spawning food.
     *
     */
    public void initializeGame() {
        snake.clear();
        snake.add(new Point(5, 5));
        isGameOver = false;
        spawnFood();
    }


    /**
     * The spawnFood function is used to randomly generate a new food point on the board.
     * It does this by generating random x and y coordinates, then checking if the snake contains that point.
     * If it does, it will continue to generate new points until one is found that isn't in the snake's body.
     *
     * @return A point object
     *
     */
    private void spawnFood() {
        int x, y;
        do {
            x = random.nextInt(20);
            y = random.nextInt(20);
        } while (snake.contains(new Point(x, y)));
        food = new Point(x, y);
    }

    /**
     * The move function is responsible for moving the snake.
     * It does this by adding a new head to the front of the snake, and removing
     * its tail from the back. The direction of movement is determined by which key
     * was pressed last (up, down, left or right). If it moves into food then it
     * grows in size instead of losing its tail. Finally it checks if there has been
     * a collision with itself or with any walls that may have been added to stop
     * players from cheating!
     *
     *
     * @return Void, so it doesn't return anything
     */
    public void move() {
        Point head = snake.get(0);
        Point newHead;

        switch (direction) {
            case KeyEvent.VK_UP:
                newHead = new Point(head.x, head.y - 1);
                break;
            case KeyEvent.VK_DOWN:
                newHead = new Point(head.x, head.y + 1);
                break;
            case KeyEvent.VK_LEFT:
                newHead = new Point(head.x - 1, head.y);
                break;
            case KeyEvent.VK_RIGHT:
                newHead = new Point(head.x + 1, head.y);
                break;
            default:
                return;
        }

        if (newHead.equals(food)) {
            snake.add(0, food);
            spawnFood();
        } else {
            snake.add(0, newHead);
            snake.remove(snake.size() - 1);
        }

        checkCollision(newHead);
    }


    /**
     * The checkCollision function checks to see if the snake has collided with itself or a wall.
     * If it has, then isRunning and isGameOver are set to false.

     *
     * @param Point head Check if the head of the snake is out of bounds or colliding with its body
     *
     * @return A boolean value
     */
    private void checkCollision(Point head) {
        if (head.x < 0 || head.x >= 20 || head.y < 0 || head.y >= 20) {
            isRunning = false;
            isGameOver = true;
        }

        for (int i = 1; i < snake.size(); i++) {
            if (head.equals(snake.get(i))) {
                isRunning = false;
                isGameOver = true;
                break;
            }
        }
    }

    /**
     * The paintComponent function is a function that draws the snake and food on the screen.
     * It also displays &quot;Game Over&quot; when the game ends.

     *
     * @param Graphics g Draw on the jpanel
     *
     * @return Nothing
     *
     */
    /**
     * The paintComponent function is a function that draws the snake and food on the screen.
     * It also displays &quot;Game Over&quot; when the game ends.

     *
     * @param Graphics g Draw the snake and food
     *
     * @return Void
     *
     * @docauthor Trelent
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.green);
        for (Point point : snake) {
            g.fillOval(point.x * 20, point.y * 20, 20, 20);
        }

        g.setColor(Color.red);
        g.fillOval(food.x * 20, food.y * 20, 20, 20);

        if (isGameOver) {
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.PLAIN, 36));
            g.drawString("Game Over", 130, 200);
        }
    }

    /**
     * The keyTyped function is used to detect when a key has been typed.
     *
     *
     * @param KeyEvent e Detect which key is pressed
     *
     * @return A character that is typed
     *
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * The keyPressed function is a function that allows the user to control the snake's direction.
     * The keyPressed function takes in an event, and if it is one of the arrow keys, then it sets
     * direction equal to that key. This way, when we call moveSnake() in our run() method, we can use
     * this variable to determine which way the snake should go next. We also check if gameOver is true;
     * if so then we don't want any more input from the user because they have already lost!

     *
     * @param KeyEvent e Get the key code of the key that was pressed
     *
     * @return The key that was pressed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if ((key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN ||
                key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) && !isGameOver) {
            direction = key;
        }
    }

    /**
     * The keyReleased function is a function that is called when the user releases a key.
     *
     *
     * @param KeyEvent e Determine which key is being pressed
     *
     * @return Nothing
     *
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }

    /**
     * The actionPerformed function is a function that is called when an action event occurs.
     * In this case, the action event occurs when the timer ticks. When it does, we move and repaint
     * our game objects to make them appear as if they are moving on their own.

     *
     * @param ActionEvent e Determine what action was performed
     *
     * @return Void
     *
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isRunning) {
            move();
            repaint();
        }
    }


    /**
     * The startGame function sets the isRunning variable to true, which allows the game loop to run.

     *
     *
     * @return Nothing
     *
     */
    public void startGame() {
        isRunning = true;
    }

    /**
     * The isRunning function returns a boolean value that indicates whether the thread is running.
     *
     *
     *
     * @return A boolean value
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * The isGameOver function checks to see if the game is over.
     *
     *
     *
     * @return A boolean value
     *
     */
    public boolean isGameOver() {
        return isGameOver;
    }

    /**
     * The getSnake function returns a collection of objects that represent the snake.
     *
     *
     *
     * @return A new arraylist that contains the snake
     */
    public Collection<Object> getSnake() {
        return new ArrayList<Object>(snake);
    }


    /**
     * The getFood function returns a Component object that is the food.
     *
     *
     *
     * @return A component that is a circle
     *
     */
    public Component getFood() {
        return new Component() {
            @Override
            public void paint(Graphics g) {
                g.fillOval(food.x * 20, food.y * 20, 20, 20);
            }
        };
    }


    /**
     * The setDirection function sets the direction of the snake.
     *
     *
     * @param int vkUp Determine the direction of the snake
     *
     * @return Nothing
     *
     */
    public void setDirection(int vkUp) {
        if (!isGameOver) {
            direction = vkUp;
        }
    }

    /**
     * The main function creates a JFrame and adds the SnakeGame to it.
     * It then sets the default close operation, makes it visible, and starts the game.

     *
     * @param String[] args Pass command line arguments to the program
     *
     * @return Nothing
     *
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        SnakeGame game = new SnakeGame();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        game.startGame();
    }
}
