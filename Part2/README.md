# Typing Race Simulator - Part II: GUI Layer

## Architecture: Building on Part I

**Part II is purely a GUI presentation layer** that uses Part I's simulation logic.

### Part I (Logic Layer)
- `Typist.java` - Individual competitor with all game mechanics
- `TypingRace.java` - **NOW MODIFIED** to support 2-6 dynamic typists
- **No GUI, pure simulation logic**

### Part II (Presentation Layer)
- **Only GUI-specific classes**, NO simulation logic
- **Uses Part I's classes directly** (imports and instantiates them)

---

## Part I Modifications (To Support Part II)

### Changes to `TypingRace.java`:

1. **Constructor now accepts seat count**
   ```java
   // OLD: public TypingRace(int passageLength)
   // NEW: Supports 2-6 typists
   public TypingRace(int passageLength, int seatCount)
   ```

2. **Uses ArrayList instead of fixed 3 seats**
   ```java
   // OLD: private Typist seat1Typist, seat2Typist, seat3Typist
   // NEW:
   private ArrayList<Typist> typists;
   ```

3. **New methods for GUI access**
   ```java
   public ArrayList<Typist> getTypists()      // Get all typists
   public Typist getTypist(int seatNumber)    // Get specific typist
   public int getSeatCount()                  // Get seat count
   ```

4. **Race loop now handles dynamic typist count**
   ```java
   // OLD: advanceTypist(seat1), advanceTypist(seat2), advanceTypist(seat3)
   // NEW:
   for (Typist t : typists) {
       advanceTypist(t);
   }
   ```

5. **Backward compatible** - Old code still works
   ```java
   new TypingRace(40)  // Still defaults to 3 seats
   ```

---

## Part II Design Flow

```
User Interface (GUI)
    ↓
ConfigurationPanel (collect settings)
    ↓
TypingRaceGUI (orchestrator)
    ├→ Creates Part I Typist objects with modifiers
    ├→ Creates Part I TypingRace instance (seat count from config)
    ├→ Adds Typists to TypingRace (Part I's addTypist method)
    ├→ Runs Part I's startRace() in background thread
    └→ RacePanel displays live Typist state as race progresses
        └→ Reads typist.getProgress(), isBurntOut(), justMistyped()
```

---

## Part II Classes

### `Passage.java`
- GUI-specific passage management
- Pre-defined passages (short/medium/long)
- Custom passage support
- No part of Part I

### `RaceConfiguration.java`
- Holds user settings (passage, seat count, modifiers)
- Modifier flags for GUI application
- No Part I interaction at this level

### `ConfigurationPanel.java`
- Swing JPanel for race setup
- Collects: passage, seat count, modifiers
- Validates input
- Returns RaceConfiguration

### `RacePanel.java`
- Swing JPanel for race display
- **Takes ArrayList of Part I Typist objects**
- Reads live: `typist.getProgress()`, `isBurntOut()`, `justMistyped()`
- Renders character-by-character progress with color coding

### `TypingRaceGUI.java`
- Main application window
- Orchestrates the flow:
  1. Gets config from ConfigurationPanel
  2. Creates Part I Typist objects
  3. Creates Part I TypingRace with dynamic seats
  4. Adds typists to Part I's TypingRace
  5. Runs Part I's startRace() (background thread)
  6. Updates RacePanel display
  7. Shows winner

---

## How to Compile & Run

### From Part2 Directory
```bash
cd Part2
javac *.java ../Part1/Typist.java ../Part1/TypingRace.java
java TypingRaceGUI
```

### What Compiles
- Part II GUI classes (ConfigurationPanel, RacePanel, TypingRaceGUI, etc.)
- Passage & RaceConfiguration (Part II configuration system)
- **Part I's Typist and TypingRace** (imported/used by Part II)

---

## Integration Example

```java
// TypingRaceGUI creates Part I Typist objects
Typist typist1 = new Typist('①', "TURBOFINGERS", 0.85);
Typist typist2 = new Typist('②', "QWERTY_QUEEN", 0.60);

// TypingRaceGUI creates Part I TypingRace with dynamic seat count
TypingRace race = new TypingRace(100, 2);  // 2 typists, 100 char passage

// Add typists using Part I's method
race.addTypist(typist1, 1);
race.addTypist(typist2, 2);

// Run Part I's simulation
race.startRace();  // Uses Part I's advanceTypist(), burnout, mistype logic

// RacePanel reads live data
while (race is running) {
    typist1.getProgress()      // From Part I Typist
    typist1.isBurntOut()       // From Part I Typist
    typist1.justMistyped()     // From Part I Typist
}
```

---

## User Experience Flow

1. **Launch Application** → TypingRaceGUI window with ConfigurationPanel
2. **Configure**:
   - Select pre-defined passage or enter custom
   - Choose 2-6 typists
   - Apply optional modifiers (Autocorrect/Caffeine/NightShift)
3. **Start Race** → RacePanel displays all typists racing
4. **Watch Race**:
   - See real-time progress (green = completed, blue = cursor)
   - Notice burnout states (orange indicator + turn counter)
   - See mistype penalties (red indicator)
   - Part I's logic runs: typeCharacter(), slideBack(), burnOut()
5. **Race Ends** → Part I determines winner
6. **Winner Announced** → Option to run new race

---

## Key Separation

| Concern | Part I | Part II |
|---------|--------|---------|
| Typist mechanics | ✓ All logic | - |
| Accuracy/burnout | ✓ All logic | - |
| Race simulation | ✓ All logic | - |
| UI configuration | - | ✓ ConfigurationPanel |
| Visual display | - | ✓ RacePanel |
| User input | - | ✓ ConfigurationPanel |
| Modifier application | ✓ (if coded) | Partial (GUI applies) |

---

## Modifier Implementation

**Applied by Part II GUI before passing to Part I:**

- **Night Shift**: Reduce all Typist accuracies by 10%
  ```java
  typist.setAccuracy(typist.getAccuracy() * 0.9);
  ```

- **Caffeine Mode**: Increase accuracies by 15%
  ```java
  typist.setAccuracy(typist.getAccuracy() * 1.15);
  ```

- **Autocorrect**: Could reduce slideBack penalties (not yet implemented in Part I)

---

## Future Enhancements

- Keyboard input detection (real typing)
- Statistics tracking
- Leaderboard system
- Network multiplayer
- Sound effects
- Replay functionality
- Custom typist profiles
