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

    @Column(name = "total", nullable = false)
    private float total;

    @Column(name = "taste", nullable = false)
    private float taste;

    @Column(name = "mood", nullable = false)
    private float mood;

    @Column(name = "service", nullable = false)
    private float service;

    @Builder
    private ReviewScore(float total, float taste, float mood, float service) {
        this.total = total;
        this.taste = taste;
        this.mood = mood;
        this.service = service;
    }
}