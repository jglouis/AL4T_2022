# Learn Java by its pitfalls

This document covers the most common sources of mistakes using Java.
It is not intended to be a reference for the Java programming language.

## Hands-on samples with unit tests

A collection of tiny samples is available in `be.ecam.basics.samples.Samples` with unit tests in `basics/src/test/java/be/ecam/basics/samples/SamplesTest.java`.
They illustrate common pitfalls such as:
- casting and integer overflow/underflow
- integer division truncation
- floating-point precision (IEEE 754)
- NaN comparison behavior
- null handling and NullPointerException (checked vs unchecked)
- checked exceptions enforcement
- reference aliasing and mutability
- string reference equality (==) vs content equality (equals)
- autounboxing null leading to NPE

How to run the tests for this module only:
- Using Gradle Wrapper from the project root: `gradlew :basics:test`
- Or from the `basics` directory: `gradlew test`

## The Java types

Although Java possesses a static type system, there are some dangers that developer should be aware off.
Some typical mistakes can be avoided by simply being aware of these dangers.

Java distinguishes between two kind of types: primitive types and references types (aka "objects").

### Primitive types

| type        |      description      | size in bits  | default value |
|-------------|:---------------------:|---------------|---------------|
| **byte**    |        integer        | 8             | 0             |
| **short**   |        integer        | 16            | 0             |
| **int**     |        integer        | 32            | 0             |
| **long**    |        integer        | 64            | 0             |
| **float**   | floating point number | 32            | 0             |
| **double**  | floating point number | 64            | 0             |
| **boolean** |        Boolean        | JVM dependant | `false`       |
| **char**    |   Unicode character   | 16            | '\u0000'      |

A primitive type value is always passed by copy as parameter for a method or constructor.
They have a known fixed size. They default to some value if not explicitly initialized.

There are a few common mistakes when dealing with primitive types, notably:

- All integer and floating point types are **signed**. Integers are encoded as **two's complement**.
  See <https://en.wikipedia.org/wiki/Two%27s_complement>.
- Integers overflow and underflow by "wrapping around".
  It might, for instance, be surprising to note that the expression `(byte) 128` evaluates to `-128`.
  This is because `Byte.MAX_VALUE` is `127`, which then "wraps around" to `Byte.MIN_VALUE` of `-128`.
- Floating points obey the `IEEE 754` standard.
  They have, as their name suggest, a floating precision.
  As such, they are not meant for usage where you need an exact value (for instance representing division of a currency)
  .

### Reference types

Non-primitive types are called reference types. This includes arrays, `String`s and even classes themselves.
A reference can be seen as a JVM pointer to the heap of your Java program.
The heap stores all object instance fields of the instance, which might be primitive types or references types
themselves, referencing in turn other portion of the heap.
The heap is essentially a shared memory location amongst all the program threads.

References types are allocated using the `new` keyword.
Reference types are subject to garbage collection.
As a first approximation, we can say that a reference is candidate for garbage collection if and only if it is not
referenced by the program.

The default value of a reference is `null`.

## Byte manipulation and endianness

Java's `byte` type is an **8-bit signed integer**, with values ranging from `-128` to `127`.

This can be surprising when using `byte` to manipulate raw binary data, because a byte is often conceptually thought of as a value from `0` to `255`.

### Signed bytes and bit patterns

A `byte` uses two's complement representation, just like the other Java integer types.

For example:

```text id="3q8w2k"
decimal     binary

   0        0000 0000
   1        0000 0001
 127        0111 1111
-128        1000 0000
  -1        1111 1111
```

Consequently, the byte containing the bit pattern `1111 1111` has the Java value `-1`.

This becomes particularly important when reading binary data:

```java id="8z5f0m"
byte b = (byte) 0xFF;

System.out.println(b); // -1
```

The bits are still `11111111`; Java is simply interpreting those bits as a signed two's-complement number.

If the byte should be treated as an **unsigned value**, use a bit mask:

```java id="1j7q3c"
int unsignedValue = b & 0xFF;

System.out.println(unsignedValue); // 255
```

The `& 0xFF` clears all the upper bits after Java promotes the `byte` to an `int`.

This is particularly common when manipulating binary protocols or file formats.

### Integer promotion

Another potential trap is that arithmetic and bitwise operations on `byte` do not generally produce another `byte`.

Java promotes `byte`, `short` and `char` operands to `int` for most arithmetic and bitwise operations.

For example:

```java id="3k9x5v"
byte a = 10;
byte b = 20;

// byte c = a + b;       // does not compile
int c = a + b;           // OK
```

Similarly:

