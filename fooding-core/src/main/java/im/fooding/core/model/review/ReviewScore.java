package im.fooding.core.model.review;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class ReviewScore {

    private float total;

    private float taste;

    private float mood;

    private float service;

    @Builder
    private ReviewScore(float total, float taste, float mood, float service) {
        this.total = total;
        this.taste = taste;
        this.mood = mood;
        this.service = service;
    }
}