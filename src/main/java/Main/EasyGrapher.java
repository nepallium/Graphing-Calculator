/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package Main;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class EasyGrapher extends Application {
    
    private static final int canvasWidth = 800;
    private static final int canvasHeight = 800;
    
    private static final int xScale = 40;
    private static final int yScale = 40;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Hello world!!");
        
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        
        VBox inputs = new VBox();
        inputs.setStyle("-fx-border-color: red; -fx-border-width: 2;");
        
        VBox graphAndTitle = new VBox();
        graphAndTitle.setStyle("-fx-border-color: blue; -fx-border-width: 2;");
        
        Canvas canvas = new Canvas(800, 800);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        StackPane canvasContainer = new StackPane(canvas);
        canvasContainer.setStyle("-fx-border-color: black; -fx-border-width: 2;");
        
        graphAndTitle.getChildren().add(canvasContainer);
        
        drawFunction(gc);
        drawAxes(gc);
        drawAxeIncrements(gc);
        
        root.getChildren().addAll(inputs, graphAndTitle);
        Scene scene = new Scene(root, 1200, 800);
        
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    
    public void drawAxes(GraphicsContext gc) {
        gc.setLineWidth(1);
        gc.setStroke(Color.BLACK);
        
        int originX = canvasWidth / 2;
        int originY = canvasHeight / 2;
        
        gc.strokeLine(0, originY, canvasWidth, originY);
        gc.strokeLine(originX, 0, originX, canvasHeight);
    }
    
    public void drawAxeIncrements(GraphicsContext gc) {
        gc.setLineWidth(1);
        gc.setStroke(Color.BLACK);
        
        int originX = canvasWidth / 2;
        int originY = canvasHeight / 2;
        
        gc.fillText("0", originX - 10, originY + 15);
        
        for (int h = 0; h < canvasWidth / 2; h += xScale) {
            if (h != 0) {
                gc.fillText("-" + (h / xScale), originX - h - 10, originY + 20);
                gc.fillText("" + (h / xScale), originX + h - 10, originY + 20);
                gc.fillText("-" + (h / xScale), originX - 20, originY + h + 10);
                gc.fillText("" + (h / xScale), originX - 20, originY - h + 10);
            }
            
            gc.strokeLine(originX - h, originY - 5, originX - h, originY + 5);
            gc.strokeLine(originX + h, originY - 5, originX + h, originY + 5);
            gc.strokeLine(originX - 5, originY + h, originX + 5, originY + h);
            gc.strokeLine(originX - 5, originY - h, originX + 5, originY - h);
        }
    }
    
    public void drawFunction(GraphicsContext gc) {
        gc.setLineWidth(2);
        gc.setStroke(Color.BLUE);
        
        int originX = canvasWidth / 2;
        int originY = canvasHeight / 2;
        
        for (double x = -originX; x < canvasWidth - originX; x++) {
            double mathX1 = x / xScale;
            double mathX2 = (x + 1) / xScale;

            double mathY1 = Math.pow(mathX1, 0.5);
            double mathY2 = Math.pow(mathX2, 0.5);

            double canvasX1 = originX + x;
            double canvasY1 = originY - mathY1 * yScale;
            double canvasX2 = originX + x + 1;
            double canvasY2 = originX - mathY2 * yScale;
            
            gc.strokeLine(canvasX1, canvasY1, canvasX2, canvasY2);
        }
    }
}