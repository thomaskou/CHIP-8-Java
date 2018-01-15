/*
 * This class is a hack for a problem that shouldn't even exist.
 * When breaking from the opcode switch statement in Game.cycle(),
 * the program counter variable (pc) resets to zero.
 * The pctemp variable was used to circumvent this. However, this
 * caused another problem: when returning from a subroutine (opcode
 * 00EE), pctemp would inexplicably change to a seemingly random
 * number, breaking the emulation.
 */
package chip8emu;

public class PCHack {
    
    private int pchack;
    private boolean hackFlag;
    
    public PCHack() {
        pchack = 0;
        hackFlag = false;
    }
    
    public boolean checkFlag() {
        return hackFlag;
    }
    
    public int get() {
        hackFlag = false;
        return pchack;
    }
    
    public void set(int x) {
        hackFlag = true;
        pchack = x;
    }
    
}