```java id="r6z1xp"
byte b = (byte) 0xFF;

int x = b;       // x is -1
int y = b & 0xFF; // y is 255
```

The distinction between the **bit pattern** and its **signed interpretation** is fundamental when working with binary data.

### Multi-byte integers

A larger integer is stored as multiple bytes.

For example, the 32-bit hexadecimal value:

```text id="p7v3na"
0x12345678
```

consists of four bytes:

```text id="w5j2qr"
0x12    0x34    0x56    0x78
  ↑       ↑       ↑       ↑
8 bits  8 bits  8 bits  8 bits
```

The question is: **in which order are these bytes stored?**

This is called **endianness**.

### Big-endian

In **big-endian** representation, the most significant byte comes first:

```text id="5h2q9p"
32-bit integer: 0x12345678

address →   +0    +1    +2    +3
            ─────────────────────
bytes       12    34    56    78
            ↑                 ↑
          MSB                 LSB
```

The first byte is the most significant byte (MSB).

Big-endian is sometimes described as **network byte order** and is used by many binary file formats and network protocols.

### Little-endian

In **little-endian** representation, the least significant byte comes first:

```text id="9c4m2x"
32-bit integer: 0x12345678

address →   +0    +1    +2    +3
            ─────────────────────
bytes       78    56    34    12
            ↑                 ↑
          LSB                 MSB
```

Modern desktop CPUs, including the x86/x86-64 CPUs commonly found in PCs, are little-endian. ARM systems commonly used by phones are also normally little-endian.

This means that the same four bytes:

```text id="2p7v4n"
12 34 56 78
```

represent different integer values depending on the byte order in which they are interpreted.

### Java and endianness

Java itself does not have a single "native integer endianness" in the way a CPU does.

The JVM defines the behavior of Java integer types independently of the CPU's memory representation. When converting between primitive values and byte sequences, however, Java APIs need to specify an order.

For example, `ByteBuffer` uses **big-endian by default**:

```java id="6x8k2p"
ByteBuffer buffer = ByteBuffer.allocate(4);

buffer.putInt(0x12345678);

byte[] bytes = buffer.array();
```

The resulting bytes are:

```text id="0v6n8m"
12 34 56 78
```

Little-endian can be explicitly requested:

```java id="4r2j7q"
buffer.order(ByteOrder.LITTLE_ENDIAN);
```

The resulting bytes are then:

```text id="3n5c8x"
78 56 34 12
```

This is important when reading a binary file: **the file format defines the byte order**. You cannot assume that the order used by the machine running your Java program is the order used by the file.

For example, if a file specifies that a 32-bit integer is little-endian, the bytes:

```text id="7q2m5z"
78 56 34 12
```

must be interpreted as:

```text id="x3c8pa"
0x12345678
```

regardless of whether the Java program happens to run on a little-endian or big-endian CPU.

### A practical rule

When manipulating binary data, keep three concepts separate:

```text id="5m9r2k"
1. Bit pattern
   11111111

2. Numeric interpretation
   byte  → -1
   unsigned byte → 255

3. Byte order
   12 34 56 78 → 0x12345678  (big-endian)
   78 56 34 12 → 0x12345678  (little-endian)
```

Many bugs involving binary protocols and file formats come from confusing one of these three concepts with another.

## Strings, Unicode and UTF-16

A `String` is a reference type representing a sequence of characters. However, it is important to understand that Java does **not** internally represent a `String` as a sequence of Unicode code points.

Java's `String` uses **UTF-16** as its conceptual encoding. This has an important consequence: one Java `char` is **not necessarily one Unicode character**.

Java's choice of UTF-16 is largely a consequence of when Java was designed, in the mid-1990s. When Java was being designed around 1994–1995, Unicode was still relatively young. The original Unicode design aimed for a 16-bit character set, giving it 65,536 possible values. At the time, this looked like a reasonable goal: enough space to encompass the world's writing systems.

### `char` is a UTF-16 code unit

A Java `char` is an unsigned 16-bit value. It represents one **UTF-16 code unit**, not necessarily one complete Unicode code point.

Unicode defines code points from `U+0000` to `U+10FFFF`. The values from `U+0000` to `U+FFFF` can be represented by a single UTF-16 code unit.

For example:

```java
String s = "A€";

System.out.println(s.length());  // 2
System.out.println(s.charAt(0)); // 'A'
System.out.println(s.charAt(1)); // '€'
```

Both characters happen to fit in a single `char`.

However, some Unicode characters have code points above `U+FFFF`. These cannot be represented by a single `char`.

For example, 😀 is Unicode code point `U+1F600`:

```java
String s = "😀";

System.out.println(s.length());  // 2
```

