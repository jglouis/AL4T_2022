# Listing issues

## SOLID principle violations

### Violation of SRP in GameEngine

Does too many things. Is a god object.

### Violation of SRP in GameObject

Handling both physics and rendering.

### Violation of DIP of ImageLoader

MarioForm and SuperMushroom are creating instances of ImageLoader.

### Violation of DIP of GameEngine

Hard GameEngine dependency in Mario class.

### Violation of OCP in MapCreator

### Violation of OCP in Map.drawMap()

It seems like we could iterate on a list of IRenderable instead.

Big "if-else".

## Bugs

### Game music stops at some point

### Collision bugs

### Concurrency issues

### Sounds overlap
