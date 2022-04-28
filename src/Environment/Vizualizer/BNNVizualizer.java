package Environment.Vizualizer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public abstract class BNNVizualizer extends Canvas implements Runnable {
    private Thread thread;
    private JFrame jFrame;
    private String title;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private static boolean running = false;

    public BNNVizualizer(String title) {
        jFrame = new JFrame();
        Dimension size = new Dimension(WIDTH, HEIGHT);
        setPreferredSize(size);
        this.title = title;

        jFrame.setTitle(title);
        jFrame.add(this);
        jFrame.pack();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setVisible(true);

        start();
    }

    public synchronized void start() {
        running = true;
        thread = new Thread(this, "Display");
        thread.start();
    }

    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60;
        double delta = 0;
        int frames = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                update();
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                jFrame.setTitle(title + " | " + frames + " fps");
                frames = 0;
            }
        }

        stop();
    }

    private void render() {
        BufferStrategy bufferStrategy = this.getBufferStrategy();
        if (bufferStrategy == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics graphics = bufferStrategy.getDrawGraphics();
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, WIDTH * 2, HEIGHT * 2);

        graphics.setColor(Color.WHITE);
        graphics = toRender(graphics);

        graphics.dispose();
        bufferStrategy.show();
    }

    public abstract Graphics toRender(Graphics graphics);

    public abstract void update();
}
