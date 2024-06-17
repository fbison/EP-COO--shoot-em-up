package graphics;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.time.Instant;

public class KeyAdapterGame extends KeyAdapter {
    // Attributes
    private int[] codes;
    private boolean[] keyStates = null;
    private Instant[] releaseTimeStamps = null;

    // Constructor
    public KeyAdapterGame() {
        // Initialize codes array with KeyEvent constants
        codes = new int[]{
                KeyEvent.VK_UP,
                KeyEvent.VK_DOWN,
                KeyEvent.VK_LEFT,
                KeyEvent.VK_RIGHT,
                KeyEvent.VK_CONTROL,
                KeyEvent.VK_ESCAPE
        };
        keyStates = new boolean[codes.length];
        releaseTimeStamps = new Instant[codes.length];
    }

    // Methods
    public int getIndexFromKeyCode(int keyCode) {
        for (int i = 0; i < codes.length; i++) {

            if (codes[i] == keyCode) return i;
        }

        return -1;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println("KeyPressed " + e.getWhen() + " " + System.currentTimeMillis());

        int index = getIndexFromKeyCode(e.getKeyCode());
        if (index >= 0) {
            keyStates[index] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e){
        //System.out.println("KeyReleased " + e.getWhen() + " " + System.currentTimeMillis());

        int index = getIndexFromKeyCode(e.getKeyCode());
        if(index >= 0){
            keyStates[index] = false;
            releaseTimeStamps[index] = Instant.now();
        }
    }

    public boolean isKeyPressed(int index) {
        boolean keyState = keyStates[index];
        Instant keyReleaseTime = releaseTimeStamps[index];

        if(!keyState){
            return Instant.now().minusMillis(5).isBefore(keyReleaseTime);
        }
        return true;
    }

    public void debugKeys() {
        System.out.print("Key states = {");

        for(int i = 0; i < codes.length; i++){

            System.out.print(" " + keyStates[i] + (i < (codes.length - 1) ? "," : ""));
        }
        System.out.println(" }");
    }

    public void verifyEnter() {
        // Method implementation
    }
}