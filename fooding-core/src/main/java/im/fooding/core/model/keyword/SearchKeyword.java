package im.fooding.core.model.keyword;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchKeyword {
    private String keyword;
    private String initials;
    private Integer count;

    public SearchKeyword(String keyword) {
        this.keyword = keyword;
        this.initials = toInitials(keyword);
        this.count = 1;
    }

    public void increaseCount() {
        this.count = (this.count == null ? 1 : this.count + 1);
    }

    @JsonIgnore
    public String getIndex() {
        return "search_keywords_v1";
    }

    private String toInitials(String text) {
        String initials = "ㄱㄲㄴㄷㄸㄹㅁㅂㅃㅅㅆㅇㅈㅉㅊㅋㅌㅍㅎ";
        StringBuilder result = new StringBuilder();

        for (char ch : text.toCharArray()) {
            if (ch >= 0xAC00 && ch <= 0xD7A3) {
                // 한글 초성 추출
                int base = ch - 0xAC00;
                int index = base / (21 * 28);
                result.append(initials.charAt(index));
            } else {
                // 영어/숫자는 그대로 저장
                result.append(ch);
            }
        }
        return result.toString().toLowerCase();
    }
}
