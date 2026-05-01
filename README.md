# Typing Race Simulator

This repository contains two versions of a typing race simulation:

- `Part1` – Textual console version
- `Part2` – Graphical Swing-based version

## Requirements

- Java Development Kit (JDK) 8 or higher
- No external libraries required

## Part1: Textual Version

### Compile

Open a terminal in `Part1` and run:

```sh
javac Typist.java TypingRace.java StartRace.java
```

### Run

```sh
java StartRace
```

This launches the console-based typing race using the `StartRace.startRace()` entry point.

## Part2: Graphical Version

### Compile

Open a terminal in `Part2` and run:

```sh
javac Typist.java TypingRace.java TypingRaceGUI.java StartRaceGUI.java
```

### Run

```sh
java StartRaceGUI
```

This launches the GUI-based typing simulator using the `StartRaceGUI.startRaceGUI()` entry point.

## Notes

- The graphical version depends on the same Part1 core classes for race simulation.
- If you modify classes in `Part1`, recompile both the text and GUI versions as needed.
