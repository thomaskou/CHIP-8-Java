package chip8emu;

public class Game {
    
    //variables
    public String opcode;                  //stores the current opcode as String
    public int I;                          //address register
    public int pc;                         //program counter
    public boolean drawFlag;               //draw only when flag is true

    //classes
    public Memory memory = new Memory();   //byte, 0-4095
    public Registers V = new Registers();  //byte, 0-15
    public Stack stack = new Stack();      //byte, 0-15
    public Timers timers = new Timers();   //two ints
    public Display gfx = new Display();    //byte, 64x32
    public Input input = new Input();      //byte, 0-15
    
    //temporary
    private int pctemp;
    
    public Game(String game) {
        
        try {
            byte[] data = new Reader().romToByteArray(game);
            for (int k = 0x00; k < data.length; k++) {
                memory.set(k + 0x200, data[k]);
            }
        } catch (Exception e) {
            System.out.println("File not found.");
            for (;;) {}
        }
        
        pc = 0x200;
        opcode = "0000";
        I = 0;
        stack.setP( (short)0x0000 );
        
    }
    
    //methods
    
    public void cycle() {
        
        //get opcode (opcodes are 2 bytes; must be concantenated)
        String mem1 = memory.getHex( (short)(pc) );
        String mem2 = memory.getHex( (short)(pc + 1) );
        opcode = mem1 + mem2;
        
        //print before
        System.out.println("opcode: " + opcode);
        //System.out.println("I: " + I);
        //System.out.println("pc: " + pc);
        
        //decode opcode
        // <editor-fold defaultstate="collapsed" desc="switch statements">
        switch(opcode.substring(0,1)) {
            case "0" : switch(opcode.substring(3)) {
                case "0" : opDisplayClear(); break;
                case "E" : opReturn(); break;
            }
            case "1" : opGoto(); break;
            case "2" : opCall(); break;
            case "3" : opCondTrue(); break;
            case "4" : opCondFalse(); break;
            case "5" : opCondRegTrue(); break;
            case "6" : opConstSet(); break;
            case "7" : opConstAdd(); break;
            case "8" : switch(opcode.substring(3)) {
                case "0" : opAssign(); break;
                case "1" : opBitOr(); break;
                case "2" : opBitAnd(); break;
                case "3" : opBitXor(); break;
                case "4" : opMathAdd(); break;
                case "5" : opMathSubtract(); break;
                case "6" : opBitRight(); break;
                case "7" : opMathDiff(); break;
                case "E" : opBitLeft(); break;
            }
            case "9" : opCondRegFalse(); break;
            case "A" : opSetI(); break;
            case "B" : opGotoAdd(); break;
            case "C" : opRand(); break;
            case "D" : opDraw(); break;
            case "E" : switch(opcode.substring(3)) {
                case "E" : opKeyTrue(); break;
                case "1" : opKeyFalse(); break;
            }
            case "F" : switch(opcode.substring(2,3)) {
                case "0" : switch(opcode.substring(3)) {
                    case "7" : opGetDelay(); break;
                    case "A" : opGetKey(); break;
                }
                case "1" : switch(opcode.substring(3)) {
                    case "5" : opSetDelay(); break;
                    case "8" : opSetSound(); break;
                    case "E" : opAddIReg(); break;
                }
                case "2" : opGetChar(); break;
                case "3" : opStoreBinary(); break;
                case "5" : opRegDump(); break;
                case "6" : opRegLoad(); break;
            }
        }
        // </editor-fold>
        
        //HACKY WORKAROUND
        pc = pctemp;
        
        //decrement timers
        timers.decrement();
        
        //print after
        //System.out.println("opcode: " + opcode);
        //System.out.println("I: " + I);
        //System.out.println("pc: " + pc);
        
    }
    
    public void setKeys() {
        
    }
    
    //opcodes
    
    private void opDisplayClear() {
        gfx.displayClear();
        pc += 2;
        pctemp = pc;
    }
    
    private void opReturn() {
        pc = stack.get(stack.getP());
        stack.down();
        pctemp = pc;
    }
    
    private void opGoto() {
        pc = Integer.parseInt(opcode.substring(1), 16);
        pctemp = pc;
    }
    
    private void opCall() {
        stack.set(stack.getP(), pc);
        stack.up();
        pc = Integer.parseInt(opcode.substring(1), 16);
        pctemp = pc;
    }
    
    private void opCondTrue() {
        int x = Integer.parseInt(opcode.substring(1,2), 16);
        int vx = V.get(x);
        int nn = Integer.parseInt(opcode.substring(2), 16);
        if (vx == nn)
            pc += 4;
        else
            pc += 2;
        pctemp = pc;
    }
    
    private void opCondFalse() {
        int x = Integer.parseInt(opcode.substring(1,2), 16);
        int vx = V.get(x);
        int nn = Integer.parseInt(opcode.substring(2), 16);
        if (vx == nn)
            pc += 2;
        else
            pc += 4;
        pctemp = pc;
    }
    
