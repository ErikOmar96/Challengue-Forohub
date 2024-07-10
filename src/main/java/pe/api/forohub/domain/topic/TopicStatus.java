package pe.api.forohub.domain.topic;

import lombok.Getter;

@Getter
public enum TopicStatus {
    RESOLVED("resolved"),
    DELETED("deleted"),
    PENDING("pending");

    private final String value;

    TopicStatus(String value) {
        this.value = value;
    }

    public static TopicStatus fromString(String text) {
        for (TopicStatus status : TopicStatus.values()) {
            if (status.value.equalsIgnoreCase(text)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }

    public boolean equalsIgnoreCase(String other) {
        return this.value.equalsIgnoreCase(other);
    }

    public boolean equalsIgnoreCase(TopicStatus other) {
        return this.value.equalsIgnoreCase(other.value);
    }
}
