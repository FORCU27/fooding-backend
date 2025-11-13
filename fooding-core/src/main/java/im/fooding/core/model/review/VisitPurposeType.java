package im.fooding.core.model.review;

import lombok.Getter;

@Getter
public enum VisitPurposeType {

    MEETING("미팅"),
    DATE("데이트"),
    FRIEND("친목"),
    FAMILY("가족 식사"),
    BUSINESS("비즈니스"),
    DUMMY("더미"),
    PARTY("파티");

    private final String label;

    VisitPurposeType(String label) {
        this.label = label;
    }
}
