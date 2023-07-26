package cz.gyarab.dino;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

class Dino {

    private final int height = 100;
    public final int width = 50;
    public int y = 0;

    public void render(GraphicsContext gc) {
        gc.setFill(Color.PURPLE);
        gc.fillRect(150, (400 - height) - y, width, height);
    }

    private final int jumpHeight = 100;
    private int jumpInterval = 1;
    private boolean fallDown = false;

    public boolean jump() {
        if (y >= jumpHeight) {
            fallDown = true;
        }
        if (fallDown) {
            jumpInterval *= -1;
            fallDown = false;
        }
        y += jumpInterval;
        if (y == 0) {
            jumpInterval *= -1;
            return true;
        }
        return false;
    }
}

class Cactus {

    public final int width = 20;
    public final int height = 40;
    public int x = 0;

    public void render(GraphicsContext gc) {
        x++;
        gc.setFill(Color.GREEN);
        gc.fillRect(640 - x, 400 - height, width, height);
    }
}

public class DinoGame extends Application {

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(640, 480);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Dino alexander = new Dino();

        Timeline tl = new Timeline(new KeyFrame(Duration.millis(3), e -> run(gc, alexander)));
        tl.setCycleCount(Timeline.INDEFINITE);

        canvas.setOnMouseClicked(e -> jumpFinished = false);

        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root);
        primaryStage.setTitle("Dino");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        tl.play();
    }

    private void renderGround(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 400, 640, 80);
    }

    private boolean jumpFinished = true;
    private int frames = 0;
    private Cactus cactus = new Cactus();
    private boolean alive = true;

    private void run(GraphicsContext gc, Dino dino) {
        if (alive) {
            frames++;
            if (frames >= 640 + cactus.width) {
                frames = 0;
                cactus = new Cactus();
            }

            gc.setFill(Color.WHITE);
            gc.fillRect(0, 0, 640, 480);
            
            renderGround(gc);
            cactus.render(gc);
            dino.render(gc);

            if ((640 - cactus.x + cactus.width) < (150 + dino.width) && (640 - cactus.x) > 150 && dino.y < cactus.height) {
                alive = false;
                frames = 0;
            }

            if (!jumpFinished) {
                jumpFinished = dino.jump();
            }
        } else {
            gc.setFill(Color.RED);
            gc.fillRect(0, 0, 640, 480);
            
            frames++;
            if (frames >= 666) System.exit(0);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
