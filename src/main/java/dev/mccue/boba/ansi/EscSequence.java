package dev.mccue.boba.ansi;

public record EscSequence(int value) implements Sequence {
    @Override
    public String string() {
        return null;
    }

    @Override
    public byte[] bytes() {
        return new byte[0];
    }

    @Override
    public Sequence copy() {
        return null;
    }
    /*

// buffer returns the buffer of the escape sequence.
func (e EscSequence) buffer() *bytes.Buffer {
	var b bytes.Buffer
	b.WriteByte('\x1b')
	if i := parser.Intermediate(int(e)); i != 0 {
		b.WriteByte(byte(i))
	}
	b.WriteByte(byte(e.Command()))
	return &b
}

// Bytes implements Sequence.
func (e EscSequence) Bytes() []byte {
	return e.buffer().Bytes()
}

// String implements Sequence.
func (e EscSequence) String() string {
	return e.buffer().String()
}

// Clone implements Sequence.
func (e EscSequence) Clone() Sequence {
	return e
}
     */
}
