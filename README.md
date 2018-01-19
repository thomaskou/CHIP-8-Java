# CHIP-8-Java
*CHIP-8 Emulator in Java*

A *very WIP* low-level emulator/interpreter for the CHIP-8 programming language / instruction set, programmed in Java.

This is the first thing I've ever done relating to emulation, so this project also serves as a hands-on learning experience for me. I've learned enough such that I'm confident in taking on more complex emulation projects. However, I also realize in retrospect that I've made many beginner mistakes within this project.

<img src="https://i.imgur.com/TavYjkX.png" width="256"> <img src="https://i.imgur.com/OgwUSlf.png" width="256"> <img src="https://i.imgur.com/trnM76x.png" width="256"> <img src="https://i.imgur.com/Gr3BkVG.png" width="256"> <img src="https://i.imgur.com/yXI9xih.png" width="256">

This being my first emulation project, do not expect it to be 100% functional. The following list explains what still needs to be fixed or implemented.

**THINGS TO WORK ON:**
* Fixing opcode interpreters.
* Game speed.
* Making more text sprites display instead of just "F".
* Input.
* Playing sound.
* Fixing the weird program counter opcode bug and removing the hack.
* Everything else, probably.

**IMPLEMENTED AND FUNCTIONAL:**
* Virtualization of data (memory, CPU registers, stack).
* Main emulation loop.
* Emulation of timers.
* Loading games and storing them to memory.
* Display emulation and rendering.
* Most opcodes.
* Interface between emulation and system (chip8emu.java).

**THINGS I'VE LEARNED:**
* Bitwise operations (and how to do them in Java).
* How Java performs non-bitwise operations. Read: poorly. It casts all bytes and shorts into ints. :(
* The basics of low-level emulation.
* Opcodes and how a CPU interprets and executes instructions from memory.
* Reading files as hexadecimal in Java.
* Rendering and updating graphics live using JPanel and JFrame.

**WHAT COULD BE IMPROVED NEXT TIME:**
* Use a different language, such as C, that plays nicer with bytes, shorts, and other variable types.
* Use a language that can have unsigned primitives. (Operations return out-of-bounds errors in Java.)
* Devise a way to test each individual opcode, making debugging more streamlined.
* Organize opcode methods in a neater way.
* Use something more sophisticated than nested switch statements for every opcode.
* Plan more beforehand!
