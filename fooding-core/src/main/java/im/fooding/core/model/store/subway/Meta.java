package im.fooding.core.model.store.subway;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Meta {
    private int total_count;
    private int pageable_count;
    private boolean is_end;
    private Object same_name;
}