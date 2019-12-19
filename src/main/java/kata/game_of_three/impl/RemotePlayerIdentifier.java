package kata.game_of_three.impl;

import kata.game_of_three.PlayerIdentifier;
import kata.game_of_three.PlayerIdentifierVisitor;

import java.util.Objects;

public class RemotePlayerIdentifier implements PlayerIdentifier {

    private String host;
    private Integer port;

    @SuppressWarnings("unused") private RemotePlayerIdentifier() {}

    public RemotePlayerIdentifier(String host, Integer port) {
	this.host = host;
	this.port = port;
    }

    public String getHost() {
	return host;
    }

    public Integer getPort() {
	return port;
    }

    @Override public String getId() {
	return null;
    }

    @Override public <T> T accept(PlayerIdentifierVisitor<T> playerIdentifierVisitor) {
	return playerIdentifierVisitor.visit(this);
    }

    @Override public String toString() {
	return "RemotePlayerIdentifier{" +
			"host='" + host + '\'' +
			", port=" + port +
			'}';
    }

    @Override public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (!(o instanceof RemotePlayerIdentifier))
	    return false;
	RemotePlayerIdentifier that = (RemotePlayerIdentifier) o;
	return Objects.equals(host, that.host) &&
			Objects.equals(port, that.port);
    }

    @Override public int hashCode() {
	return Objects.hash(host, port);
    }
}
