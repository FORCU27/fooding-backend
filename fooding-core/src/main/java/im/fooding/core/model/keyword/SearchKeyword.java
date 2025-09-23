package im.fooding.core.model.keyword;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchKeyword {
    private String keyword;

    private Integer count;

    public SearchKeyword(String keyword) {
        this.keyword = keyword;
        this.count = 1;
    }

    public void increaseCount() {
        this.count = (this.count == null ? 1 : this.count + 1);
    }

    @JsonIgnore
    public String getIndex() {
        return "search_keywords_v1";
    }
}
