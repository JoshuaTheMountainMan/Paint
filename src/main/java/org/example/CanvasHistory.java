package org.example;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.WritableImage;

import java.util.Stack;

public class CanvasHistory {
    private Node target;
    private Stack<WritableImage> undoStack = new Stack<>();
    private Stack<WritableImage> redoStack = new Stack<>();



    public CanvasHistory(Node target){
        this.target = target;
        saveState();
    }

    public void saveState(){
        Bounds b = target.getLayoutBounds();
        int w = Math.max(1, (int)Math.ceil(b.getWidth()));
        int h = Math.max(1, (int)Math.ceil(b.getHeight()));
        WritableImage snap = new WritableImage(w, h);
        target.snapshot(null, snap);
        undoStack.push(snap);
        redoStack.clear();
    }

    public void undo() {
        if (undoStack.size() > 1) {
            WritableImage last = undoStack.pop();
            redoStack.push(last);
            redraw(undoStack.peek());
        }
    }

    public void redo(){
        if (!redoStack.isEmpty()) {
            WritableImage state = redoStack.pop();
            undoStack.push(state);
            redraw(state);
        }
    }

    public void redraw(WritableImage image){
       /* gc.clearRect(0,0,canvas.getWidth(), canvas.getHeight());
        gc.drawImage(image,0,0);*/
    }

}

