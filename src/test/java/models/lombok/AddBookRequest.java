package models.lombok;

import lombok.Data;

import java.util.ArrayList;
@Data
public class AddBookRequest {
    public String userId;
    public ArrayList<AddBookRequest> collectionOfIsbns;
}
