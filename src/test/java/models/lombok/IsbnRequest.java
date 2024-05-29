package models.lombok;

import lombok.Data;

@Data
public class IsbnRequest extends AddBookRequest {
    public String isbn;
}
