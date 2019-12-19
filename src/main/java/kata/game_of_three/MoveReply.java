package kata.game_of_three;

import java.util.Objects;

public class MoveReply {

    public static final MoveReply ZERO = new MoveReply(0);
    public static final MoveReply ONE = new MoveReply(1);
    public static final MoveReply MINUS_ONE = new MoveReply(-1);

    private Integer value;

    @SuppressWarnings("unused") private MoveReply() {}

    private MoveReply(Integer value) {
	this.value = value;
    }

    public Integer getValue() {
	return value;
    }

    @Override public String toString() {
	return "MoveReply{" +
			"value=" + value +
			'}';
    }

    @Override public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (!(o instanceof MoveReply))
	    return false;
	MoveReply moveReply = (MoveReply) o;
	return Objects.equals(value, moveReply.value);
    }

    @Override public int hashCode() {
	return Objects.hash(value);
    }
}
