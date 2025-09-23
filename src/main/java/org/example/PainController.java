package org.example;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.canvas.Canvas;
import javax.imageio.ImageIO;
import java.io.File;

import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.scene.layout.StackPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

    public class PainController {
        public Pane drawingLayer;
        public Pane backgroundLayer;
        @FXML
        BorderPane borderPane;
        @FXML
        private Canvas canvas; // sigue siendo el draw canvas (brush)
        @FXML public StackPane workspaceStack;
        @FXML
        private ImageView imageView;
        @FXML private Pane imageLayer;
        @FXML
        private Pane shapesLayer;
        @FXML
        private Pane overlayPane;


        private Stage stage;
        private AlertWindow windowAlert = new AlertWindow();
        @FXML
        public ColorPicker colorPicker;
        private FileMenu fileMenuController;
        @FXML
        private TextField brushSize;
        private CanvasControl canvasControl;
        @FXML
        private TextField imageWidth;
        @FXML
        private TextField imageHeight;
        private Tools currentTool;
        private SizeableTool currentSizeableTool;
        @FXML
        private ScrollPane scrollPane;
        @FXML
        private CheckBox dashBox;

        private Image loadedImage;

        private CanvasHistory history;
        private LayerManager layerManager;
        private SelectionTool selectionTool;
        private FileMenu fileMenu;



        @FXML
        private void initialize() {
            canvasControl = new CanvasControl(canvas);
            setSizeableTool(new BrushTool());
            layerManager = new LayerManager(imageView, imageLayer, shapesLayer, overlayPane, canvas);

            history = new CanvasHistory(workspaceStack);
            selectionTool = new SelectionTool();

            drawingLayer.toFront();
            canvas.setStyle("-fx-background-color: transparent;");

            canvas.setMouseTransparent(false);
            imageView.setMouseTransparent(true);



        }


        /**
         * This method sets, intializes and installs a Tool
         * @param tool A tool that doesn't have a size
         */
        private void setTool(Tools tool){
            clearMouseEvents(canvas);
            currentTool = tool;
            tool.install(canvas, canvasControl.getGraphCon(), colorPicker);

            canvas.setOnMouseReleased(e -> history.saveState());
        }

        /**
         * This method sets, initializes and installs a SizeableTool
         * @param sizeableTool Unlike a tool, this object does have a size
         */
        private void setSizeableTool(SizeableTool sizeableTool){
            clearMouseEvents(canvas);
            currentSizeableTool = sizeableTool;
            currentSizeableTool.install(canvas, canvasControl.getGraphCon(), colorPicker);
            onBrushSize();

            canvas.setOnMouseReleased(e -> history.saveState());


        }


        /**
         * This method sets the stage and initializes the controller
         * @param stage It's the object containing the window(app)
         */
        public void setStage(Stage stage) {
            this.stage = stage;
            stage.setMaximized(true);

            // Inicializar el controlador

            fileMenuController = new FileMenu(stage, canvas);
            stage.setOnCloseRequest(windowEvent -> {
                windowEvent.consume(); windowAlert.handleExit(fileMenuController);});

        }


        public static void clearMouseEvents(Canvas canvas){
            canvas.setOnMouseDragged(null);
            canvas.setOnMouseClicked(null);
            canvas.setOnMousePressed(null);
            canvas.setOnMouseEntered(null);
            canvas.setOnMouseExited(null);
            canvas.setOnMouseMoved(null);
            canvas.setOnMouseReleased(null);
        }


        public void isDashChecked(){
            double size = Double.parseDouble(brushSize.getText());
            if (dashBox.isSelected()){
                canvasControl.getGraphCon().setLineDashes(10 * size,5 * size);
            } else
                canvasControl.getGraphCon().setLineDashes(0);
        }



        // Connecting menu buttons to the ones in controller

        public void onSave() { fileMenuController.onSave(); }
        public void onSaveAs() { fileMenuController.onSaveAs(); }

        public void onLoadImage() {
            Image img = fileMenuController.onLoadImage();
            if (img != null) {
                loadedImage = img;
                layerManager.setMainImage(img);

                imageView.setMouseTransparent(true);
                canvas.setMouseTransparent(false);

                drawingLayer.toFront();
                history.saveState();

            }
        }

        public void onSelectionTool() {
            selectionTool.install(overlayPane, imageView, layerManager, history);
        }



        public void onExit() { javafx.application.Platform.exit(); }

        public void onHelp(){ windowAlert.showHelpMenu();}
        public void onAbout(){ windowAlert.showAboutMenu();}

        public void handleExit(FileMenu fileMenuController){windowAlert.handleExit(fileMenuController);}



        public void onBrushSize(){currentSizeableTool.setSize(Double.parseDouble(brushSize.getText())); isDashChecked();}

        public void onBrush(){
            BrushTool brushTool = new BrushTool();
            brushTool.install(canvas, canvas.getGraphicsContext2D(), colorPicker);
            imageView.setMouseTransparent(true);
            canvas.setMouseTransparent(false);
            drawingLayer.toFront();
        }


        public void onEraser(){setSizeableTool(new EraserTool());}
        public void onDropper(){setTool(new DropperTool());}
        public void onStraightLine(){setSizeableTool(new LineTool());}
        public void onRectangle(){setSizeableTool(new RectangleTool(Shapes.RECTANGLE));}
        public void onEllipse(){setSizeableTool(new RectangleTool(Shapes.ELLIPSE));}
        public void onSquare(){setSizeableTool(new RectangleTool(Shapes.SQUARE));}
        public void onCircle(){setSizeableTool(new RectangleTool(Shapes.CIRCLE));}
        public void onTriangle(){setSizeableTool(new TriangleTool());}
        public void onStar(){setSizeableTool(new StarTool());}
        //public void onMoveImage(){if (loadedImage != null) {setTool(new MoveImageTool(loadedImage));}}


        public void onUndo(){history.undo();}
        public void onRedo(){history.redo();}


        public void onMoveImage() {
            imageView.toFront();
            imageView.setMouseTransparent(false);
            canvas.setMouseTransparent(true);
            layerManager.makeNodeDraggable(layerManager.getImageView(), history);
        }


    }
