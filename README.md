# SingularityCardGame

This is a Digital Implementation for the structure of the Singularity Card Game. 

Created by Jeremy Wen and Tyler Moy  
Williams College — CSCI 136: Data Structures and Advanced Programming (Spring 2025)

---

### Overview:

Singularity is a custom, unconventional card game played physically by a small but passionate community. There are a plethora of games built into the card game and this project brings Singularity into a digital format, offering a structured framework that others can use, enjoy, and build on.

We’re two students from Williams College who set out to design something that’s both fun and functional, especially for a game that previously existed only in scattered formats. By building this project in Java (and optionally using Unity for visual rendering), we aim to make Singularity more accessible, organized, and ready for experimentation.

---

### Some Of Our Goals:

~ Provide a visual digital version of the Singularity game board and logic

~ Allow others to build on our implementation through sleek code and documentation

~ Bring structure to an unstructured game while preserving creative flexibility

~ Utilize bitboards to efficiently track card positions, player control, and tile-based interactions given complex zones/rules

---

The game structure is still a work in progress, to get a sense of the mechanics so far see the BitboardADT

Compile the Java file:  
```bash
javac SCG/BitboardADT.java
Then Run the Program:

java SCG.BitboardADT
Current Status and Next Steps
This project is still a work in progress. While we have laid down the core game structure and implemented efficient zone and card tracking with bitboards, several features remain incomplete.

Our immediate focus is on fully implementing the card dragging functionality to allow intuitive and smooth movement of cards on the digital board. Currently, there is a known bug affecting this feature in the file SCG.GameBoard.java that we are actively working to resolve.

Once fixed, this will enable interactive gameplay that closely mimics the physical experience of Singularity.
