package models.lombok;

import lombok.Data;

import java.util.List;

@Data
public class AddBookResponse {
    List<AddBookResponse> books;
}