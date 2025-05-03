package im.fooding.core.global.exception;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailedError {
  private String location;
  private String message;
}