    private void opCondRegTrue() {
        int x = Integer.parseInt(opcode.substring(1,2), 16);
        int vx = V.get(x);
        int y = Integer.parseInt(opcode.substring(2,3), 16);
        int vy = V.get(y);
        if (vx == vy)
            pc += 4;
        else
            pc += 2;
        pctemp = pc;
    }
    
    private void opConstSet() {
        int x = Integer.parseInt(opcode.substring(1,2), 16);
        byte nn = Byte.parseByte(opcode.substring(2), 16);
        V.set(x, nn);
        pc += 2;
        pctemp = pc;
    }
    
    private void opConstAdd() {
        int x = Integer.parseInt(opcode.substring(1,2), 16);
        int vx = V.get(x);
        int nn = Byte.parseByte(opcode.substring(2), 16);
        int sum = vx + nn;
        if (sum > 0xFF)
            V.set(x, 0xFF);
        else
            V.set(x, sum);
        pc += 2;
        pctemp = pc;
    }
    
    private void opAssign() {
        int x = Integer.parseInt(opcode.substring(1,2), 16);
        int y = Integer.parseInt(opcode.substring(2,3), 16);
        int vy = V.get(y);
        V.set(x, vy);
        pc += 2;
        pctemp = pc;
    }
    
    private void opBitOr() {
        int x = Integer.parseInt(opcode.substring(1,2), 16);
        int vx = V.get(x);
        int y = Integer.parseInt(opcode.substring(2,3), 16);
        int vy = V.get(y);
        V.set(x, vx|vy);
        pc += 2;
        pctemp = pc;
    }
    
    private void opBitAnd() {
        int x = Integer.parseInt(opcode.substring(1,2), 16);
        int vx = V.get(x);
        int y = Integer.parseInt(opcode.substring(2,3), 16);
        int vy = V.get(y);
        V.set(x, vx&vy);
        pc += 2;
        pctemp = pc;
    }
    
    private void opBitXor() {
        int x = Integer.parseInt(opcode.substring(1,2), 16);
        int vx = V.get(x);
        int y = Integer.parseInt(opcode.substring(2,3), 16);
        int vy = V.get(y);
        V.set(x, vx^vy);
        pc += 2;
        pctemp = pc;
    }
    
    private void opMathAdd() {
        int x = Integer.parseInt(opcode.substring(1,2), 16);
        int vx = V.get(x);
        int y = Integer.parseInt(opcode.substring(2,3), 16);
        int vy = V.get(y);
        int sum = vx + vy;
        if (sum > 255) {
            V.set(x, sum - 256);
            V.set(15, 1);
        }
        else {
            V.set(x, sum);
            V.set(15, 0);
        }
        pc += 2;
        pctemp = pc;
    }
    
    private void opMathSubtract() {
        int x = Integer.parseInt(opcode.substring(1,2), 16);
        int vx = V.get(x);
        int y = Integer.parseInt(opcode.substring(2,3), 16);
        int vy = V.get(y);
        int diff = vx - vy;
        if (diff < 0) {
            V.set(x, diff + 256);
            V.set(15, 1);
        }
        else {
            V.set(x, diff);
            V.set(15, 0);
        }
        pc += 2;
        pctemp = pc;
    }
    
    private void opBitRight() {
        int x = Integer.parseInt(opcode.substring(1,2), 16);
        int y = Integer.parseInt(opcode.substring(2,3), 16);
        byte vy = (byte)(V.get(y));
        V.set(15, vy % 2);
        vy = (byte)(vy >>> 1);
        V.set(x, vy);
        V.set(y, vy);
        pc += 2;
        pctemp = pc;
    }
    
    private void opMathDiff() {
        int x = Integer.parseInt(opcode.substring(1,2), 16);
        int vx = V.get(x);
        int y = Integer.parseInt(opcode.substring(2,3), 16);
        int vy = V.get(y);
        int diff = vy - vx;
        if (diff < 0) {
            V.set(x, diff + 256);
            V.set(15, 1);
        }
        else {
            V.set(x, diff);
            V.set(15, 0);
        }
        pc += 2;
        pctemp = pc;
    }
    
    private void opBitLeft() {
        int x = Integer.parseInt(opcode.substring(1,2), 16);
        int y = Integer.parseInt(opcode.substring(2,3), 16);
        byte vy = (byte)(V.get(y));
        V.set(15, (vy&0xFF) >>> 7);
        vy = (byte)(vy << 1);
        V.set(x, vy);
        V.set(y, vy);
        pc += 2;
        pctemp = pc;
    }
    
    private void opCondRegFalse() {
        int x = Integer.parseInt(opcode.substring(1,2), 16);
        int vx = V.get(x);
        int y = Integer.parseInt(opcode.substring(2,3), 16);
        int vy = V.get(y);
        if (vx == vy)
            pc += 2;
        else
            pc += 4;
        pctemp = pc;
    }
    
    private void opSetI() {
        int nnn = Integer.parseInt(opcode.substring(1), 16);
        I = nnn;
        pc += 2;
        pctemp = pc;
    }
    
