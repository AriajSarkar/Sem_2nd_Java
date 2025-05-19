interface Shape2 {
    void draw();
}

interface Colors {
    void fill();
}

interface Drawable extends Shape2, Colors {
}

class Graphic implements Drawable {
    @Override
    public void draw() {
        System.out.println("Drawing shape");
    }
    
    @Override
    public void fill() {
        System.out.println("Filling with color");
    }
}

public class Q13_DrawableTest {
    public static void main(String[] args) {
        Drawable graphic = new Graphic();
        graphic.draw();
        graphic.fill();
    }
}
