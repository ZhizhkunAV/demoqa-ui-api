package models.lombok;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AddBookRequest {
    String userId;
    List<AddBookRequest> collectionOfIsbns;
}