    private void opGotoAdd() {
        int nnn = Integer.parseInt(opcode.substring(1), 16);
        pc = nnn + (int)(V.get(0));
        pctemp = pc;
    }
    
    private void opRand() {
        int x = Integer.parseInt(opcode.substring(1,2), 16);
        int rand = (int)( Math.floor( Math.random()*256 ) );
        int nn = Integer.parseInt(opcode.substring(2), 16);
        V.set(x, rand&nn);
        pc += 2;
        pctemp = pc;
    }
    
    private void opDraw() {
        V.set(15, 0);
        int x = Integer.parseInt(opcode.substring(1,2), 16);
        int vx = V.get(x);
        int y = Integer.parseInt(opcode.substring(2,3), 16);
        int vy = V.get(y);
        int n = Integer.parseInt(opcode.substring(3), 16);
        
        for (int r = 0; r < n; r++) {
            String row = memory.getBinary((short)(I + r));
            for (int c = 0; c < 8; c++) {
                byte current = gfx.get(vx + c, vy + r);
                byte pixel = Integer.valueOf(row.substring(c, c+1)).byteValue();
                if (current == 0) {
                    if (pixel == 1)
                        gfx.set(vx + c, vy + r, (byte)1);
                    else if (pixel == 0)
                        gfx.set(vx + c, vy + r, (byte)0);
                }
                else if (current == 1) {
                    if (pixel == 1) {
                        V.set(15, 1);
                        gfx.set(vx + c, vy + r, (byte)0);
                    } else if (pixel == 0)
                        gfx.set(vx + c, vy + r, (byte)1);
                }
            }
        }
        
        drawFlag = true;
        pc += 2;
        pctemp = pc;
    }
    
    private void opKeyTrue() {
        int x = Integer.parseInt(opcode.substring(1,2), 16);
        int vx = V.get(x);
        if (input.get(vx) != 0) pc += 4;
        else pc += 2;
        pctemp = pc;
    }
    
    private void opKeyFalse() {
        int x = Integer.parseInt(opcode.substring(1,2), 16);
        int vx = V.get(x);
        if (input.get(vx) != 0) pc += 2;
        else pc += 4;
        pctemp = pc;
    }
    
    private void opGetDelay() {
        int x = Integer.parseInt(opcode.substring(1,2), 16);
        V.set(x, timers.getDelay());
        pc += 2;
        pctemp = pc;
    }
    
    private void opGetKey() {
        int x = Integer.parseInt(opcode.substring(1,2), 16);
        for (;;) {
            for (int k = 0; k < 16; k++) {
                if (input.get(k) != 0) {
                    V.set(x, k);
                    pc += 2;
                    pctemp = pc;
                    return;
                }
            }
        }
    }
    
    private void opSetDelay() {
        int x = Integer.parseInt(opcode.substring(1,2), 16);
        int vx = V.get(x);
        timers.setDelay(vx);
        pc += 2;
        pctemp = pc;
    }
    
    private void opSetSound() {
        int x = Integer.parseInt(opcode.substring(1,2), 16);
        int vx = V.get(x);
        timers.setSound(vx);
        pc += 2;
        pctemp = pc;
    }
    
    private void opAddIReg() {
        int x = Integer.parseInt(opcode.substring(1,2), 16);
        int vx = V.get(x);
        I += vx;
        pc += 2;
        pctemp = pc;
    }
    
    private void opGetChar() {
        int x = Integer.parseInt(opcode.substring(1,2), 16);
        int vx = V.get(x);
        switch (vx) {
            case 0x0 : I = 80;
            case 0x1 : I = 85;
            case 0x2 : I = 90;
            case 0x3 : I = 95;
            case 0x4 : I = 100;
            case 0x5 : I = 105;
            case 0x6 : I = 110;
            case 0x7 : I = 115;
            case 0x8 : I = 120;
            case 0x9 : I = 125;
            case 0xA : I = 130;
            case 0xB : I = 135;
            case 0xC : I = 140;
            case 0xD : I = 145;
            case 0xE : I = 150;
            case 0xF : I = 155;
        }
        pc += 2;
        pctemp = pc;
    }
    
    private void opStoreBinary() {
        int x = Integer.parseInt(opcode.substring(1,2), 16);
        byte vx = (byte)((byte)(V.get(x)) >>> 8);
        memory.set(I,   (byte)((vx / 100)     ));
        memory.set(I+1, (byte)((vx / 10 ) % 10));
        memory.set(I+2, (byte)((vx % 100) % 10));
        pc += 2;
        pctemp = pc;
    }
    
    private void opRegDump() {
        int x = Integer.parseInt(opcode.substring(1,2), 16);
        for (int k = 0; k < x; k++) {
            byte vx = (byte)(V.get(k));
            memory.set(I, vx);
            I++;
        }
        pc += 2;
        pctemp = pc;
    }
    
    private void opRegLoad() {
        int x = Integer.parseInt(opcode.substring(1,2), 16);
        for (int k = 0; k < x; k++) {
            byte memx = (byte)(memory.get(I));
            V.set(k, memx);
            I++;
        }
        pc += 2;
        pctemp = pc;
    }
    
}