This may initially seem surprising: the string contains one Unicode character, but its `length()` is `2`.

This is because UTF-16 represents code points above `U+FFFF` using a **surrogate pair**: two 16-bit code units.

```text
Unicode code point
       U+1F600
          │
          ▼
   ┌──────────────┐
   │ UTF-16       │
   │              │
   │ high         │ low
   │ surrogate    │ surrogate
   │   D83D       │   DE00
   └──────────────┘
       16 bits       16 bits
```

Consequently:

```java
String s = "😀";

char first  = s.charAt(0);
char second = s.charAt(1);

System.out.printf("%04X%n", (int) first);  // D83D
System.out.printf("%04X%n", (int) second); // DE00
```

Neither `D83D` nor `DE00` is the complete Unicode code point. Together they represent `U+1F600`.

This is one reason why code such as:

```java
for (int i = 0; i < s.length(); i++) {
    System.out.println(s.charAt(i));
}
```

does **not necessarily iterate over Unicode characters**. It iterates over UTF-16 code units.

When code points are required, Java provides an API specifically for this:

```java
s.codePoints().forEach(cp ->
    System.out.printf("U+%04X%n", cp)
);
```

For `"😀"`, this produces one code point:

```text
U+1F600
```

### Code units, code points and graphemes

There are therefore several different concepts that are easy to confuse:

```text
UTF-16 code unit     → what char represents
Unicode code point   → a Unicode value such as U+1F600
Grapheme             → what a user generally perceives as one character
```

Even a Unicode code point does not necessarily correspond to one visible character.

For example, a character such as `é` can be represented either as a single precomposed code point or as multiple code points:

```text
é

U+00E9
```

or:

```text
e + combining acute accent

U+0065 U+0301
```

Both can render as a single visible `é`. Consequently, even `codePoints()` is not necessarily equivalent to "iterate over what the user sees as characters".

Java provides more advanced APIs for dealing with Unicode text at the grapheme-cluster level, but for most application code the distinction between `char`, code point, and grapheme is the important thing to understand.

### Strings and encodings

UTF-16 is relevant to the **internal representation of a `String`**. It is not the same thing as the encoding used when text is written to a file, sent over a network, or converted to bytes.

For example:

```java
String text = "Hello 😀";

byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
```

Here the `String` is converted into a sequence of bytes using UTF-8.

The reverse operation performs decoding:

```java
String text = new String(bytes, StandardCharsets.UTF_8);
```

The encoding must be known when converting between `String` and `byte[]`.

This is why code such as:

```java
new String(bytes)
```

can be dangerous when the bytes originate outside the application. It uses the platform's default charset rather than explicitly documenting which encoding the bytes use.

Prefer:

```java
new String(bytes, StandardCharsets.UTF_8);
```

and:

```java
text.getBytes(StandardCharsets.UTF_8);
```

when UTF-8 is the intended encoding.

A useful mental model is therefore:

```text
             Java String
          Unicode text model
                 │
                 │ encode
                 ▼
        ┌─────────────────┐
        │ UTF-8 / UTF-16  │
        │ / ISO-8859-1... │
        └─────────────────┘
                 │
                 ▼
             byte[]
```

The encoding becomes relevant when crossing the boundary between Java text and raw bytes.

### What happens with `System.out.println()`?

`System.out.println(String)` does **not** convert the string directly to UTF-8 itself.

The important pieces are:

```text
String
  │
  │ println()
  ▼
PrintStream
  │
  │ encodes characters using its charset
  ▼
OutputStream
  │
  ▼
operating system / terminal
```

The `PrintStream` associated with `System.out` has a charset that is used to encode the characters into bytes before those bytes are written to the underlying output stream.

For example:

```java
System.out.println("Hello 😀");
```

The `😀` is already represented inside the `String` as a UTF-16 surrogate pair. When `println` writes the text, that Unicode text is encoded into bytes using the charset associated with the output stream.

The terminal then interprets those bytes using its own expected encoding and renders the resulting characters using an appropriate font.

So there can actually be several stages:

```text
"😀"
  │
  │ Java String
  ▼
UTF-16 representation
  │
  │ PrintStream encoding
  ▼
bytes
  │
  │ terminal decoding
  ▼
Unicode characters
  │
  │ font rendering
  ▼
😀
```

If the encoding expected by the reader does not match the encoding used to produce the bytes, the output can become corrupted:

```text
"café"
   │
   │ encoded incorrectly
   ▼
"cafÃ©"
```

This is commonly called **mojibake**.

### A practical rule

When dealing with text, always distinguish between:

