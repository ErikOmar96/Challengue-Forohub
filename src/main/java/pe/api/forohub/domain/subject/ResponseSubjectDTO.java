package pe.api.forohub.domain.subject;


public record ResponseSubjectDTO(
    Long id,
    String name,
    String category
) {

    public ResponseSubjectDTO(Subject subject){
        this(subject.getId(), subject.getName(), subject.getCategory());
    }
}