* **`char`** — one UTF-16 code unit
* **Unicode code point** — one value in the Unicode code-point space
* **grapheme cluster** — approximately one user-perceived character
* **`String`** — a sequence of UTF-16 code units representing Unicode text
* **`byte[]`** — raw bytes that require an encoding to interpret as text
* **charset/encoding** — the rules used to convert between Unicode text and bytes

The most common pitfall is assuming that:

```java
String.length()
```

means "number of characters".

It actually returns the number of **UTF-16 code units** in the string.

For text that may contain supplementary Unicode characters, prefer the code-point API when appropriate:

```java
int codePointCount = text.codePointCount(0, text.length());
```

and use:

```java
text.codePoints()
```

when iterating over Unicode code points rather than `char` values.
## Namespaces

Like most modern languages, Java possesses the ability to define Namespace to avoid name clashing.

The canonical way of organising Java namespaces is through the usage of packages.
A java package is a group of named types (classes, interfaces and enums).
Incidentally, a package is also the directory path of the file system leading to the files containing the said type
definitions.

By convention, packages are organized to form an inverse domain name, like so:

- org.example
- java.lang
- be.ecam

This convention is enforced if you need to publish your code as a library.
In this case, you might need to own the domain name of your published package.

Additionally, a class, an interface or an enum is also a namespace.
If you take the `Object` class from the Java standard library, it's FQDN is `java.lang.Object`.

Although technically possible, it is considered bad practice to have java source code not living within a package.

## The `Object` class

All Java classes has the ability to inherit from on parent class. All java objects share one common same ancestor,
the `Object` class.

The `Object` class defines some useful methods that can be overridden.

### `toString()` method

This method is used to provide a human-readable representation of your object.
The default implementation will simply print the name of the instance class followed by its hashcode.
A lot of other classes might invoke `toString()`, like `System.out.print()` or `Formatter`.
It might be interesting to override this method for ease of debugging, logging or provide better error messages.

### `equals()` and `hashcode()`

`equals()` is used to compare two reference types between each other.
Default implementation of `equals()` is to check whether the two references are the same.
**It does not automatically compare the object internal fields!**

Common error is to override `equals()` but not `hashCode()`.
The issue with that is that your class will be incorrectly handled with other classes using both methods.
This includes `HashMap` and `HashSet`.
If `obj1.equals(obj2)` is `true`, then `obj1.hashcode() == obj2.hashcode` ***must*** be `true`.

### `finalize()`

`finalize()` is not the same thing as a destructor.
Because garbage collection is non-deterministic by definition, there is no guarantee about the order of execution of different `finalize()` from different objects.

As a side note, RAII is not applicable to Java when dealing with resources.
The proper ways to release resources is by using either:
- `try-finally` blocks
- `try-with` (Assuming the resource implements `Closeable` or `AutoCloseable`)

## Exceptions and Errors

Java uses Exceptions and Errors, both inheriting from a common class `Throwable`.
All `Throwable` objects can be "thrown" using the `throw` keyword.
The exception (or error), will then cause a "stack unwinding", bubbling up from method calls to method calls until one
of this happens:

- The `Throwable` is intercepted by a `try {} catch {}` block
- There is no more stack frame to unwind. The JVM stops.

### Errors

Errors are representing conditions that are usually not recoverable.

For instance a `JavaOutOfMemoryError` indicates that not enough memory space could be found on the heap for an
allocation.
Usually, there is no recovery from that at runtime. This could either be a bug (memory leak) or even simply that the max
JVM heap size needs to be tuned.
In such case, catching the error could still be useful for logging purposes or closing some opened resources.

### Checked exceptions vs Runtime exceptions

Runtime exception extends from `RuntimeException` class. Checked exceptions do not.

The Java compiler enforces that checked exceptions need to be handled by the calling code, either by:
- catching the exception.
- indicating that the same exception can be thrown in the calling method signature.

### Handling `null`

A `NullPointerException` occurs when trying to invoke a method on a reference type that is null.
It is also possible to throw this exception willingly.

Java's `NullPointerException` is a `RuntimeException`, meaning that it is not checked. cfr above.
This means that the compiler does not protect against null usage.

Correct `null` handling is a common source of mistake.
By default, always assume that a reference type might be null.

Common ways to handle null are:
- basic null check
- usage of type annotation `@Nullable`, `@NotNull`
- usage of `Optional<T>` as return type from a method

## Exercises

A set of 9 small, realistic exercises lives under the package `be.ecam.basics.exercises`.
Their unit tests are in `basics/src/test/java/be/ecam/basics/exercises`.
Some of these tests intentionally fail at first to reflect typical real‑world bugs; your goal is to make them pass by improving the production code.

How to run only this module’s tests:
- From project root: `gradlew :basics:test`
